package com.itfirm.udd.service;

import com.itfirm.udd.dto.FormFieldRequest;
import com.itfirm.udd.dto.SearchFormRequest;
import com.itfirm.udd.dto.enums.LogicalOperator;
import com.itfirm.udd.model.elasticsearch.ApplicantIndexUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Service
public class SearchService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public SearchHits<ApplicantIndexUnit> advancedSearch(SearchFormRequest searchFormRequest) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        for(FormFieldRequest formFieldRequest: searchFormRequest.getFormFieldRequestList()){
            if(formFieldRequest.getOperator().equals(LogicalOperator.AND)) {
                if(formFieldRequest.isPhrase()){
                    boolQuery.must(QueryBuilders.matchPhraseQuery(formFieldRequest.getName(), formFieldRequest.getValue()));
                } else {
                    boolQuery.must(QueryBuilders.matchQuery(formFieldRequest.getName(), formFieldRequest.getValue()));
                }
            } else {
                if(formFieldRequest.isPhrase()) {
                    boolQuery.must(QueryBuilders.matchPhraseQuery(formFieldRequest.getName(), formFieldRequest.getValue()));
                } else {
                    boolQuery.should(QueryBuilders.matchQuery(formFieldRequest.getName(), formFieldRequest.getValue()));
                }
            }
        }

        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .highlighterType("plain")
                .field("content");

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withHighlightBuilder(highlightBuilder)
                .build();

        SearchHits<ApplicantIndexUnit> searchHits = elasticsearchRestTemplate.search(searchQuery,
                ApplicantIndexUnit.class,
                IndexCoordinates.of("applicants"));

        return searchHits;
    }

    public SearchHits<ApplicantIndexUnit> simpleSearch(String query) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(query)
                        .field("name")
                        .field("surname")
                        .field("content")
                        .type(MultiMatchQueryBuilder.Type.BEST_FIELDS))
                .withHighlightFields(
                        new HighlightBuilder.Field("content").fragmentSize(50).numOfFragments(1))
                .build();

        SearchHits<ApplicantIndexUnit> searchHits = elasticsearchRestTemplate.search(searchQuery,
                ApplicantIndexUnit.class,
                IndexCoordinates.of("applicants"));

        return searchHits;
    }
}
