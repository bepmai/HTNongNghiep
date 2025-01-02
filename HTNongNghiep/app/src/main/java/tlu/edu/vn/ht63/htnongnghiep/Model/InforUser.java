package tlu.edu.vn.ht63.htnongnghiep.Model;

import java.io.Serializable;

public class InforUser implements Serializable {
    private String fullName;
    private String dateOfBirth;
    private String adress;
    private String gender;
    private String plant;

    public InforUser() {
    }

    public InforUser(String fullName, String dateOfBirth, String adress, String gender, String plant) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.adress = adress;
        this.gender = gender;
        this.plant = plant;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPlant() {
        return plant;
    }

    public void setPlant(String plant) {
        this.plant = plant;
    }
}
