package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import dataobject.*;

public class ServerConnection<DataObject> extends NotifyingThread {

    //Generic calls to the authentication server if needed for whatever reason.
    private final static String USER_LOGIN = "http://rentoauth.us-west-2.elasticbeanstalk.com/oauth/token";
    private final static String CREATE_USER = "http://rentoauth.us-west-2.elasticbeanstalk.com/users/createUser";
    private final static String USER_INFO = "http://rentoauth.us-west-2.elasticbeanstalk.com/users/userInfo/";
    //Generic calls to the api server if needed.
    private final static String GET_LISTING = "http://rentapi.us-west-2.elasticbeanstalk.com/userPosts/getPosts";

    //The object in question
    private DataObject dataObject;

    public ServerConnection(DataObject data){

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

        }else if(dataObject.getClass() == Integer.class){

            System.out.println("You put in an integer?");

        }else{

            throw new RuntimeException("Currently no implementation for the class: " + dataObject.getClass());
        }
    }

    private void getSpecifiedListings(){

        //The Listings object fields should be completed before ever getting to this step.

        Listings listings = (Listings) dataObject;
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

            Iterator<Listing> iterator = list.iterator();

            while(iterator.hasNext()){

                iterator.next().printProperties();
            }

        }catch(MalformedURLException malform){

            malform.printStackTrace();

        }catch(IOException IO){

            IO.printStackTrace();

        }catch(RuntimeException runtime){

            runtime.printStackTrace();

        }
    }

    private String createListingsURL(Listings listings){

        String customURL = GET_LISTING + "?";
        String[] theKeys = {"keywords", "minPrice", "maxPrice", "priceCategoryId"};
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
            connect.setRequestProperty("Authorization", "Basic cG9zdG1hbkFwaTo=");
            connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connect.setRequestProperty("charset", "UTF-8");

            DataOutputStream outputStream = new DataOutputStream(connect.getOutputStream());

            outputStream.write(data.getBytes());
            outputStream.flush();

            if(connect.getResponseCode() != 200){

                BufferedReader buffReader = new BufferedReader(new InputStreamReader(connect.getErrorStream()));
                String error;

                while((error = buffReader.readLine()) != null) {

                    this.addError(error);
                }

                buffReader.close();
                throw new RuntimeException();
            }
            
            connect.disconnect();



        }catch(MalformedURLException malform){

            malform.printStackTrace();

        }catch(IOException IO){

            IO.printStackTrace();

        }catch(RuntimeException runtime){

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
            System.out.println(json);

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
                    System.out.println(error);
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

    public String encodeString(String toEncode){

        try{

            return URLEncoder.encode(toEncode, "utf-8");

        }catch(UnsupportedEncodingException e){

            e.printStackTrace();
            System.exit(1);

        }

        return toEncode;
    }
}
