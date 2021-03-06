package com.rent.utility;

/**
 * Created by duck on 11/1/16.
 */
public class Constants {
    // TOKEN CONSTANTS
    public static final int REGISTRATION_TOKEN_TIME_DURATION = 60*24;
    public static final int OAUTH_TOKEN_DURATION = 60*60*6;


    // CONTENT TYPES
    public static final String CONTENT_TYPE_JSON = "content-type=application/json";


    // OAUTH2 AUTHORIZATION
    public static final String OAUTH2_AUTH_USER =   "#oauth2.hasScope('user')";
    public static final String OAUTH2_AUTH_MODULE = "#oauth2.hasScope('module')";


    // ROLE AUTHORIZATION
    public static final String AUTH_ROLE_USER = "hasAuthority('ROLE_USER')";
    public static final String AUTH_ROLE_ADMIN = "hasAuthority('ROLE_ADMIN')";
    public static final String AUTH_ROLE_MODULE = "hasAuthority('ROLE_MODULE')";

    // SHARED PATHS

    public static final String SHARED_BASE_PATH = "/module";
    public static final String SHARED_COMPLETE_USER_REGISTRATION_PATH = "/register-user";
    public static final String SHARED_CHANGE_USER_EMAIL_PATH = "/update-user-email";


    // USER EMAIL PATHS
    public static final String BASE_PATH = "http://rentapi.us-west-2.elasticbeanstalk.com";
    public static final String BASE_MODULE_OAUTH = "/rent-oauth";

    public static final String USER_BASE_PATH = "/user";
    public static final String USER_CONFIRM_REGISTRATION_PATH = "/register";
    public static final String USER_CONFIRM_EMAIL_PATH = "/confirm-email";

    public static final String PATH_COMPLETE_USER_REGISTRATION = "/registerUser";


    // IMAGES
    public static final String ROOT_FILE_UPLOAD_PATH = "uploads-dir";

}
