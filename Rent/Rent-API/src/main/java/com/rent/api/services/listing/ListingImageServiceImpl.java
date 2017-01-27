package com.rent.api.services.listing;

import com.rent.api.dao.listing.ListingImageRepository;
import com.rent.api.entities.listing.ListingImage;
import com.rent.api.utility.AWSConnector;
import com.rent.utility.Constants;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Asad on 11/29/2016.
 */

@Service
public class ListingImageServiceImpl implements ListingImageService {

    @Autowired
    ListingImageRepository listingImageRepository;

    public String uploadImage(int listingId, MultipartFile[] files) throws IOException {
        for(MultipartFile file : files) {
            if(!file.isEmpty()) {
                ListingImage listingImage = new ListingImage();
                listingImage.setListingId(listingId);
                String uuid = UUID.randomUUID().toString();
                listingImage.setImageUUID(uuid);
                String ext = getFileExtension(file);
                String filePath = AWSConnector.uploadImageToS3(file,listingId,uuid,ext);
                File dir = new File(Constants.ROOT_FILE_UPLOAD_PATH + File.separator + Integer.toString(listingId));
//                if (!dir.exists()) {
//                    dir.mkdirs();
//                }
                listingImage.setPath(filePath);
                File storedFile = new File(filePath);
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(storedFile));
                stream.write(bytes);
                stream.close();
                listingImageRepository.save(listingImage);
//                File storedFile = new File(filePath);
//                byte[] bytes = file.getBytes();
//                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(storedFile));
//                stream.write(bytes);
//                stream.close();
            }
            else {
                return "failed";
            }
        }
        return "Success";
    }

    public ResponseEntity<Resource> getImageById(String uuid) throws IOException {
        ListingImage listingImage = listingImageRepository.getImageById(uuid);
        Resource file = new FileSystemResource(listingImage.getPath());
        String type = Files.probeContentType(file.getFile().toPath());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .header(HttpHeaders.CONTENT_TYPE, type)
                .body(file);
    }

    public byte[] getImageByListingId(HttpServletResponse response, int listingId) throws IOException {
        List<ListingImage> list = listingImageRepository.getImageByListingId(listingId);
        List<File> files = new ArrayList<>();
        for(ListingImage e : list) {
            File file = new File(e.getPath());
            files.add(file);
        }
        response.setContentType("application/zip");
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"test.zip\"");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);
        for(File file : files) {
            zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
            FileInputStream fileInputStream = new FileInputStream(file);
            IOUtils.copy(fileInputStream, zipOutputStream);
            fileInputStream.close();
            zipOutputStream.closeEntry();
        }
        if (zipOutputStream != null) {
            zipOutputStream.finish();
            zipOutputStream.flush();
            IOUtils.closeQuietly(zipOutputStream);
        }
        IOUtils.closeQuietly(bufferedOutputStream);
        IOUtils.closeQuietly(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private String getFileExtension(MultipartFile file) {
        int check = file.getOriginalFilename().lastIndexOf('.');
        return file.getOriginalFilename().substring(check);
    }
}