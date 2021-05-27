package com.example.practical.json;

import com.example.practical.validator.IdNumberConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientJson {

    private Long id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    private String mobileNumber;

    @NotEmpty
    @IdNumberConstraint
    private String idNumber;

    private String physicalAddress;

}
