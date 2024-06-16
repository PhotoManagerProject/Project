package Files.model;

/**
 Αυτή είναι η κατηγορία Model για Users
 */
public class UserProfile {
    public UserProfile(String id, String userName, String passWord, String firstName, String lastName,
                       String phoneNum, String city, String speciality) {
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.city = city;
        this.speciality = speciality;
    }

    private String id;
    private String userName;

    private String passWord;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private String city;
    private String speciality;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    @Override
    public String toString() {//Only printing personal data, excluding sensitive data use
        return new StringBuilder().append("UserProfile{").append("id='").append(id).append('\'').append(", firstName='").
                append(firstName).append('\'').append(", lastName='").append(lastName).append('\'').append(", phoneNum='").
                append(phoneNum).append('\'').append(", city='").append(city).append('\'').append(", speciality='").
                append(speciality).append('\'').append('}').toString();
    }
}
