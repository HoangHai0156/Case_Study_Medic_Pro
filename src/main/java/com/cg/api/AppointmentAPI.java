package com.cg.api;


import com.cg.exception.DataInputException;
import com.cg.model.Appointment;
import com.cg.model.Doctor;
import com.cg.model.Room;
import com.cg.model.Speciality;
import com.cg.model.dtos.appointment.AppointmentCreReqDTO;
import com.cg.model.dtos.appointment.AppointmentResDTO;
import com.cg.model.enums.ETime;
import com.cg.service.appointment.IAppointmentService;
import com.cg.service.doctor.IDoctorService;
import com.cg.service.room.IRoomService;
import com.cg.service.speciality.ISpecialityService;
import com.cg.utils.AppUtils;
import com.cg.utils.DateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentAPI {

    @Autowired
    private IAppointmentService appointmentService;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private IDoctorService doctorService;

    @Autowired
    private IRoomService roomService;

    @Autowired
    private ISpecialityService specialityService;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody AppointmentCreReqDTO appointmentCreReqDTO,
                                    BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return appUtils.mapErrorToResponse(bindingResult);
        }

        Long specialityId = Long.parseLong(appointmentCreReqDTO.getSpecialityId());
        Speciality speciality = specialityService.findById(specialityId).orElseThrow(()-> new DataInputException("Chuyên khoa không tồn tại"));

        Long doctorId = Long.parseLong(appointmentCreReqDTO.getDoctorId());
        Doctor doctor = doctorService.findById(doctorId).orElseThrow(() -> new DataInputException("Bác sĩ không tồn tại"));

        Long doctorSpecialityId = doctor.getSpeciality().getId();
        if (!specialityId.equals(doctorSpecialityId)){
            Map<String, String> data = new HashMap<>();
            data.put("message", "Bác sĩ không thuộc "+speciality.getName());
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }

        ETime eTime = ETime.valueOf(appointmentCreReqDTO.getTimeName());
        Date day = DateFormat.parse(appointmentCreReqDTO.getDay());

        List<Appointment> appointments1 = appointmentService.getAllByDoctorIdAndDayAndTime(doctorId,day,eTime);
        if (!appointments1.isEmpty()){
            Map<String, String> data = new HashMap<>();
            data.put("message", "Bác sĩ đã có lịch khám cùng giờ");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }

        Long roomId = Long.parseLong(appointmentCreReqDTO.getRoomId());
        Room room = roomService.findById(roomId).orElseThrow(()-> new DataInputException("Phòng khám không tồn tại"));

        Long roomSpecialityId= room.getSpeciality().getId();
        if (!specialityId.equals(roomSpecialityId)){
            Map<String, String> data = new HashMap<>();
            data.put("message", "Phòng khám không thuộc "+speciality.getName());
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }

        List<Appointment> appointments = appointmentService.getAllByRoomIdAndDayAndTime(roomId,day,eTime);
        if (!appointments.isEmpty()){
            Map<String, String> data = new HashMap<>();
            data.put("message", "Phòng khám không sẵn sàng");
            return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
        }

        Appointment appointment = appointmentCreReqDTO.toAppointment(doctor,room,eTime, speciality);
        Appointment newAppointment = appointmentService.save(appointment);
        AppointmentResDTO appointmentResDTO = newAppointment.toAppointmentResDTO();

        return new ResponseEntity<>(appointmentResDTO,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<AppointmentResDTO> appointmentResDTOS = appointmentService.findAllAppointmentResDTO();

        return new ResponseEntity<>(appointmentResDTOS,HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<?> update(){


        return new ResponseEntity<>(HttpStatus.OK);
    }
}
