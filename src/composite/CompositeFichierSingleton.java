package composite;

import cache.CacheRepository;
import models.Fichier;
import repository.FichierRepository;

public class CompositeFichierSingleton extends AComposite<Fichier, Integer> {
    public final static CompositeFichierSingleton compositeFichierSingleton = new CompositeFichierSingleton(FichierRepository.fichierRepository, new CacheRepository<>());

    protected CompositeFichierSingleton(FichierRepository fichierRepository, CacheRepository<Fichier> cache) {
        super(fichierRepository, cache);
    }
}