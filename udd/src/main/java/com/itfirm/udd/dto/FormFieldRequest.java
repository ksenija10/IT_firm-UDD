package com.itfirm.udd.dto;

import com.itfirm.udd.dto.enums.LogicalOperator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormFieldRequest {

    @NotNull
    private String name;

    @NotNull
    private String value;

    @NotNull
    private LogicalOperator operator;

    @NotNull
    private boolean phrase;
}
