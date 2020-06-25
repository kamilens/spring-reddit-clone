package reddit.clone.reddit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import reddit.clone.reddit.exception.RedditException;
import reddit.clone.reddit.service.StorageService;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    @Value("${app.file-upload-dir}")
    private String fileUploadDir;

    @PostConstruct
    private void postConstruct() {
        File directory = new File(fileUploadDir);
        if (!directory.exists() && !directory.mkdir()) {
            throw new RedditException(String.format("Could't create directory '%s'", fileUploadDir));
        }
    }

    @Override
    public String upload(MultipartFile multipartFile) throws IOException {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        String fileFullPath = generateFilePath(filename);

        validateFile(multipartFile, filename);

        File file = new File(fileFullPath);
        multipartFile.transferTo(file);
        return fileFullPath;
    }

    @Override
    public void download(String filename) {
    }

    @Override
    public void delete(String filePath) throws IOException {
        File file = new File(filePath);
        Files.deleteIfExists(file.toPath());
    }

    private void validateFile(MultipartFile multipartFile, String fileName) {
        if (multipartFile.isEmpty()) {
            throw new RedditException("Failed to store empty file " + fileName);
        }
        if (fileName.contains("..")) {
            throw new RedditException(
                    "Cannot store file with relative path outside current directory "
                            + fileName);
        }
    }

    private String generateFilePath(String fileName) {
        String fileExt = FilenameUtils.getExtension(fileName);
        return String.format("%s%s.%s", fileUploadDir, UUID.randomUUID().toString(), fileExt);
    }

}
