package com.srvenient.playersettings.storage;

import com.srvenient.playersettings.storage.model.Model;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public interface ModelService<T extends Model> {

    Consumer<? extends Model> NOOP = model -> { };

    /**
     * Find a sync by id
     *
     * @param id
     * 	The id of the object to find.
     *
     * @return The method returns a nullable object of type T.
     */
    @Nullable T findSync(@NotNull String id);

    /**
     * Return a list of all the elements in the collection
     *
     * @return Nothing
     */
    @Nullable List<T> findAllSync();

    /**
     * Save the model to the database and cache if it's not already there.
     *
     * @param model The model that will be saved.
     */
    void saveSync(@NotNull T model);

    void updateSync(@NotNull T model);

    /**
     * DeleteSync deletes the model from the database
     *
     * @param model
     * 	The model to be deleted.
     *
     * @return The return type is void.
     */
    T deleteSync(@NotNull T model);

    /**
     * DeleteSync deletes the object with the given id
     *
     * @param id
     * 	The id of the sync to delete.
     */
    void deleteSync(@NotNull String id);

}
