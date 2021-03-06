package com.rent.api.utility;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Created by Asad on 1/25/2017.
 */
public class AWSConnector {

    private static AWSCredentials credentials = new BasicAWSCredentials(System.getenv("AWS_ACCESS_KEY_ID"),
            System.getenv("AWS_SECRET_ACCESS_KEY"));
    private static AmazonS3 s3Client = new AmazonS3Client(credentials);
    private static String bucketName = "rentables-file-server";
    private static String folder = "uploads-dir";

    public static String uploadImageToS3(MultipartFile multipartFile, int listingId, String uuid, String ext) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();
        String folderName = folder + "/" + Integer.toString(listingId);
        String fileName = folderName + "/" + uuid + ext;
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream, new ObjectMetadata()).withCannedAcl(CannedAccessControlList.PublicRead));
        inputStream.close();
        return fileName;
    }

    public static S3Object getImageFromS3(String path) {
        S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName,path));
        return s3Object;
    }

    public static void deleteImageFromS3(String path) {
        s3Client.deleteObject(new DeleteObjectRequest(bucketName,path));
    }
}