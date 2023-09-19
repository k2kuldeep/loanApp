package com.example.loans.service;

import com.example.loans.model.Loan;
import com.example.loans.model.LoanAggregated;

import java.util.List;


public interface LoanService {

    List<Loan> getAllLoans();

    boolean addLoan(Loan loan);

    List<Loan> getLoanById(String loanId);

    List<Loan> getLoansByCustomerId(String customerId);

    List<Loan> getLoansByLenderId(String lenderId);

    List<LoanAggregated> getAggregatedLoanGroupByLenderId();

    List<LoanAggregated> getAggregatedLoanGroupByCustomerId();

    List<LoanAggregated> getAggregatedLoanGroupByInterest();

    void checkLoanDueDateScheduler();
}
