package mk.ukim.finki.dians.api.web;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.dians.api.rest.SearchResponse;
import mk.ukim.finki.dians.api.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path = "/api/search")
@RestController
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/{query}")
    public SearchResponse searchBusData(@PathVariable String query) {
        return searchService.searchBusData(query);
    }

}
