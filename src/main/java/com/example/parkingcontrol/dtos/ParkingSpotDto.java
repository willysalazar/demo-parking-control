package com.example.parkingcontrol.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ParkingSpotDto {
    @NotBlank
    private String parkingSpotNumber;
    @NotBlank
    @Size(max = 7, message = "Máximo 7 caracteres para placa")
    private String licensePlateCar;
    @NotBlank
    private String brandCar;
    @NotBlank
    private String modelCar;
    @NotBlank
    private String colorCar;
    @NotBlank
    private String responsibleName;
    @NotBlank
    private String apartment;
    @NotBlank
    private String block;
}
