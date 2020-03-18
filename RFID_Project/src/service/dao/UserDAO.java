/**
 * @autor  Yasin Fakhar
 * edited : 13 March 2020
 * **/

package service.dao;

import model.entity.User;
import service.connetion.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;

public class UserDAO {
    private final DBConnection dbConnection;
    private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());

    public UserDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void insetUser(User user) {

        dbConnection.openConnection();
        Objects.requireNonNull(user);

        try (PreparedStatement statement = dbConnection.getConnection().prepareStatement(
	    "BEGIN insert_update(?,?,?,?,?); END;")) {
	statement.setString(1, user.getUserID());
	statement.setString(2, user.getFirstName());
	statement.setString(3, user.getLastName());
	statement.setString(4, user.getCardID());
	statement.setString(5, user.getProfilePictureAddress());

	try {
	    statement.execute();
	    LOGGER.severe("executed successfully");

	} catch (SQLException e) {
	    LOGGER.severe(e.getMessage());
	}

	dbConnection.getConnection().commit();

        } catch (SQLException e) {
	LOGGER.severe(e.getMessage());
        }
    }


    public ArrayList<User> getUsers() throws SQLException {

        dbConnection.openConnection();
        ArrayList<User> users = new ArrayList<>();
        Statement statement = dbConnection.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("select * from users");
        while (resultSet.next()) {

            //create user object
	User user = new User();

	//initial the user object
	String user_id = resultSet.getString(1);
	String first_name = resultSet.getString(2);
	String last_name = resultSet.getString(3);
	String card_id = resultSet.getString(4);
	String profile_pic = resultSet.getString(5);
	user.setUserID(user_id);
	user.setProfilePictureAddress(profile_pic);
	user.setLastName(last_name);
	user.setFirstName(first_name);
	user.setCardID(card_id);

	users.add(user);
        }
        statement.close();
        dbConnection.closeConnetion();
        return users;
    }
}