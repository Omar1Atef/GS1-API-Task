package com.assessment.api.spec;

import com.assessment.api.config.Configuration;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

/**
 * Request and response specifications shared by every service, so base URI,
 * headers and expectations are configured in exactly one place.
 *
 * <p>Logging filters are attached to the specification, which is built per request, rather
 * than registered on the global {@code RestAssured.filters(...)} list. That list is static
 * mutable state; adding to it from each test class corrupts it when classes run in parallel.
 */
public final class Specs {

    private Specs() {
    }

    public static RequestSpecification request() {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(Configuration.baseUri())
                .setBasePath(Configuration.basePath())
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON);

        if (Configuration.logAll()) {
            builder.addFilter(new RequestLoggingFilter());
            builder.addFilter(new ResponseLoggingFilter());
        }
        return builder.build();
    }

    /** Baseline expectation for any successful JSON response. */
    public static ResponseSpecification okJson() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }
}
