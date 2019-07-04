package fr.epita.quiz.manager.doujana.tests;

import java.util.Scanner;

import fr.epita.quiz.manager.doujana.datamodel.Teacher;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;

import fr.epita.quiz.manager.doujana.services.data.TeacherJDBCDAO;

public class TestTeacherDAO {

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
		String TeacherMade = "";
		while (!TeacherMade.equals("q")) {

			TeacherMade = displayMenu(scanner);

			switch (TeacherMade) {
			case "1":
				teacherCreation();
				break;
			case "3":
				TeacherDelete();
				break;
			case "2":
				Teacher teacher = new Teacher();
				int status = teacherUpdateRecord(teacher);
				if (status > 0) {
					System.out.println("teacher record UPDATED successfully!");
				} else {
					System.out.println("teacher record was NOT UPDATED");
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

	private static void teacherCreation() {

		System.out.println("Welcome to student Manager...");
		Teacher teacher = new Teacher();
		teacher.setFirst_name("teacher");
		teacher.setSecond_name("tee-last");

		TeacherJDBCDAO teacherDAO = new TeacherJDBCDAO();
		try {

			int status = 0;
			status = teacherDAO.createRecord(teacher);

			// check for success or failure of operation
			if (status > 0) {
				// if create is true or successful
				System.out.println("teacher record created successfully!");
			} else {
				// if create is false or unsuccessful
				System.out.println("teacher record failed to be created");
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
	private static int teacherUpdateRecord(Teacher teacher) {

		int updateStatus = 0;

		teacher.setId(1);
		teacher.setFirst_name("Doujana");

		teacher.setSecond_name("Test Update");
		TeacherJDBCDAO teacherJDAO = new TeacherJDBCDAO();

		updateStatus = teacherJDAO.updateRecord(teacher);

		return updateStatus;
	}

	/**
	 * 
	 */
	private static void TeacherDelete() {

		Teacher teacher = new Teacher();
		teacher.setId(1);

		TeacherJDBCDAO teacherJDAO = new TeacherJDBCDAO();

		try {
			teacherJDAO.delete(teacher);
			System.out.println("teacher  record DELETED successfully!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String displayMenu(Scanner scanner) {

		String mcq;
		System.out.println("-- Menu --");
		System.out.println("1. Create teacher Type");
		System.out.println("2. Update teacher Type");
		System.out.println("3. Delete teacher Type");

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
