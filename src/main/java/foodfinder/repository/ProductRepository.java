package foodfinder.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import foodfinder.entity.PriceRecord;
import foodfinder.entity.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	Optional<Product> findByNameIgnoreCase(String name);
	
}
