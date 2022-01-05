package repository;

import bdd.SingletonConnection;
import models.Reponse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReponseRepository implements IRepository<Reponse, String> {
    public final static ReponseRepository reponseRepository = new ReponseRepository();

    @Override
    public Reponse save(Reponse object) throws SQLException {
        Connection conn = SingletonConnection.connection;
        assert conn != null;
        PreparedStatement createStmt = conn.prepareStatement(
                "INSERT INTO Reponse (value) VALUES (?)",
                Statement.RETURN_GENERATED_KEYS);
        createStmt.setString(1, object.getValue());
        ResultSet res = createStmt.getGeneratedKeys();
        res.next();
        return get(res.getString(1));
    }

    @Override
    public Reponse get(String key) throws SQLException {
        Connection conn = SingletonConnection.connection;
        assert conn != null;
        PreparedStatement pstmt = conn.prepareStatement("SELECT * from Reponse u where value = ? LIMIT 1");
        pstmt.setString(1, key);
        ResultSet resReponse = pstmt.executeQuery();

        if (!resReponse.next()) {
            return null;
        }

        return new Reponse(resReponse.getString("value"));
    }

    @Override
    public boolean delete(String key) {
        Connection conn = SingletonConnection.connection;
        try {
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Reponse WHERE value = ?");
            pstmt.setString(1, key);
            pstmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Reponse update(Reponse object) throws SQLException {
        Connection conn = SingletonConnection.connection;
        assert conn != null;
        PreparedStatement pstmt = conn.prepareStatement("UPDATE Reponse  SET value = ? WHERE value = ?");
        pstmt.setString(1, object.getValue());
        pstmt.setString(2, (String) object.getKey());
        pstmt.execute();

        return get((String) object.getKey());
    }

    @Override
    public List<Reponse> list() throws SQLException {
        List<Reponse> fichiers = new ArrayList<>();

        Connection conn = SingletonConnection.connection;
        assert conn != null;
        PreparedStatement pstmt = conn.prepareStatement("SELECT * from Reponse");
        ResultSet res = pstmt.executeQuery();

        while (res.next()) {
            Reponse r = new Reponse(res.getString("value"));
            fichiers.add(r);
        }

        return fichiers;
    }
}
