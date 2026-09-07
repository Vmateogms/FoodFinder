package foodfinder.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import foodfinder.entity.Retailer;

@Repository
public interface RetailerRepository extends JpaRepository<Retailer, Integer> {

	Optional<Retailer> findByNameIgnoreCase(String name);
	
}