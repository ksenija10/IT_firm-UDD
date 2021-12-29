package com.itfirm.udd.repository.elasticsearch;

import com.itfirm.udd.model.elasticsearch.ApplicantIndexUnit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ApplicantIndexUnitRepository extends ElasticsearchRepository<ApplicantIndexUnit, String> {
}
