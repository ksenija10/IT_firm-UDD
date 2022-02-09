package com.itfirm.udd.service;

import com.itfirm.udd.dto.ReportResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.range.RangeAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public List<ReportResponse> getByTime() throws IOException {
        SearchRequest searchRequest = new SearchRequest("logstash-2022.02.09-000001");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        RangeAggregationBuilder aggregation = AggregationBuilders.range("timeAggregation")
                .field("hour")
                .addRange("early morning (6-9)", 6, 9)
                .addRange("work hours (9-17)", 9, 17)
                .addRange("evening (17-21)", 17, 21)
                .addRange("night (21-24)", 21, 24)
                .addRange("sleep (0-6)", 0, 6);

        searchSourceBuilder.aggregation(aggregation);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        Aggregations aggregations = searchResponse.getAggregations();
        Range timeAggregation = aggregations.get("timeAggregation");
        List<ReportResponse> response = new ArrayList<>();
        for (Range.Bucket bucket : timeAggregation.getBuckets()) {
            response.add(new ReportResponse(bucket.getKeyAsString(), bucket.getDocCount()));
        }
        return response;
    }

    public List<ReportResponse> getByCity() throws IOException {
        SearchRequest searchRequest = new SearchRequest("logstash-2022.02.09-000001");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        TermsAggregationBuilder aggregation = AggregationBuilders.terms("cityAggregation")
                .field("city.keyword");

        searchSourceBuilder.aggregation(aggregation);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        Aggregations aggregations = searchResponse.getAggregations();
        Terms cityAggregation = aggregations.get("cityAggregation");
        List<ReportResponse> response = new ArrayList<>();
        for (Terms.Bucket bucket : cityAggregation.getBuckets()) {
            response.add(new ReportResponse(bucket.getKeyAsString(), bucket.getDocCount()));
        }
        return response;
    }
}
