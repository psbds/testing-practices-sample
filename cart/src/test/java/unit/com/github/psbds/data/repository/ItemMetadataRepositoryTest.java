package unit.com.github.psbds.data.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.github.psbds.data.repository.ItemMetadataRepository;
import com.github.psbds.data.repository.dao.ItemMetadataRepositoryDAO;
import com.github.psbds.domain.item.ItemMetadata;

import io.quarkus.test.InjectMock;
import io.quarkus.test.component.QuarkusComponentTest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@QuarkusComponentTest
class ItemMetadataRepositoryTest {

    @Inject
    ItemMetadataRepository repository;

    @InjectMock
    ItemMetadataRepositoryDAO baseRepository;

    @Test
    void persist_when_entitiesProvided_should_delegateToBaseRepository() {
        // Arrange
        ItemMetadata metadata1 = mock(ItemMetadata.class);
        ItemMetadata metadata2 = mock(ItemMetadata.class);
        List<ItemMetadata> entities = Arrays.asList(metadata1, metadata2);

        // Act
        repository.persist(entities);

        // Assert
        verify(baseRepository).persist(entities);
    }

    @Test
    void testRepositoryHasApplicationScopedAnnotation() {
        // Arrange
        Class<ItemMetadataRepository> repositoryClass = ItemMetadataRepository.class;

        // Act
        ApplicationScoped annotation = repositoryClass.getAnnotation(ApplicationScoped.class);

        // Assert
        assertNotNull(annotation, 
            "ItemMetadataRepository should have @ApplicationScoped annotation");
    }
}