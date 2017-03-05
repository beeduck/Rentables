package dataobject;

public class LoginUser {

    private String username;
    private String password;

    //Set methods
    public void setUsername(String newUsername){username = newUsername;}
    public void setPassword(String newPassword){password = newPassword;}

    //Get methods
    public String getUsername(){return username;}
    public String getPassword(){return password;}
}
