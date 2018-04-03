package BackEnd.Model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import SharedObjects.StudentEnrollment;

/**
 * This class implements the abstract methods in the Table class for the student enrollment table.
 * @author 	Antoine Bizon
 * @version 1.0
 * @since	2018-03-30
 */
public class StudentEnrollmentTable extends Table {

	/**
	 * Constructs an object of class StudentEnrollmentTable.
	 * @param execute
	 * @param tableName
	 */
	public StudentEnrollmentTable(StatementExecutor execute, String tableName) {
		super(execute, tableName);
	}

	/* (non-Javadoc)
	 * @see BackEnd.Model.Table#addToDB(java.io.Serializable)
	 */
	@Override
	protected <T extends Serializable> int addToDB(T addition) {
		StudentEnrollment enrolment = (StudentEnrollment) addition;
		String update = "INSERT INTO " + tableName +
				" VALUES ( " + IDGenerator.generateID() + ", " + 
				enrolment.getStudentID() + ", " +
				enrolment.getCourseID()+ ");";
		return execute.preformUpdate(update);
	}

	/* (non-Javadoc)
	 * @see BackEnd.Model.Table#listFromResultSet(java.sql.ResultSet)
	 */
	@Override
	protected ArrayList<? extends Serializable> listFromResultSet(ResultSet results) {
		ArrayList<StudentEnrollment> enrolments = new ArrayList<StudentEnrollment>();
		try {
			while(results.next()) {
				enrolments.add(new StudentEnrollment(results.getInt(1), results.getInt(2), results.getInt(3)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return enrolments;
	}

}
