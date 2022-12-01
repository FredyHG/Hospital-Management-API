package dev.dracarys.com.hospitalquerysystem.util;

import dev.dracarys.com.hospitalquerysystem.dominio.Role;
import dev.dracarys.com.hospitalquerysystem.dominio.UserModel;
import dev.dracarys.com.hospitalquerysystem.enums.RoleName;
import dev.dracarys.com.hospitalquerysystem.repository.RoleRepository;
import dev.dracarys.com.hospitalquerysystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    public void run(String... args) throws Exception {
        try {
            Role adminRole = new Role();
            adminRole.setRoleName(RoleName.ROLE_ADMIN);
            roleRepository.save(adminRole);
            List<Role> roleAdminList = new ArrayList<>();
            roleRepository.save(adminRole);
            roleAdminList.add(adminRole);

            Role doctorRole = new Role();
            doctorRole.setRoleName(RoleName.ROLE_DOCTOR);
            List<Role> roleDoctorList = new ArrayList<>();
            roleRepository.save(doctorRole);
            roleDoctorList.add(doctorRole);

            Role headNurseRole = new Role();
            headNurseRole.setRoleName(RoleName.ROLE_HEADNURSE);
            List<Role> roleHeadNurseList = new ArrayList<>();
            roleRepository.save(headNurseRole);
            roleHeadNurseList.add(headNurseRole);

            Role attendantRole = new Role();
            attendantRole.setRoleName(RoleName.ROLE_ATTENDANT);

            List<Role> roleAttendantList = new ArrayList<>();
            roleRepository.save(attendantRole);
            roleAttendantList.add(attendantRole);


            UserModel admin = new UserModel();
            admin.setUsername("admin");
            admin.setPassword("$2a$10$ixT75lqtHwp.mwJlLLB9/.mLJGDaS7IF5PeYszZNtVvS2SchzS9/u");
            admin.setRoles(roleAdminList);

            userRepository.save(admin);

            UserModel headnurse = new UserModel();
            headnurse.setUsername("headnurse");
            headnurse.setPassword("$2a$10$ixT75lqtHwp.mwJlLLB9/.mLJGDaS7IF5PeYszZNtVvS2SchzS9/u");
            headnurse.setRoles(roleHeadNurseList);

            userRepository.save(headnurse);

            UserModel doctor = new UserModel();
            doctor.setUsername("doctor");
            doctor.setPassword("$2a$10$ixT75lqtHwp.mwJlLLB9/.mLJGDaS7IF5PeYszZNtVvS2SchzS9/u");
            doctor.setRoles(roleDoctorList);

            userRepository.save(doctor);

            UserModel attendant = new UserModel();
            attendant.setUsername("attendant");
            attendant.setPassword("$2a$10$ixT75lqtHwp.mwJlLLB9/.mLJGDaS7IF5PeYszZNtVvS2SchzS9/u");
            attendant.setRoles(roleAttendantList);

            userRepository.save(attendant);
        } catch (RuntimeException e) {
            System.out.println("Default user already registered");
        }

    }
}
