package dev.dracarys.com.hospitalquerysystem.mapper;

import dev.dracarys.com.hospitalquerysystem.dominio.employee.Doctor;
import dev.dracarys.com.hospitalquerysystem.requests.employee.doctor.DoctorPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.employee.doctor.DoctorPutRequestBody;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-13T07:19:18-0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.16.1 (Amazon.com Inc.)"
)
@Component
public class DoctorMapperImpl extends DoctorMapper {

    @Override
    public Doctor toDoctor(DoctorPostRequestBody doctorPostRequestBody) {
        if ( doctorPostRequestBody == null ) {
            return null;
        }

        Doctor doctor = new Doctor();

        doctor.setFirstName( doctorPostRequestBody.getFirstName() );
        doctor.setLastName( doctorPostRequestBody.getLastName() );
        doctor.setCrm( doctorPostRequestBody.getCrm() );

        return doctor;
    }

    @Override
    public Doctor toDoctor(DoctorPutRequestBody doctorPutRequestBody) {
        if ( doctorPutRequestBody == null ) {
            return null;
        }

        Doctor doctor = new Doctor();

        doctor.setId( doctorPutRequestBody.getId() );
        doctor.setFirstName( doctorPutRequestBody.getFirstName() );
        doctor.setLastName( doctorPutRequestBody.getLastName() );
        doctor.setCrm( doctorPutRequestBody.getCrm() );

        return doctor;
    }
}
