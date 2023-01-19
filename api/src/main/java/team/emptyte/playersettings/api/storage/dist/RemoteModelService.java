package team.emptyte.playersettings.api.storage.dist;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.emptyte.playersettings.api.storage.ModelService;
import team.emptyte.playersettings.api.storage.model.Model;

import java.util.List;
import java.util.function.Consumer;

public abstract class RemoteModelService<T extends Model> implements ModelService<T> {

    private final ModelService<T> delegate;

    public RemoteModelService(ModelService<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public @Nullable T findSync(@NotNull String id) {
        T model = delegate.findSync(id);

        if (model != null) {
            return model;
        }

        return findSync("id", id);
    }

    @Override
    public @Nullable T findSync(@NotNull String field, @NotNull String value) {
        return internalFind(field, value);
    }

    @Override
    public @Nullable List<T> findAllSync(@NotNull Consumer<T> postLoadAction) {
        return null;
    }

    @Override
    public void saveSync(@NotNull T model, boolean saveInCached) {
        internalSave(model);

        if (saveInCached) {
            delegate.saveSync(model, true);
        }
    }

    @Override
    public void updateSync(T model) {
        internalUpdate(model);
    }

    @Override
    public void deleteSync(@NotNull T model) {
        internalDelete(model);
    }

    protected abstract void internalSave(T model);

    protected abstract void internalUpdate(T model);

    protected abstract void internalDelete(T model);

    protected abstract @Nullable T internalFind(String field, String value);


}
