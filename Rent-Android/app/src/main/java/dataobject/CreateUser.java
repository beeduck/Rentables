package dataobject;

public class CreateUser {

    private String lastName = "";
    private String firstName = "";
    private String password = "";
    private String username = "";

    public void setUsername(String newUsername){

        username = newUsername;
    }

    public void setFirstName(String newFirstName){

        firstName = newFirstName;
    }

    public void setLastName(String newLastName){

        lastName = newLastName;
    }

    public void setPassword(String newPassword){

        //TODO Probably change this at some point
        password = newPassword;
    }

    public String getUsername(){

        return username;
    }

    public String getFirstName(){

        return firstName;
    }

    public String getLastName(){

        return lastName;
    }

    public String getPassword(){

        return password;
    }

    public void printProperties(){

        System.out.println("\"username\":\"" + getUsername() + "\"");
        System.out.println("\"firstName\":\"" + getFirstName() + "\"");
        System.out.println("\"lastName\":\"" + getLastName() + "\"");
        System.out.println("\"password\":\"" + getPassword() + "\"");
    }
}
