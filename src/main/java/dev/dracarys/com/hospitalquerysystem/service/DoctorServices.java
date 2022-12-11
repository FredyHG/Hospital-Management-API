package dev.dracarys.com.hospitalquerysystem.service;

import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import dev.dracarys.com.hospitalquerysystem.mapper.DoctorMapper;
import dev.dracarys.com.hospitalquerysystem.repository.DoctorRepository;
import dev.dracarys.com.hospitalquerysystem.requests.doctor.DoctorDtoViewAll;
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
import java.util.Collections;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class DoctorServices {

    private final DoctorRepository doctorRepository;

    private final AppointmentServices appointmentServices;

    private final StayServices stayServices;


    ModelMapper modelMapper = new ModelMapper();

    public Optional<DoctorGetReturnObject> findByCrmReturnDTO(String crm) {

        Optional<Doctor> doctorToBeConvert = doctorRepository.findByCrm(crm);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        if (doctorToBeConvert.isEmpty()) {
            return Optional.empty();
        }

        DoctorGetReturnObject doctorDtoView = modelMapper.map(doctorToBeConvert.get(), DoctorGetReturnObject.class);

        doctorDtoView.setAppointmentsView(appointmentServices.findAppointmentByDoctor(doctorToBeConvert.get()));

        doctorDtoView.setStaysView(stayServices.findStayByDoctor(doctorToBeConvert.get()));


        return Optional.of(doctorDtoView);


    }

    public Optional<Doctor> findByCrm(String crm) {

        Optional<Doctor> doctor = doctorRepository.findByCrm(crm);

        if (doctor.isEmpty()) {
            return Optional.empty();
        }

        return doctor;

    }



    public Page<DoctorDtoViewAll> listAllDoctors(Pageable pageable) {

        Page<Doctor> doctorList = doctorRepository.findAll(pageable);

        Page<DoctorDtoViewAll> dtoViewAlls = doctorList.map(this::convertDoctorDto);


        return dtoViewAlls;
    }


    public Doctor replace(DoctorPutRequestBody doctorPutRequestBody) {

        Doctor doctor = DoctorMapper.INSTANCE.toDoctor(doctorPutRequestBody);

        doctor.setFirstName(TitleCase.convertToTitleCaseIteratingChars(doctor.getFirstName()));

        doctor.setLastName(TitleCase.convertToTitleCaseIteratingChars(doctor.getLastName()));

        doctorRepository.save(doctor);

        return doctor;

    }

    public String save(DoctorPostRequestBody doctorPostRequestBody) {

        doctorPostRequestBody.setFirstName(TitleCase.convertToTitleCaseIteratingChars(doctorPostRequestBody.getFirstName()));

        doctorPostRequestBody.setLastName(TitleCase.convertToTitleCaseIteratingChars(doctorPostRequestBody.getLastName()));

        doctorRepository.save(DoctorMapper.INSTANCE.toDoctor(doctorPostRequestBody));

        return "Doctor saved successful";
    }

    public void deleteByCrm(String crm) {

        doctorRepository.deleteByCrm(crm);

    }

    private DoctorDtoViewAll convertDoctorDto(Doctor doctor){
        DoctorDtoViewAll dtoViewAll = new DoctorDtoViewAll();

        dtoViewAll.setAppointments(Collections.emptyList());
        dtoViewAll.setStays(Collections.emptyList());

        dtoViewAll.setId(doctor.getId());
        dtoViewAll.setCrm(doctor.getCrm());
        dtoViewAll.setLastName(doctor.getLastName());
        dtoViewAll.setFirstName(doctor.getFirstName());


        return dtoViewAll;
    }


}

