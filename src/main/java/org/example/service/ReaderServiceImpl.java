package org.example.service;

import org.example.model.Reader;
import org.example.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReaderServiceImpl implements ReaderService {

    @Autowired
    private ReaderRepository readerRepository;

    @Override
    @Transactional
    public Reader createReader(String fullName, String email) {
        if (readerRepository.existsByEmail(email)) {
            throw new RuntimeException("Email is already in use: " + email);
        }
        Reader reader = new Reader();
        reader.setFullName(fullName);
        reader.setEmail(email);
        return readerRepository.save(reader);
    }

    @Override
    @Transactional(readOnly = true)
    public Reader getReaderById(Long readerId) {
        return readerRepository.findById(readerId)
                .orElseThrow(() -> new RuntimeException("Reader not found with id: " + readerId));
    }
}