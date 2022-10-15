package dev.dracarys.com.hospitalquerysystem.mapper;

import dev.dracarys.com.hospitalquerysystem.dominio.employee.HeadNurse;
import dev.dracarys.com.hospitalquerysystem.requests.employee.headnurse.HeadNursePostBody;
import dev.dracarys.com.hospitalquerysystem.requests.employee.headnurse.HeadNursePutBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class HeadNurseMapper {
    public static final HeadNurseMapper INSTANCE = Mappers.getMapper(HeadNurseMapper.class);

    public abstract HeadNurse tpHeadNurse(HeadNursePostBody headNursePostBody);

    public abstract HeadNurse toHeadNurse(HeadNursePutBody headNursePutBody);
}
