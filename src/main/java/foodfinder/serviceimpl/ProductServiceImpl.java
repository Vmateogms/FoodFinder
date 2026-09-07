package foodfinder.serviceimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodfinder.dto.PriceComparisonDTO;
import foodfinder.entity.PriceRecord;
import foodfinder.repository.PriceRecordRepository;
import foodfinder.repository.ProductRepository;

@Service
public class ProductServiceImpl {
	
	@Autowired
	PriceRecordRepository prRepo;
	
	
	public List<PriceComparisonDTO> comparePrices(String name){
		
		List<PriceRecord> records =  prRepo.findByProduct_NameContainingIgnoreCase(name);
		
		List<PriceComparisonDTO> comparisons  = new ArrayList<>();
		for(PriceRecord record : records) {
			String retailerName = record.getRetailer().getName();
			BigDecimal price = record.getAmount();
			
			PriceComparisonDTO dto = new PriceComparisonDTO(retailerName, price);
			comparisons.add(dto);
		}
		
		comparisons.sort(Comparator.comparing(PriceComparisonDTO::price));
		
		return comparisons;
		
	}
	

}
