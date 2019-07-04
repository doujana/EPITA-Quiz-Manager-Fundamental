package fr.epita.quiz.manager.doujana.tests;

import java.util.Scanner;

import fr.epita.quiz.manager.doujana.datamodel.QuestionType;

import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.services.data.QuestionTypeJDBCDAO;

public class TestQuestionTypeDAO {
	
	private static final String LOGIN = "ADM";
	private static final String PWD = "ADM";

	public static void main(String[] args) {

//		// given
//		QuestionTypeJDBCDAO dao = new QuestionTypeJDBCDAO();
//		QuestionType questtype = new QuestionType("a votre avis");
//
//		// when
//		try {
//			dao.create(questtype);
//		} catch (CreateFailedException e) {
//			System.out.println("this question type was not created :" + e.getFaultInstance());
//			e.printStackTrace();
//		}
//
//		// then ?
//		try {
//			List<QuestionType> searchResults = dao.search(new QuestionType(1));
//			// System.out.println(searchResults);
//			if (searchResults.size() < 1) {
//				System.out.println("error, got no result");
//			} else {
//
//				for (int i = 0; i < searchResults.size(); i++) {
//					System.out.println("ID : " + searchResults.get(i).getId());
//					System.out.println("TITLE : " + searchResults.get(i).getTitle());
//				}
//				System.out.println("success!");
//			}
//		} catch (SearchFailedException e) {
//
//			e.printStackTrace();
//		}
		/**
		 * This is while loop for choosing more than operation on the questiontype
		 * exmaple usage:Scanner scanner = new Scanner(System.in);
		 * boolean authenticated = authenticate(scanner);if(!authenticated){}
		 * while (!questionMade.equals("q")) {questionMade = displayMenu(scanner);
		 * switch (questionMade){case:
		 * break;}
		 */
		Scanner scanner = new Scanner(System.in);
		boolean authenticated = authenticate(scanner);
		if (!authenticated) {
			scanner.close();
			return;
		}

		System.out.println("You're authenticated");
		String questionTypeMade = "";
		while (!questionTypeMade.equals("q")) {

			questionTypeMade = displayMenu(scanner);

			switch (questionTypeMade) {
			case "1":
				QuestionTypeCreation();
				break;
			case "3":
				QuestionTypeDelete();
				break;
			case "2":
				QuestionType questionType = new QuestionType();
				int status = QuestionTypeUpdateRecord(questionType);
				if(status > 0) {
					System.out.println("Question record UPDATED successfully!");
				} else {
					System.out.println("Question record was NOT UPDATED");
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
	 * Create questiontype in the database if any error occurs it will through a
	 * {@link CreateFailedException} usage example:QuestionType questiontype = new ....()
	 * QuestionTypeJDBCDAO questionTypeJDAO=new.....()
	 * try{questionJDAO.QuestionTypeCreation(questiontype)}catch(CreateFailedException e){e.printStackTrace();}
	 *
	 */
	
	
	private static void QuestionTypeCreation() {

		System.out.println("Welcome to question Manager...");
		QuestionType questionType = new QuestionType();
		questionType.setTitle("Open Question");

		QuestionTypeJDBCDAO questionTypeDAO = new QuestionTypeJDBCDAO();
		try {
			
			int status = 0;
			status = questionTypeDAO.createRecord(questionType);
			
			// check for success or failure of operation
			if (status > 0 ) {
				// if create is true or successful
				System.out.println("Question record created successfully!");
			} else {
				// if create is false or unsuccessful
				System.out.println("Question record failed to be created");
			}
			
		} catch (CreateFailedException e) {

			e.printStackTrace();
		}
	}

	/**
	 * Update questiontype in the database if any error occurs it will be checked using variable status
	 * usage example:QuestionType questiontype = new ....()
	 * QuestionTypeJDBCDAO questionTypeJDAO=new.....()
	 * try{questionJDAO.QuestionTypeUpdateRecord(questiontype)}}
	 *@return updateStatus
	 */
	private static int QuestionTypeUpdateRecord(QuestionType questionType) {

		int updateStatus = 0;

		questionType.setId(4);
		questionType.setTitle("new course added!");

		QuestionTypeJDBCDAO questionTypeJDAO = new QuestionTypeJDBCDAO();

		updateStatus= questionTypeJDAO.updateRecord(questionType);
		
		return updateStatus;
	}
	/**
	 * Delete questiontype in the database if any error occurs it will handledthrough try catch
	 * usage example:QuestionType questiontype = new ....()
	 * QuestionTypeJDBCDAO questionTypeJDAO=new.....()
	 *
	 * try{questionJDAO.QuestionTypeDelete(questiontype)}}
	 *catch(Exception e)
	 *{e.printStackTrace();
	 *}
	 */
	private static void QuestionTypeDelete() {

		QuestionType questionType = new QuestionType();
		questionType.setId(5);

		QuestionTypeJDBCDAO questionTypeJDAO = new QuestionTypeJDBCDAO();

		try {
			questionTypeJDAO.delete(questionType);
			System.out.println("Question Type record DELETED successfully!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	    * This method dispaly a menu in order to choose one of the CRUD process on the questiontype table
	    * @param scanner
	    * @return
	    */
	private static String displayMenu(Scanner scanner) {
		String mcq;
		System.out.println("-- Menu --");
		System.out.println("1. Create Question Type");
		System.out.println("2. Update Question Type");
		System.out.println("3. Delete Question Type");

		System.out.println("q. Quit the application");
		System.out.println("What is your choice ? (1|2|3|q) :");
		
		mcq = scanner.nextLine();
		
		return mcq;
	}
	/**
	 * This method kust authenticating the login for the user by his name and password if not he wont log in
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
