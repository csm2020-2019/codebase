package Health_System_Monitoring.Health_System_Monitoring;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		database_driver db_connect = database_driver.getConnection();
		db_connect.closeDbConnection();

		Main_GUI GUI = new Main_GUI();
	}
}
