package integration.mocks.backends;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.mockito.Mockito;

import com.github.psbds.backends.stock.StockAPIClient;

import io.quarkiverse.cucumber.ScenarioScope;
import io.quarkus.test.junit.QuarkusMock;
import lombok.Getter;

@ScenarioScope
public class BackendMocks {

    @Getter
    private StockAPIClient stockAPIClientMock;

    public BackendMocks() {
        stockAPIClientMock = Mockito.mock(StockAPIClient.class);
        QuarkusMock.installMockForType(stockAPIClientMock, StockAPIClient.class, RestClient.LITERAL);
    }
}
