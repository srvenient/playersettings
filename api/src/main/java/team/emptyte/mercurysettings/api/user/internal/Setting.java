package team.emptyte.mercurysettings.api.user.internal;

public class Setting {

    private String id;
    private byte state;

    public Setting(String id, byte state) {
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
