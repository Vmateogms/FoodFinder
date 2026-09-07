package foodfinder.dto;

import java.math.BigDecimal;



public record RawProduct(String name, BigDecimal price, String retailerName, String brand, String unit, String retailerOriginalId) {

}
