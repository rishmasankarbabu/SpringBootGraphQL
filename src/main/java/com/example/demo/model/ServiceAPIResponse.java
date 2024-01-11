package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceAPIResponse {

    @JsonProperty("communicationStatus")
    private String communicationStatus;

    @JsonProperty("services")
    List<ServiceDTO> services;

}
