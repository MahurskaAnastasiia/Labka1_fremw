package org.example.service;

import org.example.model.Book;

import java.util.List;

public interface BookService {
    Book createBook(String title, String author);
    List<Book> getAllBooks();
    Book getBookById(Long bookId);
}