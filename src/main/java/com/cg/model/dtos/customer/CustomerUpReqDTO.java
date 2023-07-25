package com.cg.model.dtos.customer;

import com.cg.model.Customer;
import com.cg.model.LocationRegion;
import com.cg.model.User;
import com.cg.model.dtos.locationRegion.LocationRegionCreReqDTO;
import com.cg.model.dtos.locationRegion.LocationRegionUpReqDTO;
import com.cg.model.enums.EGender;
import com.cg.utils.DateFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerUpReqDTO {


    private String fullName;

    private String email;

    private String nameGender;

    private String phone;

    private String birthDay;

    private String job;

    private String identityNumber;

    private String ethnic;

    private LocationRegionUpReqDTO locationRegion;

    public Customer toCustomer(EGender eGender, User user, Long customerId, Long locationRegionId){
        return new Customer(fullName, email, eGender, phone, DateFormat.parse(birthDay), job, identityNumber, ethnic,
                customerId, locationRegion.toLocationRegion(locationRegionId), user);
    }
}
