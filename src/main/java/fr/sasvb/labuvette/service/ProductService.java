package fr.sasvb.labuvette.service;

import fr.sasvb.labuvette.exception.CategoryNotFoundException;
import fr.sasvb.labuvette.model.Product;
import fr.sasvb.labuvette.model.ProductCategory;
import fr.sasvb.labuvette.repository.ProductCategoryRepository;
import fr.sasvb.labuvette.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final ProductCategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductCategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product createProduct(String name, UUID productCategoryId) {
        ProductCategory category = categoryRepository.
                findById(productCategoryId).
                orElseThrow(() -> new CategoryNotFoundException("Unable to find a ProductCategory with the provided UUID.", "id"));

        Product product = new Product(name, category);

        productRepository.save(product);

        return product;
    }
}
