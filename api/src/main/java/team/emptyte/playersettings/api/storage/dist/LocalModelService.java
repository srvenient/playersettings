package team.emptyte.playersettings.api.storage.dist;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.emptyte.playersettings.api.storage.ModelService;
import team.emptyte.playersettings.api.storage.model.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public T findSync(@NotNull String field, @NotNull String value) {
        return findSync(value);
    }

    @Override
    public List<T> findAllSync(@NotNull Consumer<T> postLoadAction) {
        return new ArrayList<>(cache.values());
    }

    @Override
    public void saveSync(@NotNull T model, boolean saveInCached) {
        cache.put(model.getId(), model);
    }

    @Override
    public void updateSync(T model) {

    }

    @Override
    public void deleteSync(@NotNull T model) {
        cache.remove(model.getId());
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
