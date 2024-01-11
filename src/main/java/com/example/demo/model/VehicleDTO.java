package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehicleDTO {


    private VehicleParentDTO vehicleParentDTO;

    @JsonProperty("msidn")
    private String msidn;

    @JsonProperty("engineStatus")
    private String engineStatus;

    @JsonProperty("fleet")
    private String fleet;

    @JsonProperty("brand")
    private String brand;


    @JsonProperty("countryOfOperation")
    private String countryOfOperation;

    @JsonProperty("chassisNumber")
    private String chassisNumber;

    @JsonProperty("cassisSeries")
    private String chassisSeries;


    public void setVehicleParentDTO(VehicleParentDTO vehicleParentDTO) {
        this.vehicleParentDTO = vehicleParentDTO;
    }


}
