package com.example.loans.model;

public interface LoanAggregated {

    String getGroups();

    String getRemainingAmount();

    String getInterestPerDay();

    String getPenaltyPerDay();

}
