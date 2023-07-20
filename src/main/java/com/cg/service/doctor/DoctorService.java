package com.cg.service.doctor;

import com.cg.model.Doctor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class DoctorService implements IDoctorService{
    @Override
    public List<Doctor> findAll() {
        return null;
    }

    @Override
    public Optional<Doctor> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Doctor save(Doctor doctor) {
        return null;
    }

    @Override
    public void delete(Doctor doctor) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
