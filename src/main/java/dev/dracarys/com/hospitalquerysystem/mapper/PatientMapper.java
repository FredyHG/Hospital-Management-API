package dev.dracarys.com.hospitalquerysystem.mapper;


import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import dev.dracarys.com.hospitalquerysystem.requests.employee.patient.PatientsPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.employee.patient.PatientsPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class PatientMapper {

    public static final PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    public abstract Patients toPatient(PatientsPostRequestBody patientsPostRequestBody);

    public abstract Patients toPatient(PatientsPutRequestBody patientsPutRequestBody);
}
