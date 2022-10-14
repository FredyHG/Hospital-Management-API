package dev.dracarys.com.hospitalquerysystem.service.employees;

import dev.dracarys.com.hospitalquerysystem.dominio.employee.Attendant;
import dev.dracarys.com.hospitalquerysystem.mapper.AttendantMapper;
import dev.dracarys.com.hospitalquerysystem.repository.employee.AttendantRepository;
import dev.dracarys.com.hospitalquerysystem.requests.employee.attendant.AttendantPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.employee.attendant.AttendantPutRequestBody;
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
public class AttendantServices {

    private final AttendantRepository attendantRepository;

    @Transactional
    public ResponseEntity<Object> save(AttendantPostRequestBody attendantPostRequestBody){

        Optional<Attendant> optionalAttendant = attendantRepository.findByIdAttendant(attendantPostRequestBody.getAttendantId());

        if(optionalAttendant.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The attendant id is already registered.");
        }

        attendantRepository.save(AttendantMapper.INSTANCE.toAttendant(attendantPostRequestBody));

        return ResponseEntity.status(HttpStatus.CREATED).body("Attendant created successfully.");
    }




    public ResponseEntity<Object> findByIdAttendant(Long id){
        Optional<Attendant> attendantsOptional = attendantRepository.findByIdAttendant(id);

        if(attendantsOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Attendant not found");
        }

        return new ResponseEntity<>(attendantsOptional, HttpStatus.OK);

    }


    public ResponseEntity<Page<Attendant>> findByNameOrAttendantId(String firstNameOrAttendantId, Pageable pageable){

        boolean verify = isNumber(firstNameOrAttendantId);

        Page<Attendant> attendantsOptionalName = attendantRepository.findByFirstName(firstNameOrAttendantId, pageable);

        if(verify) {
            Page<Attendant> attendantsOptionalId = attendantRepository.findByIdAttendantPageable(Long.parseLong(firstNameOrAttendantId), pageable);
            if(!attendantsOptionalId.isEmpty()){
                return new ResponseEntity<>(attendantsOptionalId, HttpStatus.OK);
            }
        }

        if(!attendantsOptionalName.isEmpty()){
            return new ResponseEntity<>(attendantsOptionalName, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    public Page<Attendant> listAllAttendant(Pageable pageable){
        return attendantRepository.findAll(pageable);
    }


    @Transactional
    public ResponseEntity<Attendant> replace(AttendantPutRequestBody attendantPutRequestBody) {

        Optional<Attendant> optionalAttendantByIDAttendant = attendantRepository.findByIdAttendant(attendantPutRequestBody.getAttendantId());

        if(optionalAttendantByIDAttendant.isPresent()){

        Attendant attendant = AttendantMapper.INSTANCE.toAttendant(attendantPutRequestBody);

        attendantRepository.deleteByAttendantId(optionalAttendantByIDAttendant.get().getAttendantId());

        attendantRepository.save(attendant);

        return new ResponseEntity<>(attendant, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<Object> deleteByAttendantId(Long id){
        Optional<Attendant> attendantToBeDelete = attendantRepository.findByIdAttendant(id);
        if(attendantToBeDelete.isPresent()) {
            attendantRepository.deleteByAttendantId(id);
            return ResponseEntity.status(HttpStatus.OK).body("Attendant delete successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Attendant Not Found");

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

