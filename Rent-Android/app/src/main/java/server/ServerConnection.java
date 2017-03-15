package server;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URI;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.rentables.testcenter.activity.MainActivity;

import dataobject.*;

public class ServerConnection<DataObject> extends NotifyingThread {

    //User based api calls
    public final static String PATH = "http://rentrent.ddns.net/";

    //Listing image based api calls
    public final static String BASE_DEV_API = "http://10.0.2.2:8080";
    public final static String BASE_DEV_AUTH = "http://10.0.2.2:8081";

//    public final static String BASE_DEV_API = "http://rentables.bounceme.net";
//    public final static String BASE_DEV_AUTH = "http://rentables.bounceme.net/rent-oauth";
//    public final static String BASE_DEV_API = "http://localhost:8080";
//    public final static String BASE_DEV_AUTH = "http://localhost:8081";

    public final static String BASE_DEPLOYMENT_API = "http://rentapi.us-west-2.elasticbeanstalk.com";
    public final static String BASE_DEPLOYMENT_AUTH = "http://rentapi.us-west-2.elasticbeanstalk.com/rent-oauth";

    public final static String USER_LOGIN = BASE_DEV_AUTH + "/oauth/token";
    public final static String CREATE_USER = BASE_DEV_AUTH + "/user";

    //Listing based api calls
    public final static String LISTING = BASE_DEV_API + "/listing";

    //Listing image based api calls
    public final static String LISTING_IMAGES = BASE_DEV_API + "/listing-images";
    public final static String RENTING = BASE_DEV_API + "/rent";

    //The object in question
    private DataObject dataObject;


    private User currentUser;
    private String basicAuthUsername = "postmanApi";
    private String basicAuthPassword = "";
    private String basicAuth;
    private String lineFeed = "\r\n";
    private String hyphens = "--";
    private String formBoundary = "Rentables";
    private String boundary = hyphens + formBoundary + lineFeed;

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

        }else if(dataObject.getClass() == ListingImage.class){

            ListingImage imageToUpload = (ListingImage) dataObject;
            uploadImage(imageToUpload);

        }else if(dataObject.getClass() == Integer.class){

            System.out.println("You put in an integer?");

        }else if(dataObject.getClass() == dataobject.RentRequest.class) {

            RentRequest request = (RentRequest)dataObject;
            switch (request.getOption()) {
                case CREATE_REQUEST:
                    requestToRent();
                    break;
                case REQUEST_FOR_USER:
                    getRequestsForUser();
                    break;
                default:
                    break;
            }

        }else{

            throw new RuntimeException("Currently no implementation for the class: " + dataObject.getClass());
        }
    }

    private void getRequestsForUser() {
        RentRequest rentRequest = (RentRequest)dataObject;
        try {
            URL url = new URL(RENTING + "/requested/");
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            connect.setRequestMethod("GET");
            connect.setRequestProperty("Content-Type", "application/json");
            connect.setRequestProperty("charset", "utf-8");
            connect.setRequestProperty("Authorization", "Bearer " + MainActivity.CURRENT_USER.getAccessToken());
            if(connect.getResponseCode() != 200){

                BufferedReader buffReader = new BufferedReader(new InputStreamReader(connect.getErrorStream()));
                String error;

                while((error = buffReader.readLine()) != null){

                    this.addError(error);
                }

                throw new RuntimeException("HTTP error with response code: " + connect.getResponseCode());

            }else{
                BufferedReader buffReader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
                Gson gson = new Gson();
                List<RentRequest> list = new ArrayList<>();

                String readLine;
                while((readLine = buffReader.readLine()) != null){

                    list = gson.fromJson(readLine, new TypeToken<List<RentRequest>>(){}.getType());
                }

                rentRequest.setRentRequests(list);

                connect.disconnect();
                System.out.println("Connection successful!");
            }
            connect.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getSpecifiedImage(){

        ListingImage specifiedListingImage = (ListingImage) dataObject;

    }
    private void createListing(){

        CreateListing theNewListing = (CreateListing) dataObject;
        ListingImage image = theNewListing.getListingImage();

        try{

            URL url = new URL(LISTING);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();

            connect.setRequestMethod("POST");
            connect.setRequestProperty("Content-Type", "application/json");
            connect.setRequestProperty("charset", "utf-8");
            connect.setRequestProperty("Authorization", "Bearer " + MainActivity.CURRENT_USER.getAccessToken());

            Gson gson = new GsonBuilder().setExclusionStrategies(new ImageExclusionStrategy()).serializeNulls().create();
            String json = gson.toJson(theNewListing);

            DataOutputStream outputStream = new DataOutputStream(connect.getOutputStream());

            outputStream.write(json.getBytes());
            outputStream.flush();
            outputStream.close();

            if(connect.getResponseCode() != 200){

                BufferedReader reader = new BufferedReader(new InputStreamReader(connect.getErrorStream()));
                String next;

                while((next = reader.readLine()) != null){

                    addError(next);
                    System.out.println(next);
                }

                throw new RuntimeException("HTTP error with response code: " + connect.getResponseCode());
            }

            //Get the id that was returned from the newly created listing.
            BufferedReader reader = new BufferedReader(new InputStreamReader(connect.getInputStream()));
            image.setId(getCreatedListingId(reader));

            //Disconnect.
            connect.disconnect();

            //Upload the image.
            uploadImage(image);

        }catch(MalformedURLException malform){

            malform.printStackTrace();

        }catch(IOException IO){

            IO.printStackTrace();

        }catch(RuntimeException runtime){

            runtime.printStackTrace();
        }
    }

    private void uploadImage(ListingImage image){

        //The ListingImage should have its designated Listing id within it and the path as well.

        if(image != null){
            if(image.getId() != -1){

                try{

                    URL url = new URL(LISTING_IMAGES);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("content-type", "multipart/form-data; boundary=" + formBoundary);
                    connection.setRequestProperty("authorization", "Bearer " + MainActivity.CURRENT_USER.getAccessToken());
                    connection.setRequestProperty("cache-control", "no-cache");

                    DataOutputStream requestBody = new DataOutputStream(connection.getOutputStream());

                    createImageUploadRequestBody(requestBody, image);

                    if(connection.getResponseCode() != 200){

                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                        String next;

                        while((next = reader.readLine()) != null){

                            System.out.println(next);
                            addError(next);
                        }

                        throw new RuntimeException();
                    }

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                    String next;

                    while((next = reader.readLine()) != null){

                        System.out.println(next);
                    }

                    connection.disconnect();
                    image.getImageStream().close();

                } catch (MalformedURLException e) {

                    e.printStackTrace();

                } catch (IOException e) {

                    e.printStackTrace();

                } catch (RuntimeException e){

                    e.printStackTrace();
                }


            }else{

                throw new RuntimeException("Error: The Image being uploaded does not have an associated Listing id.");
            }
        }
    }

    private void createImageUploadRequestBody(DataOutputStream requestBody, ListingImage image) throws IOException {

        //Creating the body of the request.
        int id = image.getId();

        //TODO Change this placeholder eventually?
        String placeHolder = "placeholder.jpg";

        //Add the listing Id
        requestBody.writeBytes(boundary);
        requestBody.writeBytes("Content-Disposition: form-data; name=\"listingId\"" + lineFeed);
        requestBody.writeBytes(lineFeed);
        requestBody.writeBytes(String.valueOf(id) + lineFeed);

        //Add the image
        requestBody.writeBytes(boundary);
        requestBody.writeBytes("Content-Disposition: form-data; name=\"files\"; filename=\"" + placeHolder + "\"" + lineFeed);
        requestBody.writeBytes("Content-Type: image/jpeg" + lineFeed);
        requestBody.writeBytes(lineFeed);

        writeFile(image.getImageStream(), requestBody);
        requestBody.writeBytes(lineFeed);
        requestBody.writeBytes(hyphens + formBoundary + hyphens);
    }

    private void writeFile(FileInputStream file, DataOutputStream requestBody) throws IOException {

        Bitmap bmp = BitmapFactory.decodeStream(file);
        bmp.compress(Bitmap.CompressFormat.JPEG, 30, requestBody);

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

    private void requestToRent() {
        RentRequest rentRequest = (RentRequest)dataObject;
        try {
            URL url = new URL(RENTING + "/request/" + Integer.toString(rentRequest.getListingId()));
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            connect.setRequestMethod("POST");
            connect.setRequestProperty("Content-Type", "application/json");
            connect.setRequestProperty("charset", "utf-8");
            connect.setRequestProperty("Authorization", "Bearer " + MainActivity.CURRENT_USER.getAccessToken());
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
            connect.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
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

    private int getCreatedListingId(BufferedReader reader){

        Gson gson = new Gson();

        Listing createdListing = gson.fromJson(reader, Listing.class);

        return createdListing.getId();
    }
}
