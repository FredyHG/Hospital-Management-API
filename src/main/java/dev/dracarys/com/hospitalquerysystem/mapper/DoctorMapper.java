package dev.dracarys.com.hospitalquerysystem.mapper;

import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import dev.dracarys.com.hospitalquerysystem.requests.doctor.DoctorPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.requests.doctor.DoctorPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class DoctorMapper {
    public static final DoctorMapper INSTANCE = Mappers.getMapper(DoctorMapper.class);

    public abstract Doctor toDoctor(DoctorPostRequestBody doctorPostRequestBody);

    public abstract Doctor toDoctor(DoctorPutRequestBody doctorPutRequestBody);
}
