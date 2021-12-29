package com.itfirm.udd.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JobApplicationResponse {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private String address;

    private String education;

    private String cvPath;

    private String letterPath;
}
