package com.itfirm.udd.controller;

import com.itfirm.udd.dto.GeoLocationRequest;
import com.itfirm.udd.dto.SearchFormRequest;
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

    @PostMapping("/simple")
    public ResponseEntity<List<SearchResponse>> simpleSearch(
            @NotNull @RequestParam(name = "query") String query, Pageable pageable){
        List<SearchResponse> searchResponses = searchService.simpleSearch(query, pageable);

        return new ResponseEntity<>(searchResponses, HttpStatus.OK);
    }

    @PostMapping("/advanced")
    public ResponseEntity<List<SearchResponse>> advancedSearch(@Valid @RequestBody SearchFormRequest searchFormRequest,
                                                               Pageable pageable){
        List<SearchResponse> searchResponses = searchService.advancedSearch(searchFormRequest, pageable);

        return new ResponseEntity<>(searchResponses, HttpStatus.OK);
    }

    @PostMapping("/location")
    public ResponseEntity<List<SearchResponse>> geoLocationSearch(@Valid @RequestBody GeoLocationRequest geoLocationRequest,
                                                                  Pageable pageable) {
        List<SearchResponse> searchResponses = searchService.geoLocationSearch(geoLocationRequest, pageable);

        return new ResponseEntity<>(searchResponses, HttpStatus.OK);
    }
}
