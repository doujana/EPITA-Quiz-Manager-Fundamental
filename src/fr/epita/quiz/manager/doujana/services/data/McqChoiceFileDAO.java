package fr.epita.quiz.manager.doujana.services.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.epita.quiz.manager.doujana.datamodel.McqChoice;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;

public class McqChoiceFileDAO {

	private File file;

	public McqChoiceFileDAO(File file) throws IOException {

		if (!file.exists()) {
			file.getAbsoluteFile().getParentFile().mkdirs();
			file.createNewFile(); // todo handle Exception
		}
		this.file = file;
	}

	/**
	 * This is the way to save a mcqchoice using that dao Usage :
	 * 
	 * <pre></pre>
	 * <code> McqchoiceFileDAO dao = ...;
	 * Mcqchoice mcqchioce = new Mcqchoice("choice1");
	 * try{
	 * 	dao.create(mcqchoice);
	 * }catch(CreateFailedException cfe){
	 * 	System.out.println("this mcqchoice was not created :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param mcqchoice
	 * @throws CreateFailedException
	 */
	public void create(McqChoice mcqChoice) throws CreateFailedException {

		try (PrintWriter writer = new PrintWriter(file)) {
			writer.println(mcqChoice.getTitle());
			writer.println(mcqChoice.getQuestionId());
			writer.flush();

		} catch (FileNotFoundException e) {
			throw new CreateFailedException(mcqChoice, e);
		}
	}
	/**
	 * This is the way to update a mcqchoice using that dao Usage :
	 * 
	 * <pre></pre>
	 * <code> McqchoiceFileDAO dao = ...;
	 * Mcqchoice mcqchioce = new Mcqchoice("choice1");
	 * try{
	 * 	dao.update(mcqchoice);
	 * }catch(SqlException cfe){
	 * 	System.out.println("this mcqchoice was not updated :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param mcqchoice
	 */
	public void update(McqChoice mcqChoice) {
		
	}
	
	/**
	 * This is the way to delete a mcqchoice using that dao Usage :
	 * 
	 * <pre></pre>
	 * <code> McqchoiceFileDAO dao = ...;
	 * Mcqchoice mcqchioce = new Mcqchoice("choice1");
	 * try{
	 * 	dao.delete(mcqchoice);
	 * }catch(Exception cfe){
	 * 	System.out.println("this mcqchoice was not deleted :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param mcqchoice
	 */
	public void delete(McqChoice mcqChoice) {
		
	}
	
	/**
	 * This is the way to get by id a mcqchoice using that dao Usage :
	 * 
	 * <pre></pre>
	 * <code> McqchoiceFileDAO dao = ...;
	 * Mcqchoice mcqchioce = new Mcqchoice("choice1");
	 * try{
	 * 	dao.getbyid(mcqchoice);
	 * }catch(Exception cfe){
	 * 	System.out.println("this mcqchoice was not found :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param mcqchoice
	 
	 */
	public McqChoice getById(int id) {
		return null;

	}
	
	/**
	 * This is the way to get by id a mcqchoice using that dao Usage :
	 * 
	 * <pre></pre>
	 * <code> McqchoiceFileDAO dao = ...;
	 * Mcqchoice mcqchioce = new Mcqchoice("choice1");
	 * try{
	 * 	dao.search(mcqchoice);
	 * }catch(Exception cfe){
	 * 	System.out.println("this mcqchoice was not found :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param mcqChoiceCriterion
	 * @throws SearchFailedException
	 
	 */

	public List<McqChoice> search(McqChoice mcqChoiceCriterion) throws SearchFailedException {
		List<McqChoice> resultList = new ArrayList<>();
		try (Scanner scanner = new Scanner(file)) {
			// for each line in the file,
			while (scanner.hasNext()) {
				// read the line
				String line = scanner.nextLine();

				if (line.contains(mcqChoiceCriterion.getTitle())) {
					McqChoice mcqChoice = new McqChoice(line);
					resultList.add(mcqChoice);
				}
			}

		} catch (FileNotFoundException e) {
			throw new SearchFailedException(mcqChoiceCriterion, e);
		}

		// compare to the criterion
		// if it is correct then put it in the result list

		// return the result list

		return resultList;
	}
}
