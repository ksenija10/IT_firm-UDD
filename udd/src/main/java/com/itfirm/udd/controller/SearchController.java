package com.itfirm.udd.controller;

import com.itfirm.udd.dto.SearchFormRequest;
import com.itfirm.udd.model.elasticsearch.ApplicantIndexUnit;
import com.itfirm.udd.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping
    public ResponseEntity<SearchHits<ApplicantIndexUnit>> search(@Valid @RequestBody SearchFormRequest searchFormRequest){
        SearchHits<ApplicantIndexUnit> searchHits = searchService.search(searchFormRequest);

        return new ResponseEntity<>(searchHits, HttpStatus.OK);
    }

}
