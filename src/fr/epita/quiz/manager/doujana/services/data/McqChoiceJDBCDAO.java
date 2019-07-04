package fr.epita.quiz.manager.doujana.services.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.quiz.manager.doujana.datamodel.McqChoice;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;
import fr.epita.quiz.manager.doujana.services.ConfigurationService;

public class McqChoiceJDBCDAO {
	
	private static final String SEARCH_QUERY = "SELECT id, title, question_id FROM mcqchoise WHERE id = ? or title LIKE ?";
	private static final String INSERT_QUERY = "INSERT into mcqchoise (title, question_id) VALUES (?, ?)";
	private static final String UPDATE_QUERY = "UPDATE mcqchoise SET title = ?, question_id = ? WHERE id = ?";
	private static final String DELETE_QUERY = "DELETE FROM mcqchoise  WHERE id = ?";
	private static final String SEARCH_QUERY_BY_TITLE = "SELECT id FROM mcqanswer WHERE id = ?";
	
	private String url;
	private String password;
	private String username;

	public McqChoiceJDBCDAO() {
		
		ConfigurationService conf = ConfigurationService.getInstance();
		
		this.username = conf.getConfigurationValue("db.username", "");
		this.password = conf.getConfigurationValue("db.password", "");
		this.url = conf.getConfigurationValue("db.url", "");
		

	}

	private Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(this.url, this.username, this.password);
		return connection;
	}
	
	/***
	 * Create a mcqchoice in the data base if an error occurs it throws a
	 * {@link CreateFailedException}usage example: mcqchoiceJDBCDAO mcqdao = new ... try{
	 * mcqdao.create(mcqchoiceInstance); }catch(CreateFailed e){ //log exception }
	 * @param mcqChoice
	 * @throws CreateFailedException
	 */
	public void create(McqChoice mcqChoice) throws CreateFailedException {
		
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY);) {
			pstmt.setString(1, mcqChoice.getTitle());
			pstmt.setInt(2, mcqChoice.getQuestionId());
			
			pstmt.execute();
			
		} catch (SQLException sqle) {
			throw new CreateFailedException(mcqChoice);
		}
	}
	
	/**
	 * update mcqchoice  in the database if an error occurs it throws a
	 * {@link Exception } usage example:mcqchoiceJDBCDAO mcqdao= new ...try{
	 * mcqdao.update(mcqdaoInstance)}catch(SQLException sqle){//log exception}
	 * @param mcqChoice
	 */
	public void update(McqChoice mcqChoice) {
		
		try (Connection connection = getConnection();
			
			PreparedStatement pstmt = connection.prepareStatement(UPDATE_QUERY);) {
			pstmt.setString(1, mcqChoice.getTitle());
			pstmt.setInt(2, mcqChoice.getQuestionId());
			pstmt.setInt(3, mcqChoice.getId());
			
			pstmt.execute();
			
		} catch (SQLException sqle) {
			
		}
	}
	
	/**
	 * update mcqchoice  in the database if an error occurs it throws a
	 * {@link Exception } usage example:mcqchoiceJDBCDAO mcqdao= new ...try{
	 * mcqdao.update(mcqdaoInstance)}catch(SQLException sqle){//log exception}
	 * @param mcqChoice
	 */
	public int updateRecord(McqChoice mcqChoice) throws CreateFailedException {
		
		int status = 0;
		try (Connection connection = getConnection();
			
			PreparedStatement pstmt = connection.prepareStatement(UPDATE_QUERY);) {
			pstmt.setString(1, mcqChoice.getTitle());
			pstmt.setInt(2, mcqChoice.getQuestionId());
			pstmt.setInt(3, mcqChoice.getId());
			
			status = pstmt.executeUpdate();
			
		} catch (SQLException sqle) {
			
		}
		return status;
	}
	
	/**
	 * Delete mcqchoice from the database if an error occurs then it will through an
	 * {@link Exception} usage example:mcqchoiceJDBCDAO mcqdao = new ...try{
	 * mcqdao.delete(mcqdaoInstance)}catch(SQLException sqle){//log exception}
	 * @param mcqChoice
	 */
	public void delete(McqChoice mcqChoice) {
		
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(DELETE_QUERY);) {
			
			pstmt.setInt(1, mcqChoice.getId());
			pstmt.execute();
			
		} catch (SQLException sqle) {
			// TODO transform into UpdateFailedException
		}
	}

	/**
	 * get mcqchoice from the database by id  if an error occurs then it will through an
	 * {@link Exception} usage example:mcqchoiceJDBCDAO mcqdao = new ...try{
	 * mcqdao.getbyid(mcqdaoInstance)}catch(SQLException sqle){//log exception}
	 * @param mcqChoice
	 */
	public McqChoice getById(int id) {
		return null;

	}

	/**
	 * Search mcqchoice from the database if an error occurs then it will through an
	 * {@link SearchFailedException} usage example:mcqchoiceJDBCDAO mcqdao = new ...try{
	 * mcqdao.search(mcqdaoInstance)}catch(SQLException sqle){//log exception}
	 * @param mcqChoice
	 * @throws SearchFailedException
	 */
	public List<McqChoice> search(McqChoice mcqChoiceCriterion) throws SearchFailedException {

		List<McqChoice> mcqChoiceList = new ArrayList<>();
		
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SEARCH_QUERY)) {

			pstmt.setInt(1, mcqChoiceCriterion.getId());
			pstmt.setString(2, "%" + mcqChoiceCriterion.getTitle() + "%");

			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				int id = rs.getInt("id");
				String title = rs.getString("title");
				int questionId = rs.getInt("question_id");
				
				McqChoice mcqChoice = new McqChoice(id, title, questionId);
				mcqChoiceList.add(mcqChoice);
			}

			rs.close();
			
		} catch (SQLException e) {
			throw new SearchFailedException(mcqChoiceCriterion);
		}
		return mcqChoiceList;
	}

	public int getMCQChoiceAnswerByMCQTitle(String title) {

		int id = 0;
		try (Connection connection = getConnection();
				PreparedStatement ps = connection.prepareStatement(SEARCH_QUERY_BY_TITLE)) {
			
			ps.setString(1, title);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				id = rs.getInt("id");
			}
			rs.close();
			
		} catch (SQLException e) {
			System.out.println("Error getting the MCQ Answer id");
		}

		return id;
	}
}
