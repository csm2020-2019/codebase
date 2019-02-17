package Health_System_Monitoring;

import java.sql.Date;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		database_driver db = database_driver.getConnection();
		db.deletePatientRecord(2);
//
//

	}
}
