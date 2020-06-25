package reddit.clone.reddit.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {

    String upload(MultipartFile multipartFile) throws IOException;

    void download(String filePath);

    void delete(String filePath) throws IOException;

}
