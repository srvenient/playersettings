package team.emptyte.mercuryoptions.api.registry;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class KeyedRegistry<T extends Keyed> {

    private final Map<String, T> registries = new HashMap<>();

    public T get(String key) {
        return registries.get(key);
    }

    public Collection<T> getAll() {
        return registries.values();
    }

    public void register(T model) {
        registries.put(model.getId(), model);
    }

    public void remove(String key) {
        registries.remove(key);
    }

    public void removeAll() {
        registries.clear();
    }

}
