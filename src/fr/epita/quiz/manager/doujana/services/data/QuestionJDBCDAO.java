package fr.epita.quiz.manager.doujana.services.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.SliderUI;

import fr.epita.quiz.manager.doujana.datamodel.Question;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;
import fr.epita.quiz.manager.doujana.services.ConfigurationService;

public class QuestionJDBCDAO {

	
	private static final String SEARCH_QUERY = "select ID, TITLE,QUESTION_TYPE_ID , topic_id from QUESTION where ID = ? or TITLE LIKE ? or QUESTION_TYPE_ID=? or topic_id=?";
	private static final String SEARCH_QUERY_BYTOPIC_ID = "select ID, TITLE,QUESTION_TYPE_ID , topic_id from QUESTION where topic_id=?";
	private static final String INSERT_QUERY = "INSERT into QUESTION (title, question_type_id, topic_id, difficulty_level ) VALUES (?, ?, ?, ?)";
	private static final String UPDATE_QUERY = "UPDATE QUESTION SET title=?, question_type_id=?, topic_id=?, difficulty_level=? WHERE ID = ?";
	private static final String DELETE_QUERY = "DELETE FROM question  WHERE id = ?";
	private String url;
	private String password;
	private String username;

	public QuestionJDBCDAO() {
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
	 * Creates a question in the database, if a problem occurs then it throws an
	 * {@link CreateFailedException} usage example: QuestionJDBCDAO dao = new ... try{
	 * dao.create(questionInstance); }catch(CreateFailed e){ //log exception }
	 * 
	 * @param question
	 * @throws CreateFailedException
	 */
	public void create(Question question) throws CreateFailedException {
      
		try (Connection connection = getConnection();
			
			PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY);) {
			pstmt.setString(1, question.getTitle());
			pstmt.setInt(2, question.getQuestion_type_id());
			pstmt.setInt(3,question.getTopic_id());
			pstmt.setInt(4, question.getDifficulty());
			pstmt.execute();
			
		} catch (SQLException sqle) {
			sqle.getMessage();
			throw new CreateFailedException(question);
		}

	}

	
	/**
	 * Updates a question in the database, if a problem occurs then it throws an
	 * {@link  CreateFailedException} usage example: mcqanswerJDBCDAO mcqdao = new ... try{
	 * mcqdao.update(questionInstance); }catch(createFailed e){//log exception}
	 * @param question
	 * @throws CreateFailedException
	 */
	
public int updateRecord(Question question) throws CreateFailedException {
		
		int status = 0;
		try (Connection connection = getConnection();
			
			PreparedStatement pstmt = connection.prepareStatement(UPDATE_QUERY);) {
			pstmt.setString(1, question.getTitle());
			pstmt.setInt(2, question.getQuestion_type_id());//need to change it to float having mistake in here
			pstmt.setInt(3, question.getTopic_id());
			pstmt.setInt(4, question.getId());
			pstmt.setInt(5, question.getDifficulty());
			
			status = pstmt.executeUpdate();
			
		} catch (SQLException sqle){
			
		}
		return status;
	}
/**
 * Deletes a question from the database if a problem occurs it throws an 
 * {@link exception } usage example: questionJDBCDAO qdao= new .....try{
 * qdao.delete(questionInstance);} Catch(SQLException sqle){//log exception}
 * @param question
 * @return status //which resembles if the delete successfully done or not 
 * and that depending on status value and it will print an error message.
 */
	public int delete(Question question) {
		
		int status = 0;
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(DELETE_QUERY);) {
			
			pstmt.setInt(1, question.getId());
			status = pstmt.executeUpdate();
			
		} catch (SQLException sqle) {
			System.out.println("Delete Error! " + sqle.getMessage() );
		}
		return status;
	}

	public Question getById(int id) {
		return null;

	}
	/**
	 * Searches for a question from the database if an error occurs it throws an
	 * {@link SearchFailedException} usage:questionJDBCDAO qdao= new ....try{
	 * qdao.search(questionInstance;)} catch(SearchFailedException e){// log exception}
	 * @param questionCriterion
	 * @return
	 * @throws SearchFailedException
	 */
	public List<Question> search(Question questionCriterion) throws SearchFailedException {

		List<Question> questionList = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SEARCH_QUERY)) {

			pstmt.setInt(1, questionCriterion.getId());
			pstmt.setString(2, "%" + questionCriterion.getTitle() + "%");
			pstmt.setInt(3, questionCriterion.getQuestion_type_id());
			pstmt.setInt(4, questionCriterion.getTopic_id());

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				

				Question question = new Question();
				question.setId(rs.getInt("id"));
				question.setTitle(rs.getString("title"));
				question.setQuestion_type_id(rs.getInt("question_type_id"));
				question.setTopic_id(rs.getInt("topic_id"));
				
				questionList.add(question);
			}

			rs.close();
		} catch (SQLException e) {
			throw new SearchFailedException(questionList);
		}
		return questionList;
	}
	/**
	 * Searches for a question depending on the topic from the database if an error occurs it throws an
	 * {@link SearchFailedException} usage:questionJDBCDAO qdao= new ....try{
	 * qdao.search(questionInstance;)} catch(SearchFailedException e){// log exception}
	 * @param questionCriterion
	 * @return
	 * @throws SearchFailedException
	 */
	public List<Question> SearchByTopicID(Question questionCriterion) throws SearchFailedException {

		List<Question> questionList = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SEARCH_QUERY_BYTOPIC_ID)) {

			pstmt.setInt(1, questionCriterion.getTopic_id());

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				

				Question question = new Question();
				question.setId(rs.getInt("id"));
				question.setTitle(rs.getString("title"));
				question.setQuestion_type_id(rs.getInt("question_type_id"));
				question.setTopic_id(rs.getInt("topic_id"));
				
				questionList.add(question);
			}

			rs.close();
		} catch (SQLException e) {
			throw new SearchFailedException(questionList);
		}
		return questionList;
	}

}
