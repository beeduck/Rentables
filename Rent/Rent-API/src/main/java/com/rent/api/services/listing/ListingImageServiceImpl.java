package com.rent.api.services.listing;

import com.rent.api.dao.listing.ListingImageRepository;
import com.rent.api.dao.listing.ListingRepository;
import com.rent.api.entities.listing.ListingImage;
import com.amazonaws.services.cloudformation.model.Output;
import com.amazonaws.services.s3.model.S3Object;
import com.rent.api.utility.AWSConnector;
import com.rent.api.utility.security.UserSecurity;
import com.rent.utility.Constants;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    ListingRepository listingRepository;

    @Transactional
    public String uploadImage(int listingId, MultipartFile[] files) throws Exception {
        if (listingRepository.existsByUserIdAndId(UserSecurity.getUserId(), listingId)) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    ListingImage listingImage = new ListingImage();
                    listingImage.setListingId(listingId);
                    String uuid = UUID.randomUUID().toString();
                    listingImage.setImageUUID(uuid);
                    String ext = getFileExtension(file);
                    String filePath = AWSConnector.uploadImageToS3(file, listingId, uuid, ext);
                    listingImage.setPath(filePath);
                    listingImageRepository.save(listingImage);
                } else {
                    return "failed";
                }
            }
        }
        else {
            throw new Exception("User did not make this listing");
        }
        return "Success";
    }

    @Transactional(readOnly = true)
    public void getImageByIdS3(HttpServletResponse response, String uuid) throws IOException {
        ListingImage listingImage = listingImageRepository.findByImageUUID(uuid);
        S3Object s3Object = AWSConnector.getImageFromS3(listingImage.getPath());
        response.setContentType("image/jpeg");
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition","attachment; filename=\"" + uuid + "\"");
        InputStream stream = s3Object.getObjectContent();
        OutputStream os = response.getOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = stream.read(data, 0, data.length)) != -1) {
            os.write(data, 0, nRead);
        }
        os.flush();
        stream.close();
        os.close();
    }

    @Transactional(readOnly = true)
    public List<String> getImageNamesByListingId(int listingId) {
        List<ListingImage> imageList = listingImageRepository.findAllByListingId(listingId);
        List<String> imageNameList = new ArrayList<>();
        for(ListingImage e : imageList) {
            imageNameList.add(e.getImageUUID());
        }
        return imageNameList;
    }

    @Transactional
    public void deleteByImageUUID(String uuid) throws Exception {
        int listingId = listingImageRepository.findByImageUUID(uuid).getListingId();
        if(listingRepository.existsByUserIdAndId(UserSecurity.getUserId(),listingId)) {
            String path = listingImageRepository.findByImageUUID(uuid).getPath();
            AWSConnector.deleteImageFromS3(path);
            listingImageRepository.deleteByImageUUID(uuid);
        }
        else {
            throw new Exception("User did not make this listing");
        }
    }

    @Transactional
    public void deleteByListingId(int listingId) throws Exception {
        List<ListingImage> list = listingImageRepository.findAllByListingId(listingId);
        for(ListingImage e : list) {
            deleteByImageUUID(e.getImageUUID());
        }
        AWSConnector.deleteImageFromS3(Constants.ROOT_FILE_UPLOAD_PATH + "/" + Integer.toString(listingId));
    }

    private String getFileExtension(MultipartFile file) {
        int check = file.getOriginalFilename().lastIndexOf('.');
        return file.getOriginalFilename().substring(check);
    }
}