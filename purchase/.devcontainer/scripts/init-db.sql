IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'develop')
BEGIN
    CREATE DATABASE [develop];
END