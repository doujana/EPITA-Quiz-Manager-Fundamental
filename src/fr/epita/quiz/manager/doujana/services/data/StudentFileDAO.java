package fr.epita.quiz.manager.doujana.services.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.epita.quiz.manager.doujana.datamodel.Student;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;

public class StudentFileDAO {

	private File file;

	public StudentFileDAO(File file) throws IOException {

		if (!file.exists()) {
			file.getAbsoluteFile().getParentFile().mkdirs();
			file.createNewFile(); // todo handle Exception
		}
		this.file = file;
	}

	/**
	 * This is the way to save a student using that dao Usage :
	 * 
	 * <pre>
	 * <code> StudentFileDAO dao = ...;
	 * Student student = new Student("test");
	 * try{
	 * 	dao.create(student);
	 * }catch(CreateFailedException cfe){
	 * 	System.out.println("this student was not created :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param student
	 * @throws CreateFailedException
	 */
	
	public void create(Student student) throws CreateFailedException {

		try (PrintWriter writer = new PrintWriter(file)) {
			writer.println(student.getName());
			writer.flush();

		} catch (FileNotFoundException e) {
			throw new CreateFailedException(student, e);
		}

	}
	/**
	 * This is the way to update a student using that dao Usage :
	 * 
	 * <pre>
	 * <code> StudentFileDAO dao = ...;
	 * Student student = new Student("test");
	 * try{
	 * 	dao.create(student);
	 * }catch(CreateFailedException cfe){
	 * 	System.out.println("this student was not created :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param student
	 * @throws CreateFailedException
	 */
	public void update(Student student) {

	}
	/**
	 * This is the way to delete a student using that dao Usage :
	 * 
	 * <pre>
	 * <code> StudentFileDAO dao = ...;
	 * Student student = new Student("test");
	 * try{
	 * 	dao.delete(student);
	 * }catch(Exception cfe){
	 * 	System.out.println("this student was not deleted :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param student
	 */

	public void delete(Student student) {

	}
	/**
	 * This is the way to get a student by id using that dao Usage :
	 * 
	 * <pre>
	 * <code> StudentFileDAO dao = ...;
	 * Student student = new Student("test");
	 * try{
	 * 	dao.getbyid(student);
	 * }catch(Exception cfe){
	 * 	System.out.println("this student was not found :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param id
	 */

	public Student getById(int id) {
		return null;

	}
	/**
	 * This is the way to search for a student using that dao Usage :
	 * 
	 * <pre>
	 * <code> StudentFileDAO dao = ...;
	 * Student student = new Student("test");
	 * try{
	 * 	dao.search(student);
	 * }catch(SearchFailedException cfe){
	 * 	System.out.println("this student was not found :" + cfe.getInstance());
	 * }
	 * </code>
	 * </pre>
	 * 
	 * @param studCriterion
	 * @throws SearchFailedException
	 */

	public List<Student> search(Student studCriterion) throws SearchFailedException {
		List<Student> resultList = new ArrayList<>();
		try (Scanner scanner = new Scanner(file)) {
			// for each line in the file,
			while (scanner.hasNext()) {
				// read the line
				String line = scanner.nextLine();

				if (line.contains(studCriterion.getName())) {
					Student student = new Student(line);
					resultList.add(student);
				}
			}

		} catch (FileNotFoundException e) {
			throw new SearchFailedException(studCriterion, e);
		}

		// compare to the criterion
		// if it is correct then put it in the result list

		// return the result list

		return resultList;
	}

}
