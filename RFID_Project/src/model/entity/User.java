package model.entity;

public class User {
    private String userID;
    private String firstName;
    private String lastName;
    private String cardID;
    private String profilePictureAddress;

    public String getUserID() {
        return userID;
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

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getProfilePictureAddress() {
        return profilePictureAddress;
    }

    public void setProfilePictureAddress(String profilePictureAddress) {
        this.profilePictureAddress = profilePictureAddress;
    }
}
