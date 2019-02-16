package Health_System_Monitoring;

/*
 * Patient model class
 * It holds the data of a patient
 */
import java.sql.Date;

public class Patient {
	private int patient_id;
	private Date patient_dob;
	private String patient_name;
	private String patient_address;
	private String patient_medical_history;
	private String patient_diagnosis;
	private String patient_prescriptions;
	
	public Patient(int patient_id, String patient_name, Date patient_dob, String patient_address, 
			String patient_medical_history, String patient_diagnosis, String patient_prescriptions) {
		this.patient_id = patient_id;
		this.patient_dob = patient_dob;
		this.patient_name = patient_name;
		this.patient_address = patient_address;
		this.patient_medical_history = patient_medical_history;
		this.patient_diagnosis = patient_diagnosis;
		this.patient_prescriptions = patient_prescriptions;
	}
	
	public int getPatientId() {
		return patient_id;
	}
	
	public String getPatientName() {
		return patient_name;
	}
	
	public Date getPatientDob() {
		return patient_dob;
	}
	
	public String getPatientAddress() {
		return patient_address;
	}
	
	public String getPatientMedicalHistory() {
		return patient_medical_history;
	}
	
	public String getPatientDiagnosis() {
		return patient_diagnosis;
	}
	
	public String getPatientPrescriptions() {
		return patient_prescriptions;
	}
	
	public String toString() {
		return "Id: " + getPatientId() + " \nName: " + getPatientName() +
				" \nDOB: " + getPatientDob() + " \nAddress: " + getPatientAddress() + " \nMed history: " + 
				getPatientMedicalHistory() +  " \nDiagnosis: " + getPatientDiagnosis() + " \nPrescriptions: " + 
				getPatientPrescriptions();
	}
}
