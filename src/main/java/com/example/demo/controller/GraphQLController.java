package com.example.demo.controller;

import com.example.demo.model.Service;
import com.example.demo.model.Vehicle;
import com.example.demo.repository.ServiceRepository;
import com.example.demo.repository.VehicleRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class GraphQLController {

    private final VehicleRepository vehicleRepository;
    private final ServiceRepository serviceRepository;

    public GraphQLController(VehicleRepository vehicleRepository, ServiceRepository serviceRepository) {
        this.vehicleRepository = vehicleRepository;
        this.serviceRepository = serviceRepository;
    }

    @QueryMapping
    Iterable<Vehicle> getAllVehicles(){
       return vehicleRepository.findAll();
    }

    @QueryMapping
    Optional<Vehicle> getVehicleById(@Argument String id){
        return vehicleRepository.findById(id);
    }

    @QueryMapping
    Iterable<Vehicle> getVehiclesByName(@Argument String name){
        return vehicleRepository.findVehiclesByNameContaining(name);
    }

    @QueryMapping
    List<Optional<Vehicle>> getVehiclesByService(@Argument String name, @Argument String status){

           List<Optional<Vehicle>> vehicleList = new ArrayList<>();
           List<Service> servicesList = serviceRepository.findSerVicesByNameAndStatus(name,status);
            servicesList.forEach(service ->
                    vehicleList.add(vehicleRepository.findById(service.getVehicleId())) );
            return vehicleList;
    }


}
