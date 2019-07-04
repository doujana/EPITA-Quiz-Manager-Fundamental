package fr.epita.quiz.manager.doujana.services.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.quiz.manager.doujana.datamodel.Teacher;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;
import fr.epita.quiz.manager.doujana.services.ConfigurationService;

public class TeacherJDBCDAO {
	private static final String SEARCH_QUERY = "select ID,FIRST_NAME,LAST_NAME from TEACHER  where ID = ? or FIRST_NAME LIKE ?  OR LAST_NAME LIKE ? ";
	private static final String INSERT_QUERY = "INSERT into TEACHER  (FIRST_NAME, LAST_NAME) values(?,?)";
	private static final String UPDATE_QUERY = "UPDATE TEACHER  SET FIRST_NAME =?,LAST_NAME=? WHERE ID = ?";
	private static final String DELETE_QUERY = "DELETE FROM TEACHER   WHERE ID = ?";
	private String url;
	private String password;
	private String username;

	public TeacherJDBCDAO() {
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
	public void create(Teacher teacher) throws CreateFailedException {

		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY);) {
			pstmt.setString(1, teacher.getFirst_name());
			pstmt.setString(2, teacher.getSecond_name());
			pstmt.execute();
		} catch (SQLException sqle) {
			throw new CreateFailedException(teacher);
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
	
	public int createRecord(Teacher teacher) throws CreateFailedException {

		int status = 0;

		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY);) {
			pstmt.setString(1, teacher.getFirst_name());
			pstmt.setString(2, teacher.getSecond_name());

			status = pstmt.executeUpdate();

		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			throw new CreateFailedException(teacher);
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
	
	public int updateRecord(Teacher teacher) {

		int status = 0;
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(UPDATE_QUERY);) {

			pstmt.setString(1, teacher.getFirst_name());
			pstmt.setString(2, teacher.getSecond_name());
			pstmt.setInt(3, teacher.getId());

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
	public void update(Teacher std) {
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(UPDATE_QUERY);) {
			pstmt.setString(1, std.getFirst_name());
			pstmt.setString(2, std.getSecond_name());
			pstmt.setInt(3, std.getId());
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
	public void delete(Teacher std) {
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(DELETE_QUERY);) {
			pstmt.setInt(1, std.getId());
			pstmt.execute();
		} catch (SQLException sqle) {
			// TODO transform into UpdateFailedException
		}
	}

	public Teacher getById(int id) {
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
	public List<Teacher> search(Teacher teacherCriterion) throws SearchFailedException {

		List<Teacher> teacherList = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SEARCH_QUERY)) {

			pstmt.setInt(1, teacherCriterion.getId());
			pstmt.setString(2, "%" + teacherCriterion.getFirst_name() + "%");
			pstmt.setString(3, "%" + teacherCriterion.getSecond_name() + "%");

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("ID");
				String first_name = rs.getString("FIRST_NAME");
				String second_name = rs.getString("LAST_NAME");
				Teacher teacher = new Teacher(first_name,second_name);
				teacher.setId(id);
				teacherList.add(teacher);
			}

			rs.close();
		} catch (SQLException e) {
			throw new SearchFailedException(teacherCriterion);
		}
		return teacherList;
	}

}
