package fr.epita.quiz.manager.doujana.services.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import fr.epita.quiz.manager.doujana.datamodel.Topic;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;

public class TopicFileDAO {
	private File file;

	public TopicFileDAO(File file) throws IOException {

		if (!file.exists()) {
			file.getAbsoluteFile().getParentFile().mkdirs();
			file.createNewFile(); // todo handle Exception
		}
		this.file = file;
	}
	/**
	 * This is the way to create a topic using that dao Usage :
	 * 
	 * <pre>
	 * <code> TopicFileDAO dao = ...;
	 * Topic topic = new Topic("test");
	 * try{
	 * 	dao.create(topic);
	 * }catch(CreateFailedException cfe){
	 * 	System.out.println("this topic was not created :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param topic
	 * @throws CreateFailedException
	 */
	
	public void create(Topic topic) throws CreateFailedException {

		try (PrintWriter writer = new PrintWriter(file)) {
			writer.println(topic.getName());
			
			writer.flush();

		} catch (FileNotFoundException e) {
			throw new CreateFailedException(topic, e);
		}
	}
	/**
	 * This is the way to update a topic using that dao Usage :
	 * 
	 * <pre>
	 * <code> TopicFileDAO dao = ...;
	 * Topic topic = new Topic("programming");
	 * try{
	 * 	dao.update(topic);
	 * }catch(Exception cfe){
	 * 	System.out.println("this topic was not updated :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param topic
	 */
public void update(Topic topic) {
		
	}
/**
 * This is the way to delete a topic using that dao Usage :
 * 
 * <pre>
 * <code> TopicFileDAO dao = ...;
 * Topic topic = new topic("test");
 * try{
 * 	dao.delete(topic);
 * }catch(Exception cfe){
 * 	System.out.println("this student was not deleted :" + cfe.getInstance());
 * }
 * </code>
 * </pre>
 * 
 * @param topic
 */
	public void delete(Topic topic) {
		
	}
	
	/**
	 * this method is to get the topic by id
	 * 
	 * @param id
	 * @return
	 */
	public Topic getById(int id) {
		return null;

	}
	
	/**
	 * This is the way to save a student using that dao Usage :
	 * 
	 * <pre>
	 * <code> TopicFileDAO dao = ...;
	 * Topic topic = new Topic("test");
	 * try{
	 * 	dao.search(topic);
	 * }catch(SearchFailedException cfe){
	 * 	System.out.println("the search process for the  topic was not successful :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param topicCriterion
	 * @throws SearchFailedException
	 */
	public List<Topic> search(Topic topicCriterion) throws SearchFailedException {
		List<Topic> resultList = new ArrayList<>();
		try (Scanner scanner = new Scanner(file)) {
			// for each line in the file,
			while (scanner.hasNext()) {
				// read the line
				String line = scanner.nextLine();

				if (line.contains(topicCriterion.getName())) {
					Topic topic = new Topic(line);
					resultList.add(topic);
				}
			}

		} catch (FileNotFoundException e) {
			throw new SearchFailedException(topicCriterion, e);
		}

		// compare to the criterion
		// if it is correct then put it in the result list

		// return the result list

		return resultList;
	}
	
}
