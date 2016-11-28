package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.*;

import dataobject.*;

public class ServerConnection<DataObject> extends NotifyingThread {

    //Generic calls to the server if needed for whatever reason
    private final static String USER_LOGIN = "http://rentoauth.us-west-2.elasticbeanstalk.com/oauth/token";
    private final static String CREATE_USER = "http://rentoauth.us-west-2.elasticbeanstalk.com/users/createUser";
    private final static String USER_INFO = "http://rentoauth.us-west-2.elasticbeanstalk.com/users/userInfo/";

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

        }else if(dataObject.getClass() == Integer.class){

            System.out.println("You put in an integer?");

        }else{

            throw new RuntimeException("Currently no implementation for the class: " + dataObject.getClass());
        }
    }

    private void loginUser(){

        LoginUser loginUser = (LoginUser) dataObject;

        String data = URLEncoder.encode("username") + "="
                + URLEncoder.encode(loginUser.getUsername()) + "&"
                + URLEncoder.encode("password") + "="
                + URLEncoder.encode(loginUser.getPassword()) + "&"
                + URLEncoder.encode("grant_type") + "="
                + URLEncoder.encode("password");

        try{

            URL url = new URL(this.USER_LOGIN);
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
}
