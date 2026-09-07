package foodfinder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import foodfinder.dto.PriceComparisonDTO;
import foodfinder.serviceimpl.ProductServiceImpl;

@RestController
public class ProductController {
	
	@Autowired
	ProductServiceImpl pService;
	
	@GetMapping("products/search")
	public ResponseEntity<List<PriceComparisonDTO>> comparePricesByProductName(@RequestParam String name ) {
		return ResponseEntity.ok(pService.comparePrices(name));
	}

}
