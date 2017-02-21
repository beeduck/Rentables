package dataobject;

//This class servers as a data object for the user currently using the app.
//There will only ever be one user at a time.

public class User {

    private int userId;
    private String access_token;
    private String token_type;
    private String refresh_token;
    private String expires_in;
    private String username = "";
    private String firstName = "";
    private String lastName = "";
    private String createDate = "";
    private String lastEditDate = "";
    private boolean active = false;

    public void setUsername(String newUsername){

        username = newUsername;
    }

    public String getUsername(){

        return username;
    }

    public void setFirstName(String newFirstName){

        firstName = newFirstName;
    }

    public String getFirstName(){

        return firstName;
    }

    public void setLastName(String newLastName){

        lastName = newLastName;
    }

    public String getLastName(){

        return lastName;
    }

    public void setCreateDate(String newCreateDate){

        createDate = newCreateDate;
    }

    public String getCreateDate(){

        return createDate;
    }

    public void setLastEditDate(String newLastEditDate){

        lastEditDate = newLastEditDate;
    }

    public String getLastEditDate(){

        return lastEditDate;
    }

    public void setActive(boolean isActive){

        active = isActive;
    }

    public boolean getActive(){

        return active;
    }

    public int getUserId(){

        return userId;
    }

    public void printProperties(){

        System.out.println("username: " + getUsername());
        System.out.println("firstName: " + getFirstName());
        System.out.println("lastName: " + getLastName());
        System.out.println("createDate: " + getCreateDate());
        System.out.println("lastEditDate: " + getLastEditDate());
        System.out.println("active: " + getActive());
    }
}
