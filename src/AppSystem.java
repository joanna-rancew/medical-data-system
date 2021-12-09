import javax.swing.SwingUtilities;

import controller.AppController;
import model.AppModel;
import model.PatientData;
import model.TestData;
import view.AppView;

/**
 * Klasa g³ówna aplikacji
 * 
 * @author Kryczka_Rancew
 *
 */
public class AppSystem {

	/**
	 * G³ówna funkcja programu
	 * 
	 * @param args argumenty
	 */
	public static void main(String[] args) {

		Runnable thread = new Runnable() {

			@Override
			public void run() {
				PatientData patient1 = new PatientData("Karol", "Sus", "Opole", "64120365123");
				PatientData patient2 = new PatientData("Adam", "Nowak", "Warszawa", "59120634728");
				PatientData patient3 = new PatientData("Karolina", "D¹bek", "Gdynia", "68060634558");

				// model aplikacji
				AppModel model = new AppModel();

				// dodanie kilku pacjentow do bazy
				patient1 = model.addPatient(patient1);
				patient2 = model.addPatient(patient2);
				patient3 = model.addPatient(patient3);

				// dodanie wynikow pacjentow
				model.getPatientList().addTest(new TestData(125, 85, 81, patient1.getPatientID()),
						model.getPatient(patient1.getPatientID()));
				model.getPatientList().addTest(new TestData(123, 82, 72, patient1.getPatientID()),
						model.getPatient(patient1.getPatientID()));
				model.getPatientList().addTest(new TestData(134, 87, 67, patient1.getPatientID()),
						model.getPatient(patient1.getPatientID()));
				model.getPatientList().addTest(new TestData(145, 94, 75, patient1.getPatientID()),
						model.getPatient(patient1.getPatientID()));
				model.getPatientList().addTest(new TestData(158, 99, 63, patient1.getPatientID()),
						model.getPatient(patient1.getPatientID()));

				model.getPatientList().addTest(new TestData(140, 95, 63, patient2.getPatientID()),
						model.getPatient(patient2.getPatientID()));
				model.getPatientList().addTest(new TestData(120, 83, 72, patient2.getPatientID()),
						model.getPatient(patient2.getPatientID()));
				model.getPatientList().addTest(new TestData(128, 82, 80, patient2.getPatientID()),
						model.getPatient(patient2.getPatientID()));

				model.getPatientList().addTest(new TestData(120, 80, 65, patient3.getPatientID()),
						model.getPatient(patient3.getPatientID()));
				model.getPatientList().addTest(new TestData(130, 90, 71, patient3.getPatientID()),
						model.getPatient(patient3.getPatientID()));
				model.getPatientList().addTest(new TestData(140, 90, 74, patient3.getPatientID()),
						model.getPatient(patient3.getPatientID()));
				model.getPatientList().addTest(new TestData(135, 85, 72, patient3.getPatientID()),
						model.getPatient(patient3.getPatientID()));

				// widok aplikacji
				AppView view = new AppView(model);

				// kontroler aplikacji
				AppController controller = new AppController(view, model);
				view.setController(controller);
				view.setVisible(true);

			}
		};
		SwingUtilities.invokeLater(thread);

	}
}
