package dev.dracarys.com.hospitalquerysystem.mapper;

import dev.dracarys.com.hospitalquerysystem.dominio.employee.Attendant;
import dev.dracarys.com.hospitalquerysystem.requests.employee.attendant.AttendantPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.employee.attendant.AttendantPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AttendantMapper {
    public static final AttendantMapper INSTANCE = Mappers.getMapper(AttendantMapper.class);

    public abstract Attendant toAttendant(AttendantPostRequestBody attendantPostRequestBody);

    public abstract Attendant toAttendant(AttendantPutRequestBody attendantPutRequestBody);

}
