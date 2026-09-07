package foodfinder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import foodfinder.dto.RawProduct;
import foodfinder.fetcher.RetailFetcher;
import foodfinder.serviceimpl.ProductImportServiceImpl;

@RestController
public class ImportController {

	@Autowired
	RetailFetcher aldiFetcher;
	
	@Autowired
	ProductImportServiceImpl piService;
	
	@PostMapping("/import/aldi")
	public ResponseEntity<String> importFromAldi(){
		
		List<RawProduct> rawProducts = aldiFetcher.fetchProducts();
		piService.importProducts(rawProducts);
		return ResponseEntity.ok("Success");
	}
	
}
