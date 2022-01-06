package composite;

import cache.CacheRepository;
import models.Reponse;
import repository.ReponseRepository;

public class CompositeReponseSingleton extends AComposite<Reponse, String> {
    public final static CompositeReponseSingleton compositeReponseSingleton = new CompositeReponseSingleton(ReponseRepository.reponseRepository, new CacheRepository<>());

    protected CompositeReponseSingleton(ReponseRepository userRepository, CacheRepository<Reponse> userCacheRepository) {
        super(userRepository, userCacheRepository);
    }
}