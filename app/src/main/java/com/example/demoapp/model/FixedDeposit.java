package com.example.demoapp.model;

import java.io.Serializable;
import java.time.LocalDate;

public class FixedDeposit implements Serializable {
    private Integer id;
    private Long number;
    private Double amount;
    private Integer tenure;
    private Double rate;
    private Double maturityAmount;
    private LocalDate createdDate;
    private LocalDate endDate;
    private String bankWithAddress;
    private Integer daysLeft;
    private Long userId; // Foreign key referencing User table

    public FixedDeposit() {
    }

    public FixedDeposit(Integer id, Long number, Double amount, Integer tenure, Double rate, Double maturityAmount, LocalDate createdDate, LocalDate endDate, String bankWithAddress, Integer daysLeft) {
        this.id = id;
        this.number = number;
        this.amount = amount;
        this.tenure = tenure;
        this.rate = rate;
        this.maturityAmount = maturityAmount;
        this.createdDate = createdDate;
        this.endDate = endDate;
        this.bankWithAddress = bankWithAddress;
        this.daysLeft = daysLeft;
    }

    public FixedDeposit(Integer id, Long number, Double amount, Integer tenure, Double rate, Double maturityAmount, LocalDate createdDate, LocalDate endDate, String bankWithAddress, Integer daysLeft, Long userId) {
        this.id = id;
        this.number = number;
        this.amount = amount;
        this.tenure = tenure;
        this.rate = rate;
        this.maturityAmount = maturityAmount;
        this.createdDate = createdDate;
        this.endDate = endDate;
        this.bankWithAddress = bankWithAddress;
        this.daysLeft = daysLeft;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBankWithAddress() {
        return bankWithAddress;
    }

    public void setBankWithAddress(String bankWithAddress) {
        this.bankWithAddress = bankWithAddress;
    }

    public Integer getTenure() {
        return tenure;
    }

    public void setTenure(Integer tenure) {
        this.tenure = tenure;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(Integer daysLeft) {
        this.daysLeft = daysLeft;
    }

    public Double getMaturityAmount() {
        return maturityAmount;
    }

    public void setMaturityAmount(Double maturityAmount) {
        this.maturityAmount = maturityAmount;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "FixedDeposit{" +
                "id=" + id +
                ", number=" + number +
                ", amount=" + amount +
                ", tenure='" + tenure + '\'' +
                ", rate=" + rate +
                ", maturityAmount=" + maturityAmount +
                ", createdDate=" + createdDate +
                ", endDate=" + endDate +
                ", bankWithAddress='" + bankWithAddress + '\'' +
                ", daysLeft=" + daysLeft +
                ", userId=" + userId +
                '}';
    }
}
