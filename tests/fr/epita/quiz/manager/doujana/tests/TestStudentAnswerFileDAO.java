package fr.epita.quiz.manager.doujana.tests;

import java.util.Scanner;

import fr.epita.quiz.manager.doujana.datamodel.StudentAnswer;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.services.data.StudentAnswerJDBCDAO;

public class TestStudentAnswerFileDAO {

	private static final String LOGIN = "ADM";
	private static final String PWD = "ADM";
	private static final String STUDENT_MENU = "Student Answer";
	
	public static void main(String[] args) {
		
//		// given
//		StudentAnswerJDBCDAO studentAnswerJDBCDAO = new StudentAnswerJDBCDAO();
//		StudentAnswer studentAnswer = new StudentAnswer();
//		studentAnswer.setStudentId(2);
//		studentAnswer.setValidAnswerId(11);
//		studentAnswer.setMcqAnswerId(98);
//		
//		// when
//		try {
//			int status = studentAnswerJDBCDAO.create(studentAnswer);
//			if ( status > 0) {
//				System.out.println("The Student Answer was created successfully!" );
//			} else {
//				System.out.println("The Student Answer was not created due to error");
//			}
//			
//		} catch (CreateFailedException e) {
//			
//			e.printStackTrace();
//		}
//
//		// then ?
//		try {
//			List<StudentAnswer> searchResults = studentAnswerJDBCDAO.search(new StudentAnswer(33));
//			//System.out.println(searchResults);
//			
//			if (searchResults.size() < 1) {
//				System.out.println("Error!, no search result found");
//			} else {
//				System.out.println("Success! The following result(s) were found");
//				System.out.println(" ============= ------------------ ============== ");
//				for(int i = 0; i < searchResults.size(); i++) {
//					System.out.println("ID :: " + searchResults.get(i).getId());
//					System.out.println("Student ID :: " + searchResults.get(i).getStudentId());
//					System.out.println("MCQ Answer ID :: " + searchResults.get(i).getMcqAnswerId());
//					System.out.println("Valid ID :: " + searchResults.get(i).getValidAnswerId());
//				}
//				
//			}
//		} catch (SearchFailedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		Scanner scanner = new Scanner(System.in);
		boolean authenticated = authenticate(scanner);
		if (!authenticated) {
			scanner.close();
			return;
		}

		System.out.println("You're authenticated");
		String optionChosen = "";
		while (!optionChosen.equals("q")) {

			optionChosen = displayMenu(scanner);

			switch (optionChosen) {
			case "1":
				StudentAnswerCreation();
				break;
			case "3":
				StudentAnswerDelete();
				break;
			case "2":
				StudentAnswer studentAnswer = new StudentAnswer();
				int status = StudentAnswerUpdate(studentAnswer);
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
	
	private static void StudentAnswerCreation() {

		StudentAnswer studentAnswer = new StudentAnswer();
		studentAnswer.getStudentId();
		studentAnswer.getValidAnswerId();
		studentAnswer.getMcqAnswerId();
		
		StudentAnswerJDBCDAO studentAnswerDAO = new StudentAnswerJDBCDAO();
		try {
			
			int status = 0;
			status = studentAnswerDAO.create(studentAnswer);
			
			// check for success or failure of operation
			if (status > 0) {
				// if create is true or successful
				System.out.println("Student Answer record created successfully!");
			} else {
				// if create is false or unsuccessful
				System.out.println("Student Answer record failed to be created");
			}
			
		} catch (CreateFailedException e) {

			e.printStackTrace();
		}
	}
	
	private static int StudentAnswerUpdate(StudentAnswer studentAnswer) {

		int updateStatus = 0;

		studentAnswer.setId(4);
		studentAnswer.setStudentId(2);
		studentAnswer.setValidAnswerId(98);
		studentAnswer.setMcqAnswerId(33);

		StudentAnswerJDBCDAO studentAnswerJDAO = new StudentAnswerJDBCDAO();

		updateStatus= studentAnswerJDAO.update(studentAnswer);
		
		return updateStatus;
	}

	
	private static void StudentAnswerDelete() {

		StudentAnswer studentAnswer = new StudentAnswer();
		studentAnswer.setId(5);

		StudentAnswerJDBCDAO studentAnswerJDBCDAO = new StudentAnswerJDBCDAO();

		try {
			
			studentAnswerJDBCDAO.delete(studentAnswer);
			System.out.println("Question Type record DELETED successfully!");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	private static String displayMenu(Scanner scanner) {
		String mcq; 
		System.out.println("-- Menu --");
		System.out.println("1. Create " + STUDENT_MENU );
		System.out.println("2. Update " + STUDENT_MENU );
		System.out.println("3. Delete " + STUDENT_MENU );

		System.out.println("q. Quit the application");
		System.out.println("What is your choice ? (1|2|3|q) :");
		
		mcq = scanner.nextLine();
		
		return mcq;
	}
	
	private static boolean authenticate(Scanner scanner) {
		
		System.out.println("Please enter your login : ");
		
		String login = scanner.nextLine();
		System.out.println("Please enter your password : ");
		
		String password = scanner.nextLine();

		return LOGIN.equals(login) && PWD.equals(password);
	}
}
