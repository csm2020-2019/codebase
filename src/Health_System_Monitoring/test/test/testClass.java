/**
 * 
 */
package test;

import java.util.Set;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author safouh
 *
 */
public class testClass {

	private static Validator validator;

	@BeforeClass
	public static void setUpValidator() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = (Validator) factory.getValidator();
	}
	
	@Test
	public void usernameIsNull() {
//		String usernameTextField = new String();
		JTextField usernameTextField = new JTextField();
//		String pass="";
//		usernameTextField.setText(pass);
		String strUser = usernameTextField.toString();

		Set<ConstraintViolation<String>> constraintViolations =	validator.validate(strUser);

		assertEquals( 1, constraintViolations.size() );
		assertEquals( "must not be null", constraintViolations.iterator().next().getMessage() );
	}
	   
	@Test
	public void passwordIsNull() {
//		String usernameTextField = new String();
		JPasswordField passwordField = new JPasswordField();
//		String pass="";
//		usernameTextField.setText(pass);
		String strPass = passwordField.toString();

		Set<ConstraintViolation<String>> constraintViolations =	validator.validate(strPass);

		assertEquals( 1, constraintViolations.size() );
		assertEquals( "must not be null", constraintViolations.iterator().next().getMessage() );
	}
}
