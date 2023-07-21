package com.cg.service.appointment;

import com.cg.model.Appointment;
import com.cg.model.dtos.appointment.AppointmentResDTO;
import com.cg.model.enums.ETime;
import com.cg.service.IGeneralService;

import java.util.Date;
import java.util.List;

public interface IAppointmentService extends IGeneralService<Appointment, Long> {
    List<Appointment> getAllByRoomIdAndDayAndTime(Long roomId, Date day, ETime time);

    List<Appointment> getAllByDoctorIdAndDayAndTime(Long doctorId, Date day, ETime time);

    List<AppointmentResDTO> findAllAppointmentResDTO();

    List<Appointment> getAllByDoctorId(Long doctorId);

}
