package com.cg.repository;

import com.cg.model.MedicalBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalBillRepository extends JpaRepository<MedicalBill, Long> {

}
