package dev.dracarys.com.hospitalquerysystem.mapper;

import dev.dracarys.com.hospitalquerysystem.dominio.employee.Attendant;
import dev.dracarys.com.hospitalquerysystem.requests.employee.attendant.AttendantPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.employee.attendant.AttendantPutRequestBody;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-10-14T06:11:25-0300",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 11.0.16.1 (Amazon.com Inc.)"
)
@Component
public class AttendantMapperImpl extends AttendantMapper {

    @Override
    public Attendant toAttendant(AttendantPostRequestBody attendantPostRequestBody) {
        if ( attendantPostRequestBody == null ) {
            return null;
        }

        Attendant attendant = new Attendant();

        attendant.setFirstName( attendantPostRequestBody.getFirstName() );
        attendant.setLastName( attendantPostRequestBody.getLastName() );
        attendant.setAttendantId( attendantPostRequestBody.getAttendantId() );

        return attendant;
    }

    @Override
    public Attendant toAttendant(AttendantPutRequestBody attendantPutRequestBody) {
        if ( attendantPutRequestBody == null ) {
            return null;
        }

        Attendant attendant = new Attendant();

        attendant.setFirstName( attendantPutRequestBody.getFirstName() );
        attendant.setLastName( attendantPutRequestBody.getLastName() );
        attendant.setAttendantId( attendantPutRequestBody.getAttendantId() );

        return attendant;
    }
}
