package com.example.demo;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import Hospital.Management.HospitalManagementApplication;
import Hospital.Management.Model.Doctor;
import Hospital.Management.Model.Patient;
import Hospital.Management.Controller.DoctorController;
import Hospital.Management.Repository.DoctorRepository;
import Hospital.Management.Repository.PatientRepository;
import Hospital.Management.Service.DoctorService;
import Hospital.Management.Service.PatientService;



@SpringBootTest(classes=HospitalManagementApplication.class)
class DemoApplicationTests {
	
	@Autowired
	private DoctorService doctorserv;
	
	@MockBean
	private DoctorRepository doctorrepo;
	
	@Autowired
	private PatientService patientserv;
	
	@MockBean
	private PatientRepository patientrepo;
	@Test
	public void getAllDoctorsTest() {
		when(doctorrepo.findAll()).thenReturn(Stream.of(new Doctor(1001,"Dr.Chandana",36,"Female","Cardiologist"),new Doctor(1002,"Dr.Balaji",45,"male","Radiologist"),new Doctor(1003,"Dr.Subba Rao",54,"Male","Neurologist")).collect(Collectors.toList()));
		
	assertEquals(3,(doctorserv.getAllDoctors()).size());
	}
	
	@Test
	public void getdoctorByIdTest() {
		int doctor_id=2101;
		Optional<Doctor> optionalEntity=Optional.of(new Doctor(1001,"Dr.Chandana",36,"Female","Cardiologist"));
		when(doctorrepo.findById(doctor_id)).thenReturn(optionalEntity);
		assertEquals(36,(doctorserv.getdoctorById(doctor_id)).getDoctor_age());
	}
	
	@Test
	public void createDoctorTest() {
		Doctor doctor=new Doctor(1004,"Srilakshmi",47,"M","Cardiologist");
		when(doctorrepo.save(doctor)).thenReturn(doctor);

		assertNotNull((Doctor)doctorserv.createDoctor(doctor));
	}
	

	
	@Test
	public void deleteDoctorTest() {
		doctorrepo.deleteById(1003);
		verify(doctorrepo,times(1)).deleteById(1003);
//		assertNull(Optional.of(doctorserv.getdoctorById(1003)));
	}
	
	@Test
	public void getAllPatientsTest() {
		when(patientrepo.findAll()).thenReturn(Stream.of(new Patient(1,"keerthi","Subba Rao",new Date()),new Patient(2,"sony","Chandana",new Date()),new Patient(3,"pratyusha","balaji",new Date())).collect(Collectors.toList()));
		
	assertEquals(3,(patientserv.getAllPatients()).size());
	}
	
	@Test
	public void getpatientByIdTest() {
		int patient_id=3101;
		Optional<Patient> optionalEntity=Optional.of(new Patient(1,"keerthi","Subba Rao",new Date()));
		when(patientrepo.findById(patient_id)).thenReturn(optionalEntity);
		assertEquals(1,(patientserv.getpatientById(patient_id).getPatient_id()));
	}
	
	@Test
	public void deletePatientTest() {
		patientrepo.deleteById(3);
		verify(patientrepo,times(1)).deleteById(3);
	}
	
	
	@Test
	public void createPatientTest() {
		Patient patient=new Patient(4,"pratyusha","balaji",new Date());
		when(patientrepo.save(patient)).thenReturn(patient);
		assertNotNull((Patient)patientserv.createPatient(patient));
	}

}
