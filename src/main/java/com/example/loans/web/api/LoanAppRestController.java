package com.example.loans.web.api;

import com.example.loans.exceptions.LoanApplicationException;
import com.example.loans.model.Loan;
import com.example.loans.service.LoanService;
import com.example.loans.model.LoanAggregated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/loans")
public class LoanAppRestController {

    private Logger logger = Logger.getLogger(LoanService.class.getName());

    @Autowired
    private LoanService loanService;

    @GetMapping
    public List<Loan> getAllLoans(){
        return loanService.getAllLoans();
    }

    @PostMapping(value = "/add",consumes = {"application/xml","application/json"})
    public String addLoan(@RequestBody Loan loan){
        String response = "";
        try {
            if(loanService.addLoan(loan))
                response = "success";
        }catch (Exception ex){
            logger.severe("LoanApplicationException: exception  while adding new loan");
            throw new LoanApplicationException(ex.getMessage());
        }
        return response;
    }

    @GetMapping("/{id}")
    public List<Loan> getLoanById(@PathVariable("id") String id){
        List<Loan> result;
        result = loanService.getLoanById(id);
        if (result.isEmpty()) {
            result = loanService.getLoansByCustomerId(id);
        }
        if (result.isEmpty()) {
            result = loanService.getLoansByLenderId(id);
        }
        return result;
    }

    @GetMapping("/aggregate/lender")
    private List<LoanAggregated> getAggregateByLender(){
        return loanService.getAggregatedLoanGroupByLenderId();
    }

    @GetMapping("/aggregate/customer")
    private List<LoanAggregated> getAggregateByCustomer(){
        return loanService.getAggregatedLoanGroupByCustomerId();
    }

    @GetMapping("/aggregate/interest")
    private List<LoanAggregated> getAggregateByInterest(){
        return loanService.getAggregatedLoanGroupByInterest();
    }
}
