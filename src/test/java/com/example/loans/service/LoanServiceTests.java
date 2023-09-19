package com.example.loans.service;


import com.example.loans.exceptions.LoanApplicationException;
import com.example.loans.model.Loan;
import com.example.loans.model.LoanAggregated;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoanServiceTests {

    @Autowired
    private LoanService loanService;

    @Test
    public void whenApplicationStarts_thenHibernateCreatesInitialRecords() {
        List<Loan> loans = loanService.getAllLoans();
        Assert.assertEquals(7, loans.size());
    }

    @Test(expected = LoanApplicationException.class)
    public void addLoanTest_NullLoan() {
        loanService.addLoan(null);
    }

    @Test(expected = LoanApplicationException.class)
    public void addLoanTest_PaymentDateGreaterThanDueDate() {
        Loan loan = new Loan();
        loan.setLoanId("L1");
        loan.setPaymentDate(LocalDate.now());
        loan.setDueDate(LocalDate.EPOCH);
        loanService.addLoan(loan);
    }

    @Test(expected = LoanApplicationException.class)
    public void addLoanTest_LoanIdNotPresent() {
        Loan loan = new Loan();
        loan.setPaymentDate(LocalDate.EPOCH);
        loan.setDueDate(LocalDate.now());
        loanService.addLoan(loan);
    }

    @Test
    public void addLoanTest() {
        Loan loan = new Loan();
        loan.setLoanId("L1");
        loan.setPaymentDate(LocalDate.EPOCH);
        loan.setDueDate(LocalDate.now());
        Assert.assertTrue(loanService.addLoan(loan));
    }

    @Test(expected = LoanApplicationException.class)
    public void getLoanByIdTest_IdNull(){
        loanService.getLoanById(null);
    }

    @Test(expected = LoanApplicationException.class)
    public void getLoanByIdTest_IdEmpty(){
        loanService.getLoanById("");
    }

    @Test
    public void getLoanByIdTest(){
        String loanId = "L1";
        List<Loan> loans = loanService.getLoanById(loanId);
        Assert.assertFalse(loans.isEmpty());
        Assert.assertEquals(loanId, loans.get(0).getLoanId());
    }

    @Test(expected = LoanApplicationException.class)
    public void getLoansByCustomerIdTest_IdNull(){
        loanService.getLoansByCustomerId(null);
    }

    @Test(expected = LoanApplicationException.class)
    public void getLoansByCustomerIdTest_IdEmpty(){
        loanService.getLoansByCustomerId("");
    }

    @Test
    public void getLoansByCustomerIdTest(){
        String customerId = "C1";
        List<Loan> loans = loanService.getLoansByCustomerId(customerId);
        Assert.assertFalse(loans.isEmpty());
        Assert.assertEquals(customerId, loans.get(0).getCustomerId());
    }

    @Test(expected = LoanApplicationException.class)
    public void getLoansByLenderIdTest_IdNull(){
        loanService.getLoansByLenderId(null);
    }

    @Test(expected = LoanApplicationException.class)
    public void getLoansByLenderIdTest_IdEmpty(){
        loanService.getLoansByLenderId("");
    }

    @Test
    public void getLoansByLenderIdTest(){
        String lenderId = "LEN1";
        List<Loan> loans = loanService.getLoansByLenderId(lenderId);
        Assert.assertFalse(loans.isEmpty());
        Assert.assertEquals(lenderId, loans.get(0).getLenderId());
    }

    @Test
    public void getAggregatedLoanGroupByLenderIdTest(){
        List<LoanAggregated> loanAggregatedList = loanService.getAggregatedLoanGroupByLenderId();
        Assert.assertFalse(loanAggregatedList.isEmpty());
    }

    @Test
    public void getAggregatedLoanGroupByCustomerIdTest(){
        List<LoanAggregated> loanAggregatedList = loanService.getAggregatedLoanGroupByCustomerId();
        Assert.assertFalse(loanAggregatedList.isEmpty());
    }

    @Test
    public void getAggregatedLoanGroupByInterestTest(){
        List<LoanAggregated> loanAggregatedList = loanService.getAggregatedLoanGroupByInterest();
        Assert.assertFalse(loanAggregatedList.isEmpty());
    }
}
