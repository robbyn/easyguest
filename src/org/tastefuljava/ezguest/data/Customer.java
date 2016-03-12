/*
 * Customer.java
 *
 * Created on 23 June 2002, 17:28
 */

package org.tastefuljava.ezguest.data;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author  maurice
 */
public class Customer {
    private int id;
    private String titlePerson;
    private String company;
    private String lastName;
    private String firstName;
    private String address1;
    private String address2;
    private String zip;
    private String city;
    private String state;
    private String country;
    private String telephone;
    private String mobile;
    private String fax;
    private String email;
    private Set<Invoice> invoices = new HashSet<Invoice>();

    public int getId() {
        return id;
    }

    public void setId(int newValue) {
        id = newValue;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String newValue) {
        company = newValue;
    }

    public String getTitlePerson() {
        return titlePerson;
    }

    public void setTitlePerson(String newValue) {
        titlePerson = newValue;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String newValue) {
        lastName = newValue;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String newValue) {
        firstName = newValue;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String newValue) {
        address1 = newValue;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String newValue) {
        address2 = newValue;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String newValue) {
        zip = newValue;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String newValue) {
        city = newValue;
    }

    public String getState() {
        return state;
    }

    public void setState(String newValue) {
        state = newValue;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String newValue) {
        country = newValue;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String newValue) {
        telephone = newValue;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String newValue) {
        mobile = newValue;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String newValue) {
        fax = newValue;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String newValue) {
        email = newValue;
    }

    public Set<Invoice> getInvoices() {
        return invoices;
    }
}
