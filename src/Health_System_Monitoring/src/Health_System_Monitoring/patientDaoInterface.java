package Health_System_Monitoring;

import java.sql.Date;
import java.util.List;

public interface patientDaoInterface {
    boolean addPatientToDatabase(Patient patient, User user);

    boolean updatePatientRecord(Patient patient);

    List<Patient> getAllPatientRecords();

    boolean deletePatientRecord(int patient_id);

    List<Patient> searchPatient(String patient_last_name);
}
