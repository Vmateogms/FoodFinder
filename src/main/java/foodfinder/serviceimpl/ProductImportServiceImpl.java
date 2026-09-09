package foodfinder.serviceimpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import foodfinder.dto.RawProduct;
import foodfinder.entity.PriceRecord;
import foodfinder.entity.Product;
import foodfinder.entity.Retailer;
import foodfinder.repository.PriceRecordRepository;
import foodfinder.repository.ProductRepository;
import foodfinder.repository.RetailerRepository;
import jakarta.transaction.Transactional;

@Service
public class ProductImportServiceImpl {
	
	@Autowired
	ProductRepository pRepo;
	
	@Autowired
	RetailerRepository rRepo;
	
	@Autowired
	PriceRecordRepository prRepo;

	@Transactional
	public void importProducts(List<RawProduct> rawProducts) {
		
		for(RawProduct rawProduct : rawProducts) {
			
			Optional<Product> existingProduct = pRepo.findByNameIgnoreCase(rawProduct.name());
			
			Product product;
			if(existingProduct.isPresent()) {
				product = existingProduct.get();
			} else {
				product = new Product();
				product.setName(rawProduct.name());
				product.setBrand(rawProduct.brand());
				product.setUnit(rawProduct.unit());
				product = pRepo.save(product);
				
			}
			
			Optional<Retailer> existingRetailer = rRepo.findByNameIgnoreCase(rawProduct.retailerName());
			
			Retailer retailer;
			if(existingRetailer.isPresent()) {
				retailer = existingRetailer.get();
			} else {
				retailer = new Retailer();
				retailer.setName(rawProduct.retailerName());
				retailer = rRepo.save(retailer);
				
			}

			Optional<PriceRecord> existingPriceRecord = prRepo.findByProductAndRetailer(product, retailer);
			
			PriceRecord newPriceRecord;
			if(existingPriceRecord.isPresent()) {
			newPriceRecord = existingPriceRecord.get();
			}else {	
			newPriceRecord = new PriceRecord();
			newPriceRecord.setProduct(product);
			newPriceRecord.setRetailer(retailer);
			}
			newPriceRecord.setAmount(rawProduct.price());
			newPriceRecord.setLastRecord(LocalDate.now());
			prRepo.save(newPriceRecord);
		}
		
	}
	
}
