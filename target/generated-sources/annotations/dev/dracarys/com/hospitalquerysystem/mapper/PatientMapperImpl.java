package dev.dracarys.com.hospitalquerysystem.mapper;

import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import dev.dracarys.com.hospitalquerysystem.requests.patient.PatientPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.patient.PatientPutRequestBody;
import java.time.ZoneOffset;
import java.util.Date;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-09T21:23:29-0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class PatientMapperImpl extends PatientMapper {

    @Override
    public Patients toPatient(PatientPostRequestBody patientsPostRequestBody) {
        if ( patientsPostRequestBody == null ) {
            return null;
        }

        Patients.PatientsBuilder patients = Patients.builder();

        patients.firstName( patientsPostRequestBody.getFirstName() );
        patients.lastName( patientsPostRequestBody.getLastName() );
        patients.cpf( patientsPostRequestBody.getCpf() );
        if ( patientsPostRequestBody.getBirthdate() != null ) {
            patients.birthdate( Date.from( patientsPostRequestBody.getBirthdate().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        patients.phone( patientsPostRequestBody.getPhone() );

        return patients.build();
    }

    @Override
    public Patients toPatient(PatientPutRequestBody patientsPutRequestBody) {
        if ( patientsPutRequestBody == null ) {
            return null;
        }

        Patients.PatientsBuilder patients = Patients.builder();

        patients.id( patientsPutRequestBody.getId() );
        patients.firstName( patientsPutRequestBody.getFirstName() );
        patients.lastName( patientsPutRequestBody.getLastName() );
        patients.cpf( patientsPutRequestBody.getCpf() );
        if ( patientsPutRequestBody.getBirthdate() != null ) {
            patients.birthdate( Date.from( patientsPutRequestBody.getBirthdate().atStartOfDay( ZoneOffset.UTC ).toInstant() ) );
        }
        patients.phone( patientsPutRequestBody.getPhone() );

        return patients.build();
    }
}
