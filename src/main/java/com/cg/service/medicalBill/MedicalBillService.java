package com.cg.service.medicalBill;

import com.cg.model.MedicalBill;
import com.cg.repository.MedicalBillRepository;
import com.cg.utils.DateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MedicalBillService implements IMedicalBillService{
    @Autowired
    private MedicalBillRepository medicalBillRepository;
    @Override
    public List<MedicalBill> findAll() {
        return medicalBillRepository.findAll();
    }

    @Override
    public Optional<MedicalBill> findById(Long id) {
        return medicalBillRepository.findById(id);
    }

    @Override
    public MedicalBill save(MedicalBill medicalBill) {
        return medicalBillRepository.save(medicalBill);
    }

    @Override
    public void delete(MedicalBill medicalBill) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public MedicalBill create(MedicalBill medicalBill) {
        MedicalBill newMedicalBill = medicalBillRepository.save(medicalBill);

        Long medicalBillId = newMedicalBill.getId();

        java.util.Date dateCreate = newMedicalBill.getCreatedAt();
        String dateCreateStr = DateFormat.format(dateCreate).replace("/","");
        String code = "A"+dateCreateStr+medicalBillId;
        newMedicalBill.setCode(code);

        return medicalBillRepository.save(newMedicalBill);
    }
}
