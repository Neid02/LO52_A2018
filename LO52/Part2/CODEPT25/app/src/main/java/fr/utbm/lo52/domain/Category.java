package fr.utbm.lo52.domain;

public class Category {

    private long Id;
    private String Level;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getLevel() {
        return Level;
    }

    public void setLevel(String level) {
        Level = level;
    }

    public Category(long id, String level) {
        Id = id;
        Level = level;
    }
    public Category() {

    }
}
