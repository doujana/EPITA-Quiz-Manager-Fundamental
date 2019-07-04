package fr.epita.quiz.manager.doujana.services.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.quiz.manager.doujana.datamodel.Topic;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;
import fr.epita.quiz.manager.doujana.services.ConfigurationService;

public class TopicJDBCDAO {

	private static final String SEARCH_QUERY = "select ID, NAME from QUESTIONTOPIC  where ID = ? or NAME LIKE ?";
	private static final String INSERT_QUERY = "INSERT into QUESTIONTOPIC (name) values(?)";
	private static final String UPDATE_QUERY = "UPDATE QUESTIONTOPIC SET NAME=? WHERE ID = ?";
	private static final String DELETE_QUERY = "DELETE FROM QUESTIONTOPIC  WHERE ID = ?";
	private String url;
	private String password;
	private String username;

	public TopicJDBCDAO() {
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
	 * Creates a topic in the database, if a problem occurs then it throws an
	 * {@link CreateFailedException} usage example: topicJDBCDAO dao = new ... try{
	 * dao.create(topicInstance); }catch(CreateFailed e){ //log exception }
	 * 
	 * @param topic
	 * @return
	 * @throws CreateFailedException
	 */
	////
	public int create(Topic topic) throws CreateFailedException {

		int status = 0;

		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY);) {
			pstmt.setString(1, topic.getName());

			status = pstmt.executeUpdate();
		} catch (SQLException sqle) {
			throw new CreateFailedException(topic);
		}

		return status;
	}

	/**
	 * Creates a topic in the database, if a problem occurs then it throws an
	 * {@link CreateFailedException} usage example: topicJDBCDAO dao = new ... try{
	 * dao.create(topicInstance); }catch(CreateFailed e){ //log exception }
	 * 
	 * @param topic
	 * @return status
	 * @throws CreateFailedException
	 */
	public int createRecord(Topic topic) throws CreateFailedException {

		int status = 0;

		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY);) {
			pstmt.setString(1, topic.getName());

			status = pstmt.executeUpdate();

		} catch (SQLException sqle) {
			throw new CreateFailedException(topic);
		}
		return status;
	}

	/**
	 * Updates a topic in the database, if a problem occurs then it throws an
	 * {@link Exception} usage example: topicJDBCDAO dao = new ... try{
	 * dao.updaterecord(topicInstance); }catch(SQLException e){ //log exception }
	 * return status
	 * 
	 * @param topic
	 * @throws CreateFailedException
	 */
	public int updateRecord(Topic topic) {

		int status = 0;
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(UPDATE_QUERY);) {

			pstmt.setString(1, topic.getName());
			pstmt.setInt(2, topic.getId());

			status = pstmt.executeUpdate();

		} catch (SQLException sqle) {
			// TODO transform into UpdateFailedException
		}
		return status;
	}

	/**
	 * Updates a topic in the database, if a problem occurs then it throws an
	 * {@link Exception} usage example: topicJDBCDAO dao = new ... try{
	 * dao.update(topicInstance); }catch(SQLException e){ //log exception }
	 * 
	 * @param topic
	 * @throws CreateFailedException
	 */
	public void update(Topic topic) {
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(UPDATE_QUERY);) {
			pstmt.setString(1, topic.getName());
			pstmt.setInt(2, topic.getId());
			pstmt.execute();
		} catch (SQLException sqle) {
			// TODO transform into UpdateFailedException
		}
	}

	/**
	 * Delete a topic in the database, if a problem occurs then it throws an
	 * {@link Exception} usage example: topicJDBCDAO dao = new ... try{
	 * dao.delete(topicInstance); }catch(SQLException e){ //log exception } return
	 * status
	 * 
	 * @param topic
	 *
	 */

	public void delete(Topic topic) {
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(DELETE_QUERY);) {
			pstmt.setInt(1, topic.getId());
			pstmt.execute();
		} catch (SQLException sqle) {
			// TODO transform into UpdateFailedException
		}
	}

	/**
	 * this method will get the topic by id
	 * 
	 * @param id
	 * @return
	 */

	public Topic getById(int id) {
		return null;

	}

	/**
	 * Search for a topic in the database, if a problem occurs then it throws an
	 * {@link SearchFailedException} usage example: topicJDBCDAO dao = new ... try{
	 * dao.delete(topicInstance); }catch(SearchFailedException e){ //log exception }
	 * 
	 * @param topic
	 *
	 */
	public List<Topic> search(Topic TopicCriterion) throws SearchFailedException {

		List<Topic> topicList = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SEARCH_QUERY)) {

			pstmt.setInt(1, TopicCriterion.getId());
			pstmt.setString(2, "%" + TopicCriterion.getName() + "%");

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("ID");
				String name = rs.getString("NAME");
				Topic topic = new Topic(name);
				topic.setId(id);
				topicList.add(topic);
			}

			rs.close();
		} catch (SQLException e) {
			throw new SearchFailedException(TopicCriterion);
		}
		return topicList;
	}

}
