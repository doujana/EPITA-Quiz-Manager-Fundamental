package fr.epita.quiz.manager.doujana.services.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.quiz.manager.doujana.datamodel.QuestionType;
import fr.epita.quiz.manager.doujana.datamodel.Student;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;
import fr.epita.quiz.manager.doujana.services.ConfigurationService;

public class QuestionTypeJDBCDAO
{
   
	
	private static final String SEARCH_QUERY = "select ID, TITLE from QUESTIONTYPE where ID = ? or title LIKE ?";
	private static final String INSERT_QUERY = "INSERT into QUESTIONTYPE (title) values(?)";
	private static final String UPDATE_QUERY = "UPDATE QUESTIONTYPE SET title=? WHERE ID = ?";
	private static final String DELETE_QUERY = "DELETE FROM QUESTIONTYPE  WHERE ID = ?";
	private String url;
	private String password;
	private String username;

	
	public QuestionTypeJDBCDAO() {
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
	 * Creates a questiontype in the database, if a problem occurs then it throws an
	 * {@link CreateFailedException} usage example: QuestiontypeJDBCDAO dao = new ... try{
	 * dao.create(questiontypeInstance); }catch(CreateFailed e){ //log exception }
	 * 
	 * @param questiontype
	 * @throws CreateFailedException
	 */
	public void create(QuestionType questtype) throws CreateFailedException {

		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY);) {
			pstmt.setString(1, questtype.getTitle());
			pstmt.execute();
		} catch (SQLException sqle) {
			throw new CreateFailedException(questtype);
		}
	}
	/**
	 * <b>another function to create/insert a record in the database</b>
	 * Creates a questiontype in the database, if a problem occurs then it throws an
	 * {@link CreateFailedException} usage example: QuestiontypeJDBCDAO dao = new ... try{
	 * dao.create(questiontypeInstance); }catch(CreateFailed e){ //log exception }
	 * 
	 * @param questiontype
	 * @throws CreateFailedException
	 */
	public int createRecord(QuestionType questionType) throws CreateFailedException{
		
		int status = 0;
		
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY);) {
			pstmt.setString(1, questionType.getTitle());
			
			status = pstmt.executeUpdate();
			
		} catch (SQLException sqle) {
			throw new CreateFailedException(questionType);
		}
		return status;
	}
	/**
	 * updates a questiontype in the database, if a problem occurs then it throws an
	 * {@link Exception} usage example: QuestiontypeJDBCDAO dao = new ... try{
	 * dao.update(questiontypeInstance); }catch(SQLException sqle){ //log exception }
	 * 
	 * @param questiontype
	 * @throws SQLException
	 */
	public void update(QuestionType questionType) {
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(UPDATE_QUERY);) {
			pstmt.setString(1, questionType.getTitle());
			pstmt.setInt(2, questionType.getId());
			pstmt.execute();
		} catch (SQLException sqle) {
			// TODO transform into UpdateFailedException
		}
	}
	/**
	 * 
	 * updates a questiontype in the database, if a problem occurs then it throws an
	 * {@link Exception} usage example: QuestiontypeJDBCDAO dao = new ... try{
	 * dao.update(questiontypeInstance); }catch(SQLException sqle){ //log exception }
	 * 
	 * @param questiontype
	 * @throws SQLException
	 */
	public int updateRecord(QuestionType questionType) {
		
		int status = 0;
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(UPDATE_QUERY);) {
			
			pstmt.setString(1, questionType.getTitle());
			pstmt.setInt(2, questionType.getId());
			
			status = pstmt.executeUpdate();
			
		} catch (SQLException sqle) {
//
		}
		return status;
	}
	/**
	 * Delete a questiontype in the database, if a problem occurs then it throws an
	 * {@link Exception} usage example: QuestiontypeJDBCDAO dao = new ... try{
	 * dao.delete(questiontypeInstance); }catch(SQLException sqle){ //log exception }
	 * 
	 * @param questiontype
	 * @throws SQLException
	 */
	public void delete(QuestionType questtype) {
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(DELETE_QUERY);) {
			pstmt.setInt(1, questtype.getId());
			pstmt.execute();
		} catch (SQLException sqle) {
			// TODO transform into UpdateFailedException
		}
	}
	/**
	 * get a questiontype in the database, if a problem occurs then it throws an
	 * {@link Exception} usage example: QuestiontypeJDBCDAO dao = new ... try{
	 * dao.getbyid(questiontypeInstance); }catch(SQLException sqle){ //log exception }
	 * 
	 * @param questiontype
	 * @throws SQLException
	 */
	public QuestionType getById(int id) {
		return null;

	}
	/**
	 * Search for a questiontype in the database, if a problem occurs then it throws an
	 * {@link SearchFailedException} usage example: QuestiontypeJDBCDAO dao = new ... try{
	 * dao.search(questiontypeInstance); }catch(SQLException sqle){ //log exception }
	 * 
	 * @param questiontype
	 * @throws SearchFailedException
	 */
	public List<QuestionType> search(QuestionType qtypetCriterion) throws SearchFailedException {

		List<QuestionType> qtypeList = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SEARCH_QUERY)) {

			pstmt.setInt(1, qtypetCriterion.getId());
			pstmt.setString(2, "%" + qtypetCriterion.getTitle() + "%");

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				
				QuestionType qtype = new QuestionType();
				qtype.setTitle(rs.getString("title"));
				qtype.setId(rs.getInt("id"));
				
				qtypeList.add(qtype);
			}

			rs.close();
		} catch (SQLException e) {
			throw new SearchFailedException(qtypetCriterion);
		}
		return qtypeList;
	}

}
