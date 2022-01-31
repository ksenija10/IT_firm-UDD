package com.itfirm.udd.model.elasticsearch;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Document(indexName = "applicants")
@Getter
@Setter
public class ApplicantIndexUnit {

    @Id
    @Field(type = FieldType.Keyword, index = false, store = true)
    private String id;

    @Field(type = FieldType.Text, analyzer = "serbian", store = true)
    private String name;

    @Field(type = FieldType.Text, analyzer = "serbian", store = true)
    private String surname;

    @Field(type = FieldType.Integer, store = true)
    private Integer educationLevel;

    @Field(type = FieldType.Text, analyzer = "serbian")
    private String content;
}
