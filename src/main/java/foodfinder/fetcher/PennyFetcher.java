package foodfinder.fetcher;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import foodfinder.dto.PennyOfferTile;
import foodfinder.dto.PennyOfferTiles;
import foodfinder.dto.RawProduct;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;

@Component
public class PennyFetcher implements RetailFetcher {

	
	private final RestClient restClient = RestClient.builder()
			.baseUrl("https://www.penny.de/.rest/offers/by-category")
			.build();
	
	@Override
	public List<RawProduct> fetchProducts(String searchTerm){
		
		String term = searchTerm.toLowerCase();
		
		
		String valueCategory = "obst-und-gemuese";
		String valueRegion = "15A-01-56";
		PennyOfferTiles response = restClient.get()
				.uri("/{week}/{category}?region={region}", calculateCurrentWeek(), valueCategory, valueRegion)
				.retrieve()
				.body(PennyOfferTiles.class);
		
		List<PennyOfferTile> tiles = response.offerTiles();
		List<RawProduct> rawProduct = new ArrayList<>();
		for(PennyOfferTile tile : tiles) {
			if(tile.title().toLowerCase().contains(term)) {
				rawProduct.add(new RawProduct(
						tile.title(),
						tile.priceAsBigDecimal(),
						"Penny",
						null,
						tile.quantity(),
						tile.uuid()
						));
			}
			
		}
				
		
		return rawProduct;
		
		
	}
	
	
	
	public String calculateCurrentWeek() {
		WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);
		TemporalField weekOfWeekBasedYear = weekFields.weekOfWeekBasedYear();
		LocalDate day = LocalDate.now();
		int yearResult = day.get(weekFields.weekBasedYear());
		int result = day.get(weekOfWeekBasedYear);
		String valueWeek = yearResult + "-" + result;
		return valueWeek;
	}
	
	
}
