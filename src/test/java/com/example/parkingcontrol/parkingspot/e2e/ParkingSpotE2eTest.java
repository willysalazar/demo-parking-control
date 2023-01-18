package com.example.parkingcontrol.parkingspot.e2e;

import com.example.parkingcontrol.dtos.ParkingSpotDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ParkingSpotE2eTest {

    @Test
    void shouldBeSaveParkingSpot_Created(){
        ParkingSpotDto parkingSpotDto = new ParkingSpotDto();
        parkingSpotDto.setParkingSpotNumber("205B");
        parkingSpotDto.setLicensePlateCar("RRS8562");
        parkingSpotDto.setBrandCar("Audi");
        parkingSpotDto.setModelCar("Q5");
        parkingSpotDto.setColorCar("Black");
        parkingSpotDto.setResponsibleName("Willy Wonka");
        parkingSpotDto.setApartment("205");
        parkingSpotDto.setBlock("B");


        RestAssured
                .given()
                    .baseUri("http://localhost:8080/")
                    .basePath("parking-spot")
                    .body(parkingSpotDto)
                    .contentType(ContentType.JSON)
                .when()
                    .post()
                .then()
                    .statusCode(org.apache.http.HttpStatus.SC_CREATED)
                    .body(matchesJsonSchema(new File("src/test/resources/201_created.json")))
                    .body("id", notNullValue())
                    .body("parkingSpotNumber", is(parkingSpotDto.getParkingSpotNumber()))
                    .body("licensePlateCar", is(parkingSpotDto.getLicensePlateCar()))
                    .body("brandCar", is(parkingSpotDto.getBrandCar()))
                    .body("modelCar", is(parkingSpotDto.getModelCar()))
                    .body("colorCar", is(parkingSpotDto.getColorCar()))
                    .body("registrationDate", notNullValue())
                    .body("responsibleName", is(parkingSpotDto.getResponsibleName()))
                    .body("apartment", is(parkingSpotDto.getApartment()))
                    .body("block", is(parkingSpotDto.getBlock()))


        ;
    }

    @Test
    void shouldNotSaveBecause_ConflicLicence(){
        ParkingSpotDto parkingSpotDto = new ParkingSpotDto();
        parkingSpotDto.setParkingSpotNumber("205B");
        parkingSpotDto.setLicensePlateCar("RRS8562");
        parkingSpotDto.setBrandCar("Audi");
        parkingSpotDto.setModelCar("Q5");
        parkingSpotDto.setColorCar("Black");
        parkingSpotDto.setResponsibleName("Willy Wonka");
        parkingSpotDto.setApartment("205");
        parkingSpotDto.setBlock("B");


        Response response = RestAssured
                .given()
                .baseUri("http://localhost:8080/")
                .basePath("parking-spot")
                .body(parkingSpotDto)
                .contentType(ContentType.JSON)
                .when()
                .post()
                .then().log().all()
                .statusCode(HttpStatus.SC_CONFLICT)
                .extract().response();

        assertThat(response.asPrettyString(),equalTo("Conflict: License Plate Car is already in use!"));

    }
}
