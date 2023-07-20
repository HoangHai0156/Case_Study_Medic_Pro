package com.cg.service.appointment;

import com.cg.model.Appointment;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AppointmentService implements IAppointmentService{
    @Override
    public List<Appointment> findAll() {
        return null;
    }

    @Override
    public Optional<Appointment> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Appointment save(Appointment appointment) {
        return null;
    }

    @Override
    public void delete(Appointment appointment) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
