package fr.epita.quiz.manager.doujana.services.data;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import fr.epita.quiz.manager.doujana.datamodel.StudentAnswer;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;
import fr.epita.quiz.manager.doujana.services.ConfigurationService;

public class StudentAnswerJDBCDAO {

	private static final String SEARCH_QUERY = "SELECT id, student_id, valid_answer_id, mscq_answer_id FROM StudentAnswer WHERE id = ? OR student_id = ?";
	private static final String INSERT_QUERY = "INSERT INTO StudentAnswer (student_id, valid_answer_id, mscq_answer_id) VALUES(?, ?, ?)";
	private static final String UPDATE_QUERY = "UPDATE StudentAnswer SET student_id=?, valid_answer_id=?, mscq_answer_id=? WHERE id = ?";
	private static final String DELETE_QUERY = "DELETE FROM StudentAnswer  WHERE id = ?";
	private String url;
	private String password;
	private String username;

	public StudentAnswerJDBCDAO() {

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
	 * create  a studentanswer from the database, if a problem occurs then it throws an
	 * {@link CreateFailedException} usage example: studentanswerJDBCDAO dao = new ... try{
	 * dao.delete(studentanswerInstance); }catch(CreateFailedException e){ //log exception }
	 * 
	 * @param studentAnswerCriterion
	 * @throws CreateFailedException
	 */
	public int create(StudentAnswer studentAnswer) throws CreateFailedException {

		int status = 0;
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY);) {

			pstmt.setInt(1, studentAnswer.getStudentId());
			pstmt.setInt(2, studentAnswer.getValidAnswerId());
			pstmt.setInt(3, studentAnswer.getMcqAnswerId());

			status = pstmt.executeUpdate();

		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
			throw new CreateFailedException(studentAnswer);
		}
		return status;

	}
	/**
	 * Update  a studentanswer from the database, if a problem occurs then it throws an
	 * {@link Exception} usage example: studentanswerJDBCDAO dao = new ... try{
	 * dao.update(studentanswerInstance); }catch(SQLException e){ //log exception }
	 * 
	 * @param studentAnswerCriterion
	 * @throws Exception
	 */

	public int update(StudentAnswer studentAnswer) {

		int status = 0;
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(UPDATE_QUERY);) {

			pstmt.setInt(1, studentAnswer.getStudentId());
			pstmt.setInt(2, studentAnswer.getValidAnswerId());
			pstmt.setInt(3, studentAnswer.getMcqAnswerId());
			pstmt.setInt(4, studentAnswer.getId());

			status = pstmt.executeUpdate();

		} catch (SQLException sqle) {
			// TODO transform into UpdateFailedException
		}
		return status;
	}
	/**
	 * Delete  a studentanswer from the database, if a problem occurs then it throws an
	 * {@link Exception} usage example: studentanswerJDBCDAO dao = new ... try{
	 * dao.delete(studentanswerInstance); }catch(SQLException e){ //log exception }
	 * 
	 * @param studentAnswerCriterion
	 * @throws Exception
	 */
	

	public void delete(StudentAnswer studentAnswer) {
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(DELETE_QUERY);) {

			pstmt.setInt(1, studentAnswer.getId());
			pstmt.execute();

		} catch (SQLException sqle) {
			// TODO transform into UpdateFailedException
		}
	}

	public StudentAnswer getById(int id) {
		return null;
	}
	/**
	 * searches for a studentanswer in the database, if a problem occurs then it throws an
	 * {@link SearchFailedException} usage example: studentanswerJDBCDAO dao = new ... try{
	 * dao.search(studentanswerInstance); }catch(SearchFailedException e){ //log exception }
	 * 
	 * @param studentAnswerCriterion
	 * @throws SearchFailedException
	 */
	
	public List<StudentAnswer> search(StudentAnswer studentAnswerCriterion) throws SearchFailedException {

		List<StudentAnswer> studentAnswerList = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SEARCH_QUERY)) {

			pstmt.setInt(1, studentAnswerCriterion.getId());
			pstmt.setInt(2, studentAnswerCriterion.getStudentId());
			// pstmt.setString(2, "%" + quizCriterion.getTitle() + "%");

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				StudentAnswer studentAnswer = new StudentAnswer();
				studentAnswer.setStudentId(rs.getInt("id"));
				studentAnswer.setStudentId(rs.getInt("student_id"));
				studentAnswer.setStudentId(rs.getInt("mscq_answer_id"));
				studentAnswer.setStudentId(rs.getInt("valid_answer_id"));

				studentAnswerList.add(studentAnswer);
			}

			rs.close();
		} catch (SQLException e) {
			throw new SearchFailedException(studentAnswerCriterion);
		}
		return studentAnswerList;
	}

}
