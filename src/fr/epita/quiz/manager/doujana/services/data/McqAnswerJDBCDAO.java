package fr.epita.quiz.manager.doujana.services.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.quiz.manager.doujana.datamodel.Answer;
import fr.epita.quiz.manager.doujana.datamodel.McqAnswer;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;
import fr.epita.quiz.manager.doujana.services.ConfigurationService;

public class McqAnswerJDBCDAO {

	private static final String SEARCH_QUERY = "select ID, NAME from MCQANSWER where ID = ? or NAME LIKE ?";
	private static final String INSERT_QUERY = "INSERT into MCQANSWER (title, mcqchoice_id) values(?,?)";
	private static final String UPDATE_QUERY = "UPDATE mcqanswer SET title=?, mcqchoice_id=? WHERE id = ?";
	private static final String DELETE_QUERY = "DELETE FROM mcqanswer  WHERE id = ?";

	private String url;
	private String password;
	private String username;

	public McqAnswerJDBCDAO() {
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
	 * Creates a mcq answer in the database, if a problem occurs then it throws an
	 * {@link CreateFailedException} usage example: mcqanswerJDBCDAO mcqdao = new
	 * ... try{ mcqdao.create(mcqanswerInstance); }catch(CreateFailed e){ //log
	 * exception }
	 * 
	 * @param mcqAnswer
	 * @throws CreateFailedException
	 */
	public void create(McqAnswer mcqAnswer) throws CreateFailedException {

		try (Connection connection = getConnection();

				PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY);) {
			pstmt.setString(1, mcqAnswer.getTitle());
			pstmt.setInt(2, mcqAnswer.getMcqchoice_id());

			pstmt.execute();

		} catch (SQLException sqle) {
			throw new CreateFailedException(mcqAnswer);
		}

	}

	/**
	 * Updates an mcq answer in the database, if a problem occurs thenit throws an
	 * {@link CreateFailedException} usage example: mcqanswerJDBCDAO mcqdao = new
	 * ... try{ mcqdao.update(mcqanswerInstance); }catch(createFailed e){//log
	 * exception}
	 * 
	 * @param mcqAnswer
	 * @throws CreateFailedException
	 */

	public void update(McqAnswer mcqAnswer) throws CreateFailedException {
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(UPDATE_QUERY);) {

			pstmt.setString(1, mcqAnswer.getTitle());
			pstmt.setInt(2, mcqAnswer.getMcqchoice_id());
			pstmt.setInt(3, mcqAnswer.getId());

			pstmt.execute();
		} catch (SQLException sqle) {
			throw new CreateFailedException(mcqAnswer);
		}

	}

	/**
	 * Delete an mcq answer in the database, if a problem occurs then it throws an
	 * {@link CreateFailedException} usage example: mcqanswerJDBCDAO mcqdao = new
	 * ... try{ mcqdao.delete(mcqanswerInstance); }catch(createFailed e){//log
	 * exception}
	 * 
	 * @param mcqAnswer
	 * @throws CreateFailedException
	 */
	public void delete(McqAnswer mcqanswer) throws CreateFailedException {
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(DELETE_QUERY);) {
			pstmt.setInt(1, mcqanswer.getId());
			pstmt.execute();
		} catch (SQLException sqle) {
			throw new CreateFailedException(mcqanswer);
		}
	}

	public McqAnswer getById(int id) {
		return null;

	}

	/**
	 * Searches for an mcq answer in the database, if a problem occurs then it
	 * throws an {@link SearchFailedException} usage example: mcqanswerJDBCDAO
	 * mcqdao = new ... try{ mcqdao.search(mcqanswerInstance); }catch(SearchFailed
	 * e){//log exception}
	 * 
	 * @param mcqAnswer
	 * @throws SearchFailedException
	 */

	public List<McqAnswer> search(McqAnswer mcqanswerCriterion) throws SearchFailedException {

		List<McqAnswer> mcqanswerList = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SEARCH_QUERY)) {

			pstmt.setInt(1, mcqanswerCriterion.getId());
			pstmt.setString(2, "%" + mcqanswerCriterion.getTitle() + "%");
			pstmt.setInt(3, mcqanswerCriterion.getMcqchoice_id());

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("ID");
				String text = rs.getString("NAME");
				int mcqchoice_id = rs.getInt("mcqchoice_id");

				McqAnswer mcqanswer = new McqAnswer(id, text, mcqchoice_id);
				mcqanswer.setId(id);
				mcqanswerList.add(mcqanswer);
			}

			rs.close();
		} catch (SQLException e) {
			throw new SearchFailedException(mcqanswerList);
		}
		return mcqanswerList;
	}

	
}
