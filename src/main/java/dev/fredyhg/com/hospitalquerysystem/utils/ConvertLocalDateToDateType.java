package dev.fredyhg.com.hospitalquerysystem.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class ConvertLocalDateToDateType {

    public static Date convertFrom(LocalDate date) {
        return java.sql.Timestamp.valueOf(date.atStartOfDay());
    }

}
