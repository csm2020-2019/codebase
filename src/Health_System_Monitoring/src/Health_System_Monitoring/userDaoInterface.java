package Health_System_Monitoring;

import java.util.List;


public interface User_Dao_Interface {
    User checkCredentials(String userName, String userPassword);

    List<User> searchUser(String userLastName);

    List<User> getAllUsers();

    boolean addNewUserToDatabase(int userId, String userName, String userPass, String userFirstName, String userLastName,
                                 String userEmail, String userType);

    boolean updateUserRecord(int userId, String userName, String userPass, String userFirstName, String userLastName,
                             String userEmail, String userType);

    boolean deleteUserFromDatabase(int userId);

    void createNewUser(int userId, String userName, String userPass, String userFirstName, String userLastName,
                       String userEmail, String userType);
}
