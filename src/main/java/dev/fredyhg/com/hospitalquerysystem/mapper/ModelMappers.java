package dev.fredyhg.com.hospitalquerysystem.mapper;

import dev.fredyhg.com.hospitalquerysystem.dominio.*;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.appointments.AppointmentGetResponse;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.appointments.AppointmentPostRequest;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.appointments.AppointmentPutRequest;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.doctor.DoctorDtoViewAll;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.doctor.DoctorGetReturnObject;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.doctor.DoctorPostRequestBody;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.doctor.DoctorPutRequestBody;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.patient.PatientPostRequestBody;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.stay.StayGetReturnObject;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.stay.StayPostRequestBody;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.stay.StayPutRequestBody;
import dev.fredyhg.com.hospitalquerysystem.dominio.requests.user.UserPostRequestBody;
import dev.fredyhg.com.hospitalquerysystem.enums.StatusStay;
import dev.fredyhg.com.hospitalquerysystem.utils.ConvertLocalDateToDateType;
import dev.fredyhg.com.hospitalquerysystem.utils.TitleCase;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class ModelMappers {

    public static Appointment appointmentPutRequestToAppointment(Appointment appointment, AppointmentPutRequest appointmentPutRequest){

        return Appointment.builder()
                .id(appointment.getId())
                .appointmentDate(convertLocalDateToDate(appointmentPutRequest.getAppointmentDate()))
                .patient(appointment.getPatient())
                .doctor(appointment.getDoctor())
                .patientAttended(false)
                .drugAllergy(appointmentPutRequest.getDrugAllergy())
                .build();


    }

    public static Appointment appointmentPostRequestToAppointment(
            final AppointmentPostRequest appointmentPost, Patient patient, Doctor doctor){

        return Appointment.builder()
                .appointmentDate(convertLocalDateToDate(appointmentPost.getAppointmentDate()))
                .patient(patient)
                .doctor(doctor)
                .patientAttended(false)
                .drugAllergy(appointmentPost.getDrugAllergy())
                .build();
    }


    public static AppointmentGetResponse appointmentModelToAppointmentGetResponse(Appointment appointment){
        return AppointmentGetResponse.builder()
                .patientId(appointment.getPatient().getId())
                .doctorId(appointment.getDoctor().getId())
                .appointmentDate(appointment.getAppointmentDate())
                .patientName(appointment.getPatient().getFirstName() + appointment.getPatient().getLastName())
                .doctorName(appointment.getDoctor().getFirstName() + appointment.getDoctor().getLastName())
                .patientAttended(appointment.getPatientAttended())
                .drugAllergy(appointment.getDrugAllergy())
                .doctor(appointment.getDoctor())
                .patient(appointment.getPatient())
                .build();
    }

    public static StayGetReturnObject stayModelToStayGetObject(Stay stay){
        return StayGetReturnObject.builder()
                .stayDate(stay.getStayDate())
                .description(stay.getDescription())
                .patientName(stay.getPatient().getFirstName())
                .doctorName(stay.getDoctor().getFirstName())
                .statusStay(stay.getStatus())
                .drugAllergy(stay.getDrugAllergy())
                .build();
    }

    public static DoctorDtoViewAll convertDoctorDtoViewAll(Doctor doctor){
        DoctorDtoViewAll dtoViewAll = new DoctorDtoViewAll();

        dtoViewAll.setId(doctor.getId());
        dtoViewAll.setCrm(doctor.getCrm());
        dtoViewAll.setLastName(doctor.getLastName());
        dtoViewAll.setFirstName(doctor.getFirstName());


        return dtoViewAll;
    }


    public static Date convertLocalDateToDate(LocalDate localDate){
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Doctor doctorPostRequestToDoctor(DoctorPostRequestBody doctorPostRequestBody) {
        return Doctor.builder()
                .crm(doctorPostRequestBody.getCrm())
                .firstName(TitleCase.convertToTitleCaseIteratingChars(doctorPostRequestBody.getFirstName()))
                .lastName(TitleCase.convertToTitleCaseIteratingChars(doctorPostRequestBody.getLastName()))
                .build();
    }



    public static DoctorGetReturnObject convertDoctorDtoView(Doctor doctorToBeConvert) {

        List<AppointmentGetResponse> appointmentGetResponseList = doctorToBeConvert.getAppointments()
                .stream()
                .map(ModelMappers::appointmentModelToAppointmentGetResponse)
                .toList();


        List<StayGetReturnObject> stayGetReturnObjectList = doctorToBeConvert.getStay()
                .stream()
                .map(ModelMappers::stayModelToStayGetObject)
                .toList();


        return DoctorGetReturnObject.builder()
                .appointmentsView(appointmentGetResponseList)
                .staysView(stayGetReturnObjectList)
                .firstName(doctorToBeConvert.getFirstName())
                .lastName(doctorToBeConvert.getLastName())
                .crm(doctorToBeConvert.getCrm())
                .build();
    }

    public static Doctor doctorPutRequestToDoctor(DoctorPutRequestBody doctorPutRequestBody, Doctor doctor) {
        return Doctor.builder()
                .id(doctorPutRequestBody.getId())
                .firstName(doctorPutRequestBody.getFirstName())
                .lastName(doctorPutRequestBody.getLastName())
                .crm(doctorPutRequestBody.getCrm())
                .appointments(doctor.getAppointments())
                .stay(doctor.getStay())
                .build();
    }

    public static Stay stayPostRequestToStayModel(StayPostRequestBody stayPostRequestBody, Doctor doctor, Patient patient) {
        return Stay.builder()
                .stayDate(ConvertLocalDateToDateType.convertFrom(stayPostRequestBody.getStayDate()))
                .drugAllergy(stayPostRequestBody.getDrugAllergy())
                .description(stayPostRequestBody.getDescription())
                .status(StatusStay.ADMITTED)
                .doctor(doctor)
                .patient(patient)
                .build();
    }

    public static Stay stayPutRequestToStayModel(StayPutRequestBody stay, Stay oldStay){
        return Stay.builder()
                .id(oldStay.getId())
                .stayDate(ConvertLocalDateToDateType.convertFrom(stay.getStayDate()))
                .drugAllergy(stay.getDrugAllergy())
                .doctor(oldStay.getDoctor())
                .patient(oldStay.getPatient())
                .status(oldStay.getStatus())
                .description(stay.getDescription())
                .build();
    }

    public static Patient patientPostRequestToPatientModel(PatientPostRequestBody patientPostRequestBody) {
        return Patient.builder()
                .firstName(patientPostRequestBody.getFirstName())
                .lastName(patientPostRequestBody.getLastName())
                .cpf(patientPostRequestBody.getCpf())
                .birthdate(ConvertLocalDateToDateType.convertFrom(patientPostRequestBody.getBirthdate()))
                .phone(patientPostRequestBody.getPhone())
                .build();
    }

    public static UserModel userPostRequestToUserModel(UserPostRequestBody userPostRequestBody) {
        return UserModel.builder()
                .username(userPostRequestBody.getUsername())
                .password(userPostRequestBody.getPassword())
                .build();
    }
}
