package dev.dracarys.com.hospitalquerysystem.service;

import dev.dracarys.com.hospitalquerysystem.dominio.Appointments;
import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import dev.dracarys.com.hospitalquerysystem.repository.AppointmentsRepository;
import dev.dracarys.com.hospitalquerysystem.requests.appointments.AppointmentsDto;
import dev.dracarys.com.hospitalquerysystem.requests.doctor.DoctorDto;
import dev.dracarys.com.hospitalquerysystem.mapper.DoctorMapper;
import dev.dracarys.com.hospitalquerysystem.repository.PatientsRepository;
import dev.dracarys.com.hospitalquerysystem.repository.DoctorRepository;
import dev.dracarys.com.hospitalquerysystem.requests.doctor.DoctorPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.doctor.DoctorPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorServices {

    private final DoctorRepository doctorRepository;
    private final PatientsRepository patientsRepository;
    private final AppointmentsRepository appointmentsRepository;

    ModelMapper modelMapper = new ModelMapper();

    public ResponseEntity<DoctorDto> findByCrm(Long crm) {
        Optional<Doctor> doctorNonPageable = doctorRepository.findByCrmNonPageable(crm);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);


        if (doctorNonPageable.isPresent()) {

            List<Appointments> appointments = appointmentsRepository.findByDoctorId(doctorNonPageable.get());
            Type listType = new TypeToken<List<AppointmentsDto>>() {
            }.getType();
            List<AppointmentsDto> appointmentsDtoList = modelMapper.map(appointments, listType);


            DoctorDto doctorDtoView = modelMapper.map(doctorNonPageable.get(), DoctorDto.class);

            appointmentsDtoList.forEach(appointmentsDto -> appointmentsDto.setPatientName(
                    patientsRepository.findById(appointmentsDto.getPatientId()).orElseThrow().getFirstName() +
                            " " + patientsRepository.findById(appointmentsDto.getPatientId()).orElseThrow().getLastName()));

            appointmentsDtoList.forEach(appointmentsDto -> appointmentsDto.setDoctorName(
                    doctorRepository.findById(doctorNonPageable.get().getId()).orElseThrow().getFirstName() +
                            " " + doctorRepository.findById(doctorNonPageable.get().getId()).orElseThrow().getLastName()));

            doctorDtoView.setAppointmentsView(appointmentsDtoList);

            return new ResponseEntity<>(doctorDtoView, HttpStatus.OK);
        }


        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    public Page<Doctor> listAllDoctors(Pageable pageable) {
        return doctorRepository.findAll(pageable);
    }


    public ResponseEntity<Doctor> replace(DoctorPutRequestBody doctorPutRequestBody) {
        if (doctorRepository.findByCrmOptional(doctorPutRequestBody.getCrm()).isPresent()) {
            Doctor doctor = DoctorMapper.INSTANCE.toDoctor(doctorPutRequestBody);
            doctorRepository.save(doctor);
            return new ResponseEntity<>(doctor, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<Object> save(DoctorPostRequestBody doctorPostRequestBody) {

        Optional<Doctor> doctorToBeSave = doctorRepository.findByCrmNonPageable(doctorPostRequestBody.getCrm());

        if (doctorToBeSave.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("It was not possible to save the doctor, because there is already a doctor with the same "
                            + doctorPostRequestBody.getCrm() + " registered");
        }


        doctorRepository.save(DoctorMapper.INSTANCE.toDoctor(doctorPostRequestBody));
        return ResponseEntity.status(HttpStatus.CREATED).body("Doctor save successfully");
    }

    @Transactional
    public ResponseEntity<Object> deleteByCrm(Long crm) {
        Optional<Doctor> doctorToBeDelete = doctorRepository.findByCrmOptional(crm);
        if (doctorToBeDelete.isPresent()) {
            doctorRepository.deleteByCrm(crm);
            return ResponseEntity.status(HttpStatus.OK).body("Doctor delete successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor Not Found");
    }

    public static boolean isNumber(String s) {
        if (s == null) {
            return false;
        }
        try {
            Long.parseLong(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


}

