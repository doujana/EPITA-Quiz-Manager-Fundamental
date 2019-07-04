package fr.epita.quiz.manager.doujana.services.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.epita.quiz.manager.doujana.datamodel.Quiz;
import fr.epita.quiz.manager.doujana.datamodel.StudentAnswer;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;

public class StudentAnswerFileDAO {

	private File file;

	public StudentAnswerFileDAO(File file) throws IOException {

		if (!file.exists()) {
			file.getAbsoluteFile().getParentFile().mkdirs();
			file.createNewFile(); // todo handle Exception
		}
		this.file = file;
	}
	/**
	 * This is the way to save a studentanswer using that dao Usage :
	 * 
	 * <pre>
	 * <code> StudentAnswerFileDAO dao = ...;
	 * StudentAnswer studentanswer = new StudentAnswer("test");
	 * try{
	 * 	dao.create(studentanswer);
	 * }catch(CreateFailedException cfe){
	 * 	System.out.println("this studentanswer was not created :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param studentAnswer
	 * @throws CreateFailedException
	 */
	
	
	public void create(StudentAnswer studentAnswer) throws CreateFailedException {

		try (PrintWriter writer = new PrintWriter(file)) {
			writer.println(studentAnswer.getId());
			writer.println(studentAnswer.getStudentId());
			writer.println(studentAnswer.getValidAnswerId());
			writer.println(studentAnswer.getMcqAnswerId());
			writer.flush();

		} catch (FileNotFoundException e) {
			throw new CreateFailedException(studentAnswer, e);
		}

	}
	/**
	 * This is the way to update a studentanswer using that dao Usage :
	 * 
	 * <pre>
	 * <code> StudentAnswerFileDAO dao = ...;
	 * StudentAnswer studentanswer = new StudentAnswer("test");
	 * try{
	 * 	dao.update(studentanswer);
	 * }catch(Exception cfe){
	 * 	System.out.println("this studentanswer was not updated :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param studentAnswer
	 */
	
	
	public void update(StudentAnswer studentAnswer) {

	}
	/**
	 * This is the way to delete a studentanswer using that dao Usage :
	 * 
	 * <pre>
	 * <code> StudentAnswerFileDAO dao = ...;
	 * StudentAnswer studentanswer = new StudentAnswer("test");
	 * try{
	 * 	dao.delete(studentanswer);
	 * }catch(Exception cfe){
	 * 	System.out.println("this studentanswer was not delete :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param studentAnswer
	 */
	
	public void delete(StudentAnswer studentAnswer) {

	}
	/**
	 * Find a studentanswer record by the id 
	 * 
	 * @param id
	 * @return
	 */
	
	public StudentAnswer getById(int id) {
		return null;
	}
	/**
	 * This is the way to search for a studentanswer using that dao Usage :
	 * 
	 * <pre>
	 * <code> StudentAnswerFileDAO dao = ...;
	 * StudentAnswer studentanswer = new StudentAnswer("test");
	 * try{
	 * 	dao.search(studentanswer);
	 * }catch(SearchFailedException cfe){
	 * 	System.out.println("this studentanswer was not found :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param studentAnswer
	 * @throws SearchFailedException
	 */
	
	public List<StudentAnswer> search(StudentAnswer studentAnswerCriterion) throws SearchFailedException {
		
		List<StudentAnswer> resultList = new ArrayList<>();
		
		try (Scanner scanner = new Scanner(file)) {
			// for each line in the file,
			while (scanner.hasNext()) {
				// read the line
				String line = scanner.nextLine();
				Integer filterText = studentAnswerCriterion.getId();
				
				if (line.contains(filterText.toString())) {
					StudentAnswer studentAnswer = new StudentAnswer(line);
					resultList.add(studentAnswer);
				}
			}

		} catch (FileNotFoundException e) {
			throw new SearchFailedException(studentAnswerCriterion, e);
		}

		// compare to the criterion
		// if it is correct then put it in the result list

		// return the result list

		return resultList;
	}
}
