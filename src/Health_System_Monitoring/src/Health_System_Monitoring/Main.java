package Health_System_Monitoring;

import java.sql.Date;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Patient> patientList = null;

		database_driver db = database_driver.getConnection();
		patientList = db.getAllPatientRecords();

		for(Patient patient : patientList){
			System.out.println(patientList);
		}
		
	}
}
