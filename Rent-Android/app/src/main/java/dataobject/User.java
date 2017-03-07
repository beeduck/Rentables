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

    //Set methods
    public void setUsername(String newUsername){username = newUsername;}
    public void setFirstName(String newFirstName){firstName = newFirstName;}
    public void setLastName(String newLastName){lastName = newLastName;}
    public void setCreateDate(String newCreateDate){createDate = newCreateDate;}
    public void setLastEditDate(String newLastEditDate){lastEditDate = newLastEditDate;}
    public void setActive(boolean isActive){active = isActive;}

    //Get methods
    public boolean getActive(){return active;}
    public int getUserId(){return userId;}
    public String getUsername(){return username;}
    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public String getCreateDate(){return createDate;}
    public String getLastEditDate(){return lastEditDate;}
    public String getAccessToken(){return access_token;}
    public String getTokenType(){return token_type;}
    public String getRefreshToken(){return refresh_token;}
    public String getExpiresIn(){return expires_in;}
}
