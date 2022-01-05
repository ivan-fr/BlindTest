package composite;

import cache.CacheRepository;
import models.Reponse;
import repository.ReponseRepository;

import java.sql.SQLException;
import java.util.List;

public class CompositeReponseSingleton implements IComposite<Reponse, String> {
    public final static CompositeReponseSingleton compositeReponseSingleton = new CompositeReponseSingleton();
    private final CacheRepository<Reponse> ReponseCacheRepository = new CacheRepository<>();

    private CompositeReponseSingleton() {

    }

    @Override
    public Reponse save(Reponse object) throws SQLException {
        Reponse savedReponse = ReponseRepository.reponseRepository.save(object);
        if (savedReponse != null) {
            ReponseCacheRepository.save(savedReponse);
        }
        return savedReponse;
    }

    @Override
    public Reponse get(String key) throws SQLException {
        if (ReponseCacheRepository.get(key) == null) {
            Reponse u = ReponseRepository.reponseRepository.get(key);
            ReponseCacheRepository.save(u);
            return u;
        }

        return ReponseCacheRepository.get(key);
    }

    @Override
    public boolean delete(String key) {
        if (ReponseRepository.reponseRepository.delete(key)) {
            if (ReponseCacheRepository.get(key) != null) {
                ReponseCacheRepository.delete(ReponseCacheRepository.get(key));
            }

            return true;
        }

        return false;
    }

    @Override
    public Reponse update(Reponse object) throws SQLException {
        Reponse newReponse = ReponseRepository.reponseRepository.update(object);
        ReponseCacheRepository.update(object, newReponse);
        return newReponse;
    }

    @Override
    public List<Reponse> list() {
        return ReponseCacheRepository.list();
    }

    @Override
    public void hydrate() throws SQLException {
        List<Reponse> reponses = ReponseRepository.reponseRepository.list();
        ReponseCacheRepository.clear();

        for (Reponse Reponse : reponses) {
            ReponseCacheRepository.save(Reponse);
        }
    }
}