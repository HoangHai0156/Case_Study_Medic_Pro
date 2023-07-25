package com.cg.model.dtos.doctor;

import com.cg.model.Doctor;
import com.cg.model.LocationRegion;
import com.cg.model.Speciality;
import com.cg.model.User;
import com.cg.model.dtos.locationRegion.LocationRegionCreReqDTO;
import com.cg.model.enums.EGender;
import com.cg.model.enums.ELevel;
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
public class DoctorCreReqDTO {

    private String fullName;

    private String email;

    private String nameGender;

    private String phone;

    private String birthDay;

    private String identityNumber;

    private String ethnic;

    private LocationRegionCreReqDTO locationRegion;

    private String userId;

    private String specialityId;

    private String levelName;

    public Doctor toDoctor(EGender eGender, LocationRegion locationRegion, User user, Speciality speciality, ELevel eLevel){

        return new Doctor(fullName, email, eGender, phone, DateFormat.parse(birthDay), "Bác sĩ",
                identityNumber, ethnic, null, locationRegion, user, speciality, eLevel);
    }
}
