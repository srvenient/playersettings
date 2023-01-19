package team.emptyte.playersettings.api.storage;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.emptyte.playersettings.api.storage.model.Model;

import java.util.List;
import java.util.function.Consumer;

public interface ModelService<T extends Model> {

    Consumer<? extends Model> NOOP = model -> { };
    String ID_FIELD = "id";

    /**
     * Find a sync by id
     *
     * @param id
     * 		The id of the object to find.
     *
     * @return The method returns a nullable object of type T.
     */
    @Nullable T findSync(@NotNull String id);

    @Nullable T findSync(@NotNull String field, @NotNull String value);

    /**
     * Return a list of all the elements in the collection
     *
     * @return Nothing
     */
    @SuppressWarnings("unchecked")
    default @Nullable List<T> findAllSync() {
        return findAllSync((Consumer<T>) NOOP);
    }

    @Nullable List<T> findAllSync(@NotNull Consumer<T> postLoadAction);

    /**
     * Save the model to the database and cache if it's not already there.
     *
     * @param model
     * 		The model that will be saved.
     */
    void saveSync(@NotNull T model, boolean saveInCached);

    default void updateSync(T model) {
        saveSync(model, true);
    }

    /**
     * DeleteSync deletes the model from the database
     *
     * @param model
     * 		The model to be deleted.
     */
    void deleteSync(@NotNull T model);
}
