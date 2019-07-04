package fr.epita.quiz.manager.doujana.tests;

import java.util.Scanner;

import fr.epita.quiz.manager.doujana.datamodel.Topic;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.services.data.TopicJDBCDAO;

public class TestTopicDAO {
	private static final String LOGIN = "ADM";
	private static final String PWD = "ADM";

	public  static void main(String[]args)
	{
		
		Scanner scanner = new Scanner(System.in);
		boolean authenticated = authenticate(scanner);
		if (!authenticated) {
			scanner.close();
			return;
		}

		System.out.println("You're authenticated");
		String topic = "";
		while (!topic.equals("q")) {

			topic = displayMenu(scanner);

			switch (topic) {
			case "1":
				TopicCreation();
				break;
			case "2":
				TopicDelete();
				break;
			case "3":
				Topic topic1 = new Topic();
				int status = TopicUpdateRecord(topic1);
				if(status > 0) {
					System.out.println("topic record UPDATED successfully!");
				} else {
					System.out.println("topic record was NOT UPDATED");
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

//	private static void QuizCreation() {
//
//		System.out.println("Welcome to quiz Manager...");
//
//		Quiz quiz = new Quiz("drm course");
//
//		QuizJDBCDAO quizJDAO = new QuizJDBCDAO();
//		try {
//			quizJDAO.create(quiz);
//			System.out.println("quiz record created successfully!");
//		} catch (CreateFailedException e) {
//
//			e.printStackTrace();
//		}
//
//	}
	private static void TopicCreation() {

		System.out.println("Welcome to topic Manager...");
		Topic topic = new Topic();
		topic.setName("unix");

	TopicJDBCDAO topicDAO = new TopicJDBCDAO();
		try {
			
			int status = 0;
			status = topicDAO.createRecord(topic);
			
			// check for success or failure of operation
			if (status > 0) {
				// if create is true or successful
				System.out.println("topic record created successfully!");
			} else {
				// if create is false or unsuccessful
				System.out.println("topic record failed to be created");
			}
			
		} catch (CreateFailedException e) {

			e.printStackTrace();
		}
	}
/**
 * 
 * @param scanner
 * @return
 */
	private static int TopicUpdateRecord(Topic topic) {

		int updateStatus = 0;

		topic.setId(2);
		topic.setName("networke");

	TopicJDBCDAO topicJDAO = new TopicJDBCDAO();

		updateStatus= topicJDAO.updateRecord(topic);
		
		return updateStatus;
	}
/**
 * 
 * @param scanner
 * @return
 */
	private static void TopicDelete() {

		Topic topic = new Topic();
		topic.setId(3);

		TopicJDBCDAO topicJDAO = new TopicJDBCDAO();

		try {
			topicJDAO.delete(topic);
			System.out.println("topic record DELETED successfully!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String displayMenu(Scanner scanner) {
		String student;
		System.out.println("-- Menu --");
		System.out.println("1. Create topic");
		System.out.println("2. Delete topic");
		System.out.println("3. Update topic");
		System.out.println("2. Create topic");
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

