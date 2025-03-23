package utils;

import java.util.Objects;

public class UserData {
    private String name;
    private String email;
    private String title;
    private String firstName;
    private String lastName;
    private String address;
    private String country;
    private String state;
    private String city;
    private String zipCode;
    private String phoneNumber;

    // Constructor
    public UserData(String name, String email, String title, String firstName, String lastName, String address, String country, String state, String city, String zipCode, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.country = country;
        this.state = state;
        this.city = city;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
    }

    // Getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getTitle() { return title; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getAddress() { return address; }
    public String getCountry() { return country; }
    public String getState() { return state; }
    public String getCity() { return city; }
    public String getZipCode() { return zipCode; }
    public String getPhoneNumber() { return phoneNumber; }

    // Override equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserData userData = (UserData) o;
        return Objects.equals(name, userData.name) &&
                Objects.equals(email, userData.email) &&
                Objects.equals(title, userData.title) &&
                Objects.equals(firstName, userData.firstName) &&
                Objects.equals(lastName, userData.lastName) &&
                Objects.equals(address, userData.address) &&
                Objects.equals(country, userData.country) &&
                Objects.equals(state, userData.state) &&
                Objects.equals(city, userData.city) &&
                Objects.equals(zipCode, userData.zipCode) &&
                Objects.equals(phoneNumber, userData.phoneNumber);
    }

    // Override hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(name, email, title, firstName, lastName, address, country, state, city, zipCode, phoneNumber);
    }

    // Override toString method for better debugging
    @Override
    public String toString() {
        return "UserData{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", title='" + title + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}