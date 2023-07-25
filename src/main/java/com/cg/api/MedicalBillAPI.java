package com.cg.api;

import com.cg.exception.DataInputException;
import com.cg.model.Appointment;
import com.cg.model.Customer;
import com.cg.model.Doctor;
import com.cg.model.MedicalBill;
import com.cg.model.dtos.doctor.DoctorResDTO;
import com.cg.model.dtos.medicalBill.MedicalBillCreReqDTO;
import com.cg.model.dtos.medicalBill.MedicalBillResDTO;
import com.cg.service.appointment.IAppointmentService;
import com.cg.service.customer.ICustomerService;
import com.cg.service.medicalBill.IMedicalBillService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/medical-bills")
public class MedicalBillAPI {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private IAppointmentService appointmentService;

    @Autowired
    private IMedicalBillService medicalBillService;


    @GetMapping
    public ResponseEntity<?> getAll(){
        List<MedicalBillResDTO> medicalBillResDTOS = new ArrayList<>();
        List<MedicalBill> medicalBills = medicalBillService.findAll();

        for (MedicalBill medicalBill: medicalBills){
            MedicalBillResDTO medicalBillResDTO = medicalBill.toMedicalBillResDTO();
            medicalBillResDTOS.add(medicalBillResDTO);
        }

        return new ResponseEntity<>(medicalBillResDTOS,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody MedicalBillCreReqDTO medicalBillCreReqDTO,
                                    BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            appUtils.mapErrorToResponse(bindingResult);
        }

        Long customerId = Long.parseLong(medicalBillCreReqDTO.getCustomerId());
        Customer customer = customerService.findById(customerId).orElseThrow(()-> new DataInputException("Khách hàng không tồn tại"));

        Long appointmentId = Long.parseLong(medicalBillCreReqDTO.getAppointmentId());
        Appointment appointment = appointmentService.findById(appointmentId).orElseThrow(()-> new DataInputException("Lịch khám không tồn tai"));

        MedicalBill medicalBill = medicalBillCreReqDTO.toMedicalBill(appointment,customer);
        MedicalBill newMedicalBill = medicalBillService.create(medicalBill);
        MedicalBillResDTO medicalBillResDTO = newMedicalBill.toMedicalBillResDTO();

        return new ResponseEntity<>(medicalBillResDTO,HttpStatus.CREATED);
    }
}
