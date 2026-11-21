package org.example.service;

import org.example.model.Loan;
import org.example.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReaderReliabilityServiceImpl implements ReaderReliabilityService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ReaderService readerService;

    @Override
    @Transactional(readOnly = true)
    public boolean isReliable(Long readerId) {
        readerService.getReaderById(readerId);

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime thirtyDaysAgo = now.minusDays(30);

        List<Loan> activeLoans = loanRepository.findByReaderIdAndReturnedAtIsNull(readerId);
        boolean hasOverdueActiveLoan = activeLoans.stream()
                .anyMatch(loan -> loan.getDueDate().isBefore(now));
        if (hasOverdueActiveLoan) {
            return false;
        }

        List<Loan> returnedRecently = loanRepository
                .findByReaderIdAndReturnedAtIsNotNullAndReturnedAtBetween(readerId, thirtyDaysAgo, now);
        if (returnedRecently.size() < 3) {
            return false;
        }

        return returnedRecently.stream()
                .allMatch(loan -> !loan.getReturnedAt().isAfter(loan.getDueDate()));
    }

    @Override
    @Transactional(readOnly = true)
    public int getMaxActiveLoans(Long readerId) {
        return isReliable(readerId) ? 5 : 2;
    }
}