package org.example.repository;

import org.example.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReaderRepository extends JpaRepository<Reader, Long> {
    boolean existsByEmail(String email);
}