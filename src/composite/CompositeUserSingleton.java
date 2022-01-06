package composite;

import cache.CacheRepository;
import models.User;
import repository.UserRepository;

public class CompositeUserSingleton extends AComposite<User, String> {
    public final static CompositeUserSingleton compositeUserSingleton = new CompositeUserSingleton(UserRepository.userRepository, new CacheRepository<>());

    protected CompositeUserSingleton(UserRepository userRepository, CacheRepository<User> userCacheRepository) {
        super(userRepository, userCacheRepository);
    }
}