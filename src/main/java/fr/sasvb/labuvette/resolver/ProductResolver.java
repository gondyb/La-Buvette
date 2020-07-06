package fr.sasvb.labuvette.resolver;

import fr.sasvb.labuvette.model.Product;
import fr.sasvb.labuvette.model.ProductCategory;
import fr.sasvb.labuvette.repository.ProductCategoryRepository;
import fr.sasvb.labuvette.repository.ProductRepository;
import graphql.kickstart.tools.GraphQLResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductResolver implements GraphQLResolver<Product> {
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductResolver(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public ProductCategory getProductCategory(Product product) {
        return productCategoryRepository.findById(product.getCategory().getId()).orElseThrow(null);
    }
}
