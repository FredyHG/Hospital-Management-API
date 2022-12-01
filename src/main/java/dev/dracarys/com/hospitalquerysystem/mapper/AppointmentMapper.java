package dev.dracarys.com.hospitalquerysystem.mapper;


import dev.dracarys.com.hospitalquerysystem.dominio.Appointments;
import dev.dracarys.com.hospitalquerysystem.requests.appointments.AppointmentPostRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class AppointmentMapper {

    public static final AppointmentMapper INSTANCE = Mappers.getMapper(AppointmentMapper.class);

    public abstract Appointments toAppointment(AppointmentPostRequestBody appointmentsPostRequestBody);



}
