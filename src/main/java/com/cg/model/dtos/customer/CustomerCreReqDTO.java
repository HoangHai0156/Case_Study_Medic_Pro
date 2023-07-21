package com.cg.model.dtos.customer;

import com.cg.model.Customer;
import com.cg.model.User;
import com.cg.model.dtos.locationRegion.LocationRegionCreReqDTO;
import com.cg.model.enums.EGender;
import com.cg.utils.DateFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class CustomerCreReqDTO {

    private String fullName;

    private String email;

    private String nameGender;

    private String phone;

    private String birthDay;

    private String job;

    private String identityNumber;

    private String ethnic;

    private LocationRegionCreReqDTO locationRegion;

    private String userId;

    public Customer toCustomer(EGender eGender, User user){
       return new Customer(fullName, email, eGender, phone, DateFormat.parse(birthDay), job, identityNumber, ethnic,
               null, locationRegion.toLocationRegion(), user);
    }
}
