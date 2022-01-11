package sg.edu.np.onetokenocbc;

import java.util.Date;

public class AccountHolder {

    private String CIFID;
    private String ID;
    private String IDType;

    private String Nationality;
    private String Salutation;
    private String Name;
    private String DOB;
    private String Gender;
    private String MaritalStatus;
    private String Race;
    private String TypeofResidence;
    private String Address;
    private String PostalCode;
    private String Email;
    private String PhoneNo;
    private String Occupation;
    private String Password;

    public AccountHolder(){}

    public AccountHolder(String CIFID, String ID, String IDType, String nationality, String salutation, String name, String DOB, String gender, String maritalStatus, String race, String typeofResidence, String address, String postalCode, String email, String phoneNo, String occupation, String password) {
        this.CIFID = CIFID;
        this.ID = ID;
        this.IDType = IDType;
        Nationality = nationality;
        Salutation = salutation;
        Name = name;
        this.DOB = DOB;
        Gender = gender;
        MaritalStatus = maritalStatus;
        Race = race;
        TypeofResidence = typeofResidence;
        Address = address;
        PostalCode = postalCode;
        Email = email;
        PhoneNo = phoneNo;
        Occupation = occupation;
        Password = password;
    }

    public String getCIFID() {
        return CIFID;
    }

    public void setCIFID(String CIFID) {
        this.CIFID = CIFID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIDType() {
        return IDType;
    }

    public void setIDType(String IDType) {
        this.IDType = IDType;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public String getSalutation() {
        return Salutation;
    }

    public void setSalutation(String salutation) {
        Salutation = salutation;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getMaritalStatus() {
        return MaritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        MaritalStatus = maritalStatus;
    }

    public String getRace() {
        return Race;
    }

    public void setRace(String race) {
        Race = race;
    }

    public String getTypeofResidence() {
        return TypeofResidence;
    }

    public void setTypeofResidence(String typeofResidence) {
        TypeofResidence = typeofResidence;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getOccupation() {
        return Occupation;
    }

    public void setOccupation(String occupation) {
        Occupation = occupation;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
