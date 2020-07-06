package fr.sasvb.labuvette.resolver.mutation;

import fr.sasvb.labuvette.model.Product;
import fr.sasvb.labuvette.service.ProductService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateProduct implements GraphQLMutationResolver {
    private final ProductService productService;

    @Autowired
    public CreateProduct(ProductService productService) {
        this.productService = productService;
    }

    public Product createProduct(String name, UUID categoryId) {
        return productService.createProduct(name, categoryId);
    }
}
