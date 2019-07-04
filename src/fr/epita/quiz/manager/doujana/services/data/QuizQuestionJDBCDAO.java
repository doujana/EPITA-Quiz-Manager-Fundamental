package fr.epita.quiz.manager.doujana.services.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.quiz.manager.doujana.datamodel.QuestionType;
import fr.epita.quiz.manager.doujana.datamodel.QuizQuestion;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;
import fr.epita.quiz.manager.doujana.services.ConfigurationService;

public class QuizQuestionJDBCDAO {
	private static final String SEARCH_QUERY = "select ID,question_id,quiz_id from QUIZQUESTION  where ID = ? or question_id=?,quiz_id=?";
	private static final String INSERT_QUERY = "INSERT into QUIZQUESTION  (question_id,quiz_id) values(?,?)";
	private static final String UPDATE_QUERY = "UPDATE QUIZQUESTION   SET question_id=?,quiz_id=? WHERE ID = ?";
	private static final String DELETE_QUERY = "DELETE FROM QUIZQUESTION    WHERE ID = ?";
	private String url;
	private String password;
	private String username;
	
	public QuizQuestionJDBCDAO()
	{
		ConfigurationService conf = ConfigurationService.getInstance();
		this.username = conf.getConfigurationValue("db.username", "");
		this.password = conf.getConfigurationValue("db.password", "");
		this.url = conf.getConfigurationValue("db.url", "");
		

	}
	private Connection getConnection() throws SQLException
	{
		Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
		return connection;
	}
	
	
	/**
	 * Creates a QuizQuestion in the database, if a problem occurs then it throws an
	 * {@link CreateFailedException} usage example: QuizQuestionJDBCDAO dao = new ... try{
	 * dao.create(QuizQuestionInstance); }catch(CreateFailed e){ //log exception }
	 * 
	 * @param quizquestion
	 * @throws CreateFailedException
	 */
	
	public void create(QuizQuestion quizquestion ) throws CreateFailedException{
		

		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY);) {
			pstmt.setInt(1,quizquestion.getQuestion_id());
			pstmt.setInt(2,quizquestion.getQuiz_id());
			pstmt.execute();
		} catch (SQLException sqle) {
			System.out.print("the error is"+sqle.getMessage());
			throw new CreateFailedException(quizquestion);
	
		}
	}
	
	/**
	 * <b>another function to create/insert a record in the database</b>
	 * Creates a quizquestion in the database, if a problem occurs then it throws an
	 * {@link CreateFailedException} usage example: quizquestionJDBCDAO dao = new ... try{
	 * dao.create(quizquestionInstance); }catch(CreateFailed e){ //log exception }
	 * 
	 * @param quizquestion
	 * @throws CreateFailedException
	 */
	
	
	public boolean createRecord(QuizQuestion quizquestion)throws CreateFailedException{
		
		boolean status = false;
		
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY);) {
			pstmt.setInt(1,quizquestion.getQuestion_id());
			pstmt.setInt(2,quizquestion.getQuiz_id());
			
			
			status = pstmt.execute();
			
		} catch (SQLException sqle) {
			throw new CreateFailedException(quizquestion);
		}
		return status;
	}
	/**
	 * updates a quizquestion in the database, if a problem occurs then it throws an
	 * {@link Exception} usage example: quizquestionJDBCDAO dao = new ... try{
	 * dao.update(quizquestionInstance); }catch(SQLException sqle){ //log exception }
	 * 
	 * @param questiontype
	 * @throws SQLException
	 */
	public void update(QuizQuestion quizquestion) {
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(UPDATE_QUERY);) {
			pstmt.setInt(1,quizquestion.getQuestion_id());
			pstmt.setInt(2, quizquestion.getQuiz_id());
			pstmt.setInt(3,quizquestion.getId());
			pstmt.execute();
		} catch (SQLException sqle) {
			// TODO transform into UpdateFailedException
		}
	}
	/**
	 * 
	 * updates a quizquestion in the database, if a problem occurs then it throws an
	 * {@link Exception} usage example: quizquestionJDBCDAO dao = new ... try{
	 * dao.update(quizquestionInstance); }catch(SQLException sqle){ //log exception }
	 * 
	 * @param quizquestion
	 * @throws SQLException
	 */
	public int updateRecord(QuizQuestion quizquestion) {
		
		int status = 0;
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(UPDATE_QUERY);) {
			
			pstmt.setInt(1,quizquestion.getQuestion_id());
			pstmt.setInt(2, quizquestion.getQuiz_id());
			pstmt.setInt(3,quizquestion.getId());
			
			status = pstmt.executeUpdate();
			
		} catch (SQLException sqle) {
//
		}
		return status;
	}
	/**
	 * Delete a quizquestion in the database, if a problem occurs then it throws an
	 * {@link Exception} usage example: quizquestionJDBCDAO dao = new ... try{
	 * dao.delete(quizquestionInstance); }catch(SQLException sqle){ //log exception }
	 * 
	 * @param quizquestion
	 * @throws SQLException
	 */
	public void delete(QuizQuestion quizquestion) {
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(DELETE_QUERY);) {
			pstmt.setInt(1, quizquestion.getId());
			pstmt.execute();
		} catch (SQLException sqle) {
		System.out.printf("Delete error happend",sqle.getMessage());
		}
	}
	/**
	 * get a quizquestion in the database, if a problem occurs then it throws an
	 * {@link Exception} usage example: quizquestionJDBCDAO dao = new ... try{
	 * dao.getbyid(quizquestionInstance); }catch(SQLException sqle){ //log exception }
	 * 
	 * @param id
	 * @throws SQLException
	 */
	
	public QuestionType getById(int id) {
		return null;

	}
	/**
	 * Search for a quizquestion in the database, if a problem occurs then it throws an
	 * {@link SearchFailedException} usage example: quizquestionJDBCDAO dao = new ... try{
	 * dao.search(quizquestionInstance); }catch(SQLException sqle){ //log exception }
	 * 
	 * @param quizquestionCriterion
	 * @throws SearchFailedException
	 */
	public List<QuizQuestion>search(QuizQuestion quizquestionCriterion) throws SearchFailedException {

		List<QuizQuestion> quizquestionList = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SEARCH_QUERY)) {

			pstmt.setInt(1, quizquestionCriterion.getId());
			pstmt.setInt(2,  quizquestionCriterion.getQuestion_id());
			pstmt.setInt(3, quizquestionCriterion.getQuiz_id());

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				
				QuizQuestion quizquestion = new QuizQuestion();
				quizquestion.setQuestion_id(rs.getInt("question_id"));
				quizquestion.setId(rs.getInt("id"));
				quizquestion.setQuiz_id(rs.getInt("quiz id"));
				
				quizquestionList.add(quizquestion);
			}

			rs.close();
		} catch (SQLException e) {
			throw new SearchFailedException(quizquestionCriterion);
		}
		return quizquestionList;
	}



}
