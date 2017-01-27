package com.rent.api.utility;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Created by Asad on 1/25/2017.
 */
public class AWSConnector {

    private static AWSCredentials credentials = new BasicAWSCredentials();
    private static AmazonS3 s3Client = new AmazonS3Client(credentials);
    private static String bucketName = "rentables-file-server";
    private static String folder = "uploads-dir";

    public static String uploadImageToS3(MultipartFile multipartFile, int listingId, String uuid, String ext) throws IOException {
        File file = convertFile(multipartFile);
        String folderName = folder + "/" + Integer.toString(listingId);
        String fileName = folderName + "/" + uuid + ext;
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
        return fileName;
    }

    private static void createFolder(int listingId) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(0);
        InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
                folder + "/" + Integer.toString(listingId), emptyContent, metadata);
        s3Client.putObject(putObjectRequest);
    }

    private static File convertFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}