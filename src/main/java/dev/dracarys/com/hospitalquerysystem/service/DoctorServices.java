package dev.dracarys.com.hospitalquerysystem.service;

import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import dev.dracarys.com.hospitalquerysystem.mapper.DoctorMapper;
import dev.dracarys.com.hospitalquerysystem.repository.DoctorRepository;
import dev.dracarys.com.hospitalquerysystem.requests.doctor.DoctorGetReturnObject;
import dev.dracarys.com.hospitalquerysystem.requests.doctor.DoctorPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.doctor.DoctorPutRequestBody;
import dev.dracarys.com.hospitalquerysystem.util.TitleCase;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorServices {

    private final DoctorRepository doctorRepository;

    private final AppointmentServices appointmentServices;

    ModelMapper modelMapper = new ModelMapper();

    @Transactional
    public Optional<DoctorGetReturnObject> findByCrmReturnDTO(String crm) {

        Optional<Doctor> doctorToBeConvert = doctorRepository.findByCrm(crm);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        if (doctorToBeConvert.isEmpty()) {
            return Optional.empty();
        }

        DoctorGetReturnObject doctorDtoView = modelMapper.map(doctorToBeConvert.get(), DoctorGetReturnObject.class);

        doctorDtoView.setAppointmentsView(appointmentServices.findAppointmentByDoctor(doctorToBeConvert.get()));

        return Optional.of(doctorDtoView);

    }

    public Optional<Doctor> findByCrm(String crm) {

        Optional<Doctor> doctor = doctorRepository.findByCrm(crm);

        if (doctor.isEmpty()) {
            return Optional.empty();
        }

        return doctor;

    }


    public Page<Doctor> listAllDoctors(Pageable pageable) {
        return doctorRepository.findAll(pageable);
    }


    @Transactional
    public Doctor replace(DoctorPutRequestBody doctorPutRequestBody) {

        Doctor doctor = DoctorMapper.INSTANCE.toDoctor(doctorPutRequestBody);

        doctor.setFirstName(TitleCase.convertToTitleCaseIteratingChars(doctor.getFirstName()));

        doctor.setLastName(TitleCase.convertToTitleCaseIteratingChars(doctor.getLastName()));

        doctorRepository.save(doctor);

        return doctor;

    }

    @Transactional
    public void save(DoctorPostRequestBody doctorPostRequestBody) {

        doctorPostRequestBody.setFirstName(TitleCase.convertToTitleCaseIteratingChars(doctorPostRequestBody.getFirstName()));

        doctorPostRequestBody.setLastName(TitleCase.convertToTitleCaseIteratingChars(doctorPostRequestBody.getLastName()));

        doctorRepository.save(DoctorMapper.INSTANCE.toDoctor(doctorPostRequestBody));
    }

    @Transactional
    public void deleteByCrm(String crm) {

        doctorRepository.deleteByCrm(crm);

    }

}

