package foodfinder.fetcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import foodfinder.dto.AlgoliaHit;
import foodfinder.dto.AlgoliaResult;
import foodfinder.dto.AlgoliaSearchResponse;
import foodfinder.dto.RawProduct;

@Component
public class AldiFetcher implements RetailFetcher{
	
	private final RestClient restClient = RestClient.builder()
			.baseUrl("https://R971XDJHE1-dsn.algolia.net")
			.defaultHeader("x-algolia-api-key", "094afedaf99de404c0d1a62a9cf992b6")
			.defaultHeader("x-algolia-application-id", "R971XDJHE1")
			.build();

	@Override
	public List<RawProduct> fetchProducts() {
		
		
		Map<String, Object> requestBody = Map.of(
				"requests", List.of(
						Map.of(
								"indexName", "an_prd_de_de_products2",
								"params", "query=eier&hitsPerPage=1000"
								)
						)
				);
		
		AlgoliaSearchResponse response = restClient.post()
				.uri("/1/indexes/*/queries")
				.body(requestBody)
				.retrieve()
				.body(AlgoliaSearchResponse.class);
		
		List<RawProduct> rawProducts = new ArrayList<>();
		for (AlgoliaResult result : response.results()) {
			for(AlgoliaHit hit : result.hits()) {
				rawProducts.add(new RawProduct(
					hit.name(),
					hit.currentPrice().priceValue(),
					"Aldi",
					hit.brandName(),
					hit.salesUnit(),
					hit.objectId()
					));
			}
		}
		return rawProducts;
	}

}
