package foodfinder.fetcher;

import java.util.List;

import foodfinder.dto.RawProduct;

public interface RetailFetcher {
	List<RawProduct> fetchProducts(String searchTerm);
}
