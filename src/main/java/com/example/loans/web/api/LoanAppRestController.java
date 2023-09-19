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

    /**
     * Get all loans list.
     *
     * @return the list
     */
    @GetMapping
    public List<Loan> getAllLoans(){
        return loanService.getAllLoans();
    }

    /**
     * Add loan string.
     *
     * @param loan the loan
     * @return the string
     */
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

    /**
     * Gets loan by id.
     *
     * @param id the id
     * @return the loan by id
     */
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

    /**
     * Get aggregate by lender list.
     *
     * @return the list
     */
    @GetMapping("/aggregate/lender")
    public List<LoanAggregated> getAggregateByLender(){
        return loanService.getAggregatedLoanGroupByLenderId();
    }

    /**
     * Get aggregate by customer list.
     *
     * @return the list
     */
    @GetMapping("/aggregate/customer")
    public List<LoanAggregated> getAggregateByCustomer(){
        return loanService.getAggregatedLoanGroupByCustomerId();
    }

    /**
     * Get aggregate by interest list.
     *
     * @return the list
     */
    @GetMapping("/aggregate/interest")
    public List<LoanAggregated> getAggregateByInterest(){
        return loanService.getAggregatedLoanGroupByInterest();
    }
}
