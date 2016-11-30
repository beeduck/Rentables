package com.rent.api.services.listing;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created by Asad on 11/29/2016.
 */
public interface ListingImageService {

    String uploadImage(int listingId, MultipartFile file) throws IOException;

    ResponseEntity<Resource> getImageById(String uuid) throws IOException;

    byte[] getImageByListingId(int listingId) throws IOException;
}
