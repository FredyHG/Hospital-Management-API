package dev.fredyhg.com.hospitalquerysystem.utils;

import dev.fredyhg.com.hospitalquerysystem.dominio.Role;
import dev.fredyhg.com.hospitalquerysystem.dominio.UserModel;
import dev.fredyhg.com.hospitalquerysystem.enums.RoleName;
import dev.fredyhg.com.hospitalquerysystem.repository.RoleRepository;
import dev.fredyhg.com.hospitalquerysystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {

        Optional<Role> roleExists = roleRepository.findByRoleName(RoleName.ROLE_ADMIN);

        if (roleExists.isEmpty()) {
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
                admin.setPassword("$2a$10$uUWng/8lS7u6vdrDgfEhCuN4ZjNxHKd.gSUU4UdGd4KBSixekqsN6");
                admin.setRoles(roleAdminList);

                userRepository.save(admin);

                UserModel headnurse = new UserModel();
                headnurse.setUsername("headnurse");
                headnurse.setPassword("$2a$10$uUWng/8lS7u6vdrDgfEhCuN4ZjNxHKd.gSUU4UdGd4KBSixekqsN6");
                headnurse.setRoles(roleHeadNurseList);

                userRepository.save(headnurse);

                UserModel doctor = new UserModel();
                doctor.setUsername("doctor");
                doctor.setPassword("$2a$10$uUWng/8lS7u6vdrDgfEhCuN4ZjNxHKd.gSUU4UdGd4KBSixekqsN6");
                doctor.setRoles(roleDoctorList);

                userRepository.save(doctor);

                UserModel attendant = new UserModel();
                attendant.setUsername("attendant");
                attendant.setPassword("$2a$10$uUWng/8lS7u6vdrDgfEhCuN4ZjNxHKd.gSUU4UdGd4KBSixekqsN6");
                attendant.setRoles(roleAttendantList);

                userRepository.save(attendant);
            } catch (RuntimeException e) {
                log.info("Error create default users");
            }
        }

        log.info("Default user already registered");
        log.info(passwordEncoder.encode("admin"));

    }
}
