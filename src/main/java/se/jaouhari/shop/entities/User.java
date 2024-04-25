package se.jaouhari.shop.entities;


import jakarta.persistence.*;


@Entity
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    private String name;

    private String lastname;

    private String email;

    private String adress;

    private String username;

    private String password;

    private int role;

    public User() {
    }

    public User(String name, String lastname, String email, String address, String username, String password, int role) {
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.adress = address;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(String admin, int i) {
    }

    public int getId() {
        return id;
    }

    public void setId(int idcustomer) {
        this.id = idcustomer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String address) {
        this.adress = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

}

