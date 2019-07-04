package fr.epita.quiz.manager.doujana.services.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.quiz.manager.doujana.datamodel.DifficultyLevel;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;
import fr.epita.quiz.manager.doujana.services.ConfigurationService;

public class DifficultyLevelJDBCDAO {

	private static final String SEARCH_QUERY = "SELECT id, difficulty_level FROM difficulty WHERE id = ? OR difficulty_level LIKE ?";
	private static final String INSERT_QUERY = "INSERT INTO difficulty ( difficulty_level ) VALUES (?)";
	private static final String UPDATE_QUERY = "UPDATE difficulty SET difficulty_level = ? WHERE id = ?";
	private static final String DELETE_QUERY = "DELETE FROM difficulty  WHERE id = ?";
	private String url;
	private String password;
	private String username;

	public DifficultyLevelJDBCDAO() {
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
	 * @return 
	 * @throws CreateFailedException
	 */
	public int create(DifficultyLevel difficultyLevel) throws CreateFailedException {
       int status=0;
		try (Connection connection = getConnection();
			
			PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY);) {
			pstmt.setString(1, difficultyLevel.getDifficulty_level());
			
			pstmt.executeUpdate();
			
		} catch (SQLException sqle) {
			System.out.println("Error " + sqle.getErrorCode() + " has occured. " + sqle.getMessage());
			throw new CreateFailedException(difficultyLevel);
		}
		return status;

	}

	
	/**
	 * Updates a question in the database, if a problem occurs then it throws an
	 * {@link  CreateFailedException} usage example: mcqanswerJDBCDAO mcqdao = new ... try{
	 * mcqdao.update(questionInstance); }catch(createFailed e){//log exception}
	 * @param question
	 * @throws CreateFailedException
	 */
	
public int updateRecord(DifficultyLevel difficultyLevel) throws CreateFailedException {
		
		int status = 0;
		try (Connection connection = getConnection();
			
			PreparedStatement pstmt = connection.prepareStatement(UPDATE_QUERY);) {
			pstmt.setString(1, difficultyLevel.getDifficulty_level());
			pstmt.setInt(2, difficultyLevel.getId());
			
			status = pstmt.executeUpdate();
			
		} catch (SQLException sqle){
			System.out.println("Error " + sqle.getErrorCode() + " has occured. " + sqle.getMessage());
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
	public int delete(DifficultyLevel difficultyLevel) {
		
		int status = 0;
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(DELETE_QUERY);) {
			
			pstmt.setInt(1, difficultyLevel.getId());
			status = pstmt.executeUpdate();
			
		} catch (SQLException sqle) {
			System.out.println("Delete Error! " + sqle.getMessage() );
		}
		return status;
	}

	public DifficultyLevel getById(int id) {
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
	public List<DifficultyLevel> search(DifficultyLevel difficultyLevelCriterion) throws SearchFailedException {

		List<DifficultyLevel> difficultyLevelList = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SEARCH_QUERY)) {

			pstmt.setInt(1, difficultyLevelCriterion.getId());
			pstmt.setString(2, "%" + difficultyLevelCriterion.getDifficulty_level() + "%");
		
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				
				DifficultyLevel difficultyLevel = new DifficultyLevel();
				difficultyLevel.setId(rs.getInt("id"));
				difficultyLevel.setDifficulty_level(rs.getString("title"));
				
				difficultyLevelList.add(difficultyLevel);
			}

			rs.close();
		} catch (SQLException e) {
			throw new SearchFailedException(difficultyLevelList);
		}
		return difficultyLevelList;
	}


}
