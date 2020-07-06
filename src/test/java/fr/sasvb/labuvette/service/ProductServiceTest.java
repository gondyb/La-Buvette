package fr.sasvb.labuvette.service;

import fr.sasvb.labuvette.exception.CategoryNotFoundException;
import fr.sasvb.labuvette.model.Product;
import fr.sasvb.labuvette.model.ProductCategory;
import fr.sasvb.labuvette.repository.ProductCategoryRepository;
import fr.sasvb.labuvette.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {
    @Mock
    ProductCategoryRepository productCategoryRepository;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Test
    public void it_should_create_a_product_and_save_it() {
        // Arrange
        final UUID uuid = UUID.fromString("3ccb72fc-e313-4f96-a936-5b8df9b93ab4");
        ProductCategory category = new ProductCategory(
                uuid,
                "Boissons"
        );

        when(productCategoryRepository.findById(uuid))
                .thenReturn(Optional.of(category));

        // Act
        Product iceTea = productService.createProduct("Ice Tea", uuid);

        // Assert
        verify(productRepository, times(1)).save(iceTea);
        verify(productCategoryRepository, times(1)).findById(uuid);

        Assert.assertEquals("Ice Tea", iceTea.getName());
        Assert.assertEquals(uuid.toString(), iceTea.getCategory().getId().toString());
    }

    @Test(expected = CategoryNotFoundException.class)
    public void it_should_throw_if_category_not_found() {
        // Arrange
        final UUID uuid = UUID.fromString("3ccb72fc-e313-4f96-a936-5b8df9b93ab4");

        when(productCategoryRepository.findById(uuid))
                .thenReturn(Optional.empty());

        // Act
        productService.createProduct("Ice Tea", uuid);

        // Assert
        verifyNoMoreInteractions(productRepository);
        verify(productCategoryRepository, times(1)).findById(uuid);
    }
}