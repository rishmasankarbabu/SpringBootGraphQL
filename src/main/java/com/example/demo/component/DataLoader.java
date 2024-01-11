package com.example.demo.component;

import com.example.demo.model.*;
import com.example.demo.repository.ServiceRepository;
import com.example.demo.repository.VehicleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final VehicleRepository vehicleRepository;

    private final ServiceRepository serviceRepository;
    private final RestTemplate restTemplate;

    public DataLoader(VehicleRepository vehicleRepository, ServiceRepository serviceRepository, RestTemplate restTemplate) {
        this.vehicleRepository = vehicleRepository;
        this.serviceRepository = serviceRepository;
        this.restTemplate = restTemplate;
    }


    @Override
    public void run(String... args) throws Exception {
        initializeData();
    }

    private void initializeData() {

        String apiUri = "http://localhost:1337/vehicle";
        ObjectMapper objectMapper = new ObjectMapper();

        try {
        String jsonResponse = restTemplate.getForObject(apiUri + "/list", String.class);
        VehicleAPIResponse vehicleAPIResponse= objectMapper.readValue(jsonResponse,VehicleAPIResponse.class);
        List<VehicleParentDTO> vehicleParentDTOList = vehicleAPIResponse.getVehicles();

            for (VehicleParentDTO entity : vehicleParentDTOList) {
                String vehicleId = entity.getId();
                try {
                    if (vehicleId != null && !vehicleId.isEmpty()) {

                        String response = restTemplate.getForObject(apiUri + "/info?id=" + vehicleId, String.class);
                        VehicleDTO vehicleDTO = objectMapper.readValue(response, VehicleDTO.class);

                        Vehicle vehicle = new Vehicle();
                        vehicle.setId(vehicleId);
                        vehicle.setName(entity.getName());
                        vehicle.setBrand(vehicleDTO.getBrand());
                        vehicle.setFleet(vehicleDTO.getFleet());
                        vehicle.setEngineStatus(vehicleDTO.getEngineStatus());
                        vehicle.setMsidn(vehicleDTO.getMsidn());
                        vehicle.setChassisNumber(vehicleDTO.getChassisNumber());
                        vehicle.setChassisSeries(vehicleDTO.getChassisNumber());
                        vehicle.setCountryOfOperation(vehicleDTO.getCountryOfOperation());
                        vehicleRepository.save(vehicle);

                        LOGGER.info("Fetch services for each vehicleID");
                        String serviceResponse = restTemplate.getForObject(apiUri + "/services?id=" + vehicleId,String.class);

                        ServiceAPIResponse serviceAPIResponse= objectMapper.readValue(serviceResponse,ServiceAPIResponse.class);
                        LOGGER.info("serviceAPIResponse: " +serviceAPIResponse.getServices());
                        LOGGER.info("serviceAPIResponse size: " +serviceAPIResponse.getServices().size());

                        List<ServiceDTO> servicesDTOList = serviceAPIResponse.getServices();
                        if(servicesDTOList !=null) {

                            servicesDTOList.stream().forEach(dto -> {
                                Service service = new Service();
                                service.setName(dto.getServiceName());
                                service.setStatus(dto.getStatus());
                                service.setReason(dto.getReason());
                                service.setLastUpdate(dto.getLastUpdate());
                                service.setVehicleId(vehicleId);
                                serviceRepository.save(service);
                            });
                        }

                    }
                } catch (HttpClientErrorException ex) {
                    LOGGER.error(ex.getMessage());
                } catch (JsonMappingException e) {
                    LOGGER.error(e.getMessage());
                } catch (JsonProcessingException e) {
                    LOGGER.error(e.getMessage());
                }
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

    }

}
