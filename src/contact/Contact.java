package contact;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by Lance Judan on 8/23/17
 */
public class Contact implements Serializable, Comparable<Contact> {
    //Instance variables
    private String name, gender, email, home, mobile, address;
    private Date birthday;
    private transient int age; //Transient because don't want to be included when saving

    public Contact(String name, String gender, Date birthday, String email, String mobile, String home, String address) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.email = email;
        this.mobile = mobile;
        this.home = home;
        this.address = address;
        generateAge();
    }

    //Returns whether contact alphabetically comes before or after based on first name
    public int compareTo(Contact contact) {
        return this.toString().compareToIgnoreCase(contact.toString());
    }

    public String toString() {
        return name;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
        generateAge();
    }

    public void generateAge() {
        LocalDate today = LocalDate.now();
        LocalDate birthday = this.birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        age = Period.between(birthday, today).getYears();
    }

    public int getAge() {
        return age;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getHome() {
        return home;
    }
    public void setHome(String home) {
        this.home = home;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
