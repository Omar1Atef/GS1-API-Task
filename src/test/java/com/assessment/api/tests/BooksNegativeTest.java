package com.assessment.api.tests;

import com.assessment.api.base.BaseApiTest;
import com.assessment.api.data.InvalidBookRequest;
import com.assessment.api.data.JsonDataReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class BooksNegativeTest extends BaseApiTest {

    private static final String INVALID_REQUESTS_DATA = "testdata/invalid-book-requests.json";

    @DataProvider(name = "invalidBookRequests")
    public Object[][] invalidBookRequests() {
        return JsonDataReader.asDataProvider(INVALID_REQUESTS_DATA, InvalidBookRequest.class);
    }

    @Test(dataProvider = "invalidBookRequests",
            description = "GET /Books/{id} rejects ids that cannot resolve to a book, with a problem-details body")
    public void shouldRejectInvalidBookId(InvalidBookRequest data) {
        Response response = booksService.getBookById(data.getId());

        assertEquals(response.statusCode(), data.getExpectedStatus(),
                "Unexpected status code for " + data.getCaseName());
        assertEquals(ContentType.fromContentType(response.contentType()), ContentType.JSON,
                "Error response was not JSON for " + data.getCaseName());
        assertEquals(response.jsonPath().getString("title"), data.getExpectedTitle(),
                "Unexpected error title for " + data.getCaseName());
        assertEquals(response.jsonPath().getInt("status"), data.getExpectedStatus(),
                "The status in the error body does not match the HTTP status for " + data.getCaseName());
    }
}
