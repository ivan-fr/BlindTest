package repository;

import bdd.SingletonConnection;
import models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IRepository<User, String> {
    public final static UserRepository userRepository = new UserRepository();

    @Override
    public User save(User object) throws SQLException {
        Connection conn = SingletonConnection.connection;
        assert conn != null;
        PreparedStatement createStmt = conn.prepareStatement(
                "INSERT INTO User (username, password) VALUES (?, ?)");
        createStmt.setString(1, object.getUsername());
        createStmt.setString(2, object.getPassword());
        createStmt.executeUpdate();
        return get(object.getUsername());
    }

    @Override
    public User get(String key) throws SQLException {
        Connection conn = SingletonConnection.connection;
        assert conn != null;
        PreparedStatement pstmt = conn.prepareStatement("SELECT * from User u where username = ? LIMIT 1");
        pstmt.setString(1, key);
        ResultSet resUser = pstmt.executeQuery();

        if (!resUser.next()) {
            return null;
        }

        return new User(resUser.getString("username"), resUser.getString("password"));
    }

    @Override
    public boolean delete(String key) {
        Connection conn = SingletonConnection.connection;
        try {
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM User WHERE username = ?");
            pstmt.setString(1, key);
            pstmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public User update(User object) throws SQLException {
        Connection conn = SingletonConnection.connection;
        assert conn != null;
        PreparedStatement pstmt = conn.prepareStatement("UPDATE User  SET username = ?, password = ? WHERE username = ?");
        pstmt.setString(1, object.getUsername());
        pstmt.setString(2, object.getPassword());
        pstmt.setString(3, (String) object.getKey());
        pstmt.execute();

        return get((String) object.getKey());
    }

    @Override
    public List<User> list() throws SQLException {
        List<User> users = new ArrayList<>();

        Connection conn = SingletonConnection.connection;
        assert conn != null;
        PreparedStatement pstmt = conn.prepareStatement("SELECT * from User");
        ResultSet res = pstmt.executeQuery();

        while (res.next()) {
            User r = new User(res.getString("username"), res.getString("password"));
            users.add(r);
        }

        return users;
    }

    @Override
    public IRepository<User, String> getInstance() {
        return userRepository;
    }
}