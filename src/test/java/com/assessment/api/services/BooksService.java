package com.assessment.api.services;

import com.assessment.api.models.Book;
import io.restassured.response.Response;

/**
 * Service object for the Books resource. The tests speak in terms of these
 * methods, never in terms of URLs or HTTP verbs.
 */
public class BooksService extends BaseService {

    private static final String BOOKS = "/Books";
    private static final String BOOK_BY_ID = "/Books/{id}";

    public Response getAllBooks() {
        return get(BOOKS);
    }

    public Response getBookById(Object id) {
        return get(BOOK_BY_ID, id);
    }

    public Response createBook(Book book) {
        return post(BOOKS, book);
    }
}
