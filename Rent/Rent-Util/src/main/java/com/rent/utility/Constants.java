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
    public static final String PATH_COMPLETE_USER_REGISTRATION = "/registerUser";
}
