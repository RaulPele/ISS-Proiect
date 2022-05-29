package employeesmonitor.model;

import java.io.Serializable;

public class CompanyMember extends Entity<Long> implements Serializable {
    private long badgeNumber;
    private String firstName;
    private String lastName;
    private String gender;

    public CompanyMember() {

    }

    public CompanyMember(String firstName, String lastName, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public long getBadgeNumber() {
        return badgeNumber;
    }

    public void setBadgeNumber(long badgeNumber) {
        this.badgeNumber = badgeNumber;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public Long getID() {
        return super.getID();
    }
}
