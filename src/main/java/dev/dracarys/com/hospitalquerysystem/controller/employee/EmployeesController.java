package dev.dracarys.com.hospitalquerysystem.controller.employee;

public class EmployeesController {

//    private final EmployeeService employeeService;
//
//
//    @GetMapping
//    public ResponseEntity<Page<Employees>> listAllEmployees(Pageable pageable){
//        return ResponseEntity.ok(employeeService.listAllEmployees(pageable));
//    }
//
//    @PostMapping("/admin/create")
//    public ResponseEntity<Object> save(@RequestBody @Valid EmployeePostRequestBody employeePostRequestBody){
//
//        Optional<Employees> employeeToBeSave = employeeService.findByUser(employeePostRequestBody.getUser());
//
//        if(employeeToBeSave.isEmpty()){
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("The user is already registered");
//        }
//        return new ResponseEntity<>(employeeService.save(employeePostRequestBody), HttpStatus.CREATED);
//    }
//
//    @PutMapping("/admin/replace")
//    public ResponseEntity<Object> replace(@RequestBody EmployeePutRequestBody employeePutRequestBody){
//        if(employeeService.findEmployeeByID(employeePutRequestBody.getId()).isEmpty()){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
//        }
//        return ResponseEntity.status(HttpStatus.OK).body(employeeService.replace(employeePutRequestBody));
//    }
//
//    @DeleteMapping("/admin/delete/{id}")
//    public ResponseEntity<Object> delete(@PathVariable long id){
//
//        Optional<Employees> employeeToBeDelete = employeeService.findEmployeeByID(id);
//        if(employeeToBeDelete.isEmpty()){
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
//        }
//        employeeService.delete(employeeToBeDelete.get());
//        return ResponseEntity.status(HttpStatus.OK).body("Employee delete successfully");
//    }
}
