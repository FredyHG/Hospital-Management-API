package dev.dracarys.com.hospitalquerysystem.controller.employee;

import dev.dracarys.com.hospitalquerysystem.dominio.employee.HeadNurse;
import dev.dracarys.com.hospitalquerysystem.requests.employee.headnurse.HeadNursePostBody;
import dev.dracarys.com.hospitalquerysystem.requests.employee.headnurse.HeadNursePutBody;
import dev.dracarys.com.hospitalquerysystem.service.employees.HeadNurseServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/hospital/headnurse")
@Log4j2
@RequiredArgsConstructor
public class HeadNurseController {

    private final HeadNurseServices headNurseServices;

    @GetMapping("/find/{corenOrName}")
    public ResponseEntity<Page<HeadNurse>> findByCoren(@PathVariable String corenOrName, Pageable pageable){
        return headNurseServices.findByCorenOrName(corenOrName, pageable);
    }

    @GetMapping("/list")
    public ResponseEntity<Page<HeadNurse>> listAllHeadNurse(Pageable pageable){
        return headNurseServices.listAll(pageable);
    }

    @PostMapping("/save")
    public ResponseEntity<Object> save(@RequestBody @Valid HeadNursePostBody headNursePostBody) {
        return headNurseServices.saveNewHeadNurse(headNursePostBody);
    }

    @PutMapping("/admin/update")
    public ResponseEntity<HeadNurse> replaceHeadNurse(@RequestBody HeadNursePutBody headNursePutBody){
        return headNurseServices.replace(headNursePutBody);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Object> deleteHeadNurseByCoren(@PathVariable Long id){
        return headNurseServices.deleteHeadNurseCorenId(id);
    }

}
