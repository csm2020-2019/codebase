package Health_System_Monitoring;

import java.sql.Date;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		Date date = new Date(System.currentTimeMillis());

		Main_GUI main = new Main_GUI();
		
		database_driver db = database_driver.getConnection();
		db.updatePatientRecord(1, date, "Comp sci lab", "Typhoid",
				"patient does not see clearly", "multivitamins", 1, "Jess",
				"John");


	}
}
