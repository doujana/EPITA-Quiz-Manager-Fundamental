package fr.epita.quiz.manager.doujana.tests;

import java.util.Scanner;

import fr.epita.quiz.manager.doujana.datamodel.Student;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.services.data.StudentJDBCDAO;


public class TestStudentFileDAO {
	private static final String LOGIN = "ADM";
	private static final String PWD = "ADM";

	public static void main(String[] args) {
//		//given
//		StudentJDBCDAO dao = new StudentJDBCDAO();
//		Student student = new Student("eyad");
//
//		//when
//		try {
//			dao.create(student);
//		} catch (CreateFailedException e) {
//			System.out.println("this student was not created :" + e.getFaultInstance());
//			e.printStackTrace();
//		}
//		
//		//then ?
//		try {
//			List<Student> searchResults = dao.search(new Student("eyad"));
//			System.out.println(searchResults);
//			if (searchResults.size() < 1) {
//				System.out.println("error, got no result");
//			}else {
//				System.out.println("success!");
//			}
//		} catch (SearchFailedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}
		
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
				studentCreation();
				break;
			case "3":
				StudentDelete();
				break;
			case "2":
				Student student = new Student();
				int status = studentUpdateRecord(student);
				if(status > 0) {
					System.out.println("student record UPDATED successfully!");
				} else {
					System.out.println("student record was NOT UPDATED");
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
	
	private static void studentCreation() {

		System.out.println("Welcome to student Manager...");
		Student student = new Student();
		student.setName("eyad");

		StudentJDBCDAO studentDAO = new StudentJDBCDAO();
		try {
			
			int status = 0;
			status = studentDAO.createRecord(student);
			
			// check for success or failure of operation
			if (status > 0) {
				// if create is true or successful
				System.out.println("student record created successfully!");
			} else {
				// if create is false or unsuccessful
				System.out.println("student record failed to be created");
			}
			
		} catch (CreateFailedException e) {

			e.printStackTrace();
		}
	}

    /**
     * 	
     * @param questionType
     * @return
     */
	private static int studentUpdateRecord(Student student) {

		int updateStatus = 0;

		student.setId(65);
		student.setName("eyad");

		StudentJDBCDAO studentJDAO = new StudentJDBCDAO();

		updateStatus= studentJDAO.updateRecord(student);
		
		return updateStatus;
	}

	/**
	 * 
	 */
	private static void StudentDelete() {

		Student student = new Student();
		student.setId(33);

		StudentJDBCDAO studentJDAO = new StudentJDBCDAO();

		try {
			studentJDAO.delete(student);
			System.out.println("student  record DELETED successfully!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String displayMenu(Scanner scanner) {
		String mcq;
		System.out.println("-- Menu --");
		System.out.println("1. Create student Type");
		System.out.println("2. Update student Type");
		System.out.println("3. Delete student Type");

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


