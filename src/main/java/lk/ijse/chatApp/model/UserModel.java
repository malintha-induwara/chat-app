package lk.ijse.chatApp.model;

import lk.ijse.chatApp.db.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public boolean validateUser(String userName, String password) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT * FROM user where username = ? and password = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,userName);
        pstm.setString(2,password);
        return pstm.executeQuery().next();
    }

    public String getLocation(String userName) throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT img FROM user WHERE username = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,userName);
        ResultSet resultSet = pstm.executeQuery();
        resultSet.next();
        return resultSet.getString(1);
    }
}

