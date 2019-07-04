package fr.epita.quiz.manager.doujana.tests;

import java.util.List;
import java.util.Scanner;


import fr.epita.quiz.manager.doujana.datamodel.McqAnswer;
import fr.epita.quiz.manager.doujana.datamodel.McqChoice;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.DataAccessException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;
import fr.epita.quiz.manager.doujana.exception.UpdateFailedException;
import fr.epita.quiz.manager.doujana.services.data.McqAnswerJDBCDAO;
import fr.epita.quiz.manager.doujana.services.data.McqChoiceJDBCDAO;


public class TestMcqAnswerFileDAO {

	private static final String LOGIN = "ADM";
	private static final String PWD = "ADM";

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		boolean authenticated = authenticate(scanner);
		if (!authenticated) {
			scanner.close();
			return;
		}

		System.out.println("You're authenticated");
		String mcqanswer = "";
		while (!mcqanswer.equals("q")) {

			mcqanswer = displayMenu(scanner);

			switch (mcqanswer) {
			case "1":
				mcqanswerCreation();
				break;
			case "2":
				mcqanswerUpdate();
				break;
			case "3":
				mcqanswerDelete();
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
	 * Create mcqanswer in the database if any error occurs it will through a
	 * {@link CreateFailedException} usage example:McqAnswer mcqanswer=new ....()
	 * mcqanswerJDBCDAO mcqanswerJDAO=new.....()
	 * try{mcqanswerJDAO.create(mcqanswer)}catch(CreateFailedException e){e.printStackTrace();}
	 *
	 */

	private static void mcqanswerCreation() {

		System.out.println("Welcome to McqAnswer Manager...");
		McqAnswer mcq = new McqAnswer("algo");
		mcq.setTitle("object is  an abstract data type created by a developer");
		mcq.setMcqchoice_id(11);
		
		McqAnswerJDBCDAO mcqanswerJDAO = new McqAnswerJDBCDAO();
		try {
			mcqanswerJDAO.create(mcq);
			System.out.println("mcqanswer  created successfully!");
		} catch (CreateFailedException e) {

			e.printStackTrace();
		}

	}
	/**
	 * Update mcqanswer in the database if any error occurs it will handle it using try{}catch
	 * {@link Exception} usage example:McqAnswer mcqanswer=new ....()
	 * mcqanswerJDBCDAO mcqanswerJDAO=new.....()
	 * try{mcqanswerJDAO.update(mcqanswer)}catch(Exception e){e.printStackTrace();}
	 *
	 */
	private static void mcqanswerUpdate()
	{
		System.out.println("Welcome to McqAnswer Manager...");
		
		McqAnswer mcq = new McqAnswer();
		mcq.setTitle("the new idea is");
		mcq.setMcqchoice_id(43);
		mcq.setId(129);
		
		McqAnswerJDBCDAO mcqanswerJDAO = new McqAnswerJDBCDAO();
		try {
			mcqanswerJDAO.update(mcq);
			System.out.println("mcqanswer  upated successfully!");
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	/**
	 * Delete mcqanswer in the database if any error occurs it will handle it using try{}catch
	 * {@link Exception} usage example:McqAnswer mcqanswer=new ....()
	 * mcqanswerJDBCDAO mcqanswerJDAO=new.....()
	 * try{mcqanswerJDAO.delete(mcqanswer)}catch(Exception e){e.printStackTrace();}
	 *
	 */
   private static void mcqanswerDelete()
   {
	   
	   McqAnswer mcqAnswer= new McqAnswer();
	   mcqAnswer.setId(65);
		
		McqAnswerJDBCDAO mcqAnswerJDAO = new McqAnswerJDBCDAO();
		
		try {
			mcqAnswerJDAO.delete(mcqAnswer);
			System.out.println("mcqanswer  DELETED successfully!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
   }
   /**
    * This method dispaly a menu in order to choose one of the CRUD process on the mcqanswer table
    * @param scanner
    * @return
    */
	private static String displayMenu(Scanner scanner) {
		String mcq;
		System.out.println("-- Menu --");
		System.out.println("1. insert answer");
		System.out.println("2. update answer");
		System.out.println("3. update answer");

		System.out.println("q. Quit the application");
		System.out.println("What is your choice ? (1|2|3|q) :");
		mcq = scanner.nextLine();
		return mcq;
	}
/**
 * This method will check the user name and password if they are right then it will let him login otherwise it wont  let him login
 * @param scanner
 * @return boolean
 */
	private static boolean authenticate(Scanner scanner) {
		System.out.println("Please enter your login : ");
		String login = scanner.nextLine();
		System.out.println("Please enter your password : ");
		String password = scanner.nextLine();

		return LOGIN.equals(login) && PWD.equals(password);
	}
/**
 * this part is a given-when_then way to test if the create method is working or not
 * usage example:
 *-given-
 * McqAnswerJDBCDAO dao = new ......();
 * McqAnswer mcqanswer = ......();
 *-when-
 * try{
 * dao.create(mcqanswer);
 * }
 * catch(CreateFailedException e1){e1.printStackTrace();
 * -then-
 * try{
 * List<McqAnswer> searchResults=dao........
 * if(searchResults.size() < 1){
 * 
 * }else{}
 * }
 * catch(SearchFailedException e){}
 * 
 */

	// given
	McqAnswerJDBCDAO dao = new McqAnswerJDBCDAO();
	McqAnswer mcqanswer = new McqAnswer(1, "choise1", 2);
	{
		// when
		try {
			dao.create(mcqanswer);
		} catch (CreateFailedException e1) {
			System.out.println("this Quiz was not created :" + e1.getFaultInstance());
			e1.printStackTrace();
		}

		// then ?
		try {
			List<McqAnswer> searchResults = dao.search(new McqAnswer(1, "choise1", 2));
			System.out.println(searchResults);
			if (searchResults.size() < 1) {
				System.out.println("error, got no result");
			} else {
				System.out.println("success!");
			}
		} catch (SearchFailedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	//////////////update
	/**
	 * this part is a given-when_then way to test if the update  is working or not
	 * usage example:
	 *-given-
	 * McqAnswerJDBCDAO dao = new ......();
	 * McqAnswer mcqanswer = ......();
	 *-when-
	 * try{
	 * dao.update(mcqanswer);
	 * }
	 * catch(Exception e1){e1.printStackTrace();
	 * -then-
	 * try{
	 * List<McqAnswer> searchResults=dao........
	 * if(searchResults.size() < 1){
	 * 
	 * }else{}
	 * }
	 * catch(SearchFailedException e){}
	 * 
	 */
	
	// given
		McqAnswerJDBCDAO dao1 = new McqAnswerJDBCDAO();
		McqAnswer mcqanswer1 = new McqAnswer(1, "advanced algo", 2);
		{
			// when
			try {
				dao.update(mcqanswer);
			} catch (Exception e1) {
				System.out.println("this Quiz was not updated :" + ((DataAccessException) e1).getFaultInstance());
				e1.printStackTrace();
			}

			// then ?
			try {
				List<McqAnswer> searchResults = dao.search(new McqAnswer(1, "choise1", 2));
				System.out.println(searchResults);
				if (searchResults.size() < 1) {
					System.out.println("error, got no result");
				} else {
					System.out.println("success!");
				}
			} catch (SearchFailedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
}
