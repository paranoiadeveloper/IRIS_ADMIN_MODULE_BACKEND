package com.beanClass;

public class Employee {

    private Long id;
    private String name;
    private String phoneno;
    private String address;
    private int rating;
    private String country;
    private String dateCreated;
    private int isAdmin;

    // Getters & Setters (VERY IMPORTANT)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhoneno() { return phoneno; }
    public void setPhoneno(String phoneno) { this.phoneno = phoneno; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getDateCreated() { return dateCreated; }
    public void setDateCreated(String dateCreated) { this.dateCreated = dateCreated; }

    public int getIsAdmin() { return isAdmin; }
    public void setIsAdmin(int isAdmin) { this.isAdmin = isAdmin; }
}
