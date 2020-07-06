package fr.sasvb.labuvette.repository;

import fr.sasvb.labuvette.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
