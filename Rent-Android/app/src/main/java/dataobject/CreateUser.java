package dataobject;

public class CreateUser {

    private String lastName = "";
    private String firstName = "";
    private String password = "";
    private String username = "";

    public void setUsername(String newUsername){username = newUsername;}
    public void setFirstName(String newFirstName){firstName = newFirstName;}
    public void setLastName(String newLastName){lastName = newLastName;}
    public void setPassword(String newPassword){password = newPassword;}

    public String getUsername(){return username;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public String getPassword(){return password;}
}
