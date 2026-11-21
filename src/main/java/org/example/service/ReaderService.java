package org.example.service;

import org.example.model.Reader;

public interface ReaderService {
    Reader createReader(String fullName, String email);
    Reader getReaderById(Long readerId);
}