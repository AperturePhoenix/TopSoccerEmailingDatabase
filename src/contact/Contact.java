package contact;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Lance Judan on 8/23/17
 */
public class Contact implements Serializable, Comparable<Contact> {
    //Instance variables
    private String name, gender, email, home, mobile, mother, father, address;
    private Date birthday;
    private transient int age; //Transient because don't want to be included when saving
    private ArrayList<String> categories;

    public Contact(String name, String gender, Date birthday, String email, String mobile, String home, String mother, String father, String address) {
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.email = email;
        this.mobile = mobile;
        this.home = home;
        this.mother = mother;
        this.father = father;
        this.address = address;
        categories = new ArrayList<>();
        generateAge();
    }

    //Returns whether contact alphabetically comes before or after based on first name
    public int compareTo(Contact contact) {
        return this.toString().compareToIgnoreCase(contact.toString());
    }

    public String toString() {
        return name;
    }

    /*public void printInfo() {
        System.out.println(toString());
        System.out.println(gender);
        System.out.println(age + "  " + birthday);
        System.out.println(email);
        System.out.println(mobile);
        System.out.println(home);
        System.out.println(address);
        categories.forEach(System.out::println);
        System.out.println();
    }*/
    String getGender() {
        return gender;
    }

    void setGender(String gender) {
        this.gender = gender;
    }

    Date getBirthday() {
        return birthday;
    }

    void setBirthday(Date birthday) {
        this.birthday = birthday;
        generateAge();
    }

    void generateAge() {
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

    void setEmail(String email) {
        this.email = email;
    }

    public String getHome() {
        return home;
    }

    void setHome(String home) {
        this.home = home;
    }

    public String getMobile() {
        return mobile;
    }

    void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    void setAddress(String address) {
        this.address = address;
    }

    public void addCategory(String category) {
        if (!categories.contains(category)) categories.add(category);
    }

    public boolean isInCategory(String category) {
        return categories.contains(category);
    }

    public void removeCategory(String category) {
        if (categories.contains(category)) categories.remove(category);
    }
}
