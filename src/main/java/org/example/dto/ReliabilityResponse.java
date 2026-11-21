package org.example.dto;

public class ReliabilityResponse {
    private Long readerId;
    private boolean reliable;
    private int maxActiveLoans;

    public ReliabilityResponse() {
    }

    public ReliabilityResponse(Long readerId, boolean reliable, int maxActiveLoans) {
        this.readerId = readerId;
        this.reliable = reliable;
        this.maxActiveLoans = maxActiveLoans;
    }

    public Long getReaderId() {
        return readerId;
    }

    public void setReaderId(Long readerId) {
        this.readerId = readerId;
    }

    public boolean isReliable() {
        return reliable;
    }

    public void setReliable(boolean reliable) {
        this.reliable = reliable;
    }

    public int getMaxActiveLoans() {
        return maxActiveLoans;
    }

    public void setMaxActiveLoans(int maxActiveLoans) {
        this.maxActiveLoans = maxActiveLoans;
    }
}