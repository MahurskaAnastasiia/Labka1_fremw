package org.example.repository;

import org.example.model.Loan;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    @EntityGraph(attributePaths = {"book", "reader"})
    List<Loan> findByReaderIdAndReturnedAtIsNull(Long readerId);

    long countByReaderIdAndReturnedAtIsNull(Long readerId);

    @EntityGraph(attributePaths = {"book"})
    List<Loan> findByReaderIdAndReturnedAtIsNotNullAndReturnedAtBetween(
            Long readerId,
            LocalDateTime from,
            LocalDateTime to
    );
}