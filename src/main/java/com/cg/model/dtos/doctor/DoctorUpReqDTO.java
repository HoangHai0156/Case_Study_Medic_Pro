package com.cg.model.dtos.doctor;

import com.cg.model.Doctor;
import com.cg.model.Speciality;
import com.cg.model.User;
import com.cg.model.dtos.locationRegion.LocationRegionUpReqDTO;
import com.cg.model.enums.EGender;
import com.cg.model.enums.ELevel;
import com.cg.utils.DateFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DoctorUpReqDTO {
    private String fullName;

    private String email;

    private String nameGender;

    private String phone;

    private String birthDay;

    private String job;

    private String identityNumber;

    private String ethnic;

    private LocationRegionUpReqDTO locationRegion;

    private String specialityId;

    private String levelName;

    public Doctor toDoctor(EGender eGender, Long doctorId, Long locationRegionId, User user, Speciality speciality, ELevel eLevel) {
        return new Doctor(fullName, email, eGender,phone, DateFormat.parse(birthDay),job, identityNumber,
                ethnic,doctorId,locationRegion.toLocationRegion(locationRegionId),user,speciality,eLevel);
    }
}
