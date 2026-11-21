package org.example.service;

import org.example.model.Loan;

import java.util.List;

public interface LoanService {
    Loan issueLoan(Long readerId, Long bookId);
    Loan returnLoan(Long loanId);
    List<Loan> getActiveLoansByReader(Long readerId);
}