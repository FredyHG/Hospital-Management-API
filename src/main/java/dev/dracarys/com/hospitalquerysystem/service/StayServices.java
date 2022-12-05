package dev.dracarys.com.hospitalquerysystem.service;

import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import dev.dracarys.com.hospitalquerysystem.dominio.Stay;
import dev.dracarys.com.hospitalquerysystem.mapper.StayMapper;
import dev.dracarys.com.hospitalquerysystem.repository.DoctorRepository;
import dev.dracarys.com.hospitalquerysystem.repository.PatientsRepository;
import dev.dracarys.com.hospitalquerysystem.repository.StayRepository;
import dev.dracarys.com.hospitalquerysystem.requests.stay.StayDeleteRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.stay.StayGetReturnObject;
import dev.dracarys.com.hospitalquerysystem.requests.stay.StayPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.stay.StayPutRequestBody;
import dev.dracarys.com.hospitalquerysystem.util.ConvertLocalDateToDateType;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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

    public void createNewStay(StayPostRequestBody stayPostRequestBody){

        Stay stayToBeSaved = StayMapper.INSTANCE.toStay(stayPostRequestBody);

        stayToBeSaved.setStayDate(ConvertLocalDateToDateType.convertFrom(stayPostRequestBody.getStayDate()));

        if (stayPostRequestBody.getCrmDoctor() != null && stayPostRequestBody.getCpfPatient() != null) {

            Optional<Doctor> doctorToBeSaved = doctorRepository.findByCrm(stayPostRequestBody.getCrmDoctor());
            Optional<Patients> patientsToBeSaved = patientsRepository.findByCpf(stayPostRequestBody.getCpfPatient());

            if(doctorToBeSaved.isPresent() && patientsToBeSaved.isPresent()){
                stayToBeSaved.setDoctor(doctorToBeSaved.get());
                stayToBeSaved.setPatient(patientsToBeSaved.get());

                stayRepository.save(stayToBeSaved);
            }


        }


    }

    public List<StayGetReturnObject> listAllStays() {

        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        List<Stay> stays = stayRepository.findAll();

        Type listType = new TypeToken<List<StayGetReturnObject>>() {}.getType();

        List<StayGetReturnObject> staysDtoList = modelMapper.map(stays, listType);

        staysDtoList.forEach(staysDto -> staysDto.setPatientName(
                patientsRepository.findById(staysDto.getPatientId()).orElseThrow().getFirstName() +
                        " " + patientsRepository.findById(staysDto.getPatientId()).orElseThrow().getLastName()));

        staysDtoList.forEach(staysDto -> staysDto.setDoctorName(
                doctorRepository.findById(staysDto.getDoctorId()).orElseThrow().getFirstName() +
                        " " + doctorRepository.findById(staysDto.getDoctorId()).orElseThrow().getLastName()));

        return staysDtoList;
    }

     public Optional<Stay> editStayInfo(StayPutRequestBody stayPutRequestBody) {
         Optional<Doctor> doctor = doctorRepository.findByCrm(stayPutRequestBody.getCrmDoctor());
         Optional<Patients> patients = patientsRepository.findByCpf(stayPutRequestBody.getCpfPatient());

         if (doctor.isPresent() && patients.isPresent()){
            Optional<Stay>  stay = stayRepository.findByDoctorAndPatient(doctor.get(),patients.get());

            if(stay.isPresent()){
                stay.get().setStayDate(ConvertLocalDateToDateType.convertFrom(stayPutRequestBody.getStayDate()));
                stay.get().setDescription(stayPutRequestBody.getDescription());
                stay.get().setPatient(patients.get());
                stay.get().setDoctor(doctor.get());
                stay.get().setStatus(stayPutRequestBody.getStatus());
                stay.get().setDrugAllergy(stayPutRequestBody.getDrugAllergy());


                return Optional.of(stayRepository.save(stay.get()));
            }
        }

        return Optional.empty();
     }

    public void deleteStay(StayDeleteRequestBody stayDeleteRequestBody) {
        Optional<Doctor> doctorToBeDelete = doctorRepository.findByCrm(stayDeleteRequestBody.getCrmDoctor());

        Optional<Patients> patientToBeDelete = patientsRepository.findByCpf(stayDeleteRequestBody.getCpfPatient());

        if(doctorToBeDelete.isPresent() && patientToBeDelete.isPresent()){
            Optional<Stay> stayToBeDelete = stayRepository.findByDoctorAndPatient(
                    doctorToBeDelete.get(), patientToBeDelete.get());

            stayToBeDelete.ifPresent(stayRepository::delete);
        }
    }

    public Optional<Stay> findStayByDoctorAndPatient(Doctor doctor, Patients patients){
        return stayRepository.findByDoctorAndPatient(doctor, patients);
    }
}

