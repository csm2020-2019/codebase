package Health_System_Monitoring;

import java.util.List;


public interface userDaoInterface {
    /*
     * method to check login credentials
     * @params username and password of user
     * @return User object if successfully authenticated
     * or null if not
     *
     */
    User checkCredentials(String userName, String userPassword);


    /*
    gets users matching the param user type
    @param userType the type of user
    @return a list of users matching the type specified
     */
    List<User> getUserByType(String userType);


    /*
    method to search user
    @param user last name
    @return a list of users that matches the name
    */
    List<User> searchUser(String userLastName);


    /*
     * method to fetch all user record from database
     * @return a list of all users from database
     */
    List<User> getAllUsers();


    /*
    @param patient the User object - Create a user object and pass into the method
    @return boolean true if patient record successfully added
    false for not added
     */
    boolean addNewUserToDatabase(User user);


    /*
    @param user is the User object
    Create a user object and pass into
    the method
    @return boolean true if updated and false if not updated
     */
    boolean updateUserRecord(User user);

    /*
     method to delete a user record from database
     @ param userId is the id of the row in user table
     @return true if deleted and false if not
    */
    boolean deleteUserFromDatabase(int userId);
}
