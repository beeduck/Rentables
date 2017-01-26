package com.rent.api.controllers.listing;

import com.rent.api.services.listing.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Asad on 11/27/2016.
 */

@RestController
@RequestMapping("/files")
public class FileUploadController {

    @Autowired
    FileStorageService fileStorageService;

    @RequestMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = fileStorageService.loadAsResource(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam("name") String name,
                                   @RequestParam("file") MultipartFile file) {
        return fileStorageService.store(file, name);
    }
}