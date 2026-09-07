package foodfinder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import foodfinder.entity.PriceRecord;

@Repository
public interface PriceRecordRepository extends JpaRepository<PriceRecord, Integer> {

	List<PriceRecord> findByProduct_NameContainingIgnoreCase(String name);
	
}
