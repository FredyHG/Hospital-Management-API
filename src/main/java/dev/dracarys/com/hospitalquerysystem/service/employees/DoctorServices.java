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



    public ResponseEntity<Page<Doctor>> findByCrmOrName(String crmOrName, Pageable pageable){
        Page<Doctor> doctorOptional = doctorRepository.findByCrm(crmOrName, pageable);
        Page<Doctor> doctorOptionalName = doctorRepository.findByFirstName(crmOrName, pageable);

        if(!doctorOptional.isEmpty()){
            return new ResponseEntity<>(doctorOptional, HttpStatus.OK);
        }

        if(!doctorOptionalName.isEmpty()){
            return new ResponseEntity<>(doctorOptionalName, HttpStatus.OK);
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
}

