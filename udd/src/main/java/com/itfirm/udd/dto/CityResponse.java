package com.itfirm.udd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CityResponse {

    public AddressResponse address;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class AddressResponse {
    public String City;
}
