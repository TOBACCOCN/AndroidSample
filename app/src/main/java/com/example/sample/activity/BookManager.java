package com.example.sample.activity;

import java.util.HashMap;
import java.util.Map;

public class BookManager {

    public static class Book {
        private int id;
        private String title;
        private String detail;

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getDetail() {
            return detail;
        }

        @Override
        public String toString() {
            return title;
        }

        public Book(int id, String title, String detail) {
            this.id = id;
            this.title = title;
            this.detail = detail;
        }
    }

    private static final Map<Integer, Book> bookMap = new HashMap<>();
    public static Book[] books = new Book[3];

    static {
        Book java = new Book(0, "Java", "Java is the most popular programing language");
        Book android = new Book(1, "Android", "Android is most popular programing language in mobile area");
        Book rust = new Book(2, "Rust", "Rust is the safest programing language");
        bookMap.put(java.getId(), java);
        bookMap.put(android.getId(), android);
        bookMap.put(rust.getId(), rust);
        books[java.getId()] = java;
        books[android.getId()] = android;
        books[rust.getId()] = rust;
    }

    public static Book getBook(int id) {
        return bookMap.get(id);
    }
}
