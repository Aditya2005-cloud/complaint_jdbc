
package com.example.entity;

public class Complaint {

    private int id;
    private String name;
    private String description;
    private String status;

    public Complaint(int id,
                     String name,
                     String description,
                     String status) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Complaint(String name,
                     String description,
                     String status) {

        this.name = name;
        this.description = description;
        this.status = status;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public String getDescription() { return description; }

    public String getStatus() { return status; }

    public void setStatus(String status) {

        this.status = status;

    }
}