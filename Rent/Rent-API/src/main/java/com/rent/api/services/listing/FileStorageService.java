package com.rent.api.services.listing;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Created by Asad on 11/27/2016.
 */

public interface FileStorageService {

    String store(MultipartFile file, String name);

    Resource loadAsResource(String filename);

    void delete(String filename);
}
