package com.rent.api.services.listing;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Asad on 11/29/2016.
 */
public interface ListingImageService {

    String uploadImage(int listingId, MultipartFile[] files) throws Exception;

    void getImageByIdS3(HttpServletResponse response, String uuid) throws IOException;

    List<String> getImageNamesByListingId(int listingId);

    void deleteByImageUUID(String uuid) throws Exception;

    void deleteByListingId(int listingId) throws Exception;
}