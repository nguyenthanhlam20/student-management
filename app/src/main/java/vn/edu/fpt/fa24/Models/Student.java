package vn.edu.fpt.fa24.Models;

public class Student {
    private int id;
    private String name;
    private String gender;
    private String email;

    // Constructor
    public Student(int id, String name, String gender, String email) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }
}

