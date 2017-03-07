package server;

import android.util.Base64;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.rentables.testcenter.activity.MainActivity;

import dataobject.*;

public class ServerConnection<DataObject> extends NotifyingThread {

    //User based api calls
    public final static String USER_LOGIN = "http://rentapi.us-west-2.elasticbeanstalk.com/rent-oauth/oauth/token";
    public final static String CREATE_USER = "http://rentapi.us-west-2.elasticbeanstalk.com/rent-oauth/user";

    //Listing based api calls
    public final static String LISTING = "http://rentapi.us-west-2.elasticbeanstalk.com/listing";

    //Listing image based api calls
    public final static String LISTING_IMAGES="http://rentapi.us-west-2.elasticbeanstalk.com/listing-images";

    //The object in question
    private DataObject dataObject;


    private User currentUser;
    private String basicAuthUsername = "postmanApi";
    private String basicAuthPassword = "";
    private String basicAuth;

    public ServerConnection(DataObject data){

        encodeAuthorization();
        dataObject = data;
    }

    @Override
    public void doRun() {

        decision();

    }

    public DataObject getDataObject(){

        return dataObject;
    }

    private void decision(){

        //Deciding what to do based off the object

        if(dataObject.getClass() == dataobject.CreateUser.class){

            registerUser();

        }else if(dataObject.getClass() == dataobject.LoginUser.class){

            loginUser();

        }else if(dataObject.getClass() == dataobject.Listings.class){

            getSpecifiedListings();

        }else if(dataObject.getClass() == dataobject.CreateListing.class){

            createListing();

        }else if(dataObject.getClass() == dataobject.Image.class){

            getSpecifiedImage();

        }else if(dataObject.getClass() == Integer.class){

            System.out.println("You put in an integer?");

        }else{

            throw new RuntimeException("Currently no implementation for the class: " + dataObject.getClass());
        }
    }

    private void getSpecifiedImage(){

        Image specifiedImage = (Image) dataObject;

    }

    private void createListing(){

        CreateListing theNewListing = (CreateListing) dataObject;

        try{

            URL url = new URL(LISTING);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();

            connect.setRequestMethod("POST");
            connect.setRequestProperty("Content-Type", "application/json");
            connect.setRequestProperty("charset", "utf-8");
            connect.setRequestProperty("Authorization", "Bearer " + MainActivity.CURRENT_USER.getAccessToken());

            Gson gson = new Gson();
            String json = gson.toJson(theNewListing);

            DataOutputStream outputStream = new DataOutputStream(connect.getOutputStream());

            System.out.println(json);
            outputStream.write(json.getBytes());
            outputStream.flush();
            outputStream.close();

            if(connect.getResponseCode() != 200){

                BufferedReader reader = new BufferedReader(new InputStreamReader(connect.getErrorStream()));
                String next;

                while((next = reader.readLine()) != null){

                    System.out.println(next);
                }

                throw new RuntimeException("HTTP error with response code: " + connect.getResponseCode());
            }

            connect.disconnect();

        }catch(MalformedURLException malform){

            malform.printStackTrace();

        }catch(IOException IO){

            IO.printStackTrace();

        }catch(RuntimeException runtime){

            runtime.printStackTrace();
        }
    }

    private void getSpecifiedListings(){

        //The Listings object fields should be completed before ever getting to this step.

        Listings listings = (Listings) dataObject;
        removeSpacesInKeyWords(listings);
        String customURL = createListingsURL(listings);

        try{

            URL url = new URL(customURL);
            HttpURLConnection connect  = (HttpURLConnection) url.openConnection();

            connect.setRequestMethod("GET");
            connect.setRequestProperty("Content-Type", "application/json");
            connect.setRequestProperty("charset", "utf-8");

            if(connect.getResponseCode() != 200){

                throw new RuntimeException("HTTP error with response code: " + connect.getResponseCode());
            }

            BufferedReader buffReader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            Gson gson = new Gson();
            List<Listing> list = new ArrayList<>();

            String readLine;
            while((readLine = buffReader.readLine()) != null){

                list = gson.fromJson(readLine, new TypeToken<List<Listing>>(){}.getType());
            }

            listings.setListings((ArrayList)list);

            connect.disconnect();

        }catch(MalformedURLException malform){

            malform.printStackTrace();

        }catch(IOException IO){

            IO.printStackTrace();

        }catch(RuntimeException runtime){

            runtime.printStackTrace();

        }
    }

    private void removeSpacesInKeyWords(Listings listings){

        if(listings.getKeywords() != null) {

            listings.setKeywords(listings.getKeywords().replace(" ", "+"));

        }
    }

    private String createListingsURL(Listings listings){

        String customURL = LISTING + "?";
        String[] theKeys = {"keywords", "minPrice", "maxPrice", "priceCategoryId", "userId"};
        HashMap<String, String> fields = listings.getAllFields();

        if(!fields.isEmpty()){

            for(int i = 0; i < theKeys.length; i++){

                if(fields.containsKey(theKeys[i])){

                    customURL = customURL + theKeys[i] + "=" + fields.get(theKeys[i]);

                    if(i != (theKeys.length - 1)){

                        customURL += "&";
                    }
                }
            }
        }

        return customURL;
    }

    private void loginUser(){

        LoginUser loginUser = (LoginUser) dataObject;
        MainActivity.CURRENT_USER = new User();

        String data = encodeString("username") + "="
                + encodeString(loginUser.getUsername()) + "&"
                + encodeString("password") + "="
                + encodeString(loginUser.getPassword()) + "&"
                + encodeString("grant_type") + "="
                + encodeString("password");

        try{

            URL url = new URL(USER_LOGIN);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();

            connect.setRequestMethod("POST");
            connect.setRequestProperty("Authorization", "Basic " + basicAuth);
            connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connect.setRequestProperty("charset", "UTF-8");

            DataOutputStream outputStream = new DataOutputStream(connect.getOutputStream());

            outputStream.write(data.getBytes());
            outputStream.flush();

            if(connect.getResponseCode() != 200){

                BufferedReader buffReader = new BufferedReader(new InputStreamReader(connect.getErrorStream()));
                String error;

                while((error = buffReader.readLine()) != null) {

                    System.out.println(error);
                    this.addError(error);
                }

                buffReader.close();
                throw new RuntimeException();
            }

            BufferedReader buffReader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            Gson json = new Gson();
            MainActivity.CURRENT_USER = json.fromJson(buffReader.readLine(), User.class);

            connect.disconnect();

        }catch(MalformedURLException malform){

            malform.printStackTrace();

        }catch(IOException IO){

            IO.printStackTrace();
            this.addError(java.net.UnknownHostException.class.toString());

        }catch(RuntimeException runtime){

            //TODO This runtime exception should warn the user that there was an error with the server.
            runtime.printStackTrace();
        }

        System.out.println("Result of the URLEncoder: " + data);

    }

    private void registerUser(){

        //Register the user. Note: data should already be included in the data object that was handed
        //to this class

        CreateUser createUser = (CreateUser) dataObject;

        try{

            //Converting the CreateUser object into json
            Gson converter = new Gson();
            String json = converter.toJson(createUser);

            //Converting url
            URL url = new URL(this.CREATE_USER);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();

            //Setting the connection properties
            connect.setRequestMethod("POST");
            connect.setRequestProperty("Content-Type", "application/json");
            connect.setRequestProperty("charset", "utf-8");

            //Getting the output stream
            DataOutputStream outputStream = new DataOutputStream(connect.getOutputStream());

            //Writing to the output stream
            outputStream.write(json.getBytes());
            outputStream.flush();
            outputStream.close();

            //Catching any unsuccessful response codes
            if(connect.getResponseCode() != 200){

                BufferedReader buffReader = new BufferedReader(new InputStreamReader(connect.getErrorStream()));
                String error;

                while((error = buffReader.readLine()) != null){

                    this.addError(error);
                }

                throw new RuntimeException("HTTP error with response code: " + connect.getResponseCode());

            }else{

                System.out.println("Connection successful!");
            }

            //Disconnecting from the server
            connect.disconnect();


        }catch(MalformedURLException malformed){

            System.out.println("Error(MalformedURLException): " + malformed.toString());
            malformed.printStackTrace();

        }catch(IOException IO){

            System.out.println("Error(IOException): " + IO.toString());
            IO.printStackTrace();

        }catch(RuntimeException run){

            System.out.println("Error(RuntimeException): " + run.toString());
            run.printStackTrace();

        }
    }

    private void encodeAuthorization(){

        String combination = basicAuthUsername + ":" + basicAuthPassword;

        basicAuth = Base64.encodeToString(combination.getBytes(), Base64.DEFAULT);

    }

    private String encodeString(String toEncode){

        try{

            return URLEncoder.encode(toEncode, "utf-8");

        }catch(UnsupportedEncodingException e){

            e.printStackTrace();
            System.exit(1);

        }

        return toEncode;
    }
}
