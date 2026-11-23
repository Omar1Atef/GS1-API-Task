package com.assessment.api.services;

import com.assessment.api.spec.Specs;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;


public abstract class BaseService {

    protected RequestSpecification request() {
        return given().spec(Specs.request());
    }

    protected Response get(String path, Object... pathParams) {
        return request().when().get(path, pathParams).then().extract().response();
    }

    protected Response post(String path, Object body) {
        return request().body(body).when().post(path).then().extract().response();
    }
}
