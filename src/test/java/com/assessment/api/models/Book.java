package com.assessment.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

/** Request and response payload for the Books resource. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {

    private Integer id;
    private String title;
    private String description;
    private Integer pageCount;
    private String excerpt;
    private String publishDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Book book)) {
            return false;
        }
        return Objects.equals(id, book.id)
                && Objects.equals(title, book.title)
                && Objects.equals(description, book.description)
                && Objects.equals(pageCount, book.pageCount)
                && Objects.equals(excerpt, book.excerpt)
                && Objects.equals(publishDate, book.publishDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, pageCount, excerpt, publishDate);
    }

    @Override
    public String toString() {
        return "Book{id=" + id + ", title='" + title + "', pageCount=" + pageCount + "}";
    }
}
