package org.example.service;

import org.example.model.Book;
import org.example.model.BookStatus;
import org.example.model.Loan;
import org.example.model.Reader;
import org.example.repository.BookRepository;
import org.example.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReaderService readerService;

    @Autowired
    private BookService bookService;

    @Autowired
    private ReaderReliabilityService reliabilityService;

    @Override
    @Transactional
    public Loan issueLoan(Long readerId, Long bookId) {
        Reader reader = readerService.getReaderById(readerId);
        Book book = bookService.getBookById(bookId);

        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new RuntimeException("Book is not available for loan");
        }

        int maxActiveLoans = reliabilityService.getMaxActiveLoans(readerId);
        long activeLoansCount = loanRepository.countByReaderIdAndReturnedAtIsNull(readerId);
        if (activeLoansCount >= maxActiveLoans) {
            throw new RuntimeException("Reader has reached maximum active loans limit: " + maxActiveLoans);
        }

        LocalDateTime now = LocalDateTime.now();
        Loan loan = new Loan();
        loan.setReader(reader);
        loan.setBook(book);
        loan.setBorrowedAt(now);
        loan.setDueDate(now.plusDays(14));

        book.setStatus(BookStatus.ISSUED);
        bookRepository.save(book);

        return loanRepository.save(loan);
    }

    @Override
    @Transactional
    public Loan returnLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found with id: " + loanId));

        if (loan.getReturnedAt() != null) {
            throw new RuntimeException("Loan has already been returned");
        }

        loan.setReturnedAt(LocalDateTime.now());
        Book book = loan.getBook();
        book.setStatus(BookStatus.AVAILABLE);
        bookRepository.save(book);

        return loanRepository.save(loan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> getActiveLoansByReader(Long readerId) {
        readerService.getReaderById(readerId);
        return loanRepository.findByReaderIdAndReturnedAtIsNull(readerId);
    }
}