package com.iaf.gettremp.gettremp;

/**
 * Created by Liav Bachar on 6/21/2017.
 */

public class UserInformation {

    private String Email;
    private String Name;
    private long isFirstLogin;
    private long Points;



public UserInformation()
{

}

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public long getIsFirstLogin() {
        return isFirstLogin;
    }

    public void setIsFirstLogin(long isfirstlogin) {
        this.isFirstLogin = isfirstlogin;
    }

    public long getPoints() {
        return Points;
    }

    public void setPoints(long points) {
        Points = points;
    }
}
