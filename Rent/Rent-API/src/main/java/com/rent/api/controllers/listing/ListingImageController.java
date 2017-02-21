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
import java.util.List;

/**
 * Created by Asad on 11/29/2016.
 */

@RestController
@RequestMapping("/listing-images")
public class ListingImageController {

    @Autowired
    ListingImageService listingImageService;

    @RequestMapping(method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("listingId") int listingId,
                                   @RequestParam("files") MultipartFile[] files) throws IOException {
        return listingImageService.uploadImage(listingId,files);
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    public void serveFile(HttpServletResponse response, @PathVariable("uuid") String uuid) throws IOException {
        listingImageService.getImageByIdS3(response,uuid);
    }

    @RequestMapping(value = "/get-image-names/{listingId}", method = RequestMethod.GET)
    public List<String> getImageNamesByListingId(@PathVariable("listingId") int listingId) {
        return listingImageService.getImageNamesByListingId(listingId);
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
    public void deleteByUUID(@PathVariable("uuid") String uuid) {
        listingImageService.deleteByImageUUID(uuid);
    }

    @RequestMapping(value = "/deleteByListing/{listingId}", method = RequestMethod.DELETE)
    public void deleteByListingId(@PathVariable("listingId") int listingId) {
        listingImageService.deleteByListingId(listingId);
    }
}