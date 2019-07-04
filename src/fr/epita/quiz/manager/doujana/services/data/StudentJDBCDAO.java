package fr.epita.quiz.manager.doujana.services.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.epita.quiz.manager.doujana.datamodel.QuestionType;
import fr.epita.quiz.manager.doujana.datamodel.Quiz;
import fr.epita.quiz.manager.doujana.datamodel.Student;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;
import fr.epita.quiz.manager.doujana.services.ConfigurationService;

public class StudentJDBCDAO {

	private static final String SEARCH_QUERY = "select ID, NAME from STUDENT where ID = ? or NAME LIKE ?";
	private static final String INSERT_QUERY = "INSERT into STUDENT (name) values(?)";
	private static final String UPDATE_QUERY = "UPDATE STUDENT SET NAME=? WHERE ID = ?";
	private static final String DELETE_QUERY = "DELETE FROM STUDENT  WHERE ID = ?";
	private String url;
	private String password;
	private String username;

	public StudentJDBCDAO() {
		ConfigurationService conf = ConfigurationService.getInstance();
		this.username = conf.getConfigurationValue("db.username", "");
		this.password = conf.getConfigurationValue("db.password", "");
		this.url = conf.getConfigurationValue("db.url", "");

	}

	private Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
		return connection;
	}

	/**
	 * Creates a student in the database, if a problem occurs then it throws an
	 * {@link CreateFailedException} usage example: studentJDBCDAO dao = new ... try{
	 * dao.create(studentInstance); }catch(CreateFailed e){ //log exception }
	 * 
	 * @param student
	 * @throws CreateFailedException
	 */
	public void create(Student student) throws CreateFailedException {

		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY);) {
			pstmt.setString(1, student.getName());
			pstmt.execute();
		} catch (SQLException sqle) {
			throw new CreateFailedException(student);
		}

	}
	/**
	 * Creates a student in the database, if a problem occurs then it throws an
	 * {@link CreateFailedException} usage example: studentJDBCDAO dao = new ... try{
	 * dao.create(studentInstance); }catch(CreateFailed e){ //log exception }
	 * 
	 * @param student
	 * @return status
	 * @throws CreateFailedException
	 */
	
	public int createRecord(Student student) throws CreateFailedException {

		int status = 0;

		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY);) {
			pstmt.setString(1, student.getName());

			status = pstmt.executeUpdate();

		} catch (SQLException sqle) {
			throw new CreateFailedException(student);
		}
		return status;
	}

	
	/**
	 * updates a student in the database, if a problem occurs then it throws an
	 * {@link Exception} usage example: studentJDBCDAO dao = new ... try{
	 * dao.update(studentInstance); }catch(SQLException sqle ){ //log exception }
	 * 
	 * @param student
	 * @return status
	 * @throws Exception
	 */
	
	public int updateRecord(Student student) {

		int status = 0;
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(UPDATE_QUERY);) {

			pstmt.setString(1, student.getName());
			pstmt.setInt(2, student.getId());

			status = pstmt.executeUpdate();

		} catch (SQLException sqle) {
			// TODO transform into UpdateFailedException
		}
		return status;
	}
	/**
	 * updates a student in the database, if a problem occurs then it throws an
	 * {@link Exception} usage example: studentJDBCDAO dao = new ... try{
	 * dao.update(studentInstance); }catch(SQLException sqle ){ //log exception }
	 * 
	 * @param student
	 * @throws Exception
	 */
	public void update(Student std) {
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(UPDATE_QUERY);) {
			pstmt.setString(1, std.getName());
			pstmt.setInt(2, std.getId());
			pstmt.execute();
		} catch (SQLException sqle) {
			// TODO transform into UpdateFailedException
		}
	}
	/**
	 * Delets a student in the database, if a problem occurs then it throws an
	 * {@link Exception} usage example: studentJDBCDAO dao = new ... try{
	 * dao.delete(studentInstance); }catch(SQLException sqle ){ //log exception }
	 * 
	 * @param student
	 * @return status
	 * @throws Exception
	 */
	public void delete(Student std) {
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(DELETE_QUERY);) {
			pstmt.setInt(1, std.getId());
			pstmt.execute();
		} catch (SQLException sqle) {
			// TODO transform into UpdateFailedException
		}
	}

	public Quiz getById(int id) {
		return null;

	}
	/**
	 * Search for a student in the database, if a problem occurs then it throws an
	 * {@link SearchFailedException} usage example: studentJDBCDAO dao = new ... try{
	 * dao.update(studentInstance); }catch(SearchFailedException sqle ){ //log exception }
	 * 
	 * @param student
	 * @return status
	 * @throws SearchFailedException
	 */
	public List<Student> search(Student studentCriterion) throws SearchFailedException {

		List<Student> studentList = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SEARCH_QUERY)) {

			pstmt.setInt(1, studentCriterion.getId());
			pstmt.setString(2, "%" + studentCriterion.getName() + "%");

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("ID");
				String name = rs.getString("NAME");
				Student std = new Student(name);
				std.setId(id);
				studentList.add(std);
			}

			rs.close();
		} catch (SQLException e) {
			throw new SearchFailedException(studentCriterion);
		}
		return studentList;
	}

}
