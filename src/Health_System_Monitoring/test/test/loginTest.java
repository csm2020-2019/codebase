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

import javax.swing.JPasswordField;
import javax.swing.JTextField;;

/**
 * @author safouh
 *
 */
public class loginTest {

	private static Validator validator;

	@BeforeClass
	public static void setupValidatorInstance() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Test
	public void usernameValidation() {
//		String usernameTextField = new String();
		JTextField usernameTextField = new JTextField();
		usernameTextField.setText("");

		Set<ConstraintViolation<JTextField>> violations = validator.validate(usernameTextField);

//		assertEquals( 1, violations.size() );
//		assertEquals( "must not be null", constraintViolations.iterator().next().getMessage() );

		violations.forEach(System.err::println);
		assertEquals(1, violations.size());
	}

	@Test
	public void passwordIsNull() {
		JPasswordField password = new JPasswordField();
//		password.
//		passwordField.setText(null);
//		passwordField.equals(null);
//		String pass = null;
//		passwordField.setText(pass);
//		String strPass = passwordField.toString();

		Set<ConstraintViolation<JPasswordField>> violations = validator.validate(password);

		violations.forEach(System.err::println);
		assertEquals(1, violations.size());
//		assertEquals( "must not be null", constraintViolations.iterator().next().getMessage() );
	}
}
