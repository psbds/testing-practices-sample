#!/bin/bash

# Install SQL Server command-line tools
echo "Installing SQL Server command-line tools..."
curl https://packages.microsoft.com/keys/microsoft.asc | apt-key add -
curl https://packages.microsoft.com/config/ubuntu/20.04/prod.list > /etc/apt/sources.list.d/mssql-release.list
apt-get update
ACCEPT_EULA=Y apt-get install -y mssql-tools18 unixodbc-dev

# Start SQL Server in the background
/opt/mssql/bin/sqlservr &

# Wait for SQL Server to start up
echo "Waiting for SQL Server to start..."
for i in {1..60}; do
    if /opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P "$MSSQL_SA_PASSWORD" -C -Q "SELECT 1" &>/dev/null; then
        echo "SQL Server started successfully!"
        break
    fi
    echo "Waiting for SQL Server... ($i/60)"
    sleep 2
done

# Run initialization script if it exists
if [ -f "/scripts/init-db.sql" ]; then
    echo "Running database initialization script..."
    /opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P "$MSSQL_SA_PASSWORD" -C -i /scripts/init-db.sql
    if [ $? -eq 0 ]; then
        echo "Database initialization completed successfully!"
    else
        echo "Database initialization failed!"
    fi
else
    echo "No initialization script found."
fi

# Keep the container running
wait