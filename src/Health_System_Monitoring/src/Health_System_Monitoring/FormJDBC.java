package Health_System_Monitoring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 
 * @author W!GR
 * This is the JDBC implementation of the Editable Form DAO 
 *
 */
public class FormJDBC implements FormDao {
	
	public Connection database_connection;

	public static FormDao getDAO() { return new FormJDBC(); }

	public FormJDBC()
	{
		database_connection = database_driver.getConnection();
	}

	public int addNewForm(User creator, String form_name)
	{
		 PreparedStatement sqlStatement = null;
		 try {
			 String query = "INSERT INTO forms (form_name,userId) VALUES (?,?);\n" +
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
            String query = "UPDATE forms SET form_name = ? , userId = ? WHERE form_id=?";

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
			 String query = "INSERT INTO questions (form_id,q_type,label) VALUES (?,?,?);\n" +
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
            String query = "UPDATE questions SET q_type = ?, label = ? WHERE question_id=?";

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
		PreparedStatement sqlStatement = null;
		 try {
			 String query = "INSERT INTO submissions (form_id,submitter_id) VALUES (?,?);\n" +
							"SELECT LAST_INSERT_ID()";
			
			 sqlStatement = database_connection.prepareStatement(query);
			 
			 sqlStatement.setInt(1, formId);
			 sqlStatement.setInt(2, submitterId);
			 
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

	public boolean removeSubmission(int submissionId)
	{
		PreparedStatement sqlStatement = null;
        try {
            String query = "DELETE FROM submissions WHERE submission_id=?";

            sqlStatement = database_connection.prepareStatement(query);

            sqlStatement.setInt(1, submissionId);

            sqlStatement.executeUpdate();
            System.out.println("Submission deleted");
            
            sqlStatement.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        return false;
	}
	
	// ----------------------------------------------------------------------
	
	public int addAnswer(int questionId, int submissionId, Object value)
	{
		String valueType = value.getClass().getTypeName();
		String destination_table = "answer_" + valueType.toLowerCase();
		
		PreparedStatement sqlStatement = null;
		 try {
			 String query = "INSERT INTO " + destination_table + " (question_id,submission_id,value) VALUES (?,?,?)\n" + 
							"SELECT LAST_INSERT_ID()";
			
			 sqlStatement = database_connection.prepareStatement(query);
			 
			 sqlStatement.setInt(1, questionId);
			 sqlStatement.setInt(2, submissionId);
			 switch(valueType)
			 {
			 case "String":
				 sqlStatement.setString(3, (String)value);
			 case "Integer":
				 sqlStatement.setInt(3, (Integer)value);
			 case "Float":
				 sqlStatement.setFloat(3, (Float)value);
			 case "Boolean":
				 sqlStatement.setBoolean(3, (Boolean)value);
			 }
			 
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

	public boolean updateAnswer(int questionId, int submissionId, Object value)
	{
		String valueType = value.getClass().getTypeName();
		String destination_table = "answer_" + valueType.toLowerCase();
		
		PreparedStatement sqlStatement = null;
        try {
            String query = "UPDATE " + destination_table +" SET value = ? WHERE question_id = ? AND submission_id = ?";

            sqlStatement = database_connection.prepareStatement(query);

            switch(valueType)
			 {
			 case "String":
				 sqlStatement.setString(1, (String)value);
			 case "Integer":
				 sqlStatement.setInt(1, (Integer)value);
			 case "Float":
				 sqlStatement.setFloat(1, (Float)value);
			 case "Boolean":
				 sqlStatement.setBoolean(1, (Boolean)value);
			 }
            sqlStatement.setInt(2, questionId);
            sqlStatement.setInt(3, submissionId);

            sqlStatement.executeUpdate();
            System.out.println("Answer updated");
            
            sqlStatement.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        return false;
	}

	public boolean removeAnswer(int questionId, int submissionId, FormType type)
	{
		String valueType = type.toString();
		String destination_table = "answer_" + valueType.toLowerCase();
		
		PreparedStatement sqlStatement = null;
        try {
            String query = "DELETE FROM " + destination_table + " WHERE question_id=? AND submission_id=?";

            sqlStatement = database_connection.prepareStatement(query);

            sqlStatement.setInt(1, questionId);
            sqlStatement.setInt(2, submissionId);

            sqlStatement.executeUpdate();
            System.out.println("Submission deleted");
            
            sqlStatement.close();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        return false;
	}
	
	// ----------------------------------------------------------------------
	
	// utility functions
	
	public Collection<FormElement> getFormElements(int formId)
	{
		ArrayList<FormElement> outputList = new ArrayList<FormElement>();
		
		PreparedStatement sqlStatement = null;
		 try {
			 String query = "SELECT * FROM questions WHERE form_id = ? ORDER BY question_id ASC";
			
			 sqlStatement = database_connection.prepareStatement(query);
			 sqlStatement.setInt(1, formId);
			 
			 ResultSet resultSet = sqlStatement.executeQuery();
			
			 while (resultSet.next()){
	                FormElement newElement = new FormElement();
	                
	                newElement.question_id = resultSet.getInt("question_id");
	                newElement.label = resultSet.getString("label");
	                newElement.type = FormType.fromString(resultSet.getString("q_type"));

	                outputList.add(newElement);
	            }
			 
			 if (sqlStatement != null) {
                sqlStatement.close();
            }
		 } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
		
		return outputList;
	}
	
	public int submitFormAnswers(int formId, int submitterId, List<Object> values)
	{
		int submissionId = addSubmission(formId,submitterId);
		
		ArrayList<FormElement> elements = (ArrayList)getFormElements(formId);
		
		for(int i=0;i<elements.size();i++)
		{
			Object value = values.get(i);
			FormElement element = elements.get(i);
			int answerId = addAnswer(element.question_id, submissionId, value);
		}
		
		return submissionId;
	}

	public Collection<FormElement> getSubmission(int formId, int submissionId)
	{
		// get the array structure
		ArrayList<FormElement> elements = (ArrayList)getFormElements(formId);

		PreparedStatement sqlStatement = null;

		// for each element, get the relevant value
		for(FormElement fe : elements)
		{
			FormType type = fe.type;
			String typeString = type.toString().toLowerCase();
			String query = "SELECT * FROM answer_" + typeString + " WHERE question_id = ? AND submission_id = ?";

			try {
				sqlStatement = database_connection.prepareStatement(query);

				sqlStatement.setInt(1, fe.question_id);
				sqlStatement.setInt(2, submissionId);

				ResultSet resultSet = sqlStatement.executeQuery();

				if(resultSet.next()) {
					switch(fe.type) {
						case FT_STRING:
						{
							fe.value = resultSet.getString("value");
						}
						break;

						case FT_INT:
						{
							fe.value = resultSet.getInt("value");
						}
						break;

						case FT_FLOAT:
						{
							fe.value = resultSet.getFloat("value");
						}
						break;

						case FT_BOOLEAN:
						{
							fe.value = resultSet.getBoolean("value");
						}
						break;
					}
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			finally {
				if (sqlStatement != null) {
					try {
						sqlStatement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return elements;
	}


}
