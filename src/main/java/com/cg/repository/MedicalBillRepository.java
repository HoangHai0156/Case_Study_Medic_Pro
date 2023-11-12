package com.cg.repository;

import com.cg.model.MedicalBill;
import com.cg.model.dtos.medicalBill.MedicalBillResDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalBillRepository extends JpaRepository<MedicalBill, Long> {
    List<MedicalBill> getAllByCustomer_Id(Long customerId);

    @Query("select m from MedicalBill as m where (m.customer.id = :id and m.deleted = false and m.isPaid = false) ")
    List<MedicalBill> getAllUnpaidBillsByCus(@Param("id") Long customerId);

    @Query("select m from MedicalBill as m where (m.appointment.id = :id and m.isPaid = true )")
    List<MedicalBill> getPaidBillsByApp(@Param("id") Long appointmentId);

    @Query(value = "select" +
            " new com.cg.model.dtos.medicalBill.MedicalBillResDTO(" +
            "mb.id," +
            "mb.code," +
            "mb.appointment," +
            "mb.customer," +
            "mb.isPaid," +
            "mb.paidDate" +
            ") " +
            "from MedicalBill as mb where mb.code like %:code%")
    Page<MedicalBillResDTO> getAllByCode(String code, Pageable pageable);
}
