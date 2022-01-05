package composite;

import java.sql.SQLException;
import java.util.List;

public interface IComposite<T, U> {
    public T save(T object) throws SQLException;

    public T get(U key) throws SQLException;

    public boolean delete(U key);

    public T update(T object) throws SQLException;

    public List<T> list();

    public void hydrate() throws SQLException;
}