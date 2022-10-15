package dev.dracarys.com.hospitalquerysystem.service.employees;

import dev.dracarys.com.hospitalquerysystem.dominio.employee.HeadNurse;
import dev.dracarys.com.hospitalquerysystem.mapper.HeadNurseMapper;
import dev.dracarys.com.hospitalquerysystem.repository.employee.HeadNurseRepository;
import dev.dracarys.com.hospitalquerysystem.requests.employee.headnurse.HeadNursePostBody;
import dev.dracarys.com.hospitalquerysystem.requests.employee.headnurse.HeadNursePutBody;
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
public class HeadNurseServices {

    private final HeadNurseRepository headNurseRepository;

    public ResponseEntity<Page<HeadNurse>> findByCorenOrName(String corenOrName, Pageable pageable) {

        boolean verify = isNumber(corenOrName);

        Page<HeadNurse> headNursePageByName = headNurseRepository.findByName(corenOrName, pageable);

        if (verify) {
            Page<HeadNurse> headNursePage = headNurseRepository.findByCorenPage(Long.parseLong(corenOrName), pageable);
            if (!headNursePage.isEmpty()) {
                return new ResponseEntity<>(headNursePage, HttpStatus.OK);
            }
        }

        if (!headNursePageByName.isEmpty()) {
            return new ResponseEntity<>(headNursePageByName, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<Object> saveNewHeadNurse(HeadNursePostBody headNursePostBody) {

        Optional<HeadNurse> headNurseToBeSaved = headNurseRepository.findByCoren(headNursePostBody.getCoren());

        if (headNurseToBeSaved.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("HeadNurse already registered");
        }

        headNurseRepository.save(HeadNurseMapper.INSTANCE.tpHeadNurse(headNursePostBody));

        return new ResponseEntity<>(headNurseToBeSaved, HttpStatus.OK);
    }

    public ResponseEntity<Page<HeadNurse>> listAll(Pageable pageable) {
        return new ResponseEntity<>(headNurseRepository.findAll(pageable), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<Object> deleteHeadNurseCorenId(Long id) {

        Optional<HeadNurse> headNurseToBeDelete = headNurseRepository.findByCoren(id);

        if (headNurseToBeDelete.isPresent()) {
            headNurseRepository.delete(headNurseToBeDelete.get());
            return ResponseEntity.status(HttpStatus.OK).body("HeadCurse Delete successfully.");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("HeadCurse not found.");
    }

    @Transactional
    public ResponseEntity<HeadNurse> replace(HeadNursePutBody headNursePutBody) {

        Optional<HeadNurse> optionalHeadNurseByCorenId = headNurseRepository.findByCoren(headNursePutBody.getCoren());

        if (optionalHeadNurseByCorenId.isPresent()) {
            HeadNurse headNurse = HeadNurseMapper.INSTANCE.toHeadNurse(headNursePutBody);

            headNurseRepository.deleteByCoren(headNurse.getCoren());

            headNurseRepository.save(headNurse);

            return new ResponseEntity<>(headNurse, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
