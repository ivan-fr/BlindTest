package repository;

import bdd.SingletonConnection;
import models.Reponse;
import models.Theme;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThemeRepository implements IRepository<Theme, String> {
    public final static ThemeRepository themeRepository = new ThemeRepository();

    @Override
    public Theme save(Theme object) throws SQLException {
        Connection conn = SingletonConnection.connection;
        assert conn != null;
        PreparedStatement createStmt = conn.prepareStatement(
                "INSERT INTO Theme (value) VALUES (?)",
                Statement.RETURN_GENERATED_KEYS);
        createStmt.setString(1, object.getValue());
        ResultSet res = createStmt.getGeneratedKeys();
        res.next();
        return get(res.getString(1));
    }

    @Override
    public Theme get(String key) throws SQLException {
        Connection conn = SingletonConnection.connection;
        assert conn != null;
        PreparedStatement pstmt = conn.prepareStatement("SELECT * from Theme u where value = ? LIMIT 1");
        pstmt.setString(1, key);
        ResultSet resReponse = pstmt.executeQuery();

        if (!resReponse.next()) {
            return null;
        }

        return new Theme(resReponse.getString("value"));
    }

    @Override
    public boolean delete(String key) {
        Connection conn = SingletonConnection.connection;
        try {
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Theme WHERE value = ?");
            pstmt.setString(1, key);
            pstmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Theme update(Theme object) throws SQLException {
        Connection conn = SingletonConnection.connection;
        assert conn != null;
        PreparedStatement pstmt = conn.prepareStatement("UPDATE Theme  SET value = ? WHERE value = ?");
        pstmt.setString(1, object.getValue());
        pstmt.setString(2, (String) object.getKey());
        pstmt.execute();

        return get((String) object.getKey());
    }

    @Override
    public List<Theme> list() throws SQLException {
        List<Theme> fichiers = new ArrayList<>();

        Connection conn = SingletonConnection.connection;
        assert conn != null;
        PreparedStatement pstmt = conn.prepareStatement("SELECT * from Theme");
        ResultSet res = pstmt.executeQuery();

        while (res.next()) {
            Theme r = new Theme(res.getString("value"));
            fichiers.add(r);
        }

        return fichiers;
    }

    @Override
    public IRepository<Theme, String> getInstance() {
        return themeRepository;
    }
}
