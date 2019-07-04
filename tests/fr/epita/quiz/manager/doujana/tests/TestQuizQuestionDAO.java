package fr.epita.quiz.manager.doujana.tests;

import java.util.List;
import java.util.Scanner;

import fr.epita.quiz.manager.doujana.services.data.QuestionJDBCDAO;
import fr.epita.quiz.manager.doujana.services.data.QuizQuestionJDBCDAO;
import fr.epita.quiz.manager.doujana.datamodel.Question;
import fr.epita.quiz.manager.doujana.datamodel.QuizQuestion;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;


public class TestQuizQuestionDAO {
	private static final String LOGIN = "ADM";
	private static final String PWD = "ADM";
	public static void main(String[] args) {
		
		
		/**
		 * This is while loop for choosing more than operation on the QuizQuestion
		 * exmaple usage:Scanner scanner = new Scanner(System.in);
		 * boolean authenticated = authenticate(scanner);if(!authenticated){}
		 * while (!quizquestionMade.equals("q")) {quizquestionMade = displayMenu(scanner);
		 * switch (quizquestionMade){case:
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
				QuizQuestionCreation();
				break;
			case "2":
				QuizQuestion quizquestion = new QuizQuestion();
				QuizQuestionUpdateRecord(quizquestion);
				break;
			case "3":
				QuizQuestionDelete();
				break;
			case "4":
				QuestionSearch();
			
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
			 * Create quizquestion in the database if any error occurs it will through a
			 * {@link CreateFailedException} usage example:QuizQuestion quizquestion=new ....()
			 * quizquestionJDBCDAO quizquestionJDAO=new.....()
			 * try{quizquestionJDAO.create(quizquestion)}catch(CreateFailedException e){e.printStackTrace();}
			 *
			 */
			 
			private static void QuizQuestionCreation() {

				System.out.println("Welcome to QuizQuestion Manager...");

				QuizQuestion quizquestion = new QuizQuestion ();
				quizquestion.setQuestion_id(1);
				quizquestion.setId(1);
				quizquestion.setQuiz_id(1);

				QuizQuestionJDBCDAO quizquestionDAO = new QuizQuestionJDBCDAO();
				
				try {
					
					
					quizquestionDAO.create(quizquestion);
					System.out.println("The QuizQuestion record, " + quizquestion.getId() + " created successfully" );
					
				} catch (CreateFailedException e) {

					e.printStackTrace();
				}

			}
			/**
			 * Updates quizquestion in the database if any error occurs it will through a
			 * {@link Exception} usage example:QuizQuestion quizquestion=new ....()
			 * quizquestionJDBCDAO quizquestionJDAO=new.....()
			 * try{quizquestionJDAO.update(quizquestion)}catch(Exception e){e.printStackTrace();}
			 *
			 */
			
			private static int QuizQuestionUpdateRecord(QuizQuestion quizquestion) {

				int updateStatus = 0;
				
//				McqChoice mcqChoice = new McqChoice();
				quizquestion.setId(4);
				quizquestion.setQuestion_id(2);
				quizquestion.setQuiz_id(55);

				QuizQuestionJDBCDAO quizquestionJDAO = new QuizQuestionJDBCDAO();
				
				try {
					quizquestionJDAO.updateRecord(quizquestion);
				} catch (Exception e) {
					System.out.printf("this quizquestion was updated successfuly");
					e.printStackTrace();
				}
				return updateStatus;
			}
			/**
			 * Delete quizquestion in the database if any error occurs it will through a
			 * {@link Exception} usage example:QuizQuestion quizquestion=new ....()
			 * quizquestionJDBCDAO quizquestionJDAO=new.....()
			 * try{quizquestionJDAO.Delete(quizquestion)}catch(Exception e){e.printStackTrace();}
			 *
			 */
			private static void QuizQuestionDelete() {
				
				QuizQuestion quizquestion = new QuizQuestion();
				quizquestion.setId(3);
				
				QuizQuestionJDBCDAO quizaquestionJDAO = new QuizQuestionJDBCDAO();
				
				try {
					quizaquestionJDAO.delete(quizquestion);
				} catch (Exception e) {
					System.out.printf(e.getMessage());
					e.printStackTrace();
				}
			}
			/**
			 * search for a quizquestion in the database if any error occurs it will through a
			 * {@link SearchFailedException} usage QuizQuestion quizquestion=new ....()
			 * QuizQuestionJDBCDAO quizquestionJDAO=new.....()
			 * try{quizquestionJDAO.QuizQuestionSearch(quizquestion)}catch(SearchFailedException e){e.printStackTrace();}
			 *
			 */
			
			private static void QuestionSearch()
			{
				
				System.out.println("Welcome to quizquestion Manager...");
				QuizQuestion quizquestion = new QuizQuestion();
				quizquestion.setQuestion_id(1);
				quizquestion.setQuiz_id(2);
				quizquestion.setId(1);
				

				QuizQuestionJDBCDAO qdao = new QuizQuestionJDBCDAO();
				try {
					
					List<QuizQuestion> quizquestionList = qdao.search(quizquestion);
					if(quizquestionList.size() > 0 ) {
						
					for(int i = 0; i < quizquestionList.size(); i++) {
						
						System.out.println("ID :: " + quizquestionList.get(i).getId());
						System.out.println("Question_ID:: " + quizquestionList.get(i).getQuestion_id());
						System.out.println("Quiz ID:: " + quizquestionList.get(i).getQuiz_id());
					}
					} else {
						System.out.println("No Record(s) to dispaly details");
					}
					
				} catch (SearchFailedException e) {
					
					e.printStackTrace();
				}
				
			}

			/**
			    * This method dispaly a menu in order to choose one of the CRUD process on the quizquestion table
			    * @param scanner
			    * @return
			    */
			
			private static String displayMenu(Scanner scanner) {
				
				String quizquestion;
				
				System.out.println("-- Menu --");
				System.out.println("1. Create quizquestion");
				System.out.println("2. Update quizquestion");
				System.out.println("3. Delete quizquestion");
				System.out.println("q. Quit the Application");
				System.out.println("What is your choice ? (1|2|3|q) :");
				
				quizquestion = scanner.nextLine();
				
				return quizquestion;
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

