package repository;

import java.sql.SQLException;
import java.util.List;

public interface IRepository<T, U> {
    public T save(T object) throws SQLException;

    public T get(U key) throws SQLException;

    public boolean delete(U key);

    public T update(T object) throws SQLException;

    public List<T> list() throws SQLException;
}