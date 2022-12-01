package dev.dracarys.com.hospitalquerysystem.mapper;


import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import dev.dracarys.com.hospitalquerysystem.requests.patient.PatientPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.patient.PatientPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class PatientMapper {

    public static final PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    public abstract Patients toPatient(PatientPostRequestBody patientsPostRequestBody);

    public abstract Patients toPatient(PatientPutRequestBody patientsPutRequestBody);
}
