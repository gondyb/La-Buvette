package fr.sasvb.labuvette.resolver.mutation;

import fr.sasvb.labuvette.model.Product;
import fr.sasvb.labuvette.model.ProductCategory;
import fr.sasvb.labuvette.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CreateProductTest {

    @Mock
    ProductService productService;

    @InjectMocks
    CreateProduct createProductMutation;

    @Test
    public void it_should_call_the_product_service() {
        // Arrange
        UUID categoryId = UUID.fromString("126e8a92-bed5-48fa-bc31-244fbd17a06e");

        ProductCategory category = new ProductCategory(categoryId, "Boissons");

        Product product = new Product();
        product.setName("Ice Tea");
        product.setCategory(category);

        when(productService.createProduct("Ice Tea", categoryId)).thenReturn(product);

        // Act
        Product createdProduct = createProductMutation.createProduct("Ice Tea", categoryId);

        // Assert
        assertSame(product, createdProduct);
        verify(productService, times(1)).createProduct("Ice Tea", categoryId);
    }
}