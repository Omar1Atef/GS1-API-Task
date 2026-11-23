package com.assessment.api.spec;

import com.assessment.api.config.Configuration;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

/**
 * Request and response specifications shared by every service, so base URI,
 * headers and expectations are configured in exactly one place.
 */
public final class Specs {

    private Specs() {
    }

    public static RequestSpecification request() {
        return new RequestSpecBuilder()
                .setBaseUri(Configuration.baseUri())
                .setBasePath(Configuration.basePath())
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .build();
    }

    /** Baseline expectation for any successful JSON response. */
    public static ResponseSpecification okJson() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }
}
