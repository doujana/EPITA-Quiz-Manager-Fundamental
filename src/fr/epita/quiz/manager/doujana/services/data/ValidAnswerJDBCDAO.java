package fr.epita.quiz.manager.doujana.services.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.quiz.manager.doujana.datamodel.QuestionType;
import fr.epita.quiz.manager.doujana.datamodel.Quiz;
import fr.epita.quiz.manager.doujana.datamodel.Student;
import fr.epita.quiz.manager.doujana.datamodel.ValidAnswer;
import fr.epita.quiz.manager.doujana.exception.CreateFailedException;
import fr.epita.quiz.manager.doujana.exception.SearchFailedException;
import fr.epita.quiz.manager.doujana.services.ConfigurationService;

public class ValidAnswerJDBCDAO {

	private static final String SEARCH_QUERY = "select ID, NAME from VALIDANSWER where ID = ? or NAME LIKE ?";
	private static final String INSERT_QUERY = "INSERT into VALIDANSWER (name) values(?)";
	private static final String UPDATE_QUERY = "UPDATE VALIDANSWER SET NAME=? WHERE ID = ?";
	private static final String DELETE_QUERY = "DELETE FROM VALIDANSWER  WHERE ID = ?";
	private String url;
	private String password;
	private String username;

	public ValidAnswerJDBCDAO() {
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
	 * Creates a validanswer in the database, if a problem occurs then it throws an
	 * {@link CreateFailedException} usage example: validanswerJDBCDAO dao = new ...
	 * try{ dao.create(validanswerInstance); }catch(CreateFailed e){ //log exception
	 * }
	 * 
	 * @param validanswer
	 * @return 
	 * @throws CreateFailedException
	 */
	public int create(ValidAnswer validanswer) throws CreateFailedException {
int status=0;
if (status>0) {
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY);) {
			pstmt.setString(1, validanswer.getName());
			pstmt.execute();
			
		}
		
			catch (SQLException sqle) {
			throw new CreateFailedException(validanswer);
		
		}
	}
return status;
	}

	/**
	 * Creates a validanswer in the database, if a problem occurs then it throws an
	 * {@link CreateFailedException} usage example: validanswerJDBCDAO dao = new ...
	 * try{ dao.create(validanswerInstance); }catch(CreateFailed e){ //log exception
	 * }
	 * 
	 * @param validanswer
	 * @return status
	 * @throws CreateFailedException
	 */
	public int createRecord(ValidAnswer validanswer) throws CreateFailedException {

		int status = 0;

		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(INSERT_QUERY);) {
			pstmt.setString(1, validanswer.getName());

			status = pstmt.executeUpdate();

		} catch (SQLException sqle) {
			throw new CreateFailedException(validanswer);
		}
		return status;
	}

	/**
	 * updates a validanswer in the database, if a problem occurs then it throws an
	 * exception and we handle it through try catch usage example:
	 * validanswerJDBCDAO dao = new ... try{ dao.update(validanswerInstance);
	 * }catch(SQLException e){ //log exception }
	 * 
	 * @param validanswer
	 * @return status
	 * @throws SQLException
	 */
	public int updateRecord(ValidAnswer validanswer) {

		int status = 0;
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(UPDATE_QUERY);) {

			pstmt.setString(1, validanswer.getName());
			pstmt.setInt(2, validanswer.getId());

			status = pstmt.executeUpdate();

		} catch (SQLException sqle) {
			// TODO transform into UpdateFailedException
		}
		return status;
	}

	/**
	 * updates a validanswer in the database, if a problem occurs then it throws an
	 * exception and we handle it through try catch usage example:
	 * validanswerJDBCDAO dao = new ... try{ dao.update(validanswerInstance);
	 * }catch(SQLException e){ //log exception }
	 * 
	 * @param validanswer
	 * @throws SQLException
	 */

	public void update(ValidAnswer validanswer) {
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(UPDATE_QUERY);) {
			pstmt.setString(1, validanswer.getName());
			pstmt.setInt(2, validanswer.getId());
			pstmt.execute();
		} catch (SQLException sqle) {
			// TODO transform into UpdateFailedException
		}

	}

	/**
	 * Delete a validanswer in the database, if a problem occurs then it throws an
	 * exception and we handle it through try catch usage example:
	 * validanswerJDBCDAO dao = new ... try{ dao.delete(validanswerInstance);
	 * }catch(SQLException e){ //log exception }
	 * 
	 * @param validanswer
	 * @throws SQLException
	 */
	public void delete(ValidAnswer validanswer) {
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(DELETE_QUERY);) {
			pstmt.setInt(1, validanswer.getId());
			pstmt.execute();
		} catch (SQLException sqle) {
			// TODO transform into UpdateFailedException
		}
	}

	/**
	 * This method will get the valid answer using the id of a function
	 * 
	 * @param id
	 * @return
	 */
	public ValidAnswer getById(int id) {
		return null;

	}
	/**
	 * search for a validanswer in the database, if a problem occurs then it throws an
	 * exception and we handle it through try catch usage example:
	 * validanswerJDBCDAO dao = new ... try{ dao.search(validanswerInstance);
	 * }catch( SearchFailedException e){ //log exception }
	 * 
	 * @param validanswer
	 * @return status
	 * @throws SearchFailedException
	 */

	public List<ValidAnswer> search(ValidAnswer validanswerCriterion) throws SearchFailedException {

		List<ValidAnswer> validanswerList = new ArrayList<>();
		try (Connection connection = getConnection();
				PreparedStatement pstmt = connection.prepareStatement(SEARCH_QUERY)) {

			pstmt.setInt(1, validanswerCriterion.getId());
			pstmt.setString(2, "%" + validanswerCriterion.getName() + "%");

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("ID");
				String name = rs.getString("NAME");
				ValidAnswer vanswer = new ValidAnswer(name);
				vanswer.setId(id);
				validanswerList.add(vanswer);
			}

			rs.close();
		} catch (SQLException e) {
			throw new SearchFailedException(validanswerCriterion);
		}
		return validanswerList;
	}

}
