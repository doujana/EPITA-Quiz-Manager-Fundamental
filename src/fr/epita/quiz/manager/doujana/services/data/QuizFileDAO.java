package fr.epita.quiz.manager.doujana.services.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.epita.quiz.manager.doujana.datamodel.Quiz;

import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;

public class QuizFileDAO {

	private File file;

	public QuizFileDAO(File file) throws IOException {

		if (!file.exists()) {
			file.getAbsoluteFile().getParentFile().mkdirs();
			file.createNewFile(); // todo handle Exception
		}
		this.file = file;
	}

	/**
	 * This is the way to save a quiz using that dao Usage :
	 * 
	 * <pre>
	 * <code> QuizFileDAO dao = ...;
	 * Quiz quiz = new Quiz("test");
	 * try{
	 * 	dao.create(quiz);
	 * }catch(CreateFailedException cfe){
	 * 	System.out.println("this Quiz was not created :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param quiz
	 * @throws CreateFailedException
	 */
	public void create(Quiz quiz) throws CreateFailedException {

		try (PrintWriter writer = new PrintWriter(file)) {
			writer.println(quiz.getTitle());
			writer.flush();

		} catch (FileNotFoundException e) {
			throw new CreateFailedException(quiz, e);
		}

	}
	/**
	 * This is the way to update a quiz using that dao Usage :
	 * 
	 * <pre>
	 * <code> QuizFileDAO dao = ...;
	 * Quiz quiz = new Quiz("test");
	 * try{
	 * 	dao.update(quiz);
	 * }catch(UpdateFailedException cfe){
	 * 	System.out.println("this Quiz was not updated :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param quiz
	 * @throws UpdateFailedException
	 */

	public void update(Quiz quiz) {

	}
	/**
	 * This is the way to delete a quiz using that dao Usage :
	 * 
	 * <pre>
	 * <code> QuizFileDAO dao = ...;
	 * Quiz quiz = new Quiz("test");
	 * try{
	 * 	dao.delete(quiz);
	 * }catch(DeleteFailedException cfe){
	 * 	System.out.println("this Quiz was not Deleted :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param quiz
	 */
	public void delete(Quiz quiz) {

	}
	/**
	 * This is the way to get  a quiz  by id using that dao Usage :
	 * 
	 * <pre>
	 * <code> QuizFileDAO dao = ...;
	 * Quiz quiz = new Quiz("test");
	 * try{
	 * 	dao.getbyid(quiz);
	 * }catch(Exception cfe){
	 * 	System.out.println("this Quiz was not found :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param id
	 */

	public Quiz getById(int id) {
		return null;

	}
	/**
	 * This is the way to search for  a quiz using that dao Usage :
	 * 
	 * <pre>
	 * <code> QuizFileDAO dao = ...;
	 * Quiz quiz = new Quiz("test");
	 * try{
	 * 	dao.search(quiz);
	 * }catch(SearchFailedException cfe){
	 * 	System.out.println("this Quiz was not created :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param quizCriterion
	 * @throws SearchFailedException
	 */
	
	public List<Quiz> search(Quiz quizCriterion) throws SearchFailedException {
		List<Quiz> resultList = new ArrayList<>();
		try (Scanner scanner = new Scanner(file)) {
			// for each line in the file,
			while (scanner.hasNext()) {
				// read the line
				String line = scanner.nextLine();

				if (line.contains(quizCriterion.getTitle())) {
					Quiz quiz = new Quiz(line);
					resultList.add(quiz);
				}
			}

		} catch (FileNotFoundException e) {
			throw new SearchFailedException(quizCriterion, e);
		}

		// compare to the criterion
		// if it is correct then put it in the result list

		// return the result list

		return resultList;
	}

}
