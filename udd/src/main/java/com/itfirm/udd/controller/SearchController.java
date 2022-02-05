package com.itfirm.udd.controller;

import com.itfirm.udd.dto.GeoLocationRequest;
import com.itfirm.udd.dto.SearchFormRequest;
import com.itfirm.udd.dto.SearchPageResponse;
import com.itfirm.udd.dto.SearchResponse;
import com.itfirm.udd.model.elasticsearch.ApplicantIndexUnit;
import com.itfirm.udd.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/simple")
    public ResponseEntity<SearchPageResponse> simpleSearch(
            @NotNull @RequestParam(name = "query") String query, Pageable pageable){
        SearchPageResponse searchResponses = searchService.simpleSearch(query, pageable);

        return new ResponseEntity<>(searchResponses, HttpStatus.OK);
    }

    @PostMapping("/advanced")
    public ResponseEntity<SearchPageResponse> advancedSearch(@Valid @RequestBody SearchFormRequest searchFormRequest,
                                                             Pageable pageable){
        SearchPageResponse searchResponses = searchService.advancedSearch(searchFormRequest, pageable);

        return new ResponseEntity<>(searchResponses, HttpStatus.OK);
    }

    @PostMapping("/location")
    public ResponseEntity<SearchPageResponse> geoLocationSearch(@Valid @RequestBody GeoLocationRequest geoLocationRequest,
                                                                  Pageable pageable) {
        SearchPageResponse searchResponses = searchService.geoLocationSearch(geoLocationRequest, pageable);

        return new ResponseEntity<>(searchResponses, HttpStatus.OK);
    }

}
