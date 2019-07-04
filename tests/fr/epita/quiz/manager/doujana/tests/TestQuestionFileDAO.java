package fr.epita.quiz.manager.doujana.tests;

import java.util.List;
import java.util.Scanner;

import fr.epita.quiz.manager.doujana.datamodel.Difficulty;
import fr.epita.quiz.manager.doujana.datamodel.McqAnswer;
import fr.epita.quiz.manager.doujana.datamodel.McqChoice;
import fr.epita.quiz.manager.doujana.datamodel.Question;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;
import fr.epita.quiz.manager.doujana.services.data.McqChoiceJDBCDAO;
import fr.epita.quiz.manager.doujana.services.data.QuestionJDBCDAO;

public class TestQuestionFileDAO {

	private static final String LOGIN = "ADM";
	private static final String PWD = "ADM";

	public static void main(String[] args) {
		/**
		 * This is while loop for choosing more than operation on the question
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
		String questionMade = "";
		while (!questionMade.equals("q")) {

			questionMade = displayMenu(scanner);

			switch (questionMade) {
			case "1":
				QuestionCreation();
				break;
			case "2":
				Question question = new Question();
				QuestionUpdateRecord(question);
				break;
			case "3":
				QuestionDelete();
				break;
			case "4":
				QuestionSearch();
				break;
			case "5":
				QuestionSearchByTopicID();
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
	 * search for a question in the database if any error occurs it will through a
	 * {@link SearchFailedException} usage Question question=new ....()
	 * QuestionJDBCDAO questionJDAO=new.....()
	 * try{questionJDAO.QuestionSearch(question)}catch(SearchFailedException e){e.printStackTrace();}
	 *
	 */
	
	private static void QuestionSearch()
	{
		
		System.out.println("Welcome to question Manager...");
		Question question = new Question();
		question.setTitle("i am here");
		question.setQuestion_type_id(3);
		question.setTopic_id(1);
		

		QuestionJDBCDAO qdao = new QuestionJDBCDAO();
		try {
			
			List<Question> questionList = qdao.search(question);
			if(questionList.size() > 0 ) {
				
			for(int i = 0; i < questionList.size(); i++) {
				
				System.out.println("ID :: " + questionList.get(i).getId());
				System.out.println("TITLE :: " + questionList.get(i).getTitle());
				//System.out.println("QUESTION TYPE :: " + questionList.get(i).getQuestionType().getTitle());
				System.out.println("QUESTION TYPE ID :: " + questionList.get(i).getTopic_id());
				System.out.println("Question ID :: " + questionList.get(i).getTopic_id());
				//System.out.println("Question ID :: " + questionList.get(i).getTopic().getName());
			}
			} else {
				System.out.println("No Record(s) to dispaly details");
			}
			
		} catch (SearchFailedException e) {
			
			e.printStackTrace();
		}
		
	}
	/**
	 * search for a question in the database depending on the topic if any error occurs it will through a
	 * {@link SearchFailedException} usage Question question=new ....()
	 * QuestionJDBCDAO questionJDAO=new.....()
	 * try{questionJDAO.QuestionSearchByTopicID(question)}catch(SearchFailedException e){e.printStackTrace();}
	 *
	 */
	
	private static void QuestionSearchByTopicID()
	{
		
		System.out.println("Welcome to question Manager...");
		Question question = new Question();
		question.setTopic_id(1);
		

		QuestionJDBCDAO qdao = new QuestionJDBCDAO();
		try {
			
			List<Question> questionList = qdao.search(question);
			if(questionList.size() > 0 ) {
				
			for(int i = 0; i < questionList.size(); i++) {
				
				System.out.println("ID :: " + questionList.get(i).getId());
				System.out.println("TITLE :: " + questionList.get(i).getTitle());
				//System.out.println("QUESTION TYPE :: " + questionList.get(i).getQuestionType().getTitle());
				System.out.println("QUESTION TYPE ID :: " + questionList.get(i).getTopic_id());
				System.out.println("Question ID :: " + questionList.get(i).getTopic_id());
				//System.out.println("Question ID :: " + questionList.get(i).getTopic().getName());
			}
			} else {
				System.out.println("No Record(s) to dispaly details");
			}
			
		} catch (SearchFailedException e) {
			
			e.printStackTrace();
		}
		
	}
	/**
	 * Create question in the database if any error occurs it will through a
	 * {@link CreateFailedException} usage example:Question question=new ....()
	 * QuestionJDBCDAO questionJDAO=new.....()
	 * try{questionJDAO.create(question)}catch(CreateFailedException e){e.printStackTrace();}
	 *
	 */
	private static void QuestionCreation() {

		System.out.println("Welcome to question Manager...");
		
		Question question = new Question();
		question.setTitle("i am here");
		question.setQuestion_type_id(3);
		question.setTopic_id(1);
		question.setDifficulty(1);

		QuestionJDBCDAO qdao = new QuestionJDBCDAO();
		try {
			qdao.create(question);
			System.out.println("question record created successfully!");
		} catch (CreateFailedException e) {

			e.printStackTrace();
		}
	}
	/**
	 * Update question in the database if any error occurs it will through a
	 * {@link Exception} usage Question question=new ....()
	 * QuestionJDBCDAO questionJDAO=new.....()
	 * try{questionJDAO.Delete(question)}catch(Exception e){e.printStackTrace();}
	 *
	 */

	private static int QuestionUpdateRecord(Question question) {

		int updateStatus = 0;

		question.setId(72);
		question.setTitle("update please");
		question.setQuestion_type_id(1);
		question.setTopic_id(1);

		QuestionJDBCDAO questionJDAO = new QuestionJDBCDAO();

		try {
			
			updateStatus = questionJDAO.updateRecord(question);
			if(updateStatus > 0) {
				System.out.println("Question record UPDATED successfully!");
			} else {
				System.out.println("Question record NOT UPDATED");
			}		
			
		} catch (CreateFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updateStatus;
	}

	/**
	 * Delete question in the database if any error occurs it will through a
	 * {@link Exception} usage example:Question question=new ....()
	 * QuestionJDBCDAO questionJDAO=new.....()
	 * try{questionJDAO.Delete(question)}catch(Exception e){e.printStackTrace();}
	 *
	 */
	private static void QuestionDelete() {

		Question question = new Question();
		question.setId(74);

		QuestionJDBCDAO questionJDAO = new QuestionJDBCDAO();

		try {
			
			int status = questionJDAO.delete(question);
			
			if(status > 0) {
				System.out.println("Question record DELETED successfully!");
			} else {
				System.out.println("Question record NOT DELETED!");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	    * This method dispaly a menu in order to choose one of the CRUD process on the question table
	    * @param scanner
	    * @return
	    */

	private static String displayMenu(Scanner scanner) {
		String mcq;
		System.out.println("-- Menu --");
		System.out.println("1. Create Question");
		System.out.println("2. Update Question");
		System.out.println("3. Delete Question");
		System.out.println("4. search Question");
		System.out.println("5. Search Question by TPIC Id");
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

	// given
	QuestionJDBCDAO qdao = new QuestionJDBCDAO();
	Question question = new Question(1, "drm", 2);
	{
		// when
		try {
			qdao.create(question);
		} catch (CreateFailedException e1) {
			System.out.println("this question was not created :" + e1.getFaultInstance());
			e1.printStackTrace();
		}

		// then ?
		try {
			List<Question> searchResults = qdao.search(new Question(1, "what is a class", 2));
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
