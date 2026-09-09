package foodfinder.dto;

import java.math.BigDecimal;



public record PennyOfferTile(String title, String quantity, String price, String uuid ) {

	public BigDecimal priceAsBigDecimal() {
		BigDecimal newPrice = new BigDecimal(price);
		return newPrice;
	}
	
	
}
