package Health_System_Monitoring;

/*
 * Patient model class
 * It holds the data of a patient
 */
import java.sql.Date;

public class Patient {
	private int patient_id;
	private Date patient_dob;
	private String patient_first_name;
	private String patient_last_name;
	private String patient_address;
	private String patient_medical_history;
	private String patient_diagnosis;
	private String patient_prescriptions;
	
	public Patient() {
		
	}
	
	public Patient(int patient_id, String patient_first_name, String patient_last_name, Date patient_dob, String patient_address,
			String patient_medical_history, String patient_diagnosis, String patient_prescriptions) {
		this.patient_id = patient_id;
		this.patient_dob = patient_dob;
		this.patient_first_name = patient_first_name;
		this.patient_last_name = patient_last_name;
		this.patient_address = patient_address;
		this.patient_medical_history = patient_medical_history;
		this.patient_diagnosis = patient_diagnosis;
		this.patient_prescriptions = patient_prescriptions;
	}
	
	public int getPatientId() {
		return patient_id;
	}
	
	public String getPatientFirstName() {
		return patient_first_name;
	}

	public String getPatientLastName() { return patient_last_name; }
	
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
		return "Id: " + getPatientId() + " \nFirst name: " + getPatientFirstName() + " \nLast name: " + getPatientLastName() +
				" \nDOB: " + getPatientDob() + " \nAddress: " + getPatientAddress() + " \nMed history: " + 
				getPatientMedicalHistory() +  " \nDiagnosis: " + getPatientDiagnosis() + " \nPrescriptions: " + 
				getPatientPrescriptions();
	}

	/**
	 * @param patient_id the patient_id to set
	 */
	public void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
	}

	/**
	 * @param patient_dob the patient_dob to set
	 */
	public void setPatient_dob(Date patient_dob) {
		this.patient_dob = patient_dob;
	}

	/**
	 * @param patient_first_name the patient_first_name to set
	 */
	public void setPatient_first_name(String patient_first_name) {
		this.patient_first_name = patient_first_name;
	}

	/**
	 * @param patient_last_name the patient_last_name to set
	 */
	public void setPatient_last_name(String patient_last_name) {
		this.patient_last_name = patient_last_name;
	}

	/**
	 * @param patient_address the patient_address to set
	 */
	public void setPatient_address(String patient_address) {
		this.patient_address = patient_address;
	}

	/**
	 * @param patient_medical_history the patient_medical_history to set
	 */
	public void setPatient_medical_history(String patient_medical_history) {
		this.patient_medical_history = patient_medical_history;
	}

	/**
	 * @param patient_diagnosis the patient_diagnosis to set
	 */
	public void setPatient_diagnosis(String patient_diagnosis) {
		this.patient_diagnosis = patient_diagnosis;
	}

	/**
	 * @param patient_prescriptions the patient_prescriptions to set
	 */
	public void setPatient_prescriptions(String patient_prescriptions) {
		this.patient_prescriptions = patient_prescriptions;
	}
}
