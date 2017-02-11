package com.rent.api.controllers.listing;

import com.amazonaws.services.xray.model.Http;
import com.rent.api.services.listing.ListingImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
                                   @RequestParam("files") MultipartFile[] files) throws IOException {
        return listingImageService.uploadImage(listingId,files);
    }

//    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
//    public ResponseEntity<Resource> serveFile(@PathVariable("uuid") String uuid) throws IOException {
//        return listingImageService.getImageById(uuid);
//    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    public void serveFile(HttpServletResponse response, @PathVariable("uuid") String uuid) throws IOException {
        listingImageService.getImageByIdS3(response,uuid);
    }

    @RequestMapping(value = "/byListingId/{listingId}", method = RequestMethod.GET, produces = "application/zip")
    public byte[] serveFileByListing(HttpServletResponse response, @PathVariable("listingId") int listingId) throws IOException {
        return listingImageService.getImageByListingId(response, listingId);
    }

    @RequestMapping(value = "/deleteByUUID/{uuid}", method = RequestMethod.DELETE)
    public void deleteByUUID(@PathVariable("uuid") String uuid) {
        listingImageService.deleteByImageUUID(uuid);
    }

    @RequestMapping(value = "/deleteByListing/{listingId}", method = RequestMethod.DELETE)
    public void deleteByListingId(@PathVariable("listingId") int listingId) {
        listingImageService.deleteByListingId(listingId);
    }
}