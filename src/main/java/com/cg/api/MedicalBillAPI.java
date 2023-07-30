package com.cg.api;

import com.cg.exception.DataInputException;
import com.cg.model.*;
import com.cg.model.dtos.doctor.DoctorResDTO;
import com.cg.model.dtos.medicalBill.MedicalBillCreReqDTO;
import com.cg.model.dtos.medicalBill.MedicalBillResDTO;
import com.cg.service.appointment.IAppointmentService;
import com.cg.service.customer.ICustomerService;
import com.cg.service.medicalBill.IMedicalBillService;
import com.cg.utils.AppUtils;
import com.cg.utils.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/unpaid/{customerId}")
    public ResponseEntity<?> getUnpaidByCus(@PathVariable("customerId") String customerIdStr){

        if (!ValidateUtil.isNumberValid(customerIdStr)){
            Map<String, String> data = new HashMap<>();
            data.put("message", "ID khách hàng không hợp đúng định dạng");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }

        Long customerId = Long.parseLong(customerIdStr);
        Customer customer = customerService.findById(customerId).orElseThrow(()-> new DataInputException("Khách hàng không tồn tại"));

        List<MedicalBill> medicalBills = medicalBillService.getAllByCustomer_Id(customerId);
        List<MedicalBillResDTO> medicalBillResDTOS = new ArrayList<>();
        for (MedicalBill medicalBill: medicalBills){
            if (!medicalBill.isPaid() && !medicalBill.isDeleted()){
                MedicalBillResDTO medicalBillResDTO = medicalBill.toMedicalBillResDTO();
                medicalBillResDTOS.add(medicalBillResDTO);
            }
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

        appointment.setId(appointmentId);
        appointment.setAvailable(false);

        MedicalBill medicalBill = medicalBillCreReqDTO.toMedicalBill(appointment,customer);
        MedicalBill newMedicalBill = medicalBillService.create(medicalBill,appointment);
        MedicalBillResDTO medicalBillResDTO = newMedicalBill.toMedicalBillResDTO();

        return new ResponseEntity<>(medicalBillResDTO,HttpStatus.CREATED);
    }

    @DeleteMapping("/unpaid/{medicalBillId}")
    public ResponseEntity<?> deleteUnpaidMedBill(@PathVariable("medicalBillId") String medicalBillIdStr){
        if (!ValidateUtil.isNumberValid(medicalBillIdStr)){
            Map<String, String> data = new HashMap<>();
            data.put("message", "ID phiếu khám không hợp đúng định dạng");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }

        Long medicalBillId = Long.parseLong(medicalBillIdStr);
        MedicalBill medicalBill = medicalBillService.findById(medicalBillId).orElseThrow(()-> new DataInputException("Phiếu khám không tồn tại"));

        if (medicalBill.isPaid()){
            Map<String, String> data = new HashMap<>();
            data.put("message", "Phiếu khám đã được thanh toán, không thể xóa");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }

        Appointment appointment = medicalBill.getAppointment();
        Long appointmentId = appointment.getId();
        appointment.setId(appointmentId);
        appointment.setAvailable(true);

        medicalBill.setId(medicalBillId);
        medicalBill.setDeleted(true);
        medicalBillService.save(medicalBill);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
