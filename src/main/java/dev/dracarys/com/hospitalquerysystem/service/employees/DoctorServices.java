package dev.dracarys.com.hospitalquerysystem.service.employees;

import dev.dracarys.com.hospitalquerysystem.dominio.employee.Doctor;
import dev.dracarys.com.hospitalquerysystem.mapper.DoctorMapper;
import dev.dracarys.com.hospitalquerysystem.repository.employee.DoctorRepository;
import dev.dracarys.com.hospitalquerysystem.requests.employee.doctor.DoctorPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.employee.doctor.DoctorPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorServices {

    private final DoctorRepository doctorRepository;



    public ResponseEntity<Page<Doctor>> findByCrmOrName(String crmOrName, Pageable pageable){
        boolean verify = isNumber(crmOrName);

        Page<Doctor> doctorPageByName = doctorRepository.findByFirstName(crmOrName, pageable);

        if(verify) {
            Page<Doctor> doctorByCrm = doctorRepository.findByCrm(Long.parseLong(crmOrName), pageable);
            if(!doctorByCrm.isEmpty()){
                return new ResponseEntity<>(doctorByCrm, HttpStatus.OK);
            }
        }

        if(!doctorPageByName.isEmpty()){
            return new ResponseEntity<>(doctorPageByName, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    public Page<Doctor> listAllDoctors(Pageable pageable){
        return doctorRepository.findAll(pageable);
    }


    public ResponseEntity<Doctor> replace(DoctorPutRequestBody doctorPutRequestBody) {
        if(doctorRepository.findByCrmOptional(doctorPutRequestBody.getCrm()).isPresent()){
        Doctor doctor = DoctorMapper.INSTANCE.toDoctor(doctorPutRequestBody);
        doctorRepository.save(doctor);
        return new ResponseEntity<>(doctor, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<Object> save(DoctorPostRequestBody doctorPostRequestBody){

        Optional<Doctor> doctorToBeSave = doctorRepository.findByCrmNonPageable(doctorPostRequestBody.getCrm());

        if(doctorToBeSave.isPresent()){
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
        if(doctorToBeDelete.isPresent()){
            doctorRepository.deleteByCrm(crm);
            return ResponseEntity.status(HttpStatus.OK).body("Doctor delete successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor Not Found");
    }

    public static boolean isNumber(String s){
        if(s==null){
            return false;
        }
        try {
           Long.parseLong(s);
        } catch (NumberFormatException e){
            return false;
        }
        return true;
    }
}

