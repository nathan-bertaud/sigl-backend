package com.sigl.sigl.service;

import com.sigl.sigl.model.Company;
import com.sigl.sigl.repository.CompanyRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.times;

public class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    @Before
    public void setUp() {MockitoAnnotations.initMocks(this); }

    @Test
    public void testAddCompanySuccess(){
        //Init
        Company company = new Company();
        company.setEmail("test@email.com");

        //Mock
        Mockito.when(
                this.companyRepository.findByEmail(company.getEmail())
        ).thenReturn(
                Optional.empty()
        );

        //Traitement
        this.companyService.addCompany(company);

        //Verification
        Mockito.verify(this.companyRepository, times(1)).findByEmail(company.getEmail());
        Mockito.verify(this.companyRepository, times(1)).save(company);
        Mockito.verifyNoMoreInteractions(this.companyRepository);
    }

    @Test(expected = RuntimeException.class)
    public void testAddApprenticeFailAlreadyExisting(){
        //Init
        Company company = new Company();
        company.setEmail("test@email.com");

        //Mock
        Mockito.when(
                this.companyRepository.findByEmail(company.getEmail())
        ).thenReturn(
                Optional.of(company)
        );

        //Traitement
        this.companyService.addCompany(company);

        //Verification
        Mockito.verify(this.companyRepository, times(1)).findByEmail(company.getEmail());
        Mockito.verifyNoMoreInteractions(this.companyRepository);
    }

    @Test(expected = RuntimeException.class)
    public void testAddApprenticeFailSave(){
        //Init
        Company company = new Company();
        company.setEmail("test@email.com");

        //Mock
        Mockito.when(
                this.companyRepository.findByEmail(company.getEmail())
        ).thenReturn(
                Optional.empty()
        );

        Mockito.when(
                this.companyRepository.save(company)
        ).thenThrow(
                RuntimeException.class
        );

        //Traitement
        this.companyService.addCompany(company);

        //Verification
        Mockito.verify(this.companyRepository, times(1)).findByEmail(company.getEmail());
        Mockito.verify(this.companyRepository, times(1)).save(company);
        Mockito.verifyNoMoreInteractions(this.companyRepository);
    }
}
