package dev.dracarys.com.hospitalquerysystem.mapper;

import dev.dracarys.com.hospitalquerysystem.dominio.Stay;
import dev.dracarys.com.hospitalquerysystem.requests.stay.StayPostRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class StayMapper {

    public static final StayMapper INSTANCE = Mappers.getMapper(StayMapper.class);

    public abstract Stay toStay(StayPostRequestBody stayPostRequestBody);

}
