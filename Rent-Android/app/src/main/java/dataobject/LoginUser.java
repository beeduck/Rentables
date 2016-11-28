package dataobject;

public class LoginUser {

    private String username;
    private String password;

    public void setUsername(String newUsername){

        username = newUsername;
    }

    public String getUsername(){

        return username;
    }

    public void setPassword(String newPassword){

        password = newPassword;
    }

    public String getPassword(){

        return password;
    }
}
