package dev.fredyhg.com.hospitalquerysystem.service;

import dev.fredyhg.com.hospitalquerysystem.models.Doctor;
import dev.fredyhg.com.hospitalquerysystem.models.Patient;
import dev.fredyhg.com.hospitalquerysystem.models.Stay;
import dev.fredyhg.com.hospitalquerysystem.exception.stay.InvalidStayDate;
import dev.fredyhg.com.hospitalquerysystem.exception.stay.StayNotFound;
import dev.fredyhg.com.hospitalquerysystem.mapper.ModelMappers;
import dev.fredyhg.com.hospitalquerysystem.repository.StayRepository;
import dev.fredyhg.com.hospitalquerysystem.models.requests.stay.StayDeleteRequestBody;
import dev.fredyhg.com.hospitalquerysystem.models.requests.stay.StayGetReturnObject;
import dev.fredyhg.com.hospitalquerysystem.models.requests.stay.StayPostRequestBody;
import dev.fredyhg.com.hospitalquerysystem.models.requests.stay.StayPutRequestBody;
import dev.fredyhg.com.hospitalquerysystem.utils.ConvertLocalDateToDateType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StayServices {

    private final DoctorServices doctorServices;

    private final PatientServices patientServices;

    private final StayRepository stayRepository;

    public void createNewStay(StayPostRequestBody stayPostRequestBody){

        if(new Date().after(ConvertLocalDateToDateType.convertFrom(stayPostRequestBody.getStayDate()))){
            throw new InvalidStayDate("Invalid Stay date");
        }

        Doctor doctor = doctorServices.ensureDoctorExists(stayPostRequestBody.getCrmDoctor());

        Patient patient = patientServices.ensurePatientExists(stayPostRequestBody.getCpfPatient());

        Stay stayToBeSaved = ModelMappers.stayPostRequestToStayModel(stayPostRequestBody, doctor, patient);

        stayRepository.save(stayToBeSaved);
    }

    public List<StayGetReturnObject> listAllStays() {

        List<Stay> stays = stayRepository.findAll();

        return stays.stream().map(ModelMappers::stayModelToStayGetObject).toList();
    }

    @Transactional
    public void editStayInfo(StayPutRequestBody stayPutRequestBody) {
        if(new Date().after(ConvertLocalDateToDateType.convertFrom(stayPutRequestBody.getStayDate()))){
            throw new InvalidStayDate("Invalid stay date");
        }

        Doctor doctor = doctorServices.ensureDoctorExists(stayPutRequestBody.getCrmDoctor());
        Patient patient = patientServices.ensurePatientExists(stayPutRequestBody.getCpfPatient());

        Stay stayToBeEdited = ensureStayExistsByDoctorAndPatient(doctor, patient);

        Stay stayToBeSaved = ModelMappers.stayPutRequestToStayModel(stayPutRequestBody, stayToBeEdited);

        stayRepository.save(stayToBeSaved);
    }

    @Transactional
    public void deleteStay(StayDeleteRequestBody stayDeleteRequestBody) {
        Doctor doctor = doctorServices.ensureDoctorExists(stayDeleteRequestBody.getCrmDoctor());
        Patient patient = patientServices.ensurePatientExists(stayDeleteRequestBody.getCpfPatient());

        Stay stayToBeDeleted = ensureStayExistsByDoctorAndPatient(doctor, patient);

        stayRepository.delete(stayToBeDeleted);
    }


    public List<StayGetReturnObject> findStayByDoctor(Doctor doctor) {

        Doctor doctorExists = doctorServices.ensureDoctorExists(doctor.getCrm());

        List<Stay> stays = stayRepository.findByDoctor(doctorExists);

        return stays.stream().map(ModelMappers::stayModelToStayGetObject).toList();
    }

    public Stay ensureStayExistsByDoctorAndPatient(Doctor doctor, Patient patient){
        return stayRepository.findByDoctorAndPatient(doctor, patient).orElseThrow(() -> new StayNotFound("Stay not found"));
    }
}

