/**
 * 
 */
package FrontEnd.FrontController;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import FrontEnd.View.ViewStudentsPage;
import SharedObjects.DBMessage;
import SharedObjects.Student;
import SharedObjects.StudentEnrollment;

/**
 * @author 	Antoine Bizon & Ross Bartlett
 *
 */
public class ViewStudentsListListener implements ListSelectionListener {
	/**
	 * The panel with the button calling this listener
	 */
	private ViewStudentsPage panel;
	/**
	 * the controller constructing this listener
	 */
	private ProfController controller;

	public ViewStudentsListListener(ViewStudentsPage p, ProfController c) {
		panel = p;
		controller=c;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		JList<Student> list = panel.getResultsList();
		int index = list.getSelectedIndex();
		if (index<0) return;
		Student selected = (Student)  list.getSelectedValue();
		panel.setSelectedStudent(selected);
		//check if the selected student is enrolled in the course
		ArrayList<String> params = new ArrayList<>();
		params.add("STUDENTID");
		params.add("'"+Integer.toString(selected.getID())+"'");	
		DBMessage msg = new DBMessage(2, 0, params); // 2,0 is studentEnrollmentTableNum, searchOpNum
		//send the message, get response
		ArrayList<? extends Serializable> response = controller.getCommunicator().communicate(msg);
		//response should be a list of all the StudentEnrollments that that student is enrolled in 
		int enrollmentID = -1;
		for (Serializable enrollment : response) {
			if (((StudentEnrollment) enrollment).getCourseID() == panel.getCourse().getID() ){
				enrollmentID = ((StudentEnrollment) enrollment).getID(); //if student is enrolled in this course, enrollmentID will change to its positive ID
			}
		}
		//update the panel fields
		panel.setSelectedStudentEnrollmentID(enrollmentID);



	}
}
