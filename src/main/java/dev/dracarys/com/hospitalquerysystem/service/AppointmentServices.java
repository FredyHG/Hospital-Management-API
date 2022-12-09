package dev.dracarys.com.hospitalquerysystem.service;

import dev.dracarys.com.hospitalquerysystem.dominio.Appointments;
import dev.dracarys.com.hospitalquerysystem.dominio.Doctor;
import dev.dracarys.com.hospitalquerysystem.dominio.Patients;
import dev.dracarys.com.hospitalquerysystem.mapper.AppointmentMapper;
import dev.dracarys.com.hospitalquerysystem.repository.AppointmentsRepository;
import dev.dracarys.com.hospitalquerysystem.repository.DoctorRepository;
import dev.dracarys.com.hospitalquerysystem.repository.PatientsRepository;
import dev.dracarys.com.hospitalquerysystem.requests.appointments.*;
import dev.dracarys.com.hospitalquerysystem.util.ConvertLocalDateToDateType;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
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

    public void save(AppointmentPostRequestBody appointmentsPostRequestBody) {


        Appointments appointmentsToBeSaved = AppointmentMapper.INSTANCE.toAppointment(appointmentsPostRequestBody);
        Optional<Doctor> doctorToBeSaved = doctorRepository.findByCrm(appointmentsPostRequestBody.getCrmDoctor());
        Optional<Patients> patientsToBeSaved = patientsRepository.findByCpf(appointmentsPostRequestBody.getCpfPatient());

        if (doctorToBeSaved.isPresent() && patientsToBeSaved.isPresent()) {

            appointmentsToBeSaved.setDoctor(doctorToBeSaved.get());

            appointmentsToBeSaved.setPatient(patientsToBeSaved.get());

            appointmentsRepository.save(appointmentsToBeSaved);

        }

    }


    public Optional<Appointments> editAppointmentInfo(AppointmentPutRequestBody appointmentsPutRequestBody) {

        Optional<Doctor> doctor = doctorRepository.findByCrm(appointmentsPutRequestBody.getCrmDoctor());

        Optional<Patients> patients = patientsRepository.findByCpf(appointmentsPutRequestBody.getCpfPatient());


        if (doctor.isPresent() && patients.isPresent()) {
            Optional<Appointments> appointments = appointmentsRepository.findByDoctorAndPatient(doctor.get(), patients.get());

            if (appointments.isPresent()) {
                appointments.get().setAppointmentDate(ConvertLocalDateToDateType.convertFrom(appointmentsPutRequestBody.getAppointmentDate()));
                appointments.get().setDoctor(doctor.get());
                appointments.get().setPatient(patients.get());
                appointments.get().setPatientAttended(false);
                appointments.get().setDrugAllergy(appointmentsPutRequestBody.getDrugAllergy());

                appointmentsRepository.save(appointments.get());
            }
        }

        return Optional.empty();
    }

    public void deleteAppointment(AppointmentDeleteRequestBody appointmentDeleteBody) {

        Optional<Doctor> doctor = doctorRepository.findByCrm(appointmentDeleteBody.getCrmDoctor());

        Optional<Patients> patient = patientsRepository.findByCpf(appointmentDeleteBody.getCpfPatient());

        if (doctor.isPresent() && patient.isPresent()) {

            Optional<Appointments> appointmentToBeDelete = appointmentsRepository.findByDoctorAndPatient(doctor.get(), patient.get());

            appointmentToBeDelete.ifPresent(appointmentsRepository::delete);

        }
    }

    public void switchPatientStatement(SwitchPatientStatementRequestBody requestBody) {
        Optional<Doctor> doctor = doctorRepository.findByCrm(requestBody.getCrmDoctor());

        Optional<Patients> patient = patientsRepository.findByCpf(requestBody.getCpfPatient());

        if (doctor.isPresent() && patient.isPresent()) {
            Optional<Appointments> appointment = appointmentsRepository.findByDoctorAndPatient(
                    doctor.get(), patient.get());

            if (appointment.isPresent()) {
                appointment.get().setPatientAttended(true);

                appointmentsRepository.save(appointment.get());
            }

        }
    }

    public List<AppointmentGetReturnObject> findAppointmentByDoctor(Doctor doctor) {

        Optional<Doctor> doctorNonPageable = doctorRepository.findByCrm(doctor.getCrm());

        if (doctorNonPageable.isEmpty()) {
            return Collections.emptyList();
        }

        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        List<Appointments> appointments = appointmentsRepository.findByDoctor(doctorNonPageable.get());
        Type listType = new TypeToken<List<AppointmentGetReturnObject>>() {
        }.getType();

        List<AppointmentGetReturnObject> appointmentsDtoList = modelMapper.map(appointments, listType);


        appointmentsDtoList.forEach(appointmentsDto -> appointmentsDto.setPatientName(
                patientsRepository.findById(appointmentsDto.getPatientId()).orElseThrow().getFirstName() +
                        " " + patientsRepository.findById(appointmentsDto.getPatientId()).orElseThrow().getLastName()));

        appointmentsDtoList.forEach(appointmentsDto -> appointmentsDto.setDoctorName(
                doctorRepository.findById(doctorNonPageable.get().getId()).orElseThrow().getFirstName() +
                        " " + doctorRepository.findById(doctorNonPageable.get().getId()).orElseThrow().getLastName()));

        return appointmentsDtoList;
    }


    public Optional<Appointments> findByDoctorAndPatient(Doctor doctor, Patients patients) {

        return appointmentsRepository.findByDoctorAndPatient(doctor, patients);

    }
}
