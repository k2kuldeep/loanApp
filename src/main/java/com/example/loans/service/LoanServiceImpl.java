package com.example.loans.service;

import com.example.loans.dao.LoanRepository;
import com.example.loans.exceptions.LoanApplicationException;
import com.example.loans.model.Loan;
import com.example.loans.model.LoanAggregated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@Service("LoanService")
public class LoanServiceImpl implements LoanService{

    private Logger logger = Logger.getLogger(LoanService.class.getName());

    @Autowired
    private LoanRepository loanRepository;

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public boolean addLoan(Loan loan) {
        if (loan == null){
            throw new LoanApplicationException("loan application can't be null");
        }
        //check if payment date is greater than due date then reject loan
        if (loan.getPaymentDate().isAfter(loan.getDueDate())){
            logger.severe("Payment date is be greater than the Due Date");
            throw new LoanApplicationException("Payment date is be greater than the Due Date");
        }
        try {
            loanRepository.save(loan);
        }catch (Exception ex){
            logger.severe("Invalid loan record error: "+ex.getMessage());
            throw new LoanApplicationException("Exception While adding loan : Invalid loan details");
        }
        return true;
    }

    @Override
    public List<Loan> getLoanById(String loanId) {
        if (loanId != null && !loanId.isEmpty())
            return loanRepository.findByLoanId(loanId).stream().toList();
        else
            throw new LoanApplicationException("Invalid loan application id");
    }

    @Override
    public List<Loan> getLoansByCustomerId(String customerId) {
        if (customerId != null && !customerId.isEmpty())
            return loanRepository.findByCustomerId(customerId);
        else
            throw new LoanApplicationException("Invalid customer id");
    }

    @Override
    public List<Loan> getLoansByLenderId(String lenderId) {
        if (lenderId != null && !lenderId.isEmpty())
            return loanRepository.findByLenderId(lenderId);
        else
            throw new LoanApplicationException("Invalid lender id");

    }

    @Override
    public List<LoanAggregated> getAggregatedLoanGroupByLenderId() {
        return loanRepository.getAggregatedLoanGroupByLenderId();
    }

    @Override
    public List<LoanAggregated> getAggregatedLoanGroupByCustomerId() {
        return loanRepository.getAggregatedLoanGroupByCustomerId();
    }

    @Override
    public List<LoanAggregated> getAggregatedLoanGroupByInterest() {
        return loanRepository.getAggregatedLoanGroupByInterest();
    }

    @Scheduled(cron = "0 0 1 * * ?") // Cron expression for 1:00 AM every day
    public void checkLoanDueDateScheduler() {
        LocalDate currentDate = LocalDate.now();
        for (Loan loan: getAllLoans()){
            //check if loan has crossed its due date
            if (loan.getDueDate().isBefore(currentDate)){
                logger.info("Alert: loan id ["+loan.getLoanId()+"] crossed its due date.");
            }
        }
    }
}
