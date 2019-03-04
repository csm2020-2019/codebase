package Health_System_Monitoring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 
 * @author W!GR
 * This is the JDBC implementation of the Editable Form DAO 
 *
 */
public class FormJDBC implements FormDao {
	
	public Connection database_connection;
	
	public FormJDBC()
	{
		database_connection = database_driver.getConnection();
	}

	public int addNewForm(User creator, String form_name)
	{
		 PreparedStatement sqlStatement = null;
		 try {
			 String query = "INSERT INTO forms (form_name,userId) VALUES (?,?)\n" + 
							"SELECT LAST_INSERT_ID()";
			
			 sqlStatement = database_connection.prepareStatement(query);
			 sqlStatement.setString(1, form_name);
			 sqlStatement.setInt(2, creator.getUserId());
			 
			 ResultSet resultSet = sqlStatement.executeQuery();
			
			 if (resultSet.next()) {
				 return resultSet.getInt(0);
			 }
			 if (sqlStatement != null) {
                 sqlStatement.close();
             }
		 } catch (SQLException e) {
             e.printStackTrace();
             System.out.println(e.getMessage());

         }
         
         return -1;
	}

	public boolean updateForm(int formId, User creator, String form_name)
	{
		PreparedStatement sqlStatement = null;
        try {
            String query = "UPDATE forms SET (form_name,userId) = (?,?) WHERE form_id=?";

            sqlStatement = database_connection.prepareStatement(query);

            sqlStatement.setString(1, form_name);
            sqlStatement.setInt(2, creator.getUserId());
            sqlStatement.setInt(3, formId);

            sqlStatement.executeUpdate();
            System.out.println("Form updated");
            
            sqlStatement.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        return false;
	}

	public boolean removeForm(int formId)
	{
		PreparedStatement sqlStatement = null;
        try {
            String query = "DELETE FROM forms WHERE form_id=?";

            sqlStatement = database_connection.prepareStatement(query);

            sqlStatement.setInt(1, formId);

            sqlStatement.executeUpdate();
            System.out.println("Form deleted");
            
            sqlStatement.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        return false;
	}
	
	// ----------------------------------------------------------------------

	public int addQuestion(int formId, FormType type, String label)
	{
		PreparedStatement sqlStatement = null;
		 try {
			 String query = "INSERT INTO questions (form_id,q_type,label) VALUES (?,?,?)\n" + 
							"SELECT LAST_INSERT_ID()";
			
			 sqlStatement = database_connection.prepareStatement(query);
			 
			 sqlStatement.setInt(1, formId);
			 sqlStatement.setString(2, type.toString());
			 sqlStatement.setString(3, label);
			 
			 
			 ResultSet resultSet = sqlStatement.executeQuery();
			
			 if (resultSet.next()) {
				 return resultSet.getInt(0);
			 }
			 if (sqlStatement != null) {
                sqlStatement.close();
            }
		 } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        
        return -1;
	}

	public boolean updateQuestion(int questionId, FormType type, String label)
	{
		PreparedStatement sqlStatement = null;
        try {
            String query = "UPDATE questions SET (q_type,label) = (?,?) WHERE question_id=?";

            sqlStatement = database_connection.prepareStatement(query);

            sqlStatement.setString(1, type.toString());
            sqlStatement.setString(2, label);
            sqlStatement.setInt(3, questionId);

            sqlStatement.executeUpdate();
            System.out.println("Question updated");
            
            sqlStatement.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        return false;
	}

	public boolean removeQuestion(int questionId)
	{
		PreparedStatement sqlStatement = null;
        try {
            String query = "DELETE FROM questions WHERE question_id=?";

            sqlStatement = database_connection.prepareStatement(query);

            sqlStatement.setInt(1, questionId);

            sqlStatement.executeUpdate();
            System.out.println("Question deleted");
            
            sqlStatement.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        return false;
	}
	
	// ----------------------------------------------------------------------
	
	public int addSubmission(int formId, int submitterId)
	{
		
	}

	public boolean removeSubmission(int submissionId)
	{
		
	}
	
	// ----------------------------------------------------------------------
	
	public int addStringAnswer(int questionId, int submissionId, String value)
	{
		
	}

	public boolean updateStringAnswer(int answerStringId, int submissionId, String value)
	{
		
	}

	public boolean removeStringAnswer(int answerStringId)
	{
		
	}
	
	// ----------------------------------------------------------------------
	
	public int addIntAnswer(int questionId, int submissionId, Integer value)
	{
		
	}

	public boolean updateIntnswer(int answerIntId, int submissionId, Integer value)
	{
		
	}

	public boolean removeIntAnswer(int answerIntId)
	{
		
	}

	// ----------------------------------------------------------------------
	
	public int addFloatAnswer(int questionId, int submissionId, Float value)
	{
		
	}

	public boolean updateFloatAnswer(int answerFloatId, int submissionId, Float value)
	{
		
	}

	public boolean removeFloatAnswer(int answerFloatId)
	{
		
	}
	
	// ----------------------------------------------------------------------
	
	public int addBooleanAnswer(int questionId, int submissionId, Boolean value)
	{
		
	}

	public boolean updateBooleanAnswer(int answerBooleanId, int submissionId, Boolean value)
	{
		
	}

	public boolean removeBooleanAnswer(int answerBooleanId)
	{
		
	}
	
	// ----------------------------------------------------------------------
	
	// utility functions
	
	public List<FormElement> getFormElements(int formId)
	{
		
	}
	
	public int submitFormAnswers(int formId, int submitterId, List<Object> values)
	{
		
	}
	
}
