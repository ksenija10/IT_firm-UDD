package com.itfirm.udd.controller;

import com.itfirm.udd.dto.SearchFormRequest;
import com.itfirm.udd.model.elasticsearch.ApplicantIndexUnit;
import com.itfirm.udd.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("/advanced")
    public ResponseEntity<SearchHits<ApplicantIndexUnit>> advancedSearch(@Valid @RequestBody SearchFormRequest searchFormRequest){
        SearchHits<ApplicantIndexUnit> searchHits = searchService.advancedSearch(searchFormRequest);

        return new ResponseEntity<>(searchHits, HttpStatus.OK);
    }

    @PostMapping("/simple")
    public ResponseEntity<SearchHits<ApplicantIndexUnit>> simpleSearch(
            @NotNull @RequestParam(name = "query") String query){
        SearchHits<ApplicantIndexUnit> searchHits = searchService.simpleSearch(query);

        return new ResponseEntity<>(searchHits, HttpStatus.OK);
    }

}
