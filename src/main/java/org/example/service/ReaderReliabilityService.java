package org.example.service;

public interface ReaderReliabilityService {
    boolean isReliable(Long readerId);
    int getMaxActiveLoans(Long readerId);
}