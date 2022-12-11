package dev.dracarys.com.hospitalquerysystem.controller;

import dev.dracarys.com.hospitalquerysystem.requests.doctor.DoctorDtoViewAll;
import dev.dracarys.com.hospitalquerysystem.requests.doctor.DoctorGetReturnObject;
import dev.dracarys.com.hospitalquerysystem.requests.doctor.DoctorPostRequestBody;
import dev.dracarys.com.hospitalquerysystem.service.DoctorServices;
import dev.dracarys.com.hospitalquerysystem.util.DoctorCreate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class DoctorControllerTest {

    @InjectMocks
    private DoctorController doctorController;

    @Mock
    private DoctorServices doctorServiceMock;

    @BeforeEach
    void setUp() {
        Page<DoctorDtoViewAll> doctorPage = new PageImpl<>(List.of(DoctorCreate.createValidDoctorDTO()));
        BDDMockito.when(doctorServiceMock.listAllDoctors(ArgumentMatchers.any()))
                .thenReturn(doctorPage);

        BDDMockito.when(doctorServiceMock.save(ArgumentMatchers.any(DoctorPostRequestBody.class))).thenReturn("Doctor saved successful");

        BDDMockito.when(doctorServiceMock.findByCrmReturnDTO(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(DoctorCreate.createValidDoctorDtoView()));

    }


    @Test
    @DisplayName("List returns list of doctor inside page object when successful")
    void list_ReturnsListOfDoctorInsidePageObject_WhenSuccessful() {
        String expectedFirstName = DoctorCreate.createValidDoctorDTO().getFirstName();

        Page<DoctorDtoViewAll> doctorDtoPage = doctorController.listAllDoctor(null).getBody();

        Assertions.assertThat(doctorDtoPage).isNotNull();

        Assertions.assertThat(doctorDtoPage.toList()).isNotEmpty();

        Assertions.assertThat(doctorDtoPage.toList().get(0).getFirstName()).isEqualTo(expectedFirstName);

        Assertions.assertThat(doctorDtoPage.getSize()).isEqualTo(1);
    }



    @Test
    @DisplayName("Save returns string 'Doctor saved successful' when successful")
    void saveDoctor_ReturnsString_WhenSuccessful() {

        String expectedReturnStringBody = "Doctor saved successful";


        Object doctorReturn = doctorController.saveNewDoctor(DoctorCreate.createDoctorToBeSaved()).getBody();

        Assertions.assertThat(doctorReturn).isNotNull();
        Assertions.assertThat(doctorReturn).isEqualTo(expectedReturnStringBody);
    }

    @Test
    @DisplayName("Find By Crm return doctor when successful")
    void findByCrmReturnDoctorWhenSuccessful() {

        String expectedCRM = DoctorCreate.createValidDoctor().getCrm();

        DoctorGetReturnObject doctorExpected = DoctorCreate.createValidDoctorDtoView();

        Object doctor = doctorController.findByCrm(expectedCRM).getBody();

        Assertions.assertThat(doctor).isNotNull();
        Assertions.assertThat(doctor).isEqualTo(doctorExpected);

    }



}