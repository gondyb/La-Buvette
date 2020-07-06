package fr.sasvb.labuvette.resolver.query;

import fr.sasvb.labuvette.model.ProductCategory;
import fr.sasvb.labuvette.repository.ProductCategoryRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

@Component
public class FindAllProductCategories implements GraphQLQueryResolver {
    private final ProductCategoryRepository productCategoryRepository;

    @Autowired
    public FindAllProductCategories(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public Iterable<ProductCategory> findAllProductCategories() {
        return productCategoryRepository.findAll();
    }
}
