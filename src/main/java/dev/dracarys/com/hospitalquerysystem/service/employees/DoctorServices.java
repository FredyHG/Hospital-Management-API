package dev.dracarys.com.hospitalquerysystem.service.employees;

import dev.dracarys.com.hospitalquerysystem.dominio.employee.Doctor;
import dev.dracarys.com.hospitalquerysystem.mapper.DoctorMapper;
import dev.dracarys.com.hospitalquerysystem.repository.employee.DoctorRepository;
import dev.dracarys.com.hospitalquerysystem.requests.employee.doctor.DoctorPostRequestBody;
import lombok.RequiredArgsConstructor;
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

        Optional<Doctor> doctorToBeSave = doctorRepository.findByCrm(doctorPostRequestBody.getCrm());

        if(doctorToBeSave.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("It was not possible to save the doctor, because there is already a doctor with the same "
                            + doctorPostRequestBody.getCrm() + " registered");
        }


        doctorRepository.save(DoctorMapper.INSTANCE.toDoctor(doctorPostRequestBody));
        return ResponseEntity.status(HttpStatus.CREATED).body("Doctor save successfully");
    }



    public Optional<Doctor> findByCrm(String crm){
        return doctorRepository.findByCrm(crm);
    }


}
