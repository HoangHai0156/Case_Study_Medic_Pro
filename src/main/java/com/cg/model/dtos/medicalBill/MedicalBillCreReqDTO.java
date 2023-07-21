package com.cg.model.dtos.medicalBill;


import com.cg.model.Appointment;
import com.cg.model.Customer;
import com.cg.model.MedicalBill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MedicalBillCreReqDTO {

    private String appointmentId;

    private String customerId;

    public MedicalBill toMedicalBill(Appointment appointment, Customer customer){
        return new MedicalBill()
                .setId(null)
                .setAppointment(appointment)
                .setCustomer(customer)
                .setPaid(false);
    }
}
