package fr.epita.quiz.manager.doujana.services.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.epita.quiz.manager.doujana.datamodel.QuestionType;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;

public class QuestionTypeFileDAO {

	
	
	private File file;

	public QuestionTypeFileDAO(File file) throws IOException {

		if (!file.exists()) {
			file.getAbsoluteFile().getParentFile().mkdirs();
			file.createNewFile(); // todo handle Exception
		}
		this.file = file;
	}

	/**
	 * This is the way to save a question type using that dao Usage :
	 * 
	 * <pre>
	 * <code> QuestionTypeFileDAO dao = ...;
	 * QuestionType questtype = new QuestionType("test");
	 * try{
	 * 	dao.create(questtype);
	 * }catch(CreateFailedException cfe){
	 * 	System.out.println("this question type was not created :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param questtype
	 * @throws CreateFailedException
	 */
	
	public void create(QuestionType questtype) throws CreateFailedException {

		try (PrintWriter writer = new PrintWriter(file)) {
			writer.println(questtype.getTitle());
			writer.flush();

		} catch (FileNotFoundException e) {
			throw new CreateFailedException(questtype, e);
		}

	}

	/**
	 * This is the way to update a question type using that dao Usage :
	 * 
	 * <pre>
	 * <code> QuestionTypeFileDAO dao = ...;
	 * QuestionType questtype = new QuestionType("test");
	 * try{
	 * 	dao.update(questtype);
	 * }catch(Exception cfe){
	 * 	System.out.println("this question type was not updated :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param questtype
	 */
	
	
	public void update(QuestionType questtypet) {

	}
	/**
	 * This is the way to delete a question type using that dao Usage :
	 * 
	 * <pre>
	 * <code> QuestionTypeFileDAO dao = ...;
	 * QuestionType questtype = new QuestionType("test");
	 * try{
	 * 	dao.delete(questtype);
	 * }catch(Exception cfe){
	 * 	System.out.println("this question type was not deleted :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param questtype
	 */
	
	public void delete(QuestionType questtype) {

	}
	/**
	 * This is the way to get a question type by id using that dao Usage :
	 * 
	 * <pre>
	 * <code> QuestionTypeFileDAO dao = ...;
	 * QuestionType questtype = new QuestionType("test");
	 * try{
	 * 	dao.getbyid(questtype);
	 * }catch(Exception cfe){
	 * 	System.out.println("this question type was not found :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param id
	 */
	
	public QuestionType getById(int id) {
		return null;

	}
	
	/**
	 * This is the way to search for a question type using that dao Usage :
	 * 
	 * <pre>
	 * <code> QuestionTypeFileDAO dao = ...;
	 * QuestionType questtype = new QuestionType("test");
	 * try{
	 * 	dao.search(questtype);
	 * }catch(Exception cfe){
	 * 	System.out.println("this question type was not found :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param questtypeCriterion
	 * @return status
	 * @throws SearchFailedException
	 */
	

	public List<QuestionType> search(QuestionType questtypeCriterion) throws SearchFailedException {
		List<QuestionType> resultList = new ArrayList<>();
		try (Scanner scanner = new Scanner(file)) {
			// for each line in the file,
			while (scanner.hasNext()) {
				// read the line
				String line = scanner.nextLine();

				if (line.contains(questtypeCriterion.getTitle())) {
					QuestionType questtype = new QuestionType(line);
					resultList.add(questtype);
				}
			}

		} catch (FileNotFoundException e) {
			throw new SearchFailedException(questtypeCriterion, e);
		}

		// compare to the criterion
		// if it is correct then put it in the result list

		// return the result list

		return resultList;
	}

}
