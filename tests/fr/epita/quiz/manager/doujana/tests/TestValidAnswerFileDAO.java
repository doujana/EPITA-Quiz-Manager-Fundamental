package fr.epita.quiz.manager.doujana.tests;

import java.util.List;
import java.util.Scanner;

import fr.epita.quiz.manager.doujana.datamodel.QuestionType;
import fr.epita.quiz.manager.doujana.datamodel.ValidAnswer;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;
import fr.epita.quiz.manager.doujana.services.data.QuestionTypeJDBCDAO;
import fr.epita.quiz.manager.doujana.services.data.QuizJDBCDAO;
import fr.epita.quiz.manager.doujana.services.data.ValidAnswerFileDAO;
import fr.epita.quiz.manager.doujana.services.data.ValidAnswerJDBCDAO;

public class TestValidAnswerFileDAO {
	private static final String LOGIN = "ADM";
	private static final String PWD = "ADM";

	public static void main(String[] args) {
		   
		
		     // given
				ValidAnswerJDBCDAO dao = new ValidAnswerJDBCDAO();
				ValidAnswer validanswer = new ValidAnswer("IPHONE VS ");

				// when
				try {
					dao.create(validanswer);
				} catch (CreateFailedException e) {
					System.out.println("this answer was not inserted :" + e.getFaultInstance());
					e.printStackTrace();
				}

				// then ?
				try {
					List<ValidAnswer> searchResults = dao.search(new ValidAnswer());
					System.out.println(searchResults);
					if (searchResults.size() < 1) {
						System.out.println("error, got no result");
					} else {
						System.out.println("success!");
					}
				} catch (SearchFailedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// testing adding the valid answers
				Scanner scanner = new Scanner(System.in);
				boolean authenticated = authenticate(scanner);
				if (!authenticated) {
					scanner.close();
					return;
				}

				System.out.println("You're authenticated");
				String ValidAnswer = "";
				while (!ValidAnswer.equals("q")) {

					ValidAnswer = displayMenu(scanner);

					switch (ValidAnswer) {
					case "1":
						validanswerCreation();
						break;
					case "2":
						ValidanswerDelete();
						break;
					case "3":
						ValidAnswer validanswer1 = new ValidAnswer ();
						int status =validanswerUpdateRecord(validanswer1);
						if(status > 0) {
							System.out.println("valid answer UPDATED successfully!");
						} else {
							System.out.println("valid answer was NOT UPDATED");
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

			
			/**
			 * 
			 * @param scanner
			 * @return
			 */
			private static void validanswerCreation() {

				System.out.println("Welcome to valid  answer  Manager...");
				ValidAnswer validanswer = new ValidAnswer();
				validanswer.setName("java is good");

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

					e.printStackTrace();
				}
			}
			/**
			 * 
			 * @param scanner
			 * @return
			 */
			private static int validanswerUpdateRecord(ValidAnswer validanswer) {

				int updateStatus = 0;

				validanswer.setId(4);
				validanswer.setName("java is good");

				ValidAnswerJDBCDAO validanswerJDAO = new ValidAnswerJDBCDAO();

				updateStatus= validanswerJDAO.updateRecord(validanswer);
				
				return updateStatus;
			}
			/**
			 * 
			 * @param scanner
			 * @return
			 */
			private static void ValidanswerDelete() {

				ValidAnswer validanswer = new ValidAnswer();
				validanswer.setId(5);

				ValidAnswerJDBCDAO validanswerJDAO = new ValidAnswerJDBCDAO();

				try {
					validanswerJDAO.delete(validanswer);
					System.out.println("valid answer record DELETED successfully!");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			private static String displayMenu(Scanner scanner) {
				String validanswer;
				System.out.println("-- Menu --");
				System.out.println("1. insert valid answers");
				System.out.println("2. DELETE valid answers");
				System.out.println("3. UPDATE valid answers");
				System.out.println("q. Quit the application");
				System.out.println("What is your choice ? (1|2|q) :");
				validanswer = scanner.nextLine();
				return validanswer;
			}

			private static boolean authenticate(Scanner scanner) {
				System.out.println("Please enter your login : ");
				String login = scanner.nextLine();
				System.out.println("Please enter your password : ");
				String password = scanner.nextLine();

				return LOGIN.equals(login) && PWD.equals(password);
			}



	}


