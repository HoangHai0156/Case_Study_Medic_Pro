package com.cg.api;

import com.cg.exception.DataInputException;
import com.cg.model.*;
import com.cg.model.dtos.customer.CustomerResDTO;
import com.cg.model.dtos.doctor.DoctorCreReqDTO;
import com.cg.model.dtos.doctor.DoctorResDTO;
import com.cg.model.dtos.locationRegion.LocationRegionCreReqDTO;
import com.cg.model.enums.EGender;
import com.cg.model.enums.ELevel;
import com.cg.service.doctor.IDoctorService;
import com.cg.service.speciality.ISpecialityService;
import com.cg.service.user.IUserService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/doctors")
public class DoctorAPI {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private IDoctorService doctorService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ISpecialityService specialityService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<DoctorResDTO> doctorResDTOS = new ArrayList<>();
        List<Doctor> doctors = doctorService.findAll();

        for (Doctor doctor: doctors){
            DoctorResDTO doctorResDTO = doctor.toDoctorResDTO();
            doctorResDTOS.add(doctorResDTO);
        }

        return new ResponseEntity<>(doctorResDTOS,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody DoctorCreReqDTO doctorCreReqDTO,
                                    BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            appUtils.mapErrorToResponse(bindingResult);
        }

        String eGenderName = doctorCreReqDTO.getNameGender();
        EGender eGender;
        try {
            eGender = EGender.valueOf(eGenderName);
        }catch (IllegalArgumentException e){
            throw new DataInputException("Giới tính không tồn tại");
        }

        LocationRegionCreReqDTO locationRegionCreReqDTO = doctorCreReqDTO.getLocationRegion();
        LocationRegion locationRegion = locationRegionCreReqDTO.toLocationRegion();

        Long userid = Long.parseLong(doctorCreReqDTO.getUserId());
        User user = userService.findById(userid).orElseThrow(() -> new DataInputException("Ngưới dùng không tồn tại"));

        Long specialityId = Long.valueOf(doctorCreReqDTO.getSpecialityId());
        Speciality speciality = specialityService.findById(specialityId).orElseThrow(()->{
            throw new DataInputException("Chuyên khoa không tồn tại");
        });

        String eLevelName = doctorCreReqDTO.getLevelName();
        ELevel eLevel;
        try {
            eLevel = ELevel.valueOf(eLevelName);
        }catch (IllegalArgumentException e){
            throw new DataInputException("Trình độ không tồn tại");
        }

        Doctor doctor = doctorCreReqDTO.toDoctor(eGender,locationRegion,user,speciality,eLevel);
        Doctor newDoctor = doctorService.create(locationRegion, doctor);
        DoctorResDTO doctorResDTO = newDoctor.toDoctorResDTO();

        return new ResponseEntity<>(doctorResDTO,HttpStatus.CREATED);
    }


}
