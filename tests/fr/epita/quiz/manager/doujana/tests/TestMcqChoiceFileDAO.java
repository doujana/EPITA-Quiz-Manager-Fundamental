package fr.epita.quiz.manager.doujana.tests;

import java.util.List;
import java.util.Scanner;

import fr.epita.quiz.manager.doujana.datamodel.McqChoice;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;
import fr.epita.quiz.manager.doujana.services.data.McqChoiceJDBCDAO;

public class TestMcqChoiceFileDAO {

	private static final String LOGIN = "ADM";
	private static final String PWD = "ADM";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// given
		McqChoiceJDBCDAO mcqChoiceDAO = new McqChoiceJDBCDAO();
		McqChoice mcqChoice = new McqChoice();
		mcqChoice.setTitle("Alha Choice");
		mcqChoice.setQuestionId(3);
		
		// when
		try {
			
			mcqChoiceDAO.create(mcqChoice);
			System.out.println("The MCQ Choice," + mcqChoice.getTitle() + " was created successfully" );
			
		} catch (CreateFailedException e) {
			
			System.out.println("The MCQ Choice " + mcqChoice.getTitle() + " was not created :" + e.getFaultInstance());
			e.printStackTrace();
		}

		// then ?
		try {
			
			List<McqChoice> searchResults = mcqChoiceDAO.search(new McqChoice("Alha Choice"));
			
			System.out.println(searchResults);
			
			if (searchResults.size() < 1) {
				System.out.println("Error!, got no result for MCQ Choice");
			} else {
				
				for(int i=0; i<searchResults.size(); i++) {
					System.out.println("ID :: " + searchResults.get(i).getId() );
					System.out.println("Title :: " + searchResults.get(i).getTitle());
					System.out.println("Question ID :: " + searchResults.get(i).getQuestionId());
				}
				System.out.println("Success! MCQ Choice record is found");
			}
			
		} catch (SearchFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		// testing adding the quiz
//		Scanner scanner = new Scanner(System.in);
//		boolean authenticated = authenticate(scanner);
//		if (!authenticated) {
//			scanner.close();
//			return;
//		}
//
//		System.out.println("You're authenticated");
//		String mcqChoiceMade = "";
//		while (!mcqChoiceMade.equals("q")) {
//
//			mcqChoiceMade = displayMenu(scanner);
//
//			switch (mcqChoiceMade) {
//			case "1":
//				McqChoiceCreation();
//				break;
//			case "2":
//				McqChoice mcqChoice = new McqChoice();
//				int status = McqChoiceUpdateRecord(mcqChoice);
//				if ( status >= 1) {
//					System.out.println("The MCQ Choice record, " + mcqChoice.getTitle() + " created successfully" );
//				}else {
//					System.out.println("The MCQ Choice record, " + mcqChoice.getTitle() + " was not created due to unkown error" );
//				}
//				break;
//			case "3":
//				McqChoiceDelete();
//				System.out.println("The MCQ Choice record Deleted successfully" );
//				break;
//			case "q":
//				System.out.println("Good bye!");
//				break;
//
//			default:
//				System.out.println("Option not recognized, please enter an other option");
//				break;
//			}
//		}
//
//		scanner.close();
	}
	
	 /**
	 * Create mcqchoice in the database if any error occurs it will through a
	 * {@link CreateFailedException} usage example:McqChoice mcqchoice=new ....()
	 * mcqchoiceJDBCDAO mcqchoiceJDAO=new.....()
	 * try{mcqchoiceJDAO.create(mcqchoice)}catch(CreateFailedException e){e.printStackTrace();}
	 *
	 */
	 
	private static void McqChoiceCreation() {

		System.out.println("Welcome to MCQ Choice Manager...");

		McqChoice mcqChoice = new McqChoice();
		mcqChoice.setTitle("Another Choice to insert");
		mcqChoice.setQuestionId(5);

		McqChoiceJDBCDAO mcqChoiceJDAO = new McqChoiceJDBCDAO();
		
		try {
			
			mcqChoiceJDAO.create(mcqChoice);
			System.out.println("The MCQ Choice record, " + mcqChoice.getTitle() + " created successfully" );
			
		} catch (CreateFailedException e) {

			e.printStackTrace();
		}

	}
	/**
	 * Updates mcqanswer in the database if any error occurs it will through a
	 * {@link Exception} usage example:McqAnswer mcqanswer=new ....()
	 * mcqanswerJDBCDAO mcqanswerJDAO=new.....()
	 * try{mcqanswerJDAO.update(mcqanswer)}catch(Exception e){e.printStackTrace();}
	 *
	 */
	
	private static int McqChoiceUpdateRecord(McqChoice mcqChoice) {

		int updateStatus = 0;
		
//		McqChoice mcqChoice = new McqChoice();
		mcqChoice.setId(37);
		mcqChoice.setTitle("Updated from Alha Choice to Update is working!");
		mcqChoice.setQuestionId(5);

		McqChoiceJDBCDAO mcqChoiceJDAO = new McqChoiceJDBCDAO();
		
		try {
			mcqChoiceJDAO.updateRecord(mcqChoice);
		} catch (CreateFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updateStatus;
	}
	/**
	 * Delete mcqanswer in the database if any error occurs it will through a
	 * {@link Exception} usage example:McqAnswer mcqanswer=new ....()
	 * mcqanswerJDBCDAO mcqanswerJDAO=new.....()
	 * try{mcqanswerJDAO.Delete(mcqanswer)}catch(Exception e){e.printStackTrace();}
	 *
	 */
	private static void McqChoiceDelete() {
		
		McqChoice mcqChoice = new McqChoice();
		mcqChoice.setId(33);
		
		McqChoiceJDBCDAO mcqChoiceJDAO = new McqChoiceJDBCDAO();
		
		try {
			mcqChoiceJDAO.delete(mcqChoice);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	    * This method dispaly a menu in order to choose one of the CRUD process on the mcqchoice table
	    * @param scanner
	    * @return
	    */
	
	private static String displayMenu(Scanner scanner) {
		
		String mcqChoice;
		
		System.out.println("-- Menu --");
		System.out.println("1. Create MCQ Choice");
		System.out.println("2. Update MCQ Choice");
		System.out.println("3. Delete MCQ Choice");
		System.out.println("q. Quit the Application");
		System.out.println("What is your choice ? (1|2|q) :");
		
		mcqChoice = scanner.nextLine();
		
		return mcqChoice;
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

}
