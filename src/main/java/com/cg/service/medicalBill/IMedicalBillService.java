package com.cg.service.medicalBill;

import com.cg.model.MedicalBill;
import com.cg.service.IGeneralService;

public interface IMedicalBillService extends IGeneralService<MedicalBill, Long> {
    MedicalBill create(MedicalBill medicalBill);
}
