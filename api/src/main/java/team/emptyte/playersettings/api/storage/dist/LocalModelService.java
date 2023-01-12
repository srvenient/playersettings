package team.emptyte.playersettings.api.storage.dist;

import org.jetbrains.annotations.NotNull;
import team.emptyte.playersettings.api.storage.ModelService;
import team.emptyte.playersettings.api.storage.model.Model;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class LocalModelService<T extends Model>
        implements ModelService<T> {

    private final Map<String, T> cache;

    private LocalModelService(Map<String, T> cache) {
        this.cache = cache;
    }

    @Override
    public @Nullable T findSync(@NotNull String id) {
        return cache.get(id);
    }

    @Override
    public List<T> findSync(@NotNull String field, @NotNull String value) {
        return Collections.singletonList(findSync(value));
    }

    @Override
    public List<T> findAllSync(@NotNull Consumer<T> postLoadAction) {
        return new ArrayList<>(cache.values());
    }

    @Override
    public void saveSync(@NotNull T model) {
        cache.put(model.getId(), model);
    }

    @Override
    public void deleteSync(@NotNull T model) {
        cache.remove(model.getId());
    }

    @Override
    public T deleteSync(@NotNull String id) {
        return cache.remove(id);
    }

    public static <T extends Model> LocalModelService<T> hashMap() {
        return new LocalModelService<>(new HashMap<>());
    }

    public static <T extends Model> LocalModelService<T> concurrent() {
        return new LocalModelService<>(new ConcurrentHashMap<>());
    }

    public static <T extends Model> LocalModelService<T> create(Map<String, T> cache) {
        return new LocalModelService<>(cache);
    }

}
