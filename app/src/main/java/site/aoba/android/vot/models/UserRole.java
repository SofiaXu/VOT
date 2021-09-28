package site.aoba.android.vot.models;

public class UserRole {
    private long id;
    private String name;
    private int accessLevel;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }
}
