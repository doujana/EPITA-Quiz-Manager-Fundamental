package fr.epita.quiz.manager.doujana.services.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.epita.quiz.manager.doujana.datamodel.McqAnswer;
import fr.epita.quiz.manager.doujana.datamodel.Question;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;

public class QuestionFileDAO {

	
	
	private File file;

	public QuestionFileDAO(File file) throws IOException {

		if (!file.exists()) {
			file.getAbsoluteFile().getParentFile().mkdirs();
			file.createNewFile(); // todo handle Exception
		}
		this.file = file;
	}

	/**
	 * This is the way to save a question using that dao Usage :
	 * 
	 * <pre>
	 * <code> QuestionFileDAO dao = ...;
	 * Question question = new Question("test");
	 * try{
	 * 	dao.create(question);
	 * }catch(CreateFailedException cfe){
	 * 	System.out.println("this Question was not created :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param question
	 * @throws CreateFailedException
	 */
	public void create(Question question) throws CreateFailedException {

		try (PrintWriter writer = new PrintWriter(file)) {
			writer.println(question.getTitle());
			writer.flush();

		} catch (FileNotFoundException e) {
			throw new CreateFailedException(question, e);
		}

	}
	/**
	 * This is the way to update a question using that dao Usage :
	 * 
	 * <pre>
	 * <code> QuestionFileDAO dao = ...;
	 * Question question = new Question("test");
	 * try{
	 * 	dao.update(question);
	 * }catch(Exception cfe){
	 * 	System.out.println("this Question was not updated :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 */
	public void update(Question question) {

	}
	/**
	 * This is the way to save a question using that dao Usage :
	 * 
	 * <pre>
	 * <code> QuestionFileDAO dao = ...;
	 * Question question = new Question("test");
	 * try{
	 * 	dao.delete(question);
	 * }catch(Exception cfe){
	 * 	System.out.println("this Question was not deleted :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param question
	 */
	
	public void delete(Question question) {

	}
	/**
	 * This is the way to get a question by id using that dao Usage :
	 * 
	 * <pre>
	 * <code> QuestionFileDAO dao = ...;
	 * Question question = new Question("test");
	 * try{
	 * 	dao.getbyid(question);
	 * }catch(Exception cfe){
	 * 	System.out.println("this Question was not deleted :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param id
	 
	 */
	
	public Question getById(int id) {
		return null;

	}
	/**
	 * This is the way to update a question using that dao Usage :
	 * 
	 * <pre>
	 * <code> QuestionFileDAO dao = ...;
	 * Question question = new Question("test");
	 * try{
	 * 	dao.update(question);
	 * }catch(Exception cfe){
	 * 	System.out.println("this Question was not updated :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param question
	 */
	public List<Question> search(Question questionCriterion) throws SearchFailedException {
		List<Question> resultList = new ArrayList<>();
		try (Scanner scanner = new Scanner(file)) {
			// for each line in the file,
			while (scanner.hasNext()) {
				// read the line
				String line = scanner.nextLine();

				if (line.contains(questionCriterion.getTitle())) {
					Question question = new Question(line);
					resultList.add(question);
				}
			}

		} catch (FileNotFoundException e) {
			throw new SearchFailedException(questionCriterion, e);
		}

		// compare to the criterion
		// if it is correct then put it in the result list

		// return the result list

		return resultList;
	}


}
