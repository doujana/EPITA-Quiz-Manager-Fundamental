package fr.epita.quiz.manager.doujana.tests;

import java.util.Scanner;

import fr.epita.quiz.manager.doujana.datamodel.QuestionType;
import fr.epita.quiz.manager.doujana.datamodel.Quiz;

import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.services.data.QuestionTypeJDBCDAO;
import fr.epita.quiz.manager.doujana.services.data.QuizJDBCDAO;


public class TestQuizFileDAO {
	private static final String LOGIN = "ADM";
	private static final String PWD = "ADM";

	public static void main(String[] args) {

		// testing adding the quiz
		Scanner scanner = new Scanner(System.in);
		boolean authenticated = authenticate(scanner);
		if (!authenticated) {
			scanner.close();
			return;
		}

		System.out.println("You're authenticated");
		String Quiz = "";
		while (!Quiz.equals("q")) {

			Quiz = displayMenu(scanner);

			switch (Quiz) {
			case "1":
				QuizCreation();
				break;
			case "2":
				QuizDelete();
				break;
			case "3":
				Quiz quiz = new Quiz();
				int status = QuizUpdateRecord(quiz);
				if(status > 0) {
					System.out.println("Quiz record UPDATED successfully!");
				} else {
					System.out.println("Quiz record was NOT UPDATED");
				}
				break;

			case "q":
				break;

			default:
				System.out.println("Option not recognized, please enter an other option");
				break;
			}
		}

		scanner.close();

	}


	/**
	 * this method is for creating a quiz to the database if any error occurs it will be handled using a try{}catch{}
	 * example usage:Quiz quiz = new Quiz();
	 * QuizJDBCDAO quizDAO = new QuizJDBCDAO();
	 * try{quizDAO.createRecord()}catch(CreateFailedException e){}
	 */
	private static void QuizCreation() {

		System.out.println("Welcome to quiz Manager...");
		Quiz quiz = new Quiz();
		quiz.setTitle("advanced algo");

	QuizJDBCDAO quizDAO = new QuizJDBCDAO();
		try {
			
			int status = 0;
			status = quizDAO.createRecord(quiz);
			
			// check for success or failure of operation
			if (status > 0) {
				// if create is true or successful
				System.out.println("Quiz record created successfully!");
			} else {
				// if create is false or unsuccessful
				System.out.println("Quiz record failed to be created");
			}
			
		} catch (CreateFailedException e) {

			e.printStackTrace();
		}
	}
	/**
	 * this method is for updating a quiz to the database if any error occurs it will be handled using a try{}catch{}
	 * example usage:Quiz quiz = new Quiz();
	 * QuizJDBCDAO quizDAO = new QuizJDBCDAO();
	 * try{quizDAO.QuizUpdateRecord()}catch(Exception e){}
	 */
	private static int QuizUpdateRecord(Quiz quiz) {

		int updateStatus = 0;

		quiz.setId(4);
		quiz.setTitle("new course updated!");

	QuizJDBCDAO quizJDAO = new QuizJDBCDAO();

		updateStatus= quizJDAO.updateRecord(quiz);
		
		return updateStatus;
	}
	/**
	 * this method is for deleting a quiz to the database if any error occurs it will be handled using a try{}catch{}
	 * example usage:Quiz quiz = new Quiz();
	 * QuizJDBCDAO quizDAO = new QuizJDBCDAO();
	 * try{quizDAO.delete()}catch(Exception e){}
	 */
	private static void QuizDelete() {

		Quiz quiz = new Quiz();
		quiz.setId(5);

		QuizJDBCDAO quizJDAO = new QuizJDBCDAO();

		try {
			quizJDAO.delete(quiz);
			System.out.println("Question Type record DELETED successfully!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
/**
 * this method for displaying the menu which contain what we need to do 
 * @param scanner
 * @return
 */
	private static String displayMenu(Scanner scanner) {
		String student;
		System.out.println("-- Menu --");
		System.out.println("1. Create Quiz");
		System.out.println("2. Delete Quiz");
		System.out.println("3. Update Quiz");
		System.out.println("2. Create Question");
		System.out.println("q. Quit the application");
		System.out.println("What is your choice ? (1|2|3|q) :");
		student = scanner.nextLine();
		return student;
	}

	private static boolean authenticate(Scanner scanner) {
		System.out.println("Please enter your login : ");
		String login = scanner.nextLine();
		System.out.println("Please enter your password : ");
		String password = scanner.nextLine();

		return LOGIN.equals(login) && PWD.equals(password);
	}

}
