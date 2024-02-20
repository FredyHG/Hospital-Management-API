package dev.fredyhg.com.hospitalquerysystem.service;

import dev.fredyhg.com.hospitalquerysystem.dominio.Doctor;
import dev.fredyhg.com.hospitalquerysystem.exception.doctor.DoctorAlreadyExistsException;
import dev.fredyhg.com.hospitalquerysystem.exception.doctor.DoctorNotFoundException;
import dev.fredyhg.com.hospitalquerysystem.mapper.ModelMappers;
import dev.fredyhg.com.hospitalquerysystem.repository.DoctorRepository;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.doctor.DoctorDtoViewAll;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.doctor.DoctorGetReturnObject;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.doctor.DoctorPostRequestBody;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.doctor.DoctorPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class DoctorServices {

    private final DoctorRepository doctorRepository;

    public DoctorGetReturnObject findByCrmReturnDTO(String crm) {

        Doctor doctorToBeConvert = doctorRepository.findByCrm(crm).orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));

        return ModelMappers.convertDoctorDtoView(doctorToBeConvert);
    }

    public Doctor findByCrm(String crm) {

        return doctorRepository.findByCrm(crm).orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));
    }



    public Page<DoctorDtoViewAll> listAllDoctors(Pageable pageable) {

        Page<Doctor> doctorList = doctorRepository.findAll(pageable);

        return doctorList.map(ModelMappers::convertDoctorDtoViewAll);
    }


    public void replace(DoctorPutRequestBody doctorPutRequestBody) {

        Doctor doctorExists = ensureDoctorExists(doctorPutRequestBody.getCrm());

        Doctor doctor = ModelMappers.doctorPutRequestToDoctor(doctorPutRequestBody, doctorExists);

        doctorRepository.save(doctor);
    }

    public void save(DoctorPostRequestBody doctorPostRequestBody) {

        ensureDoctorNonExists(doctorPostRequestBody.getCrm());

        Doctor doctorToBeSaved = ModelMappers.doctorPostRequestToDoctor(doctorPostRequestBody);

        doctorRepository.save(doctorToBeSaved);

    }

    public void deleteByCrm(String crm) {
        Doctor doctor = ensureDoctorExists(crm);

        doctorRepository.delete(doctor);
    }

    public Doctor ensureDoctorExists(String crm){
        return doctorRepository.findByCrm(crm).orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));
    }

    public void ensureDoctorNonExists(String crm){
        doctorRepository.findByCrm(crm).ifPresent(doctor -> {
            throw new DoctorAlreadyExistsException("Doctor already exists");
        });
    }



}

