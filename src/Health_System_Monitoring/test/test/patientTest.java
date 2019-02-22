/**
 * 
 */
package test;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.BeforeClass;
import org.junit.Test;

import Health_System_Monitoring.Patient;

/**
 * @author safouh
 *
 */
public class patientTest {

	private static Validator validator;

	@BeforeClass
	public static void setupValidatorInstance() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Test
	public void test1() {
		Patient patient1 = new Patient();
		patient1.setPatient_id(-1); // violated
		patient1.setPatient_first_name(null); // violated
		patient1.setPatient_address(" sa ");
		patient1.setPatient_last_name(null); // violated
		patient1.setPatient_prescriptions("bla");
		patient1.setPatient_medical_history("a history");
		patient1.setPatient_diagnosis("diagnosis");

		Set<ConstraintViolation<Patient>> violations = validator.validate(patient1);

		System.out.println("1:");
		violations.forEach(System.err::println);
		assertEquals(3, violations.size());
//		assertEquals( "size must be between 10 and 300", violations.iterator().next().getMessage() );

	}

	@Test
	public void test2() {
		Patient patient2 = new Patient();
		patient2.setPatient_id(1);
		patient2.setPatient_first_name("first name");
		patient2.setPatient_last_name("last name");

		Set<ConstraintViolation<Patient>> violations2 = validator.validate(patient2);
		System.out.println("2:");
		violations2.forEach(System.err::println);
		assertEquals(0, violations2.size());
//		assertEquals( "size must be between 10 and 300", violations.iterator().next().getMessage() );

	}
}
