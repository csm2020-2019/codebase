/**
 * 
 */
package Health_System_Monitoring;

import java.util.List;

/**
 * @author user
 * This DAO interface permits users to read and write editable forms
 * Values for the results are entered in object-form so we can return the list of results as Objects
 */
public interface FormDao {
	
	// CRUD functions
	
	/**
	 * Add new Form to the DB
	 * @param creator User that created the form (userId)
	 * @param form_name Name of created form
	 * @return id of created form
	 */
	public int addNewForm(User creator, String form_name);
	/**
	 * Update form info
	 * @param formId to update
	 * @param creator value
	 * @return success
	 */
	public boolean updateForm(int formId, User creator, String form_name);
	/**
	 * Remove form from DB
	 * @param formId to remove
	 * @return success
	 */
	public boolean removeForm(int formId);
	
	// ----------------------------------------------------------------------
	
	/**
	 * Add question to specified Form
	 * @param formId to add question to
	 * @param type of the question
	 * @param label the question text
	 * @return id of the created question
	 */
	public int addQuestion(int formId, FormType type, String label);
	/**
	 * Update question in form
	 * @param questionId to update
	 * @param type value
	 * @param label value
	 * @return success
	 */
	public boolean updateQuestion(int questionId, FormType type, String label);
	/**
	 * Remove question from form
	 * @param questionId to remove
	 * @return success
	 */
	public boolean removeQuestion(int questionId);
	
	// ----------------------------------------------------------------------
	
	/**
	 * Add a submission to the DB
	 * @param formId submission relates to
	 * @param submitterId from user table
	 * @return id of the created submission
	 */
	public int addSubmission(int formId, int submitterId);
	/**
	 * Remove a submission from the DB
	 * @param submissionId to remove
	 * @return success
	 */
	public boolean removeSubmission(int submissionId);
	
	// ----------------------------------------------------------------------
	
	/**
	 * Add a string-value answer
	 * @param questionId that this is answering
	 * @param submissionId that this is from
	 * @param value the answer to the question
	 * @return id of the created answer
	 */
	public int addStringAnswer(int questionId, int submissionId, String value);
	/**
	 * Update a string-value answer
	 * @param answerStringId to update
	 * @param submissionId submission that this is from
	 * @param value to set the answer to
	 * @return success
	 */
	public boolean updateStringAnswer(int answerStringId, int submissionId, String value);
	/**
	 * Removes a string-value answer
	 * @param answerStringId to remove
	 * @return success
	 */
	public boolean removeStringAnswer(int answerStringId);
	
	// ----------------------------------------------------------------------
	
	/**
	 * Add an int-value answer
	 * @param questionId that this is answering
	 * @param submissionId that this is from
	 * @param value the answer to the question
	 * @return id of the created answer
	 */
	public int addIntAnswer(int questionId, int submissionId, Integer value);
	/**
	 * Update an int-value answer
	 * @param answerIntId to update
	 * @param submissionId submission that this is from
	 * @param value to set the answer to
	 * @return success
	 */
	public boolean updateIntnswer(int answerIntId, int submissionId, Integer value);
	/**
	 * Removes an int-value answer
	 * @param answerIntId to remove
	 * @return success
	 */
	public boolean removeIntAnswer(int answerIntId);

	// ----------------------------------------------------------------------
	
	/**
	 * Add a float-value answer
	 * @param questionId that this is answering
	 * @param submissionId that this is from
	 * @param value the answer to the question
	 * @return id of the created answer
	 */
	public int addFloatAnswer(int questionId, int submissionId, Float value);
	/**
	 * Update a float-value answer
	 * @param answerFloatId to update
	 * @param submissionId submission that this is from
	 * @param value to set the answer to
	 * @return success
	 */
	public boolean updateFloatAnswer(int answerFloatId, int submissionId, Float value);
	/**
	 * Removes a float-value answer
	 * @param answerFloatId to remove
	 * @return success
	 */
	public boolean removeFloatAnswer(int answerFloatId);
	
	// ----------------------------------------------------------------------
	
	/**
	 * Add a boolean-value answer
	 * @param questionId that this is answering
	 * @param submissionId that this is from
	 * @param value the answer to the question
	 * @return id of the created answer
	 */
	public int addBooleanAnswer(int questionId, int submissionId, Boolean value);
	/**
	 * Update a boolean-value answer
	 * @param answerBooleanId to update
	 * @param submissionId submission that this is from
	 * @param value to set the answer to
	 * @return success
	 */
	public boolean updateBooleanAnswer(int answerBooleanId, int submissionId, Boolean value);
	/**
	 * Removes a boolean-value answer
	 * @param answerBooleanId to remove
	 * @return success
	 */
	public boolean removeBooleanAnswer(int answerBooleanId);
	
	// ----------------------------------------------------------------------
	
	// utility functions
	
	/**
	 * Get structure of specified Form
	 * @param formId to retrieve
	 * @return List of FormElement objects
	 */
	public List<FormElement> getFormElements(int formId);
	
	/**
	 * Submit form answers for specified Form
	 * @param formId to submit an answer to
	 * @param submitterId that is submitting the answers
	 * @param values to fill the form with
	 * @return submission id
	 */
	public int submitFormAnswers(int formId, int submitterId, List<Object> values);
}
