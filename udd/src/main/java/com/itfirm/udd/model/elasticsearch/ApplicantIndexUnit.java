package com.itfirm.udd.model.elasticsearch;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

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

    @GeoPointField
    private GeoPoint location;

    @Field(type = FieldType.Text, analyzer = "serbian")
    private String content;

    //stored just be to presented to the user
    @Field(type = FieldType.Text, index = false)
    private String email;

    @Field(type = FieldType.Text, index = false)
    private String address;

    @Field(type = FieldType.Text, index = false)
    private String educationName;

    @Field(type = FieldType.Keyword, index = false)
    private Long cvId;
}
