package fr.epita.quiz.manager.doujana.launcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.epita.quiz.manager.doujana.datamodel.DifficultyLevel;
import fr.epita.quiz.manager.doujana.datamodel.McqAnswer;
import fr.epita.quiz.manager.doujana.datamodel.McqChoice;
import fr.epita.quiz.manager.doujana.datamodel.Question;
import fr.epita.quiz.manager.doujana.datamodel.QuestionType;
import fr.epita.quiz.manager.doujana.datamodel.Quiz;
import fr.epita.quiz.manager.doujana.datamodel.QuizQuestion;
import fr.epita.quiz.manager.doujana.datamodel.Student;
import fr.epita.quiz.manager.doujana.datamodel.StudentAnswer;
import fr.epita.quiz.manager.doujana.datamodel.Teacher;
import fr.epita.quiz.manager.doujana.datamodel.Topic;
import fr.epita.quiz.manager.doujana.datamodel.ValidAnswer;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;
import fr.epita.quiz.manager.doujana.logging.QuizManagerLogger;
import fr.epita.quiz.manager.doujana.services.data.DifficultyLevelJDBCDAO;
import fr.epita.quiz.manager.doujana.services.data.McqAnswerJDBCDAO;
import fr.epita.quiz.manager.doujana.services.data.McqChoiceJDBCDAO;
import fr.epita.quiz.manager.doujana.services.data.QuestionJDBCDAO;
import fr.epita.quiz.manager.doujana.services.data.QuestionTypeJDBCDAO;
import fr.epita.quiz.manager.doujana.services.data.QuizJDBCDAO;
import fr.epita.quiz.manager.doujana.services.data.QuizQuestionJDBCDAO;
import fr.epita.quiz.manager.doujana.services.data.StudentAnswerJDBCDAO;
import fr.epita.quiz.manager.doujana.services.data.StudentJDBCDAO;
import fr.epita.quiz.manager.doujana.services.data.TeacherJDBCDAO;
import fr.epita.quiz.manager.doujana.services.data.TopicJDBCDAO;
import fr.epita.quiz.manager.doujana.services.data.ValidAnswerJDBCDAO;

public class Launcher {

	private static final String LOGIN = "ADM";
	private static final String PWD = "ADM";
	private static final String STUDENT_USERNAME = "student";
	private static final String STUDENT_PASSWORD = "stupass";
	private static final String TEACHER_USERNAME = "teacher";
	private static final String TEACHER_PASSWORD = "teepass";

	private static QuizManagerLogger quizmanagerlogger = new QuizManagerLogger();

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		/*
		 * show the welcome screen or menu to the application perform action based on
		 * selected option
		 */
		welcomeMenuSelections(scanner);

		scanner.close();

	}

	/**
	 * This method is display a welcome menu and in here he can choose log in as a
	 * teacher or as a student or to quit the application
	 * 
	 * @param welcomeScanner
	 * @return
	 */
	private static String welcomeMenu(Scanner welcomeScanner) {

		String chosenMenuItem;

		System.out.println("--=== WELCOM TO DOUJANA QUIZ MANAGER ===--");
		System.out.println("Please select you function by entering...");
		System.out.println("1. If you are a TEACHER");
		System.out.println("2. If you are a STUDENT");
		System.out.println("q. To QUIT the application");
		System.out.println("--- === ------------------------ === ---");

		chosenMenuItem = welcomeScanner.nextLine();

		return chosenMenuItem;
	}

	/**
	 * In this method we have two cases either a student or a teacher using switch
	 * cases switch(selectedItem){case
	 * "1":teacherLogin=validateUserLogin(welcomescanner,false) else{it will be a
	 * student login}}
	 * 
	 * @param welcomeScanner
	 * @param selectedItem
	 */
	private static void welcomeMenuSelections(Scanner welcomeScanner) {

		String loginMsg = "\t\tApplication login successful...\n";
		String selectedItem = welcomeMenu(welcomeScanner);

		while (!selectedItem.equals("q") || !selectedItem.equals("Q")) {

			switch (selectedItem) {
			case "1": // user is a Teacher

				boolean teacher_login = validateLogin(welcomeScanner, false);
				if (teacher_login == true) {

					System.out.println(loginMsg + "\t\tLoading Teachers Menu\n");

					// show student menu
					showFunctionalMenu(welcomeScanner, false);
					break;
				} else {

					// show main menu
				}
				break;

			case "2": // user is a Student

				boolean login_success = validateLogin(welcomeScanner, true);
				if (login_success == true) {

					System.out.println(loginMsg + "\nLoading Students Menu\n");

					// show student menu
					showFunctionalMenu(welcomeScanner, true);
					break;
				} else {

					// show main menu
				}
				break;
			case "q":
			case "Q":
				System.out.println("Okay! You have quit the application. Thanks for using");
				break;
			default:
				System.out.println("Option not recognized. Please enter another option");
				break;
			}
		}

	}

	/**
	 * generic method to validate the user login it will be either a student or a
	 * teacher login depending on the value of the variable is_student
	 * if(is_student==true) { STUDENT_USERNAME.equals(userName) &&
	 * STUDENT_PASSWORD.equals(userPassword); } else{
	 * TEACHER_USERNAME.equals(userName) && TEACHER_PASSWORD.equals(userPassword); }
	 * 
	 * @param loginScanner
	 * @param is_student
	 * @return
	 */

	private static boolean validateLogin(Scanner loginScanner, boolean is_student) {

		System.out.println("Enter your Username provided : ");
		String userName = loginScanner.nextLine();

		System.out.println("Enter your Password provided : ");
		String userPassword = loginScanner.nextLine();

		if (is_student == true) {
			// student is logging into the application
			return STUDENT_USERNAME.equals(userName) && STUDENT_PASSWORD.equals(userPassword);
		} else {
			// teacher is login in
			return TEACHER_USERNAME.equals(userName) && TEACHER_PASSWORD.equals(userPassword);
		}
	}
	/*
	 * Menu for Showing the functionality and capacity of the student about what he
	 * can doso he can view is results and take quiz example usage:{if(is_student)
	 * so the idea is if it is true then he will display the menu for the student's
	 * options else he will display a menu for the teacher which include create
	 * quiz,create questiontype,create questiondifficulty and so on}
	 */

	private static String showFunctionalMenu(Scanner scanner, boolean is_student) {
		String selectedOption = "";

		if (is_student == true) {
			/*
			 * 1. will take a Quiz 2. view results 3. view student info
			 */
			System.out.println("\t====================================");
			System.out.println("\t-- Menu Options Available --");
			System.out.println("\t====================================");
			System.out.println("3. Take Quiz");
			System.out.println("4. View Results");
			System.out.println("r. Return to Main Menu");
			System.out.println("q. Quit the Application");

			selectedOption = scanner.nextLine();

		} else {
			/*
			 * performs CRUD on 1. Student 2. Question 3. Quiz 4. Others (etc)
			 */
			teacherFunctionalOptions(scanner);
		}

		return selectedOption;
	}

	private static String teacherSelection(Scanner scanner) {

		String selectedOption = "";
		System.out.println("\t====================================");
		System.out.println("\t\t-- Menu Options Available --");
		System.out.println("\t====================================");
		System.out.println("5. Create Teacher");
		System.out.println("6. Create Student");
		System.out.println("7. Create Quiz");
		System.out.println("8. Create Question Type");
		System.out.println("9. Create Question Difficulty Level");
		System.out.println("10. Create Question Topic");
		System.out.println("11. Create Question");
		System.out.println("12. Create MCQ Answer");
		System.out.println("13. Create Valid Answer");
		System.out.println("14. create mcq choice");
		System.out.println("15. search by topic id");
		System.out.println("16. search in general");
		System.out.println("17. update quiz");
		System.out.println("18. delete quiz");
		System.out.println("19. Delete question");
		System.out.println("20. update question");
		System.out.println("21. ");
		System.out.println("22. ");
		System.out.println("23. search for a quiz");
		System.out.println("24. View Review");
		System.out.println("r. Return to Main Menu");
		System.out.println("q. Quit the Application");

		selectedOption = scanner.nextLine();

		return selectedOption;
	}

	/**
	 * shows the functions that the teacher can make and we are using the scanner as
	 * a parameter usage example:switch(selectedItem){}
	 * 
	 * @param scanner
	 */
	private static void teacherFunctionalOptions(Scanner scanner) {

		String selectedItem = "";
		selectedItem = teacherSelection(scanner);

		while (!selectedItem.equals("q") || !selectedItem.equals("Q") || !selectedItem.equals("r")) {

			switch (selectedItem) {
			case "5": // create a teacher

				Teacher teacher = new Teacher();

				System.out.println("Enter First Name ");
				String firstName = scanner.nextLine();

				System.out.println("Enter Last Name ");
				String lastName = scanner.nextLine();

				teacher.setFirst_name(firstName);
				teacher.setSecond_name(lastName);

				teacherCreation(teacher);

				break;

			case "6": // create a student

				Student student = new Student();
				System.out.println("Enter the student's Name ");
				student.setName(scanner.nextLine());

				studentCreation(student);
				break;

			case "7": // create a quiz
				Quiz quiz = new Quiz();
				System.out.println("Enter the quiz's Name ");
				quiz.setTitle(scanner.nextLine());
				//
				//
				quizCreation(quiz);
				break;

			case "8": // create a question type

				QuestionType qtype = new QuestionType();
				System.out.println("Enter the Question type : e.g. MCQ or Open ");

				qtype.setTitle(scanner.nextLine());

				questionTypeCreation(qtype);
				break;

			case "9":// create question difficulty level

				DifficultyLevel difficultylevel = new DifficultyLevel();
				System.out.println(
						"Enter the Difficulty level to be assigned a Question\n e.g. EASY, VERY EASY, HARD, or VERY HARD");

				difficultylevel.setDifficulty_level(scanner.nextLine());
				difficultyLevelCreation(difficultylevel);
				break;

			case "10":// create question topic
				Topic topic = new Topic();

				System.out.println("Enter the Topic title");
				topic.setName(scanner.nextLine());

				createTopic(topic);
				break;
			case "11":// create question

				Question question = new Question();
				System.out.println("Enter Question title");
				question.setTitle(scanner.nextLine());
				
				// in here you just enter 0 for very easy, 1 for easy and so on
				System.out.println("Enter Difficulty level\n" + "\t 1 -> Very EasY\n" + "\t 2 -> Easy\n" + "\t 3 -> Hard\n"
						+ "\t 4 -> Very Hard\n" + "\t 5 -> Difficult");
				
				// accept question difficulty level
				String df_level = scanner.nextLine();
				int df = 0;
					switch(df_level) {
						case "1":
							df = 1;
							break;
						case "2":
							df = 2;
							break;
						case "3":
							df = 3;
							break;
						case "4":
							df = 4;
							break;
						default :
							df = 5;
							break;
					}
				question.setDifficulty(df);
				
				// accept question type
				System.out.println("Enter Question type\n" + "\t1 -> Open Question\n" + "\t2 -> MCQ");
				String qType = scanner.nextLine();
				
				// accept question topic
				System.out.println("Enter the Topic associated with Question\n" + "\t1 -> Variables and Constants in Java\n" + "\t2 -> Implmenting Singleton in Java");
				String qTopic = scanner.nextLine();
				int qtopic = 0;
				if (qTopic.equals("1")) {
					qtopic = 33; // to be retrieved from db
				} else {
					qtopic = 34; // to be retrieved from db
				}
				question.setQuestion_type_id(qtopic);
				
				//create a list of array to accept MCQ choices to be defined
				System.out.println("Enter the Total MCQ choice options");
				List<String> mcqChoices = new ArrayList<>();
				int qt = 0;
				if ( qType.equals("1")) {
					qt = 1;
				} else {
					qt = 70;
					// if question type is MCQ, then ask for the number of choices
					// loop to get the choices 
					// define the choice which is the correct answer
					int choiceCount = 0;
					choiceCount = Integer.parseInt(scanner.nextLine());
					if(choiceCount <= 0 ) {
						System.out.println("Enter a numeric value greater than Zero (0)");
					} else {
						for(int i = 1; i <= choiceCount; i++) {
							System.out.println("Enter the Title for choice " + i);
							String enteredChoice = scanner.nextLine();
							mcqChoices.add(enteredChoice);
						}
					}
				}
				question.setQuestion_type_id(qt);
				
				/*System.out.println("Enter the number of choices you want to make:");
				int number = Integer.parseInt(scanner.nextLine());
				for (int i = 0; i < number; i++) {
					System.out.println("Enter the choice" + i);
					mcqchoice.setTitle(scanner.nextLine());
				}*/
				
				//1. save questions
				questionCreation(question);
				
				//2. get question id and set it to the mcqChoice
				int q_id = getQuestionId(question.getTitle());

				//3. now we save the MCQ choice
				McqChoice mcq_choice = new McqChoice();
				McqChoiceJDBCDAO mcqChoiceDAO = new McqChoiceJDBCDAO();
				for(int i = 0; i < mcqChoices.size(); i++ ) {
					mcq_choice.setId(q_id);
					mcq_choice.setTitle(mcqChoices.get(i));

					try {
						//create the record
						mcqChoiceDAO.create(mcq_choice);
					} catch (CreateFailedException e) {
						quizmanagerlogger.logError("Error occured when adding or creating MCQ choice " + e.getMessage());
					}
				}
				
				// 3. set the choice from the options as answer
				System.out.println("Which option from the MCQ Choice is the correct answer?");
				String correctAnswer = "";
				for(int j = 1; j <= mcqChoices.size(); j++) {
					System.out.println("\tEnter " + j + " for " + mcqChoices.get(j) + "\n");
				}
				correctAnswer = scanner.nextLine();
				
				//4. create or save the MCQ choice answer
				//4a. get the mcq choice id by title
				int choice_id = getMCQchoiceAnswerByMCQTitle(correctAnswer);
				
				//4b. save the mcq answer
				McqAnswer answerMCQ = new McqAnswer();
				answerMCQ.setId(choice_id);
				McqAnswerJDBCDAO answerMCQJDBC = new McqAnswerJDBCDAO();
				try {
					answerMCQJDBC.create(answerMCQ);
				} catch (CreateFailedException e) {
					quizmanagerlogger.logError("Error occured when creating MCQ answer " + e.getMessage());
				}
				// enter the title
				break;

			case "12":// create mcq answer

				McqAnswer mcqanswer = new McqAnswer();
				System.out.println("Enter the mcq answer :");
				mcqanswer.setTitle(scanner.nextLine());

				mcqAnswerCreation(mcqanswer);
				break;
			case "13":// create valid answer

				ValidAnswer validAnswer = new ValidAnswer();
				System.out.println("Enter the valid answer :");
				validAnswer.setName(scanner.nextLine());

				validAnswerCreation(validAnswer);
				break;
			case "14":// create mcq choice
				McqChoice mcqchoice1 = new McqChoice();
				System.out.println("Enter the mcq choice :");
				mcqchoice1.setTitle(scanner.nextLine());
				mcqChoiceCreation(mcqchoice1);
				break;
			case "15":// search by topic id
				Question question1 = new Question();
				System.out.println("Please Enter the topic id to search for the question you want :");
				question1.setTopic_id(Integer.parseInt(scanner.nextLine()));
				questionSearchByTopicID(question1);
				break;
			case "16":// search in general
				Question question2 = new Question();
				System.out.println("Please Enter the title of the question to search for the question you want :");
				question2.setTitle(scanner.nextLine());
				questionSearch(question2);
				break;
			case "17":// update the quiz
				Quiz quiz1 = new Quiz();
				System.out.println("Please Enter the title of the quiz you want to update :");
				quiz1.setTitle(scanner.nextLine());
				quizUpdateRecord(quiz1);
				break;
			case "18":// delete the quiz
				Quiz quiz2 = new Quiz();
				System.out.println("Please Enter the title of the quiz you want to delete :");
				quiz2.setTitle(scanner.nextLine());
				quizDelete(quiz2);
				break;
			case "19":// delete the question
				Question question3 = new Question();
				System.out.println("Please Enter the title of the question you want to delete :");
				question3.setTitle(scanner.nextLine());
				questionDelete(question3);
				break;
			case "20":// update the question
				Question question4 = new Question();
				System.out.println("Please Enter the title of the quiz you want to delete :");
				question4.setId(Integer.parseInt(scanner.nextLine()));
				questionUpdateRecord(question4);
				break;
			case "21":
				McqChoice mcqchoice2 = new McqChoice();
				System.out.println("Enter the mcq choice you want to delete :");
				mcqchoice2.setTitle(scanner.nextLine());
				mcqChoiceDelete(mcqchoice2);
				break;
			case "22":
				break;
			case "23":
				break;
			case "":// view review
				System.out.print("");
				break;
			case "r":
				// return back to menu
				break;
			case "q":
				System.exit(0);
				break;
			default:
				System.out.println("Option not recognized. Please enter another option");
				break;
			}
		}

	}

	private static int getMCQchoiceAnswerByMCQTitle(String title) {
		McqChoiceJDBCDAO mcqChoiceJDBCDAO = new McqChoiceJDBCDAO();
		return mcqChoiceJDBCDAO.getMCQChoiceAnswerByMCQTitle(title);
	}

	private static int getQuestionId(String title) {
		QuestionJDBCDAO questionJDBCDAO = new QuestionJDBCDAO();
		return questionJDBCDAO.getQuestionIdByTitle(title);
	}

	private static void createTopic(Topic topic) {
		TopicJDBCDAO topicJDAO = new TopicJDBCDAO();
		try {

			int status = topicJDAO.create(topic);
			if (status > 0) {
				System.out.println("The topic," + topic.getName().toUpperCase() + " is created successfully!");
			} else {
				System.out.println("The Topic record was not CREATED due to unknown error!");
			}

		} catch (CreateFailedException e) {

			quizmanagerlogger.logError("Creating Topic record error " + e.getMessage());
		}
	}

	/**
	 * create valid answer in the database if an error occurs it will catch it in
	 * the try catch and i implemented the logger usage example:ValidAnswerJDBCDAO
	 * validanswerJDAO=
	 * new......try{validanswerJDAO.create(validAnswer);}catch(CreateFailedException
	 * e) {quizmanagerlogger.logError("Creating valid answer record error " +
	 * e.getMessage()}
	 * 
	 * @param validAnswer
	 */
	private static void validAnswerCreation(ValidAnswer validAnswer) {
		ValidAnswerJDBCDAO validanswerJDAO = new ValidAnswerJDBCDAO();
		try {

			int status = validanswerJDAO.create(validAnswer);
			if (status > 0) {

				System.out.println(
						"The valid answer," + validAnswer.getName().toUpperCase() + " is created successfully!");

			}

			else {
				System.out.println("The validAnswer record was not CREATED due to unknown error!");

			}
		} catch (CreateFailedException e) {
			quizmanagerlogger.logError("Creating valid answer record error " + e.getMessage());
		}

	}

	private static String studentFunctionalOptions() {

		String selectedItem = "";
		switch (selectedItem) {
		case "4": // take a quiz

			break;

		case "5": // review results

			break;

		default:
			System.out.println("Option not recognized. Please enter another option");
			break;
		}
		return "";
	}

	/**
	 * display a menu in order whether to create a quiz or question using the
	 * scanner this
	 * 
	 * @param scanner
	 * @return option
	 */
	private static String displayMenu(Scanner scanner) {
		String option;
		System.out.println("-- Menu --");
		System.out.println("1. Create Quiz");
		System.out.println("2. Create Question");
		System.out.println("q. Quit the application");
		System.out.println("What is your choice ? (1|2|q) :");

		option = scanner.nextLine();
		return option;
	}

	/**
	 * authenticate the login for the user using his name and his password usage
	 * example{login=scanner.nextline()} eventually it will login if the password
	 * and the name are matched
	 * 
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

	/**
	 * create a student in the database if it an error occurs it will throw an error
	 * {@link CreateFailedException}usage example:student std=new.... studentJDBCDAO
	 * studJDAO=new.....try{studJDAO.create(std)}catch(CreateFailedException e)
	 * {//e.printstacktrace()}
	 * 
	 * @param scanner
	 * @throws CreateFailedException
	 */
	private static void studentCreation(Student student) {

		StudentJDBCDAO studJDAO = new StudentJDBCDAO();
		try {
			studJDAO.create(student);
			System.out.println("Student record created successfully!");

		} catch (CreateFailedException e) {
			quizmanagerlogger.logError("Creating student error " + e.getMessage());
		}
	}

	/**
	 * Delete a student in the database if it an error occurs it will handle the
	 * error using try{}catch{} {@link Exception}usage example:student std=new....
	 * studentJDBCDAO
	 * studJDAO=new.....try{studJDAO.delete(std)}catch(CreateFailedException e)
	 * {//e.printstacktrace()}
	 * 
	 * @param scanner
	 * @throws Exception
	 */
	private static void studentDelete(Student student) {

		StudentJDBCDAO studentJDAO = new StudentJDBCDAO();

		try {
			studentJDAO.delete(student);
			System.out.println("student  record DELETED successfully!");

		} catch (Exception e) {
			quizmanagerlogger.logError("Deleting student error " + e.getMessage());
		}

	}

	/**
	 * update a student in the database if it an error occurs it will handle the
	 * error using try{}catch{} {@link Exception}usage example:student std=new....
	 * studentJDBCDAO
	 * studJDAO=new.....try{studJDAO.updateRecord(std)}catch(Exception e)
	 * {//e.printstacktrace()}
	 * 
	 * @param scanner
	 * @throws Exception
	 */
	private static int studentUpdateRecord(Scanner scanner) {

		int updateStatus = 0;
		Student student = new Student();
		student.setId(Integer.parseInt(scanner.nextLine()));
		student.setName(scanner.nextLine());

		StudentJDBCDAO studentJDAO = new StudentJDBCDAO();

		updateStatus = studentJDAO.updateRecord(student);

		return updateStatus;
	}

	/**
	 * this method for updating the mcq answer in the database if an error occurs it
	 * will handle it using try{}catch(Exception e){}usage example:Mcqanswer
	 * mcanswer= new ..... McqAnswerJDBCDAO
	 * mcqanswerJDAO=new......try{mcqanswerJDAO.update(mcanswer)}catch(Exception e)
	 * {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	private static void mcqAnswerUpdate(Scanner scanner) {
		System.out.println("Welcome to McqAnswer Manager...");

		McqAnswer mcq = new McqAnswer();
		mcq.setTitle(scanner.nextLine());
		mcq.setMcqchoice_id(Integer.parseInt(scanner.nextLine()));
		mcq.setId(Integer.parseInt(scanner.nextLine()));

		McqAnswerJDBCDAO mcqanswerJDAO = new McqAnswerJDBCDAO();
		try {
			mcqanswerJDAO.update(mcq);
			System.out.println("mcqanswer  upated successfully!");
		} catch (Exception e) {
			quizmanagerlogger.logError("updating McqAnswer error");
			e.printStackTrace();
		}
	}

	/**
	 * this method for creating the mcq answer in the database if an error occurs it
	 * will handle it using try{}catch(CreateFailedException e){}usage
	 * example:Mcqanswer mcanswer= new ..... McqAnswerJDBCDAO
	 * mcqanswerJDAO=new......try{mcqanswerJDAO.create(mcanswer)}catch(CreateFailedException
	 * e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	private static void mcqAnswerCreation(McqAnswer mcqanswer) {

		McqAnswerJDBCDAO mcqanswerJDAO = new McqAnswerJDBCDAO();
		try {
			mcqanswerJDAO.create(mcqanswer);
			System.out.println("mcqanswer  created successfully!");
		} catch (CreateFailedException e) {
			quizmanagerlogger.logError("creating McqAnswer error");

			e.printStackTrace();
		}

	}

	/**
	 * this method for deleting the mcq answer in the database if an error occurs it
	 * will handle it using try{}catch(Exception e){}usage example:Mcqanswer
	 * mcanswer= new ..... McqAnswerJDBCDAO
	 * mcqanswerJDAO=new......try{mcqanswerJDAO.delete(mcanswer)}catch(Exception e)
	 * {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	private static void mcqAnswerDelete(McqAnswer mcqAnswer) {

		McqAnswerJDBCDAO mcqAnswerJDAO = new McqAnswerJDBCDAO();

		try {
			mcqAnswerJDAO.delete(mcqAnswer);
			System.out.println("mcqanswer  DELETED successfully!");
		} catch (Exception e) {

			quizmanagerlogger.logError("updating McqAnswer error" + e.getMessage());
		}

	}

	/**
	 * this method for updating the DifficultyLevel of a question in the database if
	 * an error occurs it will handle it using try{}catch(Exception e){}usage
	 * example:DifficultyLevel difficultylevel= new ..... DifficultyLevelJDBCDAO
	 * difficultylevelJDAO=new......try{difficultylevelJDAO.DifficultyLevelUpdateRecord(difficultylevel)}
	 * catch(Exception e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	private static int difficultyLevelUpdateRecord(DifficultyLevel difficultylevel) {

		int updateStatus = 0;

		DifficultyLevelJDBCDAO difficultylevelJDAO = new DifficultyLevelJDBCDAO();

		try {
			updateStatus = difficultylevelJDAO.updateRecord(difficultylevel);
		} catch (Exception e) {
			quizmanagerlogger.logError("updating DifficultyLevel error" + e.getMessage());

			e.printStackTrace();
		}

		return updateStatus;
	}

	/**
	 * this method for deleting the DifficultyLevel of a question in the database if
	 * an error occurs it will handle it using try{}catch(Exception e){}usage
	 * example:DifficultyLevel difficultylevel= new ..... DifficultyLevelJDBCDAO
	 * difficultylevelJDAO=new....try{difficultylevelJDAO.DifficultyLevelDelete(difficultylevel)}
	 * catch(Exception e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	private static void difficultyLevelDelete(DifficultyLevel difficultylevel) {

		DifficultyLevelJDBCDAO difficultylevelJDAO = new DifficultyLevelJDBCDAO();

		try {
			difficultylevelJDAO.delete(difficultylevel);
			System.out.println("Difficulty level record DELETED successfully!");
		} catch (Exception e) {
			quizmanagerlogger.logError("Deleting DifficultyLevel error" + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * this method for creating the DifficultyLevel of a question in the database if
	 * an error occurs it will handle it using try{}catch(Exception e){}usage
	 * example:DifficultyLevel difficultylevel= new ..... DifficultyLevelJDBCDAO
	 * difficultylevelJDAO=new....try{difficultylevelJDAO.DifficultyLevelCreation(difficultylevel)}
	 * catch(Exception e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	private static void difficultyLevelCreation(DifficultyLevel difficultylevel) {

		DifficultyLevelJDBCDAO difficultylevelDAO = new DifficultyLevelJDBCDAO();
		try {
			int status = 0;

			status = difficultylevelDAO.create(difficultylevel);

			// check for success or failure of operation
			if (status > 0) {
				// if create is greater than zero so it is successful
				System.out.println("Difficultylevel record created successfully!");
			} else {
				// if create is less than zero or unsuccessful
				System.out.println("Difficultylevel record failed to be created");
			}

		} catch (CreateFailedException e) {
			quizmanagerlogger.logError("creation DifficultyLevel error " + e.getMessage());
		}

	}

	/**
	 * this method for deleting the mcqchoice in the database if an error occurs it
	 * will handle it using try{}catch(Exception e){}usage McqChoice mcqchoice= new
	 * ..... McqChoiceJDBCDAO
	 * mcqchoiceJDAO=new....try{mcqchoiceJDAO.McqChoiceDelete(mcqChoice)}
	 * catch(Exception e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	private static void mcqChoiceDelete(McqChoice mcqChoice) {

		McqChoiceJDBCDAO mcqChoiceJDAO = new McqChoiceJDBCDAO();

		try {
			mcqChoiceJDAO.delete(mcqChoice);
		} catch (Exception e) {
			quizmanagerlogger.logError("Deleting McqChoice error" + e.getMessage());

			e.printStackTrace();
		}
	}

	/**
	 * this method for updating the McqChoice o in the database if an error occurs
	 * it will handle it using try{}catch(Exception e){}usage McqChoice mcqchoice=
	 * new ..... McqChoiceJDBCDAO
	 * mcqchoiceJDAO=new....try{mcqchoiceJDAO.updateRecord(mcqChoice)}
	 * catch(Exception e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */

	private static int mcqChoiceUpdateRecord(McqChoice mcqChoice) {

		int updateStatus = 0;

		McqChoiceJDBCDAO mcqChoiceJDAO = new McqChoiceJDBCDAO();

		try {
			mcqChoiceJDAO.updateRecord(mcqChoice);
		} catch (CreateFailedException e) {
			quizmanagerlogger.logError("Updating McqChoice error" + e.getMessage());

			e.printStackTrace();
		}
		return updateStatus;
	}

	/**
	 * this method for creating the McqChoice o in the database if an error occurs
	 * it will handle it using try{}catch(Exception e){}usage McqChoice mcqchoice=
	 * new ..... McqChoiceJDBCDAO
	 * mcqchoiceJDAO=new....try{mcqchoiceJDAO.McqChoiceCreation(mcqChoice)}
	 * catch(Exception e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	private static void mcqChoiceCreation(McqChoice mcqChoice) {

		McqChoiceJDBCDAO mcqChoiceJDAO = new McqChoiceJDBCDAO();

		try {

			mcqChoiceJDAO.create(mcqChoice);
			System.out.println("The MCQ Choice record, " + mcqChoice.getTitle() + " created successfully");

		} catch (CreateFailedException e) {
			quizmanagerlogger.logError("creation McqChoice error");

			e.printStackTrace();
		}

	}

	/**
	 * this method for searching for a tquestion by id in the database if an error
	 * occurs it will handle it using try{}catch(SearchFailedException e){}usage
	 * Question question= new ..... QuestionJDBCDAO
	 * qdao=new....try{qdao.QuestionSearchByTopicID(question)} catch(Exception e)
	 * {e.printStackTrace()}
	 * 
	 * @param scanner
	 */

	@SuppressWarnings("unused")
	private static void questionSearchByTopicID(Question question) {

		QuestionJDBCDAO qdao = new QuestionJDBCDAO();
		try {

			List<Question> questionList = qdao.search(question);
			if (questionList.size() > 0) {

				for (int i = 0; i < questionList.size(); i++) {

					System.out.println("ID :: " + questionList.get(i).getId());
					System.out.println("TITLE :: " + questionList.get(i).getTitle());
					System.out.println("QUESTION TYPE ID :: " + questionList.get(i).getTopic_id());
					System.out.println("Question ID :: " + questionList.get(i).getTopic_id());

				}
			} else {
				System.out.println("No Record(s) to dispaly details");
			}

		} catch (SearchFailedException e) {
			quizmanagerlogger.logError("searching for a question error");

			e.printStackTrace();
		}

	}

	/**
	 * this method for creating a question by id in the database if an error occurs
	 * it will handle it using try{}catch(CreateFailedException e){}usage Question
	 * question= new ..... QuestionJDBCDAO
	 * qdao=new....try{qdao.QuestionCreation(question)} catch(CreateFailedException
	 * e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	private static void questionCreation(Question question) {

		QuestionJDBCDAO qdao = new QuestionJDBCDAO();
		try {
			qdao.create(question);
			System.out.println("question record created successfully!");
		} catch (CreateFailedException e) {
			System.out.println("Quiz error " + e.getMessage());
			quizmanagerlogger.logError("creation Question error");
		}
	}

	/**
	 * this method for updating a question by id in the database if an error occurs
	 * it will handle it using try{}catch(Exception e){}usage Question question= new
	 * ..... QuestionJDBCDAO qdao=new....try{qdao.QuestionUpdateRecord(question)}
	 * catch(Exception e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	@SuppressWarnings("unused")
	private static int questionUpdateRecord(Question question) {

		int updateStatus = 0;

		QuestionJDBCDAO questionJDAO = new QuestionJDBCDAO();

		try {

			updateStatus = questionJDAO.updateRecord(question);
			if (updateStatus > 0) {
				System.out.println("Question record UPDATED successfully!");
			} else {
				System.out.println("Question record NOT UPDATED");
			}

		} catch (CreateFailedException e) {
			System.out.print("question  not updated" + e.getMessage());
			e.printStackTrace();
			quizmanagerlogger.logError("update Question error");
		}
		return updateStatus;
	}

	/**
	 * this method for deleting a question by id in the database if an error occurs
	 * it will handle it using try{}catch(Exception e){}usage Question question= new
	 * ..... QuestionJDBCDAO qdao=new....try{qdao.delete(question)} catch(Exception
	 * e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	private static void questionDelete(Question question) {

		QuestionJDBCDAO questionJDAO = new QuestionJDBCDAO();

		try {

			int status = questionJDAO.delete(question);

			if (status > 0) {
				System.out.println("Question record DELETED successfully!");
			} else {
				System.out.println("Question record NOT DELETED!");
			}

		} catch (Exception e) {
			quizmanagerlogger.logError("Deletion Question error");

			e.printStackTrace();
		}

	}

	/**
	 * this method for searching for a question by id in the database if an error
	 * occurs it will handle it using try{}catch(SearchFailedException e){}usage
	 * Question question= new ..... QuestionJDBCDAO
	 * qdao=new....try{qdao.QuestionUpdateRecord(question)}
	 * catch(SearchFailedException e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	@SuppressWarnings("unused")
	private static void questionSearch(Question question) {

		QuestionJDBCDAO qdao = new QuestionJDBCDAO();
		try {

			List<Question> questionList = qdao.search(question);
			if (questionList.size() > 0) {

				for (int i = 0; i < questionList.size(); i++) {

					System.out.println("ID :: " + questionList.get(i).getId());
					System.out.println("TITLE :: " + questionList.get(i).getTitle());

					System.out.println("QUESTION TYPE ID :: " + questionList.get(i).getTopic_id());
					System.out.println("Question ID :: " + questionList.get(i).getTopic_id());

				}
			} else {
				System.out.println("No Record(s) to dispaly details");
			}

		} catch (SearchFailedException e) {
			quizmanagerlogger.logError("search Question error");

			e.printStackTrace();
		}

	}

	/**
	 * this method for creating a question type by id in the database if an error
	 * occurs it will handle it using try{}catch(Exception e){}usage Question
	 * question= new ..... QuestionJDBCDAO
	 * qdao=new....try{qdao.QuestionTypeCreation(question)} catch(Exception e)
	 * {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	private static void questionTypeCreation(QuestionType questionType) {

		QuestionTypeJDBCDAO questionTypeDAO = new QuestionTypeJDBCDAO();
		try {

			int status = 0;
			status = questionTypeDAO.createRecord(questionType);

			// check for success or failure of operation
			if (status > 0) {
				// if create is true or successful
				System.out.println("Question record created successfully!");
			} else {
				// if create is false or unsuccessful
				System.out.println("Question record failed to be created");
			}

		} catch (CreateFailedException e) {
			quizmanagerlogger.logError("creation Questiontype error " + e.getMessage());
		}
	}

	/**
	 * this method for updating a question type the database if an error occurs it
	 * will handle it using try{}catch(Exception e){}usage Question question= new
	 * ..... QuestionJDBCDAO
	 * qdao=new....try{qdao.QuestionTypeUpdateRecord(question)} catch(Exception e)
	 * {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	@SuppressWarnings("unused")
	private static int questionTypeUpdateRecord(QuestionType questionType) {

		int updateStatus = 0;

		QuestionTypeJDBCDAO questionTypeJDAO = new QuestionTypeJDBCDAO();

		updateStatus = questionTypeJDAO.updateRecord(questionType);

		return updateStatus;
	}

	/**
	 * this method for deleting a question type the database if an error occurs it
	 * will handle it using try{}catch(Exception e){}usage Question question= new
	 * ..... QuestionJDBCDAO qdao=new....try{qdao.QuestionTypeDelete(question)}
	 * catch(Exception e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	@SuppressWarnings("unused")
	private static void questionTypeDelete(QuestionType questionType) {

		QuestionTypeJDBCDAO questionTypeJDAO = new QuestionTypeJDBCDAO();

		try {
			questionTypeJDAO.delete(questionType);
			System.out.println("Question Type record DELETED successfully!");
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	/**
	 * this method for creating a question type the database if an error occurs it
	 * will handle it using try{}catch(Exception e){}usage Question question= new
	 * ..... QuestionJDBCDAO
	 * qdao=new....try{qdao.QuizCreation(CreateFailedException)}
	 * catch(CreateFailedException e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	private static void quizCreation(Quiz quiz) {

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
			quizmanagerlogger.logError("creation Quiz error" + e.getMessage());

			e.printStackTrace();
		}
	}

	/**
	 * this method for update a quiz the database if an error occurs it will handle
	 * it using try{}catch(Exception e){}usage Question question= new .....
	 * QuestionJDBCDAO qdao=new....try{qdao.QuizUpdateRecord(CreateFailedException)}
	 * catch(CreateFailedException e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */

	@SuppressWarnings("unused")
	private static int quizUpdateRecord(Quiz quiz) {

		int updateStatus = 0;

		QuizJDBCDAO quizJDAO = new QuizJDBCDAO();

		updateStatus = quizJDAO.updateRecord(quiz);

		return updateStatus;
	}

	/**
	 * this method for deleting a quiz the database if an error occurs it will
	 * handle it using try{}catch(Exception e){}usage Question question= new .....
	 * QuestionJDBCDAO qdao=new....try{qdao.QuizDelete(Exception)} catch(Exception
	 * e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	@SuppressWarnings("unused")
	private static void quizDelete(Quiz quiz) {

		QuizJDBCDAO quizJDAO = new QuizJDBCDAO();

		try {
			quizJDAO.delete(quiz);
			System.out.println("Question Type record DELETED successfully!");
		} catch (Exception e) {
			quizmanagerlogger.logError("creation Quiz error");

			e.printStackTrace();
		}

	}

	/**
	 * this method for creating a quizquestion the database if an error occurs it
	 * will handle it using try{}catch(CreateFailedException e){}usage QuizQuestion
	 * quizquestion= new ..... QuizQuestionJDBCDAO
	 * qdao=new....try{qdao.QuizQuestionCreation(CreateFailedException)}
	 * catch(CreateFailedException e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	@SuppressWarnings("unused")
	private static void quizQuestionCreation(QuizQuestion quizquestion) {

		QuizQuestionJDBCDAO quizquestionDAO = new QuizQuestionJDBCDAO();

		try {

			quizquestionDAO.create(quizquestion);
			System.out.println("The QuizQuestion record, " + quizquestion.getId() + " created successfully");

		} catch (CreateFailedException e) {
			quizmanagerlogger.logError("creation QuizQuestion error");

			e.printStackTrace();
		}

	}

	/**
	 * this method for creating a StudentAnswer the database if an error occurs it
	 * will handle it using try{}catch(CreateFailedException e){}usage StudentAnswer
	 * studentAnswer= new ..... StudentAnswerJDBCDAO
	 * qdao=new....try{qdao.StudentAnswerCreation(CreateFailedException)}
	 * catch(CreateFailedException e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	@SuppressWarnings("unused")
	private static void studentAnswerCreation(StudentAnswer studentAnswer) {

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
			quizmanagerlogger.logError("creation StudentAnswer error" + e.getMessage());

			e.printStackTrace();
		}
	}

	/**
	 * this method for updating a StudentAnswer the database if an error occurs it
	 * will handle it using try{}catch(Exception e){}usage StudentAnswer
	 * studentAnswer= new ..... StudentAnswerJDBCDAO
	 * qdao=new....try{qdao.StudentAnswerUpdate(Exception)} catch(Exception e)
	 * {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	@SuppressWarnings("unused")
	private static int studentAnswerUpdate(StudentAnswer studentAnswer) {

		int updateStatus = 0;

		StudentAnswerJDBCDAO studentAnswerJDAO = new StudentAnswerJDBCDAO();

		updateStatus = studentAnswerJDAO.update(studentAnswer);

		return updateStatus;
	}

	/**
	 * this method for deleting a StudentAnswer the database if an error occurs it
	 * will handle it using try{}catch(Exception e){}usage StudentAnswer
	 * studentAnswer= new ..... StudentAnswerJDBCDAO
	 * qdao=new....try{qdao.StudentAnswerDelete(Exception)} catch(Exception e)
	 * {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	@SuppressWarnings("unused")
	private static void studentAnswerDelete(StudentAnswer studentAnswer) {

		StudentAnswerJDBCDAO studentAnswerJDBCDAO = new StudentAnswerJDBCDAO();

		try {

			studentAnswerJDBCDAO.delete(studentAnswer);
			System.out.println("Question Type record DELETED successfully!");

		} catch (Exception e) {
			quizmanagerlogger.logError("Deletion StudentAnswer error");

			e.printStackTrace();
		}
	}

	/**
	 * this method for creating a teacher the database if an error occurs it will
	 * handle it using try{}catch(CreateFailedException e){}usage Teacher teacher=
	 * new ..... TeacherJDBCDAO
	 * teacherDAO=new....try{teacherDAO.teacherCreation(CreateFailedException)}
	 * catch(CreateFailedException e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	@SuppressWarnings("unused")
	private static void teacherCreation(Teacher teacher) {

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
			quizmanagerlogger.logError("creation teacher error");

			e.printStackTrace();
		}
	}

	/**
	 * this method for updating a teacher the database if an error occurs it will
	 * handle it using try{}catch(Exception e){}usage Teacher teacher= new .....
	 * TeacherJDBCDAO
	 * teacherDAO=new....try{teacherDAO.teacherUpdateRecord(Exception)}
	 * catch(CreateFailedException e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */

	@SuppressWarnings("unused")
	private static int teacherUpdateRecord(Teacher teacher) {

		int updateStatus = 0;

		try {
			TeacherJDBCDAO teacherJDAO = new TeacherJDBCDAO();

			updateStatus = teacherJDAO.updateRecord(teacher);

		} catch (Exception e) {
			quizmanagerlogger.logError("UPDATE Teacher error with error message \n" + e.getMessage());
		}
		return updateStatus;
	}

	/**
	 * this method for deleting a teacher the database if an error occurs it will
	 * handle it using try{}catch(Exception e){}usage Teacher teacher= new .....
	 * TeacherJDBCDAO teacherDAO=new....try{teacherDAO.TeacherDelete(Exception)}
	 * catch(Exception e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	private static void teacherDelete(Teacher teacher) {

		TeacherJDBCDAO teacherJDAO = new TeacherJDBCDAO();

		try {
			teacherJDAO.delete(teacher);
			System.out.println("teacher  record DELETED successfully!");
		} catch (Exception e) {
			quizmanagerlogger.logError("Deletion Teacher error");

			e.printStackTrace();
		}

	}

	/**
	 * this method for creating a topic the database if an error occurs it will
	 * handle it using try{}catch(CreateFailedException e){}usage Topic topic= new
	 * ..... TopicJDBCDAO topicDAO=new....try{topicDAO.TopicCreation(topic)}
	 * catch(CreateFailedException e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	@SuppressWarnings("unused")
	private static void topicCreation(Topic topic) {

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
			quizmanagerlogger.logError("Creation Topic error");
			e.printStackTrace();
		}
	}

	/**
	 * this method for updating a topic the database if an error occurs it will
	 * handle it using try{}catch(Exception e){}usage Topic topic= new .....
	 * TopicJDBCDAO topicDAO=new....try{topicDAO.TopicUpdateRecord(topic)}
	 * catch(Exception e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	@SuppressWarnings("unused")
	private static int topicUpdateRecord(Topic topic) {

		int updateStatus = 0;

		TopicJDBCDAO topicJDAO = new TopicJDBCDAO();

		updateStatus = topicJDAO.updateRecord(topic);
		quizmanagerlogger.logError("Update Topic error");

		return updateStatus;

	}

	/**
	 * this method for deleting a topic the database if an error occurs it will
	 * handle it using try{}catch(Exception e){}usage Topic topic= new .....
	 * TopicJDBCDAO topicDAO=new....try{topicDAO.TopicDelete(topic)} catch(Exception
	 * e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	@SuppressWarnings("unused")
	private static void topicDelete(Topic topic) {

		TopicJDBCDAO topicJDAO = new TopicJDBCDAO();

		try {
			topicJDAO.delete(topic);
			System.out.println("topic record DELETED successfully!");
		} catch (Exception e) {
			quizmanagerlogger.logError("Delete Topic error");
			e.printStackTrace();
		}

	}

	/**
	 * this method for creating a validanswer the database if an error occurs it
	 * will handle it using try{}catch(Exception e){}usage ValidAnswer validanswer=
	 * new ..... ValidAnswerJDBCDAO
	 * validanswerDAO=new....try{validanswerDAO.validanswerCreation(validanswer)}
	 * catch(Exception e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	private static void validanswerCreation(ValidAnswer validanswer) {

		ValidAnswerJDBCDAO validanswerDAO = new ValidAnswerJDBCDAO();
		try {

			int status = 0;
			status = validanswerDAO.createRecord(validanswer);

			// check for success or failure of operation
			if (status > 0) {
				// if create is true or successful
				System.out.println("valid answer record created successfully!");
			} else {
				// if create is false or unsuccessful
				System.out.println("valid answer record failed to be created");
			}

		} catch (CreateFailedException e) {
			quizmanagerlogger.logError("Creation validanswer error");

			e.printStackTrace();
		}
	}

	/**
	 * this method for updating a validanswer the database if an error occurs it
	 * will handle it using try{}catch(Exception e){}usage ValidAnswer validanswer=
	 * new ..... ValidAnswerJDBCDAO
	 * validanswerDAO=new....try{validanswerDAO.validanswerUpdateRecord(validanswer)}
	 * catch(Exception e) {e.printStackTrace()}
	 * 
	 * @param scanner
	 */
	@SuppressWarnings("unused")
	private static int validanswerUpdateRecord(ValidAnswer validanswer) {

		int updateStatus = 0;

		ValidAnswerJDBCDAO validanswerJDAO = new ValidAnswerJDBCDAO();

		updateStatus = validanswerJDAO.updateRecord(validanswer);
		quizmanagerlogger.logError("updating validanswer error");
		return updateStatus;

	}

	@SuppressWarnings("unused")
	private static void validanswerDelete(ValidAnswer validanswer) {

		ValidAnswerJDBCDAO validanswerJDAO = new ValidAnswerJDBCDAO();

		try {
			validanswerJDAO.delete(validanswer);
			System.out.println("valid answer record DELETED successfully!");
		} catch (Exception e) {
			System.out.print("Error delete" + e.getMessage());
			e.printStackTrace();
			quizmanagerlogger.logError("Deleting validanswer error");
		}

	}
}
