/**
 * 
 */
package SharedObjects;

import java.io.Serializable;

/**
 * @author Ross
 *
 */
public class Assignment implements Serializable{
	static final long serialVersionUID = 50;
	/**
	 * Static counter to set the ID to the next ID 
	 */
	private static int idCount;
	private int id;
	private int courseID;
	private String path;
	private String title;
	private char active;
	private String dueDate;
	
	/**
	 * Constructor that inits all fields and increments idCount, used for creating new assignments
	 */
	public Assignment(int courseID, String path, String title, char active, String dueDate) {
		this.id = idCount++;
		this.courseID = courseID;
		this.path = path;
		this.title = title;
		this.active = active;
		this.dueDate = dueDate;
	}
	/**
	 * Constructor that inits all fields and receives int ID, for creating a temporary Assignment to return from the DB that already has an ID
	 */
	public Assignment(int id, int courseID, String path, String title, char active, String dueDate) {
		this.id = id;
		this.courseID = courseID;
		this.path = path;
		this.title = title;
		this.active = active;
		this.dueDate = dueDate;
	}
	
	/**
	 * initialize the ID count to a specified value, used when first reading the DB table in order to get next id to create
	 */
	public static void setIdCount(int idCount) {
		Assignment.idCount = idCount;
		System.out.println("Assignment idCount set to "+idCount);
	}
	
	

	/**
	 * @return the courseID
	 */
	public int getCourseID() {
		return courseID;
	}
	/**
	 * @param courseID the courseID to set
	 */
	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @return the id
	 */
	public int getID() {
		return id;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the active
	 */
	public char getActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(char active) {
		this.active = active;
	}
	/**
	 * @return the dueDate
	 */
	public String getDueDate() {
		return dueDate;
	}
	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	

}
