package dev.fredyhg.com.hospitalquerysystem.service;

import dev.fredyhg.com.hospitalquerysystem.models.Appointment;
import dev.fredyhg.com.hospitalquerysystem.models.Doctor;
import dev.fredyhg.com.hospitalquerysystem.models.Patient;
import dev.fredyhg.com.hospitalquerysystem.models.requests.appointments.*;
import dev.fredyhg.com.hospitalquerysystem.exception.appointment.AppointmentAlreadyFinishException;
import dev.fredyhg.com.hospitalquerysystem.exception.appointment.AppointmentNotFound;
import dev.fredyhg.com.hospitalquerysystem.exception.appointment.AppointmentPendingAlreadyExists;
import dev.fredyhg.com.hospitalquerysystem.exception.doctor.DoctorNotFoundException;
import dev.fredyhg.com.hospitalquerysystem.exception.patient.PatientNotFoundException;
import dev.fredyhg.com.hospitalquerysystem.mapper.ModelMappers;
import dev.fredyhg.com.hospitalquerysystem.repository.AppointmentsRepository;
import dev.fredyhg.com.hospitalquerysystem.repository.DoctorRepository;
import dev.fredyhg.com.hospitalquerysystem.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServices {

    private final AppointmentsRepository appointmentsRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    ModelMapper modelMapper = new ModelMapper();


    public List<AppointmentGetResponse> listAllAppointments() {

        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        List<Appointment> appointments = appointmentsRepository.findAll();

        return appointments.stream().map(ModelMappers::appointmentModelToAppointmentGetResponse).toList();
    }

    public void createNewAppointment(AppointmentPostRequest appointmentsPostRequestBody) {

          Doctor doctor = ensureDoctorExists(appointmentsPostRequestBody.getCrmDoctor());

          Patient patient = ensurePatientExists(appointmentsPostRequestBody.getCpfPatient());

          ensureAppointmentNonExists(doctor, patient);

          if(appointmentsPostRequestBody.getAppointmentDate().isBefore(LocalDate.now())){
              throw new DateTimeException("Invalid date, the date cannot be earlier than now");
          }

          Appointment appointmentToBeSaved = ModelMappers.appointmentPostRequestToAppointment(appointmentsPostRequestBody, patient, doctor);

          appointmentsRepository.save(appointmentToBeSaved);
    }


    public void editAppointmentInfo(AppointmentPutRequest appointmentsPutRequest) {

        Doctor doctor = ensureDoctorExists(appointmentsPutRequest.getCrmDoctor());

        Patient patient = ensurePatientExists(appointmentsPutRequest.getCpfPatient());

        Appointment appointment = ensureAppointmentExists(doctor, patient);

        if(appointment.getPatientAttended()){
            throw new AppointmentAlreadyFinishException("This appointment already finish");
        }

        Appointment appointmentToBeSaved = ModelMappers.appointmentPutRequestToAppointment(appointment, appointmentsPutRequest);

        appointmentsRepository.save(appointmentToBeSaved);
    }

    public void deleteAppointment(AppointmentDeleteRequestBody appointmentDeleteBody) {

        Patient patient = ensurePatientExists(appointmentDeleteBody.getCpfPatient());

        Doctor doctor = ensureDoctorExists(appointmentDeleteBody.getCrmDoctor());

        Appointment appointment = ensureAppointmentExists(doctor, patient);

        if (appointment.getPatientAttended()) {
            throw new AppointmentAlreadyFinishException("you cannot close this appointment because it is already finished");
        }

        appointmentsRepository.delete(appointment);
    }

    @Transactional
    public void switchPatientStatement(SwitchPatientStatementRequestBody requestBody) {
        Patient patient = ensurePatientExists(requestBody.getCpfPatient());
        Doctor doctor = ensureDoctorExists(requestBody.getCrmDoctor());

        Appointment appointmentExists = appointmentsRepository.findByDoctorAndPatient(doctor, patient).orElseThrow(() -> new AppointmentNotFound("Appointment not found"));

        appointmentExists.setPatientAttended(true);

        appointmentsRepository.save(appointmentExists);
    }

    public List<AppointmentGetResponse> findAppointmentByDoctor(Doctor doctor) {

        Doctor doctorExists = doctorRepository.findByCrm(doctor.getCrm()).orElseThrow(
                () -> new DoctorNotFoundException("Doctor not found"));


        List<Appointment> appointments = appointmentsRepository.findByDoctor(doctorExists);

        return appointments.stream().map(ModelMappers::appointmentModelToAppointmentGetResponse).toList();
    }


    public Optional<Appointment> findByDoctorAndPatient(Doctor doctor, Patient patient) {

        return appointmentsRepository.findByDoctorAndPatient(doctor, patient);

    }

    private Doctor ensureDoctorExists(String crm){

        return doctorRepository.findByCrm(crm)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));

    }

    private Patient ensurePatientExists(String cpf){
        return patientRepository.findByCpf(cpf)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found"));
    }

    private Appointment ensureAppointmentExists(Doctor doctor, Patient patient){
        return appointmentsRepository.findByDoctorAndPatient(doctor, patient)
                .orElseThrow(() -> new AppointmentNotFound("Appointment not found"));
    }

    private void ensureAppointmentNonExists(Doctor doctor, Patient patient){
        if(appointmentsRepository.findByDoctorAndPatient(doctor, patient).isPresent()){
            throw new AppointmentPendingAlreadyExists("There is already a pending appointment for this patient");
        }
    }
}
