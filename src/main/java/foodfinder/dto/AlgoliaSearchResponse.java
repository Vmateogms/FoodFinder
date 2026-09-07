package foodfinder.dto;

import java.util.List;

public record AlgoliaSearchResponse(List<AlgoliaResult> results) {

}
