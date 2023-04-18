package com.gymguru.frontend.domain.edit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EditPlan {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("dietDescription")
    private String dietDescription;
    @JsonProperty("exerciseDescription")
    private String exerciseDescription;
    @JsonProperty("userId")
    private Long userId;
    @JsonProperty("trainerId")
    private Long trainerId;
}
