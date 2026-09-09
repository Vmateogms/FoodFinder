package foodfinder.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import foodfinder.entity.PriceRecord;
import foodfinder.entity.Product;
import foodfinder.entity.Retailer;

@Repository
public interface PriceRecordRepository extends JpaRepository<PriceRecord, Integer> {

	List<PriceRecord> findByProduct_NameContainingIgnoreCase(String name);
	Optional<PriceRecord> findByProductAndRetailer(Product product, Retailer retailer);
}
