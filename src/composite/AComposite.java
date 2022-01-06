package composite;

import cache.CacheRepository;
import models.AbstractModel;
import repository.IRepository;

import java.sql.SQLException;
import java.util.List;

public abstract class AComposite<T extends AbstractModel, U> {
    protected IRepository<T, U> repository;
    protected CacheRepository<T> cache;

    protected AComposite(IRepository<T, U> repository, CacheRepository<T> cache) {
        this.repository = repository;
        this.cache = cache;
    }

    public T save(T object) throws SQLException {
        T savedUser = repository.getInstance().save(object);
        if (savedUser != null) {
            cache.save(savedUser);
        }
        return savedUser;
    }

    public T get(U key) throws SQLException {
        if (cache.get(key) == null) {
            T u = repository.getInstance().get(key);
            cache.save(u);
            return u;
        }

        return cache.get(key);
    }

    public boolean delete(U key) {
        if (repository.getInstance().delete(key)) {
            if (cache.get(key) != null) {
                cache.delete(cache.get(key));
            }

            return true;
        }

        return false;
    }

    public T update(T object) throws SQLException {
        T new_object = repository.getInstance().update(object);
        cache.update(object, new_object);
        return new_object;
    }

    public List<T> list() {
        return cache.list();
    }

    public void hydrate() throws SQLException {
        List<T> ts = repository.getInstance().list();
        cache.clear();
        for (T t : ts) {
            cache.save(t);
        }
    }
}