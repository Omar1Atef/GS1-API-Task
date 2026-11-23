package com.assessment.api.base;

import com.assessment.api.services.BooksService;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

/**
 * Shared test setup. A failing assertion logs the full request and response, so a red report
 * carries its own evidence; {@code api.log.all} controls logging of passing calls and is
 * applied in {@link com.assessment.api.spec.Specs}, per request rather than globally.
 */
public abstract class BaseApiTest {

    protected BooksService booksService;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        booksService = new BooksService();
    }
}
