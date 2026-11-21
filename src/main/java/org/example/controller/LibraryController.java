package org.example.controller;

import org.example.dto.CreateBookRequest;
import org.example.dto.CreateLoanRequest;
import org.example.dto.CreateReaderRequest;
import org.example.dto.ReliabilityResponse;
import org.example.model.Book;
import org.example.model.Loan;
import org.example.model.Reader;
import org.example.service.BookService;
import org.example.service.LoanService;
import org.example.service.ReaderReliabilityService;
import org.example.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LibraryController {

    @Autowired
    private ReaderService readerService;

    @Autowired
    private BookService bookService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private ReaderReliabilityService reliabilityService;

    @PostMapping("/readers")
    @ResponseStatus(HttpStatus.CREATED)
    public Reader createReader(@RequestBody CreateReaderRequest request) {
        return readerService.createReader(request.getFullName(), request.getEmail());
    }

    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody CreateBookRequest request) {
        return bookService.createBook(request.getTitle(), request.getAuthor());
    }

    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping("/loans")
    @ResponseStatus(HttpStatus.CREATED)
    public Loan issueLoan(@RequestBody CreateLoanRequest request) {
        return loanService.issueLoan(request.getReaderId(), request.getBookId());
    }

    @GetMapping("/readers/{readerId}/reliability")
    public ReliabilityResponse getReliability(@PathVariable Long readerId) {
        boolean reliable = reliabilityService.isReliable(readerId);
        int maxActiveLoans = reliabilityService.getMaxActiveLoans(readerId);
        return new ReliabilityResponse(readerId, reliable, maxActiveLoans);
    }

    @PostMapping("/loans/{loanId}/return")
    public Loan returnLoan(@PathVariable Long loanId) {
        return loanService.returnLoan(loanId);
    }

    @GetMapping("/readers/{readerId}/loans/active")
    public List<Loan> getActiveLoans(@PathVariable Long readerId) {
        return loanService.getActiveLoansByReader(readerId);
    }
}