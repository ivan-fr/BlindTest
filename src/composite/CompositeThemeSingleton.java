package composite;

import cache.CacheRepository;
import models.Theme;
import repository.IRepository;
import repository.ThemeRepository;

public class CompositeThemeSingleton extends AComposite<Theme, String> {
    public final static CompositeThemeSingleton compositeThemeSingleton = new CompositeThemeSingleton(ThemeRepository.themeRepository, new CacheRepository<>());

    protected CompositeThemeSingleton(IRepository<Theme, String> repository, CacheRepository<Theme> cache) {
        super(repository, cache);
    }
}
