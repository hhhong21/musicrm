package com._1.musicrm.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com._1.musicrm.model.Administrator;
import com._1.musicrm.repository.AdministratorRepository;
import com._1.musicrm.service.AdministratorService;

class AdministratorServiceTest {

    @Mock
    private AdministratorRepository adminRepository;

    @InjectMocks
    private AdministratorService administratorService;

    private Administrator admin;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp() {
        MockitoAnnotations.openMocks(this);
        admin = new Administrator();
        admin.setAdminName("admin");
        admin.setAdminPassword("password");
    }

    @Test
    void testSaveAdmin() {
        when(adminRepository.save(admin)).thenReturn(admin);

        Administrator savedAdmin = administratorService.saveAdmin(admin);

        assertNotNull(savedAdmin);
        assertEquals("admin", savedAdmin.getAdminName());
        verify(adminRepository, times(1)).save(admin);
    }

    @Test
    void testLoginAdmin_Success() {
        when(adminRepository.findByAdminName("admin")).thenReturn(Optional.of(admin));

        String result = administratorService.loginAdmin(admin);

        assertEquals("Login success!", result);
        verify(adminRepository, times(1)).findByAdminName("admin");
    }

    @Test
    void testLoginAdmin_AdminNotFound() {
        when(adminRepository.findByAdminName("admin")).thenReturn(Optional.empty());

        String result = administratorService.loginAdmin(admin);

        assertEquals("Administrator Not Found!", result);
        verify(adminRepository, times(1)).findByAdminName("admin");
    }

    @Test
    void testLoginAdmin_PasswordMismatch() {
        Administrator wrongAdmin = new Administrator();
        wrongAdmin.setAdminName("admin");
        wrongAdmin.setAdminPassword("wrongPassword");

        when(adminRepository.findByAdminName("admin")).thenReturn(Optional.of(admin));

        String result = administratorService.loginAdmin(wrongAdmin);

        assertEquals("Password Mismatch!", result);
        verify(adminRepository, times(1)).findByAdminName("admin");
    }
}

