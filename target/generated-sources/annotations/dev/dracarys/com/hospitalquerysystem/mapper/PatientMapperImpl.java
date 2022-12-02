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
    date = "2022-12-01T22:56:09-0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
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
        patients.susCard( patientsPostRequestBody.getSusCard() );

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
        patients.birthdate( patientsPutRequestBody.getBirthdate() );
        patients.phone( patientsPutRequestBody.getPhone() );
        patients.susCard( patientsPutRequestBody.getSusCard() );

        return patients.build();
    }
}
