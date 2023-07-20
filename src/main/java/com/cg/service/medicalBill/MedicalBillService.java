package com.cg.service.medicalBill;

import com.cg.model.MedicalBill;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MedicalBillService implements IMedicalBillService{
    @Override
    public List<MedicalBill> findAll() {
        return null;
    }

    @Override
    public Optional<MedicalBill> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public MedicalBill save(MedicalBill medicalBill) {
        return null;
    }

    @Override
    public void delete(MedicalBill medicalBill) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
