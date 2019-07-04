package fr.epita.quiz.manager.doujana.services.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.epita.quiz.manager.doujana.datamodel.Answer;
import fr.epita.quiz.manager.doujana.datamodel.McqAnswer;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;

public class McqAnswerFileDAO {
	
	private File file;

	public McqAnswerFileDAO(File file) throws IOException {

		if (!file.exists()) {
			file.getAbsoluteFile().getParentFile().mkdirs();
			file.createNewFile(); // todo handle Exception
		}
		this.file = file;
	}

	/**
	 * This is the way to save a mcqanswer using that dao Usage :
	 * 
	 * <pre></pre>
	 * <code> McqanswerFileDAO dao = ...;
	 * Mcqanswer mcqanswer = new Mcqanswer("test");
	 * try{
	 * 	dao.create(mcqanswer);
	 * }catch(CreateFailedException cfe){
	 * 	System.out.println("this mcqanswer was not created :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param mcqanswer
	 * @throws CreateFailedException
	 */
	public void create(McqAnswer mcqanswer) throws CreateFailedException {

		try (PrintWriter writer = new PrintWriter(file)) {
			writer.println(mcqanswer.getTitle());
			writer.flush();

		} catch (FileNotFoundException e) {
			throw new CreateFailedException(mcqanswer, e);
		}

	}
	/**
	 * This is the way to update a mcqanswer using that dao Usage :
	 * 
	 * <pre></pre>
	 * <code> McqanswerFileDAO dao = ...;
	 * Mcqanswer mcqanswer = new Mcqanswer("test");
	 * try{
	 * 	dao.update(mcqanswer);
	 * }catch(CreateFailedException cfe){
	 * 	System.out.println("this mcqanswer was not updated :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param mcqanswer
	 */
	
	public void update(McqAnswer mcqanswer) {

	}

	/**
	 * This is the way to delete a mcqanswer using that dao Usage :
	 * 
	 * <pre></pre>
	 * <code> McqanswerFileDAO dao = ...;
	 * Mcqanswer mcqanswer = new Mcqanswer("test");
	 * try{
	 * 	dao.delete(mcqanswer);
	 * }catch(CreateFailedException cfe){
	 * 	System.out.println("this mcqanswer was not deleted :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param mcqanswer
	 * @throws CreateFailedException
	 */
	public void delete(McqAnswer mcqanswer) {

	}
	/**
	 * This is the way to get the mcqanswer by id using that dao Usage :
	 * 
	 * <pre></pre>
	 * <code> McqanswerFileDAO dao = ...;
	 * Mcqanswer mcqanswer = new Mcqanswer("test");
	 * try{
	 * 	dao.get by id(mcqanswer);
	 * }catch(CreateFailedException cfe){
	 * 	System.out.println("this mcqanswer was not found :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param mcqanswer
	 * @throws CreateFailedException
	 */
	public McqAnswer getById(int id) {
		return null;

	}
/**
 * Searches for a mcqanswer in the database and through a{@link SearchFailedException}
 * @param mcqanswerCriterion
 * @return
 * @throws SearchFailedException
 */
	public List<McqAnswer> search(McqAnswer mcqanswerCriterion) throws SearchFailedException {
		List<McqAnswer> resultList = new ArrayList<>();
		try (Scanner scanner = new Scanner(file)) {
			// for each line in the file,
			while (scanner.hasNext()) {
				// read the line
				String line = scanner.nextLine();

				if (line.contains(mcqanswerCriterion.getTitle())) {
					McqAnswer mcqanswer = new McqAnswer(line);
					resultList.add(mcqanswer);
				}
			}

		} catch (FileNotFoundException e) {
			throw new SearchFailedException(mcqanswerCriterion, e);
		}

		// compare to the criterion
		// if it is correct then put it in the result list

		// return the result list

		return resultList;
	}


}
