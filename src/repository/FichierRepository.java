package repository;

import bdd.SingletonConnection;
import models.Fichier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FichierRepository implements IRepository<Fichier, Integer> {
    public final static FichierRepository fichierRepository = new FichierRepository();

    @Override
    public Fichier save(Fichier object) throws SQLException {
        Connection conn = SingletonConnection.connection;
        assert conn != null;
        PreparedStatement createStmt = conn.prepareStatement(
                "INSERT INTO Fichier (name, extension, type, Theme_value, Reponse_value) VALUES (?, ?, ?, ?,?)",
                Statement.RETURN_GENERATED_KEYS);
        createStmt.setString(1, object.getName());
        createStmt.setString(2, object.getExtension());
        createStmt.setString(3, object.getType());
        createStmt.setString(4, object.getTheme().getValue());
        createStmt.setString(5, object.getReponse().getValue());
        createStmt.executeUpdate();

        ResultSet res = createStmt.getGeneratedKeys();
        res.next();
        return get(res.getInt(1));
    }

    @Override
    public Fichier get(Integer key) throws SQLException {
        Connection conn = SingletonConnection.connection;
        assert conn != null;
        PreparedStatement pstmt = conn.prepareStatement("SELECT * from Fichier u where idFichier = ? LIMIT 1");
        pstmt.setInt(1, key);
        ResultSet resFichier = pstmt.executeQuery();

        if (!resFichier.next()) {
            return null;
        }

        Fichier f = new Fichier(resFichier.getString("name"), resFichier.getString("extension"), resFichier.getString("type"),  resFichier.getString("Theme_value"), resFichier.getString("Reponse_value"));
        f.setKey(resFichier.getInt("idFichier"));

        return f;
    }

    @Override
    public boolean delete(Integer key) {
        Connection conn = SingletonConnection.connection;
        try {
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM Fichier WHERE idFichier = ?");
            pstmt.setInt(1, key);
            pstmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Fichier update(Fichier object) throws SQLException {
        Connection conn = SingletonConnection.connection;
        assert conn != null;
        PreparedStatement pstmt = conn.prepareStatement("UPDATE Fichier  SET name = ?, extension = ? WHERE idFichier = ?");
        pstmt.setString(1, object.getName());
        pstmt.setString(2, object.getExtension());
        pstmt.setInt(3, (Integer) object.getKey());
        pstmt.execute();

        return get((Integer) object.getKey());
    }

    @Override
    public List<Fichier> list() throws SQLException {
        List<Fichier> fichiers = new ArrayList<>();

        Connection conn = SingletonConnection.connection;
        assert conn != null;
        PreparedStatement pstmt = conn.prepareStatement("SELECT * from Fichier");
        ResultSet res = pstmt.executeQuery();

        while (res.next()) {
            Fichier f = new Fichier(res.getString("name"), res.getString("extension"), res.getString("type"), res.getString("Theme_value"), res.getString("Reponse_value"));
            f.setKey(res.getInt("idFichier"));
            fichiers.add(f);
        }

        return fichiers;
    }

    @Override
    public IRepository<Fichier, Integer> getInstance() {
        return fichierRepository;
    }
}
