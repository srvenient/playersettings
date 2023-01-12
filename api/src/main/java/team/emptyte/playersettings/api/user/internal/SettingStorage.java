package team.emptyte.playersettings.api.user.internal;

public class SettingStorage {

    private String id;
    private byte state;

    public SettingStorage(String id, byte state) {
        this.id = id;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }
}
