package dev.dracarys.com.hospitalquerysystem.mapper;

import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import dev.dracarys.com.hospitalquerysystem.requests.doctor.DoctorPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.doctor.DoctorPutRequestBody;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-07T22:34:39-0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class DoctorMapperImpl extends DoctorMapper {

    @Override
    public Doctor toDoctor(DoctorPostRequestBody doctorPostRequestBody) {
        if ( doctorPostRequestBody == null ) {
            return null;
        }

        Doctor.DoctorBuilder doctor = Doctor.builder();

        doctor.firstName( doctorPostRequestBody.getFirstName() );
        doctor.lastName( doctorPostRequestBody.getLastName() );
        doctor.crm( doctorPostRequestBody.getCrm() );

        return doctor.build();
    }

    @Override
    public Doctor toDoctor(DoctorPutRequestBody doctorPutRequestBody) {
        if ( doctorPutRequestBody == null ) {
            return null;
        }

        Doctor.DoctorBuilder doctor = Doctor.builder();

        doctor.id( doctorPutRequestBody.getId() );
        doctor.firstName( doctorPutRequestBody.getFirstName() );
        doctor.lastName( doctorPutRequestBody.getLastName() );
        doctor.crm( doctorPutRequestBody.getCrm() );

        return doctor.build();
    }
}
