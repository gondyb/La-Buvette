package fr.sasvb.labuvette.resolver.query;

import fr.sasvb.labuvette.model.Product;
import fr.sasvb.labuvette.repository.ProductRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FindAllProducts implements GraphQLQueryResolver {
    private final ProductRepository productRepository;

    @Autowired
    public FindAllProducts(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Iterable<Product> findAllProducts() {
        return productRepository.findAll();
    }
}
