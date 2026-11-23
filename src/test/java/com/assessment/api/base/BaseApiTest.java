package com.assessment.api.base;

import com.assessment.api.config.Configuration;
import com.assessment.api.services.BooksService;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeClass;


public abstract class BaseApiTest {

    protected BooksService booksService;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        if (Configuration.logAll()) {
            RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        }
        booksService = new BooksService();
    }
}
