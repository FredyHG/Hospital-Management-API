package dev.dracarys.com.hospitalquerysystem.service;

import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import dev.dracarys.com.hospitalquerysystem.dominio.Stay;
import dev.dracarys.com.hospitalquerysystem.mapper.StayMapper;
import dev.dracarys.com.hospitalquerysystem.repository.DoctorRepository;
import dev.dracarys.com.hospitalquerysystem.repository.PatientsRepository;
import dev.dracarys.com.hospitalquerysystem.repository.StayRepository;
import dev.dracarys.com.hospitalquerysystem.requests.stay.StayDeleteRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.stay.StayDto;
import dev.dracarys.com.hospitalquerysystem.requests.stay.StayPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.stay.StayPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StayServices {



    private final DoctorRepository doctorRepository;
    private final PatientsRepository patientsRepository;
    private final StayRepository stayRepository;
    ModelMapper modelMapper = new ModelMapper();



    public ResponseEntity<Object> createNewStay(StayPostRequestBody stayPostRequestBody){

        Stay stayToBeSaved = StayMapper.INSTANCE.toStay(stayPostRequestBody);

        if (stayPostRequestBody.getCrmDoctor() != null && stayPostRequestBody.getCpfPatient() != null) {

            Optional<Doctor> doctorToBeSaved = doctorRepository.findByCrm(stayPostRequestBody.getCrmDoctor());
            Optional<Patients> patientsToBeSaved = patientsRepository.findByCpf(stayPostRequestBody.getCpfPatient());


            if (doctorToBeSaved.isPresent() && patientsToBeSaved.isPresent()) {
                Optional<Stay> stayExists = stayRepository.findByDoctorAndPatient(doctorToBeSaved.get(), patientsToBeSaved.get());

                if (stayExists.isPresent()) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("There is already a pending stay for this patient");
                }
            }


            if (doctorToBeSaved.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found");
            }

            if (patientsToBeSaved.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found");
            }

            stayToBeSaved.setDoctor(doctorToBeSaved.get());
            stayToBeSaved.setPatient(patientsToBeSaved.get());

            stayRepository.save(stayToBeSaved);

            return ResponseEntity.status(HttpStatus.CREATED).body("Stay create successfully!");
        }


        return ResponseEntity.status(HttpStatus.CONFLICT).body("Check the information and try again");



    }

    public List<StayDto> listAllStays() {

        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        List<Stay> stays = stayRepository.findAll();

        Type listType = new TypeToken<List<StayDto>>() {}.getType();

        List<StayDto> staysDtoList = modelMapper.map(stays, listType);


        staysDtoList.forEach(staysDto -> staysDto.setPatientName(
                patientsRepository.findById(staysDto.getPatientId()).orElseThrow().getFirstName() +
                        " " + patientsRepository.findById(staysDto.getPatientId()).orElseThrow().getLastName()));

        staysDtoList.forEach(staysDto -> staysDto.setDoctorName(
                doctorRepository.findById(staysDto.getDoctorId()).orElseThrow().getFirstName() +
                        " " + doctorRepository.findById(staysDto.getDoctorId()).orElseThrow().getLastName()));

        return staysDtoList;


    }

     public ResponseEntity<Object> editStayInfo(StayPutRequestBody stayPutRequestBody) {
         Optional<Doctor> doctor = doctorRepository.findByCrm(stayPutRequestBody.getCrmDoctor());
         Optional<Patients> patients = patientsRepository.findByCpf(stayPutRequestBody.getCpfPatient());

         if (doctor.isPresent() && patients.isPresent()){
            Optional<Stay>  stay = stayRepository.findByDoctorAndPatient(doctor.get(),patients.get());

            if(stay.isPresent()){
                stay.get().setStayDate(stayPutRequestBody.getStayDate());
                stay.get().setDescription(stayPutRequestBody.getDescription());
                stay.get().setPatient(patients.get());
                stay.get().setDoctor(doctor.get());
                stay.get().setStatus(stayPutRequestBody.getStatus());
                stay.get().setDrugAllergy(stayPutRequestBody.getDrugAllergy());

                return new ResponseEntity<>(stayRepository.save(stay.get()), HttpStatus.OK);
            }
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body("Stay not found, check the parameters and try again");
     }

    public ResponseEntity<Object> deleteStay(StayDeleteRequestBody stayDeleteRequestBody) {
        Optional<Doctor> doctorToBeDelete = doctorRepository.findByCrm(stayDeleteRequestBody.getCrmDoctor());

        Optional<Patients> patientToBeDelete = patientsRepository.findByCpf(stayDeleteRequestBody.getCpfPatient());

        if(doctorToBeDelete.isPresent() && patientToBeDelete.isPresent()){
            Optional<Stay> stayToBeDelete = stayRepository.findByDoctorAndPatient(
                    doctorToBeDelete.get(), patientToBeDelete.get());

            if(stayToBeDelete.isPresent()){
               stayRepository.delete(stayToBeDelete.get());
               return ResponseEntity.status(HttpStatus.OK).body("Stay delete successfully");
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Stay not found");
        }


        return ResponseEntity.status(HttpStatus.CONFLICT).body("Check parameters and try again");
    }
}
