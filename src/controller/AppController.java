package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.AppModel;
import model.PatientData;
import model.TestData;
import view.AppView;

/**
 * Klasa odpowiadajaca za wykonywanie dzialan z polecen uzytkownika
 * 
 * @author Kryczka_Rancew
 *
 */
public class AppController implements ActionListener, ListSelectionListener {
	private AppView appView = null;
	private AppModel appModel = null;

	/**
	 * Konstruktor kontrollera
	 * 
	 * @param view  widok GUI
	 * @param model model danych
	 */
	public AppController(AppView view, AppModel model) {
		this.appModel = model;
		this.appView = view;
	}

	/**
	 * Funkcja, ktora pozwala skomunikowac sie poprzez model z baz¹ danych, by
	 * pobraæ dane pacjenta o podanym ID
	 * 
	 * @param ID ID pacjenta
	 * @return obiekt typu PatientData o podanym ID
	 */
	public PatientData getPatient(int ID) {
		if (ID > 0 && ID <= this.appModel.getPatientList().getLastId()
				&& this.appModel.getPatientList().checkId(ID) == 1)
			return (this.appModel.getPatientList().getPatient(ID));

		return (null);
	}

	/**
	 * Dodawanie pacjenta - funkcja, ktora pozwala dodac pacjenta poprzez
	 * komunikacje modelu danych z baz¹ danych
	 * 
	 * @param patient obiekt typu PatientData zawierajacy dane do dodania
	 * @return dodany obiekt PatientData
	 */
	public PatientData addPatient(PatientData patient) {

		try {
			int r = this.appModel.getPatientList().checkPesel(patient.getPesel());
			if (r == 0)
				return this.appModel.getPatientList().addPatient(patient);
			else if (r == 1)
				JOptionPane.showMessageDialog(this.appView.getMainPanel(), "Pesel jest ju¿ w bazie");
			return null;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this.appView.getMainPanel(), "Dodaj pacjenta ponownie oraz sprawdz dane");
			System.out.println("Blad dodawania pacjenta");
			return null;
		}

	}

	/**
	 * Usuwanie pacjenta - funkcja, ktora pozwala usunac pacjenta o wybranym ID
	 * poprzez komunikacje modelu danych z baz¹ danych
	 * 
	 * @param ID PatientId pacjenta do usuniecia z bazy
	 */
	public void deletePatient(int ID) {

		if (ID > 0 && ID <= this.appModel.getPatientList().getLastId()
				&& this.appModel.getPatientList().checkId(ID) == 1) {
			PatientData patient = this.getPatient(ID);
			if ((patient) != null)

				this.appModel.getPatientList().deletePatient(ID);
		} else {
			JOptionPane.showMessageDialog(this.appView.getMainPanel(),
					"Wprowadz dane ponownie - usuwanie sie nie powiodlo");
			System.out.println("Blad wprowadzania danych- usuwanie sie nie powiodlo");

		}

	}

	@Override
	/**
	 * Funkcja, ktora pozwala wskazac odpowiednie instrukcje
	 * 
	 * @param e zaistniale dzialanie
	 */
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("addPatient")) {
			PatientData newPatient = this.appView.getPatientData();
			if (newPatient != null) {
				newPatient = this.addPatient(newPatient);
				this.appView.updatePatientListTable();
				this.appView.clearTextFields();

			} else {
				JOptionPane.showMessageDialog(this.appView.getMainPanel(), "Wprowadz dane ponownie");
				System.out.println("Blad wprowadzania danych");
				return;
			}

		} else if (e.getActionCommand().equals("updatePatient")) {
			PatientData updatedPatient = this.appView.getUpdatedData();
			if (updatedPatient != null) {
				this.appModel.getPatientList().updatePatient(this.appView.getIDtoUpdate(), updatedPatient);
				this.appView.updatePatientListTable();
				this.appView.clearTextFields();
			}

		} else if (e.getActionCommand().equals("deletePatient")) {
			if (!String.valueOf(this.appView.getIDtoDelete()).isBlank()) {
				this.deletePatient(this.appView.getIDtoDelete());
				this.appView.updatePatientListTable();
				this.appView.clearTextFields();
			}

		} else if (e.getActionCommand().equals("addTest")) {
			try {
				TestData test = this.appView.getTestData();
				this.appModel.getPatientList().addTest(test, appModel.getPatient(test.getPatientId()));
				this.appView.clearTextFields();
			} catch (Exception e1) {
				System.out.println(e1);
			}
		}

	}

	@Override
	/**
	 * Funkcja, ktora pozwala zebrac wybrany wiersz z tabeli
	 * 
	 * @param e zaistniale dzialanie - wybrany wiersz
	 */
	public void valueChanged(ListSelectionEvent e) {
		int index = this.appView.getSelectedIndex();
		PatientData p = this.appModel.getPatient(index);
		if (p == null) {
			JOptionPane.showMessageDialog(this.appView.getMainPanel(), "Wybierz ponownie pacjenta");
			return;
		}
		if (p.getTestNumber() == 0) {
			JOptionPane.showMessageDialog(this.appView.getMainPanel(), "B³¹d: brak testów, wprowadz testy pacjenta");
			return;
		}
		this.appView.showTestWindow(p);

	}

}
