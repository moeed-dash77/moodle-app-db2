package de.unidue.inf.is.domain;

public final class User {
    private int userId;
    private String userName;
    private String userEmail;

    public User() {
    	   

    }
    
    public User(int userId,String userName,String userEmail) {
    	this.userId = userId;
    	this.userName = userName;
    	this.userEmail = userEmail;
    }
    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserId(int userId) {
    	this.userId = userId;
    }
    public void setUserName(String userName) {
    	this.userName = userName;
    }
    public void setUserEmail(String userEmail) {
    	this.userEmail = userEmail;
    }

}