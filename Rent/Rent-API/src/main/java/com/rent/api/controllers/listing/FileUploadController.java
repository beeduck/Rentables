package com.rent.api.controllers.listing;

import com.rent.api.services.listing.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Asad on 11/27/2016.
 */

@RestController
@RequestMapping("/fileUpload")
public class FileUploadController {

    @Autowired
    FileStorageService fileStorageService;


}
