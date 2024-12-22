package com.guya.DAOs;

import com.guya.models.Reimbursement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReimbursementDAO extends JpaRepository <Reimbursement, Integer>{

    //This method will find a List of Reimbursement by their status

    List<Reimbursement>  findByStatus(String  status);

}