package com.itfirm.udd.service;

import com.itfirm.udd.dto.*;
import com.itfirm.udd.dto.enums.LogicalOperator;
import com.itfirm.udd.exceptions.GeocodeException;
import com.itfirm.udd.model.Location;
import com.itfirm.udd.model.elasticsearch.ApplicantIndexUnit;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Service
public class SearchService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private LocationService locationService;

    public SearchPageResponse simpleSearch(String query, Pageable pageable) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(query)
                        .field("name")
                        .field("surname")
                        .field("content")
                        .type(MultiMatchQueryBuilder.Type.BEST_FIELDS))
                .withPageable(pageable)
                .withHighlightFields(
                        new HighlightBuilder.Field("content").fragmentSize(50).numOfFragments(1)
                                .preTags("<b>").postTags("</b>"))
                .build();

        SearchHits<ApplicantIndexUnit> searchHits = elasticsearchRestTemplate.search(searchQuery,
                ApplicantIndexUnit.class,
                IndexCoordinates.of("applicants"));

        return searchHitToSearchResponse(searchHits);
    }

    public SearchPageResponse advancedSearch(SearchFormRequest searchFormRequest, Pageable pageable) {
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
                    boolQuery.should(QueryBuilders.matchPhraseQuery(formFieldRequest.getName(), formFieldRequest.getValue()));
                } else {
                    boolQuery.should(QueryBuilders.matchQuery(formFieldRequest.getName(), formFieldRequest.getValue()));
                }
            }
        }

        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .highlighterType("plain")
                .field("content")
                .preTags("<b>")
                .postTags("</b>");

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withHighlightBuilder(highlightBuilder)
                .withPageable(pageable)
                .build();

        SearchHits<ApplicantIndexUnit> searchHits = elasticsearchRestTemplate.search(searchQuery,
                ApplicantIndexUnit.class,
                IndexCoordinates.of("applicants"));

        return searchHitToSearchResponse(searchHits);
    }

    public SearchPageResponse geoLocationSearch(GeoLocationRequest geoLocationRequest, Pageable pageable) throws GeocodeException {
        Location location = locationService.getLocationFromAddress(geoLocationRequest.getCity());

        GeoDistanceQueryBuilder geoDistanceBuilder = new GeoDistanceQueryBuilder("location")
                .point(location.getLat(), location.getLon())
                .distance(geoLocationRequest.getRadius(),
                        DistanceUnit.parseUnit(geoLocationRequest.getMeasureUnit(), DistanceUnit.KILOMETERS));

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFilter(geoDistanceBuilder)
                .withPageable(pageable)
                .build();

        SearchHits<ApplicantIndexUnit> searchHits =
                elasticsearchRestTemplate.search(searchQuery, ApplicantIndexUnit.class, IndexCoordinates.of("applicants"));

        return searchHitToSearchResponse(searchHits);
    }

    private SearchPageResponse searchHitToSearchResponse(SearchHits<ApplicantIndexUnit> searchHits) {
        List<SearchResponse> searchResponses = new ArrayList<>();

        for(SearchHit<ApplicantIndexUnit> searchHit : searchHits) {
            SearchResponse searchResponse = new SearchResponse();

            searchResponse.setName(searchHit.getContent().getName());
            searchResponse.setSurname(searchHit.getContent().getSurname());
            searchResponse.setEmail(searchHit.getContent().getEmail());
            searchResponse.setEducation(searchHit.getContent().getEducationName());
            searchResponse.setAddress(searchHit.getContent().getAddress());
            searchResponse.setCvId(searchHit.getContent().getCvId());
            if (searchHit.getHighlightFields().isEmpty()) {
                searchResponse.setHighlight(searchHit.getContent().getContent().substring(0, 200) + "...");
            } else {
                searchResponse.setHighlight("..." + searchHit.getHighlightFields().get("content").get(0) + "...");
            }

            searchResponses.add(searchResponse);
        }

        return new SearchPageResponse(searchResponses, searchHits.getTotalHits());
    }
}
