package composite;

import cache.CacheRepository;
import models.Fichier;
import repository.FichierRepository;

import java.sql.SQLException;
import java.util.List;

public class CompositeFichierSingleton implements IComposite<Fichier, Integer> {
    public final static CompositeFichierSingleton compositeFichierSingleton = new CompositeFichierSingleton();
    private final CacheRepository<Fichier> fichierCacheRepository = new CacheRepository<>();

    private CompositeFichierSingleton() {

    }

    @Override
    public Fichier save(Fichier object) throws SQLException {
        Fichier savedFichier = FichierRepository.fichierRepository.save(object);
        if (savedFichier != null) {
            fichierCacheRepository.save(savedFichier);
        }
        return savedFichier;
    }

    @Override
    public Fichier get(Integer key) throws SQLException {
        if (fichierCacheRepository.get(key) == null) {
            Fichier u = FichierRepository.fichierRepository.get(key);
            fichierCacheRepository.save(u);
            return u;
        }

        return fichierCacheRepository.get(key);
    }

    @Override
    public boolean delete(Integer key) {
        if (FichierRepository.fichierRepository.delete(key)) {
            if (fichierCacheRepository.get(key) != null) {
                fichierCacheRepository.delete(fichierCacheRepository.get(key));
            }

            return true;
        }

        return false;
    }

    @Override
    public Fichier update(Fichier object) throws SQLException {
        Fichier newFichier = FichierRepository.fichierRepository.update(object);
        fichierCacheRepository.update(object, newFichier);
        return newFichier;
    }

    @Override
    public List<Fichier> list() {
        return fichierCacheRepository.list();
    }

    @Override
    public void hydrate() throws SQLException {
        List<Fichier> Fichiers = FichierRepository.fichierRepository.list();
        fichierCacheRepository.clear();

        for (Fichier Fichier : Fichiers) {
            fichierCacheRepository.save(Fichier);
        }
    }
}