package fr.epita.quiz.manager.doujana.services.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.epita.quiz.manager.doujana.datamodel.Student;
import fr.epita.quiz.manager.doujana.datamodel.ValidAnswer;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;

public class ValidAnswerFileDAO {
	
	private File file;

	public ValidAnswerFileDAO(File file) throws IOException {

		if (!file.exists()) {
			file.getAbsoluteFile().getParentFile().mkdirs();
			file.createNewFile(); // todo handle Exception
		}
		this.file = file;
	}

	/**
	 * This is the way to save a valid answer using that dao Usage :
	 * 
	 * <pre>
	 * <code> ValidAnswerFileDAO dao = ...;
	 * ValidAnswer validanswer = new ValidAnswer("test");
	 * try{
	 * 	dao.create(validanswer);
	 * }catch(CreateFailedException cfe){
	 * 	System.out.println("this validanswer was not created :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param validanswer
	 * @throws CreateFailedException
	 */
	public void create(ValidAnswer validanswer) throws CreateFailedException {

		try (PrintWriter writer = new PrintWriter(file)) {
			writer.println(validanswer.getName());
			writer.flush();

		} catch (FileNotFoundException e) {
			throw new CreateFailedException(validanswer, e);
		}

	}
	/**
	 * This is the way to update a valid answer using that dao Usage :
	 * 
	 * <pre>
	 * <code> ValidAnswerFileDAO dao = ...;
	 * ValidAnswer validanswer = new ValidAnswer("test");
	 * try{
	 * 	dao.update(validanswer);
	 * }catch(Exception cfe){
	 * 	System.out.println("this validanswer was not updated :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param validanswer
	 */
	public void update(ValidAnswer validanswer) {

	}
	/**
	 * This is the way to delete a valid answer using that dao Usage :
	 * 
	 * <pre>
	 * <code> ValidAnswerFileDAO dao = ...;
	 * ValidAnswer validanswer = new ValidAnswer("test");
	 * try{
	 * 	dao.delete(validanswer);
	 * }catch(Exception cfe){
	 * 	System.out.println("this validanswer was not deleted :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param validanswer
	 */
	public void delete(ValidAnswer validanswer) {

	}
	/**
	 * this method will get the valid answer by id
	 * @param id
	 * @return
	 */

	public ValidAnswer getById(int id) {
		return null;

	}
	/**
	 * This is the way to search for a valid answer using that dao Usage :
	 * 
	 * <pre>
	 * <code> ValidAnswerFileDAO dao = ...;
	 * ValidAnswer validanswer = new ValidAnswer("test");
	 * try{
	 * 	dao.search(validanswer);
	 * }catch(SearchFailedException cfe){
	 * 	System.out.println("the search process for the valid answer not successful :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param validanswerCriterion
	 * @throws SearchFailedException
	 */
	public List<ValidAnswer> search(ValidAnswer validanswerCriterion) throws SearchFailedException {
		List<ValidAnswer> resultList = new ArrayList<>();
		try (Scanner scanner = new Scanner(file)) {
			// for each line in the file,
			while (scanner.hasNext()) {
				// read the line
				String line = scanner.nextLine();

				if (line.contains(validanswerCriterion.getName())) {
					 ValidAnswer validanswers = new ValidAnswer(line);
					resultList.add(validanswers);
				}
			}

		} catch (FileNotFoundException e) {
			throw new SearchFailedException(validanswerCriterion, e);
		}

		// compare to the criterion
		// if it is correct then put it in the result list

		// return the result list

		return resultList;
	}

	

}
