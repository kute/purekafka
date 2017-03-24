package com.kute.kafka.dto;

import java.io.Serializable;

/**
 * Created by kute on 2017/3/24.
 */
public class Book implements Serializable {

    private String bookId;

    private String content;

    private String author;

    private String price;

    public Book() {
    }

    public Book(String bookId, String content, String author, String price) {
        this.bookId = bookId;
        this.content = content;
        this.author = author;
        this.price = price;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
