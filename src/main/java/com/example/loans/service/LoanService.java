package com.example.loans.service;

import com.example.loans.model.Loan;
import com.example.loans.model.LoanAggregated;

import java.util.List;


/**
 * The interface Loan service.
 */
public interface LoanService {

    /**
     * Gets all loans.
     *
     * @return the all loans
     */
    List<Loan> getAllLoans();

    /**
     * Add loan boolean.
     *
     * @param loan the loan
     * @return the boolean
     */
    boolean addLoan(Loan loan);

    /**
     * Gets loan by id.
     *
     * @param loanId the loan id
     * @return the loan by id
     */
    List<Loan> getLoanById(String loanId);

    /**
     * Gets loans by customer id.
     *
     * @param customerId the customer id
     * @return the loans by customer id
     */
    List<Loan> getLoansByCustomerId(String customerId);

    /**
     * Gets loans by lender id.
     *
     * @param lenderId the lender id
     * @return the loans by lender id
     */
    List<Loan> getLoansByLenderId(String lenderId);

    /**
     * Gets aggregated loan, group by lender id.
     *
     * @return the aggregated loan, group by lender id
     */
    List<LoanAggregated> getAggregatedLoanGroupByLenderId();

    /**
     * Gets aggregated loan, group by customer id.
     *
     * @return the aggregated loan, group by customer id
     */
    List<LoanAggregated> getAggregatedLoanGroupByCustomerId();

    /**
     * Gets aggregated loan, group by interest.
     *
     * @return the aggregated loan, group by interest
     */
    List<LoanAggregated> getAggregatedLoanGroupByInterest();

    /**
     * Check loan due date scheduler.
     */
    void checkLoanDueDateScheduler();
}
