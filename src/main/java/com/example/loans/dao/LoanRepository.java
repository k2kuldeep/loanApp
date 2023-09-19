package com.example.loans.dao;

import com.example.loans.model.Loan;
import com.example.loans.model.LoanAggregated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, String> {

    Optional<Loan> findByLoanId(String id);

    List<Loan> findByCustomerId(String id);

    List<Loan> findByLenderId(String id);

    @Query(value = "select lender_id as groups, SUM(remaining_amount) as remainingAmount, " +
            "SUM(interest_per_day) as interestPerDay, SUM(penalty_per_day) as penaltyPerDay " +
            "from loans group by lender_id",nativeQuery=true)
    List<LoanAggregated> getAggregatedLoanGroupByLenderId();

    @Query(value = "select customer_id as groups, SUM(remaining_amount) as remainingAmount, " +
            "SUM(interest_per_day) as interestPerDay, SUM(penalty_per_day) as penaltyPerDay " +
            "from loans group by customer_id",nativeQuery=true)
    List<LoanAggregated> getAggregatedLoanGroupByCustomerId();

    @Query(value = "select interest_per_day as groups, SUM(remaining_amount) as remainingAmount, " +
            "SUM(interest_per_day) as interestPerDay, SUM(penalty_per_day) as penaltyPerDay " +
            "from loans group by interest_per_day",nativeQuery=true)
    List<LoanAggregated> getAggregatedLoanGroupByInterest();

}
