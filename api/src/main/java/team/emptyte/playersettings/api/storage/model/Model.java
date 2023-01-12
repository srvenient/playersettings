package team.emptyte.playersettings.api.storage.model;

/**
 * Represents any kind of model which can be stored at database. For this purpose, it is necessary to implement an
 * identifier to this model and can find it by a simple query.
 */
public interface Model {

    /**
     * NOTE:This value cannot be null.
     *
     * @return the id of the model
     */
    String getId();
}
