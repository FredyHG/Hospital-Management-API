package dev.dracarys.com.hospitalquerysystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class HospitalQuerySystemApplication{

    public static void main(String[] args) {
        SpringApplication.run(HospitalQuerySystemApplication.class, args);

        System.out.println(new BCryptPasswordEncoder().encode("admin"));


    }


}
