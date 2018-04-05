package FrontEnd.FrontController;

import javax.swing.*;

import FrontEnd.View.ProfHomepage;
import SharedObjects.Course;
import SharedObjects.DBMessage;

import java.awt.event.*;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * Listener class for the Create a New Course button
 * @author 	Antoine Bizon & Ross Bartlett
 */
public class NewCourseButtonListener implements ActionListener{

	/**
	 * The panel with the button calling this listener
	 */
	private ProfHomepage panel;
	/**
	 * the controller constructing this listener
	 */
	private ProfController controller;
	/**
	 * The textFields with which user will input the new course name
	 */
	private JTextField courseNameField = new JTextField();
	/**
	 * The user's input
	 */
	private String name;


	/**
	 * All the components to display on the frame 
	 * Used for displaying multiple components on JOPtionPane
	 */
	private Object[] items = {"New Course", "Name:", courseNameField};

	/**
	 * Constructor that inits the calling JPanel
	 * @param p the calling JPanel
	 * @param c the controller to access the backend
	 */
	public NewCourseButtonListener(ProfHomepage p, ProfController c) {
		panel = p;
		controller=c;
	}



	/**
	 * Displays a JOptionPane to add a new Course
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int option = JOptionPane.showConfirmDialog(null, items, "Create a New Assignment", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			name = courseNameField.getText();
			courseNameField.setText(""); //empty the field

			if(name.equals("")) {
				JOptionPane.showMessageDialog(null, "Error: course name can't be empty", "Error creating course", JOptionPane.WARNING_MESSAGE);
				return;
			}

			//construct the new course
			Course temp = new Course(-1, panel.getProf().getID(), name, false); // false so the course starts as inactive // -1 is ID that will be generated by model 

			//make a message to tell the DB to add the course
			ArrayList<Course> params = new ArrayList<>();
			params.add(temp);
			DBMessage msg = new DBMessage(1, 2, params); // 1,2 is courseTableNum, addOpNum

			//send the message, get response
			ArrayList<? extends Serializable> response = controller.getCommunicator().communicate(msg); 
			checkResponse(response);
			controller.fillHomePageCourseList(panel); //refresh the courseList
		}
		courseNameField.setText(""); //empty the field
	}
	
	/**
	 * Helper method to determine if add to DB was successfull
	 * @param response the arrayList returned by addToDB()
	 */
	private void checkResponse(ArrayList<? extends Serializable> response) {
		if(response.size()>1) {
			System.out.println("Unexpected error adding a course: addToDB returned "+response.size()+" sized arrayList");
			JOptionPane.showMessageDialog(null, "Error: Course "+name+" may not have been created.", "Course Creation Error", JOptionPane.WARNING_MESSAGE);
		}
		else if (response.size()==0 || (Integer)response.get(0)!=1) {
			System.out.println("Error adding course - addToDB returned empty arrayList or returned not 1");
			JOptionPane.showMessageDialog(null, "Error: Course "+name+" could not be created.", "Course Creation Error", JOptionPane.WARNING_MESSAGE);

		}	
		else {
			//success, DB added 1 row 
			System.out.println("Course "+name+" successfuly created.");
			JOptionPane.showMessageDialog(null, "Course "+name+" successfuly created.", "Course Created", JOptionPane.INFORMATION_MESSAGE);
		}
	}






}
