package Health_System_Monitoring;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class Main {

	public static void main(String[] args) throws SQLException {

//		Main_GUI main = new Main_GUI();
//		List<User> userRecord = null;
		User user = null;

		database_driver db = database_driver.getConnection();
		user = db.checkCredentials("test", "test");

		if(user != null){
			System.out.println(user);
		}
		else{
			System.out.println("Invalid ");

		}


	}
}
