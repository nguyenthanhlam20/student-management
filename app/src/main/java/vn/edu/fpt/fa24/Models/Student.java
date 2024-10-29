package vn.edu.fpt.fa24.Models;

public class Student {
    private int id;
    private String name;
    private String gender;
    private String email;
    private String address;
    private String date; // Date of birth or registration date, depending on your needs
    private String major;
    private int majorId; // Adding majorId as an integer


    // Constructor with all fields
    public Student(int id, String name, String gender, String email, String address, String date, String major) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.date = date;
        this.major = major;
    }

    // Constructor with all fields
    public Student(int id, String name, String gender, String email, String address, String date, int majorId) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.date = date;
        this.majorId = majorId;
    }

    // Additional constructor without id (useful for creating a new student)
    public Student(String name, String gender, String email, String address, String date, String major) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.date = date;
        this.major = major;
    }

    public Student(String name, String gender, String email, String address, String date, int majorId) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.date = date;
        this.majorId = majorId;
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

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }

    public String getMajor() {
        return major;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getMajorId() {
        return majorId; // Getter for majorId
    }

    public void setMajorId(int majorId) { // Setter for majorId
        this.majorId = majorId;
    }
}
