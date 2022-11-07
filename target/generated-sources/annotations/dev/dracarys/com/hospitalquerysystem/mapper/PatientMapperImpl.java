package dev.dracarys.com.hospitalquerysystem.mapper;

import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import dev.dracarys.com.hospitalquerysystem.requests.patient.PatientsPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.patient.PatientsPutRequestBody;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-11-06T20:01:11-0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class PatientMapperImpl extends PatientMapper {

    @Override
    public Patients toPatient(PatientsPostRequestBody patientsPostRequestBody) {
        if ( patientsPostRequestBody == null ) {
            return null;
        }

        Patients.PatientsBuilder patients = Patients.builder();

        patients.firstName( patientsPostRequestBody.getFirstName() );
        patients.lastName( patientsPostRequestBody.getLastName() );
        patients.cpf( patientsPostRequestBody.getCpf() );
        patients.birthdate( patientsPostRequestBody.getBirthdate() );
        patients.phone( patientsPostRequestBody.getPhone() );
        patients.susCard( patientsPostRequestBody.getSusCard() );

        return patients.build();
    }

    @Override
    public Patients toPatient(PatientsPutRequestBody patientsPutRequestBody) {
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
