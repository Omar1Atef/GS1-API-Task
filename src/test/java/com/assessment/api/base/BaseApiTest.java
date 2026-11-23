package com.assessment.api.base;

import com.assessment.api.services.BooksService;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

/**
 * Shared test setup. A failing assertion logs the full request and response, so
 * a red report always carries the evidence while a green run stays quiet.
 */
public abstract class BaseApiTest {

    protected BooksService booksService;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        booksService = new BooksService();
    }
}
