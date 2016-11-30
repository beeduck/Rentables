package com.rent.api.controllers.listing;

import com.rent.api.services.listing.ListingImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by Asad on 11/29/2016.
 */

@RestController
@RequestMapping("/listingImages")
public class ListingImageController {

    @Autowired
    ListingImageService listingImageService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("listingId") int listingId,
                                   @RequestParam("file") MultipartFile file) throws IOException {
        return listingImageService.uploadImage(listingId,file);
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<Resource> serveFile(@PathVariable("uuid") String uuid) throws IOException {
        return listingImageService.getImageById(uuid);
    }

    @RequestMapping(value = "/byListing/{listingId}", method = RequestMethod.GET, produces = "application/zip")
    public byte[] serveFileByListing(@PathVariable("listingId") int listingId) throws IOException {
        return listingImageService.getImageByListingId(listingId);

    }

}
