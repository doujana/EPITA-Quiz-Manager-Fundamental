package fr.epita.quiz.manager.doujana.tests;

import java.util.List;
import java.util.Scanner;

import fr.epita.quiz.manager.doujana.datamodel.DifficultyLevel;
import fr.epita.quiz.manager.doujana.datamodel.QuestionType;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;
import fr.epita.quiz.manager.doujana.services.data.DifficultyLevelJDBCDAO;
import fr.epita.quiz.manager.doujana.services.data.QuestionTypeJDBCDAO;

public class TestDifficultyLevelFileDAO {

	private static final String LOGIN = "ADM";
	private static final String PWD = "ADM";

	public static void main(String[] args) {

		// given
		DifficultyLevelJDBCDAO difficultyLevelJDBCDAO = new DifficultyLevelJDBCDAO();
		
		DifficultyLevel difficultyLevel = new DifficultyLevel();
		difficultyLevel.setDifficulty_level("EASY");
		
		// when
		try {
			
			difficultyLevelJDBCDAO.create(difficultyLevel);
			
		} catch (CreateFailedException e) {
			System.out.println("this difficulty level was not created :" + e.getFaultInstance());
			e.printStackTrace();
		}

		// then ?
		try {
			List<DifficultyLevel> searchResults = difficultyLevelJDBCDAO.search(new DifficultyLevel("easy"));

			if (searchResults.size() < 1) {
				
				System.out.println("error, got no result");
				
			} else {

				for (int i = 0; i < searchResults.size(); i++) {
					System.out.println("ID : " + searchResults.get(i).getId());
					System.out.println("Difficulty Level : " + searchResults.get(i).getDifficulty_level());
				}
				System.out.println("success!");
			}
		} catch (SearchFailedException e) {

			e.printStackTrace();
		}
		
		/**
		 * This is while loop for choosing more than operation on the DifficultyLevel
		 * exmaple usage:Scanner scanner = new Scanner(System.in);
		 * boolean authenticated = authenticate(scanner);if(!authenticated){}
		 * while (!difficultylevelMade.equals("q")) {difficultylevelMade = displayMenu(scanner);
		 * switch (difficultylevelnMade){case:
		 * break;}
		 */
		Scanner scanner = new Scanner(System.in);
		boolean authenticated = authenticate(scanner);
		if (!authenticated) {
			scanner.close();
			return;
		}

		System.out.println("You're authenticated");
		String difficultylevelMade = "";
		while (! difficultylevelMade.equals("q")) {

			 difficultylevelMade = displayMenu(scanner);

			switch ( difficultylevelMade) {
			case "1":
				DifficultyLevelCreation();
				break;
			case "3":
				DifficultyLevelDelete();
				break;
			case "2":
				DifficultyLevel difficultylevel = new DifficultyLevel();
				int status = DifficultyLevelUpdateRecord(difficultylevel);
				if(status > 0) {
					System.out.println("Difficulty Level record UPDATED successfully!");
				} else {
					System.out.println("Difficulty Level record was NOT UPDATED");
				}
				break;

			case "q":
				System.out.println("Good bye!");
				break;

			default:
				System.out.println("Option not recognized, please enter an other option");
				break;
			}
		}

		scanner.close();

	}

	
/**
 * Create Difficulty Level in the database if any error occurs it will through a
 * {@link CreateFailedException} usage DifficultyLevel difficultylevel = new ....()
 * difficultylevelJDBCDAO difficultylevelJDAO=new.....()
 * try{difficultylevelJDAO.difficultylevelCreation(difficultylevel)}catch(CreateFailedException e){e.printStackTrace();}
 *
 */
private static void DifficultyLevelCreation() {
	
	System.out.println("Welcome to difficulty level Manager...");
	DifficultyLevel difficultylevel = new DifficultyLevel();
	difficultylevel.setDifficulty_level("Hard");

	DifficultyLevelJDBCDAO difficultylevelDAO = new DifficultyLevelJDBCDAO();
	try {
		int status = 0;
		
		status = difficultylevelDAO.create(difficultylevel);
		
		// check for success or failure of operation
		if (status >0) {
			// if create is greater than zero so it is  successful
			System.out.println("Difficultylevel record created successfully!");
		} else {
			// if create is less than zero or unsuccessful
			System.out.println("Difficultylevel record failed to be created");
		}
	
		
	} catch (CreateFailedException e) {

		e.printStackTrace();
	}
	
}

/**
 * Update difficultylevel in the database if any error occurs it will be checked using variable status
 * usage example:DifficultyLevel difficultylevel = new ....()
 * QuestionTypeJDBCDAO questionTypeJDAO=new.....()
 * try{difficultylevelJDAO.difficultylevelUpdateRecord(difficultylevel)}}
 *@return updateStatus
 */
private static int DifficultyLevelUpdateRecord(DifficultyLevel difficultylevel) {

	int updateStatus = 0;

	difficultylevel.setId(4);
	difficultylevel.setDifficulty_level("VERY HARD");

	DifficultyLevelJDBCDAO difficultylevelJDAO = new DifficultyLevelJDBCDAO();

	try {
		updateStatus= difficultylevelJDAO.updateRecord(difficultylevel);
	} catch (Exception e) {
		
		e.printStackTrace();
	}
	
	return updateStatus;
}

/**
 * Delete difficultylevel in the database if any error occurs it will handled through try catch
 * usage DifficultyLevel difficultylevel = new ....()
 *difficultylevelJDBCDAO questionTypeJDAO=new.....()
 *
 * try{difficultylevelJDAO.difficultylevelDelete(difficultylevel)}}
 *catch(Exception e)
 *{e.printStackTrace();
 *}
 */
private static void DifficultyLevelDelete() {

	DifficultyLevel difficultylevel = new DifficultyLevel();
	difficultylevel.setId(5);

	DifficultyLevelJDBCDAO difficultylevelJDAO = new DifficultyLevelJDBCDAO();

	try {
		difficultylevelJDAO.delete(difficultylevel);
		System.out.println("Difficulty level record DELETED successfully!");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
/**
    * This method dispaly a menu in order to choose one of the CRUD process on the difficulty level table
    * @param scanner
    * @return
    */
private static String displayMenu(Scanner scanner) {
	String mcq;
	System.out.println("-- Menu --");
	System.out.println("1. Create DifficultyLevel ");
	System.out.println("2. Update DifficultyLevel ");
	System.out.println("3. Delete DifficultyLevel ");

	System.out.println("q. Quit the application");
	System.out.println("What is your choice ? (1|2|3|q) :");
	
	mcq = scanner.nextLine();
	
	return mcq;
}
/**
 * This method is for authenticating the login for the user by his name and password if not he wont log in
 * @param scanner
 * @return
 */

private static boolean authenticate(Scanner scanner) {
	System.out.println("Please enter your login : ");
	String login = scanner.nextLine();
	System.out.println("Please enter your password : ");
	String password = scanner.nextLine();

	return LOGIN.equals(login) && PWD.equals(password);
}

	
}
