package com.assessment.api.tests;

import com.assessment.api.base.BaseApiTest;
import com.assessment.api.data.JsonDataReader;
import com.assessment.api.models.Book;
import com.assessment.api.spec.Specs;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class BooksHappyPathTest extends BaseApiTest {

    private static final String NEW_BOOKS_DATA = "testdata/new-books.json";

    @DataProvider(name = "newBooks")
    public Object[][] newBooks() {
        return JsonDataReader.asDataProvider(NEW_BOOKS_DATA, Book.class);
    }

    @Test(description = "GET /Books returns the catalogue with a valid schema and consistent ids")
    public void shouldReturnAllBooks() {
        Response response = booksService.getAllBooks();

        response.then()
                .spec(Specs.okJson())
                .body(matchesJsonSchemaInClasspath("schemas/books-array-schema.json"));

        List<Book> books = response.jsonPath().getList("", Book.class);
        assertFalse(books.isEmpty(), "The catalogue returned no books");
        books.forEach(book -> {
            assertNotNull(book.getId(), "A book was returned without an id");
            assertNotNull(book.getTitle(), "Book " + book.getId() + " was returned without a title");
        });
        assertEquals(books.stream().map(Book::getId).distinct().count(), books.size(),
                "The catalogue contains duplicated book ids");
    }

    @Test(dataProvider = "newBooks",
            description = "POST /Books accepts a book and returns the submitted representation")
    public void shouldCreateBook(Book newBook) {
        Response response = booksService.createBook(newBook);

        response.then()
                .spec(Specs.okJson())
                .body(matchesJsonSchemaInClasspath("schemas/book-schema.json"));

        Book created = response.as(Book.class);
        assertEquals(created, newBook, "The created book did not match the submitted payload");
        assertTrue(created.getPageCount() > 0, "The created book has a non-positive page count");
    }
}
