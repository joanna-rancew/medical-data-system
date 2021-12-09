package model;

import java.util.Date;

/**
 * Klasa reprezentujaca dane badania (karta kardiologiczna)
 * 
 * @author Kryczka_Rancew
 *
 */
public class TestData {

	private int bloodPressure1 = 0; // w mm Hg
	private int bloodPressure2 = 0; // w mm Hg
	private int heartRate = 0; // w bpm
	private int patientId = 1;
	public Date testDate = new Date(); // czas wprowadzania badania

	/**
	 * Konstruktor z aktualna data badania
	 * 
	 * 
	 * @param bP1       pierwsza wartosc cisnienia w mm Hg
	 * @param bP2       druga wartosc cisnienia w mm Hg
	 * @param heartRate tetno w uderzeniach serca na minute
	 * @param patientId ID pacjenta
	 */
	public TestData(int bP1, int bP2, int heartRate, int patientId) {
		super();
		this.bloodPressure1 = bP1;
		this.bloodPressure2 = bP2;
		this.heartRate = heartRate;
		this.testDate = new Date();
		this.patientId = patientId;
	}

	/**
	 * 
	 * @return cisnienie skurczowe w mm rteci
	 */
	public int getBloodPressure1() {
		return bloodPressure1;
	}

	public void setBloodPressure1(int bloodPressure1) {
		this.bloodPressure1 = bloodPressure1;
	}

	/**
	 * 
	 * @return cisnienie rozkurczowe w mm rteci
	 */
	public int getBloodPressure2() {
		return bloodPressure2;
	}

	public void setBloodPressure2(int bloodPressure2) {
		this.bloodPressure2 = bloodPressure2;
	}

	/**
	 * 
	 * @return tetno w liczbie uderzen serca na minute
	 */
	public int getHeartRate() {
		return heartRate;
	}

	/**
	 * 
	 * @param heartRate tetno w liczbie uderzen serca na minute
	 */
	public void setHeartRate(int heartRate) {
		this.heartRate = heartRate;
	}

	@Override
	public String toString() {
		return "TestData [bloodPressure1=" + bloodPressure1 + ", bloodPressure2=" + bloodPressure2 + ", heartRate="
				+ heartRate + ", testDate=" + testDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bloodPressure1;
		result = prime * result + bloodPressure2;
		result = prime * result + heartRate;
		result = prime * result + ((testDate == null) ? 0 : testDate.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		TestData other = (TestData) obj;

		if (bloodPressure1 != other.bloodPressure1)
			return false;
		if (bloodPressure2 != other.bloodPressure2)
			return false;
		if (heartRate != other.heartRate)
			return false;
		if (testDate == null) {
			if (other.testDate != null)
				return false;
		} else if (!testDate.equals(other.testDate))
			return false;

		return true;
	}

	public int getPatientId() {
		return this.patientId;
	}

}
