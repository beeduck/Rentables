package com.rent.api.services.listing;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Asad on 11/29/2016.
 */
public interface ListingImageService {

    String uploadImage(int listingId, MultipartFile[] files) throws IOException;

    ResponseEntity<Resource> getImageById(String uuid) throws IOException;

    byte[] getImageByListingId(HttpServletResponse response, int listingId) throws IOException;

    void deleteImage(String uuid);

    String deleteImagesByListing(int id);
}
