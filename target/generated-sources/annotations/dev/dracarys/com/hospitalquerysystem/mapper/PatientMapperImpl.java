package dev.dracarys.com.hospitalquerysystem.mapper;

import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import dev.dracarys.com.hospitalquerysystem.requests.employee.patient.PatientsPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.employee.patient.PatientsPutRequestBody;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-14T18:32:20-0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.16.1 (Amazon.com Inc.)"
)
@Component
public class PatientMapperImpl extends PatientMapper {

    @Override
    public Patients toPatient(PatientsPostRequestBody patientsPostRequestBody) {
        if ( patientsPostRequestBody == null ) {
            return null;
        }

        Patients patients = new Patients();

        patients.setFirstName( patientsPostRequestBody.getFirstName() );
        patients.setLastName( patientsPostRequestBody.getLastName() );
        patients.setCpf( patientsPostRequestBody.getCpf() );
        patients.setBirthdate( patientsPostRequestBody.getBirthdate() );
        patients.setPhone( patientsPostRequestBody.getPhone() );
        patients.setSusCard( patientsPostRequestBody.getSusCard() );

        return patients;
    }

    @Override
    public Patients toPatient(PatientsPutRequestBody patientsPutRequestBody) {
        if ( patientsPutRequestBody == null ) {
            return null;
        }

        Patients patients = new Patients();

        patients.setId( patientsPutRequestBody.getId() );
        patients.setFirstName( patientsPutRequestBody.getFirstName() );
        patients.setLastName( patientsPutRequestBody.getLastName() );
        patients.setCpf( patientsPutRequestBody.getCpf() );
        patients.setBirthdate( patientsPutRequestBody.getBirthdate() );
        patients.setPhone( patientsPutRequestBody.getPhone() );
        patients.setSusCard( patientsPutRequestBody.getSusCard() );

        return patients;
    }
}
