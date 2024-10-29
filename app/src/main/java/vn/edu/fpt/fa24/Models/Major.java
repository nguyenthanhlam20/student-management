package vn.edu.fpt.fa24.Models;

public class Major {
    private int id;
    private String name;

    public Major(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Major(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Major)) return false;
        Major major = (Major) obj;
        return id == major.id && name.equals(major.name);
    }

    @Override
    public String toString() {
        return name; // This will display the name in the spinner
    }
}
