/**
 * 
 */
package BackEnd.BackController;

import java.io.Serializable;
import java.util.ArrayList;

import BackEnd.Model.ModelExecutor;
import BackEnd.Model.Table;
import SharedObjects.Assignment;
import SharedObjects.DBMessage;
import SharedObjects.FileContents;
import SharedObjects.FileMessage;
import SharedObjects.Message;
import SharedObjects.Submission;

/**
 * @author 	Antoine Bizon & Ross Bartlett
 */
class FileController extends ModelController {
	private FileMessage fileMessage;
	
	/**
	 * @param theModel
	 */
	FileController(ModelExecutor theModel) {
		super(theModel);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see BackEnd.BackController.ModelController#executeMessage(SharedObjects.Message)
	 */
	@Override
	ArrayList<? extends Serializable> executeMessage(Message theMessage) {
		fileMessage = (FileMessage) theMessage;
		if(fileMessage.getOp() == 2) {
			return saveFile();
		}
		else if(fileMessage.getOp() == 0) {
			return loadFile();
		}
		
		return new ArrayList<Integer>();
	}

	/**
	 * 
	 */
	private ArrayList<? extends Serializable> loadFile() {
		ArrayList<FileContents> returnMessage = new ArrayList<FileContents>();
		if(fileMessage.getTable() == 3) {
			Assignment loadedAssign = (Assignment) theModel.getDatabase().getTableAt(3).search
					((String)fileMessage.getParams().get(0), (String)fileMessage.getParams().get(1)).get(0);
			
			returnMessage.add(theModel.getFileOperator().loadFile(loadedAssign.getPath()));
		}
		else if (fileMessage.getTable() == 4 ) {
			Submission loadedSubmission = (Submission) theModel.getDatabase().getTableAt(4).search
					((String)fileMessage.getParams().get(0), (String)fileMessage.getParams().get(1)).get(0);
			
			returnMessage.add(theModel.getFileOperator().loadFile(loadedSubmission.getPath()));
		}
		return returnMessage;
	}

	/**
	 * 
	 */
	private ArrayList<? extends Serializable> saveFile() {
		String path;
		if(fileMessage.getTable() == 3) {
			Assignment newAssignment = (Assignment) fileMessage.getParams().get(0);
			path = theModel.getFileOperator().saveFile(newAssignment.getTitle(), fileMessage.getExt(),
														fileMessage.getContents());
			newAssignment.setPath(path);
			return theModel.getDatabase().getTableAt(3).addToDB(newAssignment);
		}
		else if (fileMessage.getTable() == 4 ) {
			Submission newSubmission = (Submission) fileMessage.getParams().get(0);
			path = theModel.getFileOperator().saveFile(newSubmission.getTitle(), fileMessage.getExt(),
					fileMessage.getContents());
			newSubmission.setPath(path);
			return theModel.getDatabase().getTableAt(4).addToDB(newSubmission);
		}
		return new ArrayList<Integer>();
	}

}
