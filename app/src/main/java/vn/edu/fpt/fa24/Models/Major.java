package vn.edu.fpt.fa24.Models;

public class Major {
    private int idMajor;
    private String nameMajor;

    // Constructor
    public Major(int idMajor, String nameMajor) {
        this.idMajor = idMajor;
        this.nameMajor = nameMajor;
    }

    // Getters
    public int getIdMajor() {
        return idMajor;
    }

    public String getNameMajor() {
        return nameMajor;
    }
}

