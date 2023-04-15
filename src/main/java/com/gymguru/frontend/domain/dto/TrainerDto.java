package com.gymguru.frontend.domain.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gymguru.frontend.domain.Specialization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrainerDto {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("description")
    private String description;
    @JsonProperty("education")
    private String education;
    @JsonProperty("monthPrice")
    private BigDecimal monthPrice;
    @JsonProperty("specialization")
    private Specialization specialization;
    @JsonProperty("credentialId")
    private Long credentialId;
}
