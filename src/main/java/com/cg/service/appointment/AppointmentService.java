package com.cg.service.appointment;

import com.cg.model.Appointment;
import com.cg.model.dtos.appointment.AppointmentResDTO;
import com.cg.model.enums.ETime;
import com.cg.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AppointmentService implements IAppointmentService{

    @Autowired
    AppointmentRepository appointmentRepository;

    @Override
    public List<Appointment> getAllByRoomIdAndDayAndTime(Long roomId, Date day, ETime time) {
        return appointmentRepository.getAllByRoomIdAndDayAndTime(roomId, day, time);
    }

    @Override
    public List<AppointmentResDTO> findAllAppointmentResDTO() {
        return appointmentRepository.findAllAppointmentResDTO();
    }

    @Override
    public List<Appointment> getAllByDoctorIdAndDayAndTime(Long doctorId, Date day, ETime time) {
        return appointmentRepository.getAllByDoctorIdAndDayAndTime(doctorId, day, time);
    }

    @Override
    public List<Appointment> getAllByDoctorId(Long doctorId) {
        return appointmentRepository.getAllByDoctorId(doctorId);
    }

    @Override
    public List<Appointment> findAll() {
        return null;
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public void delete(Appointment appointment) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
