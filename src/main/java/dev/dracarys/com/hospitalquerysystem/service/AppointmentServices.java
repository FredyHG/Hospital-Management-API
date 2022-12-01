package dev.dracarys.com.hospitalquerysystem.service;

import dev.dracarys.com.hospitalquerysystem.dominio.Appointments;
import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import dev.dracarys.com.hospitalquerysystem.mapper.AppointmentMapper;
import dev.dracarys.com.hospitalquerysystem.repository.*;
import dev.dracarys.com.hospitalquerysystem.requests.appointments.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppointmentServices {

    private final AppointmentsRepository appointmentsRepository;
    private final DoctorRepository doctorRepository;
    private final PatientsRepository patientsRepository;

    ModelMapper modelMapper = new ModelMapper();


    public List<AppointmentGetReturnObject> listAllAppointments() {

        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        List<Appointments> appointments = appointmentsRepository.findAll();

        Type listType = new TypeToken<List<AppointmentGetReturnObject>>() {
        }.getType();

        List<AppointmentGetReturnObject> appointmentsDtoList = modelMapper.map(appointments, listType);


        appointmentsDtoList.forEach(appointmentsDto -> appointmentsDto.setPatientName(
                patientsRepository.findById(appointmentsDto.getPatientId()).orElseThrow().getFirstName() +
                        " " + patientsRepository.findById(appointmentsDto.getPatientId()).orElseThrow().getLastName()));

        appointmentsDtoList.forEach(appointmentsDto -> appointmentsDto.setDoctorName(
                doctorRepository.findById(appointmentsDto.getDoctorId()).orElseThrow().getFirstName() +
                        " " + doctorRepository.findById(appointmentsDto.getDoctorId()).orElseThrow().getLastName()));

        return appointmentsDtoList;
    }

    @SuppressWarnings("DuplicatedCode")
    public ResponseEntity<Object> save(AppointmentPostRequestBody appointmentsPostRequestBody) {


        Appointments appointmentsToBeSaved = AppointmentMapper.INSTANCE.toAppointment(appointmentsPostRequestBody);


        if (appointmentsPostRequestBody.getCrmDoctor() != null && appointmentsPostRequestBody.getCpfPatient() != null) {

            Optional<Doctor> doctorToBeSaved = doctorRepository.findByCrm(appointmentsPostRequestBody.getCrmDoctor());
            Optional<Patients> patientsToBeSaved = patientsRepository.findByCpf(appointmentsPostRequestBody.getCpfPatient());


            if (doctorToBeSaved.isPresent() && patientsToBeSaved.isPresent()) {
                Optional<Appointments> appointmentsExists = appointmentsRepository.findByDoctorAndPatient(doctorToBeSaved.get(), patientsToBeSaved.get());

                if (appointmentsExists.isPresent()) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("There is already a pending appointment for this patient");
                }
            }


            if (doctorToBeSaved.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Doctor not found");
            }

            if (patientsToBeSaved.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found");
            }

            appointmentsToBeSaved.setDoctor(doctorToBeSaved.get());
            appointmentsToBeSaved.setPatient(patientsToBeSaved.get());

            appointmentsRepository.save(appointmentsToBeSaved);

            return ResponseEntity.status(HttpStatus.CREATED).body("Appointment create successfully!");
        }


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Check the information and try again");

    }


    public ResponseEntity<Object> editAppointmentInfo(AppointmentPutRequestBody appointmentsPutRequestBody) {
        Optional<Doctor> doctor = doctorRepository.findByCrm(appointmentsPutRequestBody.getCrmDoctor());
        Optional<Patients> patients = patientsRepository.findByCpf(appointmentsPutRequestBody.getCpfPatient());


        if (doctor.isPresent() && patients.isPresent()) {
            Optional<Appointments> appointments = appointmentsRepository.findByDoctorAndPatient(doctor.get(), patients.get());

            if (appointments.isPresent()) {
                appointments.get().setAppointmentDate(appointmentsPutRequestBody.getAppointmentDate());
                appointments.get().setDoctor(doctor.get());
                appointments.get().setPatient(patients.get());
                appointments.get().setPatientAttended(false);
                appointments.get().setDrugAllergy(appointmentsPutRequestBody.getDrugAllergy());

                return new ResponseEntity<>(appointmentsRepository.save(appointments.get()), HttpStatus.OK);
            }
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body("Appointment not found, check the parameters and try again");
    }

    public ResponseEntity<Object> deleteAppointment(AppointmentDeleteRequestBody appointmentDeleteBody) {

        Optional<Doctor> doctorToBeDelete = doctorRepository.findByCrm(appointmentDeleteBody.getCrmDoctor());

        Optional<Patients> patientToBeDelete = patientsRepository.findByCpf(appointmentDeleteBody.getCpfPatient());

        if (doctorToBeDelete.isPresent() && patientToBeDelete.isPresent()) {
            Optional<Appointments> appointmentToBeDelete = appointmentsRepository.findByDoctorAndPatient(
                    doctorToBeDelete.get(), patientToBeDelete.get());

            if (appointmentToBeDelete.isPresent()) {
                appointmentsRepository.delete(appointmentToBeDelete.get());
                return ResponseEntity.status(HttpStatus.OK).body("Appointment delete successfully");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found");
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body("Check parameters and try again");
    }

    public ResponseEntity<Object> switchPatientStatement(SwitchPatientStatementRequestBody requestBody) {
        Optional<Doctor> doctor = doctorRepository.findByCrm(requestBody.getCrmDoctor());

        Optional<Patients> patient = patientsRepository.findByCpf(requestBody.getCpfPatient());

        if (doctor.isPresent() && patient.isPresent()) {
            Optional<Appointments> appointment = appointmentsRepository.findByDoctorAndPatient(
                    doctor.get(), patient.get());

            if (appointment.isPresent() && Boolean.TRUE.equals(!appointment.get().getPatientAttended())) {
                appointment.get().setPatientAttended(true);
                return ResponseEntity.status(HttpStatus.OK).body("Patient attended successfully");
            }
            if(appointment.isPresent() && Boolean.TRUE.equals(appointment.get().getPatientAttended())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("the patient has already been treated");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment not found");
    }
}