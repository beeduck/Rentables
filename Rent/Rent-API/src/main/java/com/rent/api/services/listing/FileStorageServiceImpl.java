package com.rent.api.services.listing;

import com.rent.utility.Constants;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Created by Asad on 11/27/2016.
 */

@Service
public class FileStorageServiceImpl implements FileStorageService {

    public String store(MultipartFile file, String name) {
        if(!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                File dir = new File(Constants.ROOT_FILE_UPLOAD_PATH + File.separator + "tmpFiles");
                if(!dir.exists()) {
                    dir.mkdirs();
                }
                File storedFile = new File(dir.getAbsolutePath() + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(storedFile));
                stream.write(bytes);
                stream.close();
                return "Success";
            } catch (IOException e) {
                e.printStackTrace();
                return "failed";
            }
        }
        else {
            return "file was empty....did not upload";
        }
    }

    public Stream<Path> loadAll() {
        return null;
    }

    public Path load(String fileName) {
        return null;
    }

    public Resource loadAsResource(String fileName) {
        return null;
    }

    public void deleteAll() {

    }
}
