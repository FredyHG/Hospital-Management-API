package dev.dracarys.com.hospitalquerysystem.controller.patients;

public class PatientsController {

//    private final PatientService patientService;
//
//    @GetMapping("/{name}")
//    public List<Patients> findByName(@PathVariable String name) {
//        return patientService.findByName(name);
//    }
//
//    @GetMapping("/list")
//    public ResponseEntity<Page<Patients>> listAllPatients(Pageable pageable){
//        return ResponseEntity.ok(patientService.listAllPatients(pageable));
//    }
//
//    @GetMapping("/{firstName}/{lastName}")
//    public List<Patients> findByFirstNameAndLastName(@PathVariable String firstName, @PathVariable String lastName) {
//        return patientService.findByFirstNameAndLastName(firstName, lastName);
//    }
//
//    @PostMapping("/create/")
//    public ResponseEntity<Object> save(@RequestBody @Valid PatientsPostRequestBody patients){
//        Optional<Patients> patientToBeSave = patientService.findByCpf(patients.getCpf());
//
//        if(patientToBeSave.isPresent()){
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("The patient is already registered");
//        }
//        return new ResponseEntity<>(patientService.save(patients), HttpStatus.CREATED);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Object> save(@PathVariable Long id){
//        Optional<Patients> patientToBeDelete = patientService.findById(id);
//        if(patientToBeDelete.isEmpty()){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found");
//        }
//        patientService.delete(patientToBeDelete.get());
//        return ResponseEntity.status(HttpStatus.OK).body("Patient delete successfully");
//    }
//
//    @DeleteMapping("/delete/cpf/{cpf}")
//    public ResponseEntity<Object> save(@PathVariable String cpf){
//        Optional<Patients> patientToBeDelete = patientService.findByCpf(cpf);
//        if(patientToBeDelete.isEmpty()){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found");
//        }
//        patientService.delete(patientToBeDelete.get());
//        return ResponseEntity.status(HttpStatus.OK).body("Patient delete successfully");
//    }


}
