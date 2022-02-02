package com.itfirm.udd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeoLocationRequest {

    @NotNull
    private String City;

    @NotNull
    private int Radius;

    @NotNull
    private String MeasureUnit;

}
