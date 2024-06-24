package co.develhope.fileUploadAndDownload.services;

import co.develhope.fileUploadAndDownload.models.FileEntity;
import co.develhope.fileUploadAndDownload.repositories.FileRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    private final String uploadDir = "uploads/";

    @PostConstruct
    public void init() {
        File uploadDirFile = new File(uploadDir);
        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }
    }

    public FileEntity saveFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        File targetFile = new File(uploadDir + fileName);

        try (OutputStream os = new FileOutputStream(targetFile)) {
            os.write(file.getBytes());
        }

        FileEntity fileEntity = new FileEntity();
        fileEntity.setFileName(fileName);
        fileEntity.setFileType(file.getContentType());
        fileEntity.setFilePath(targetFile.getAbsolutePath());

        return fileRepository.save(fileEntity);
    }

    public Optional<FileEntity> getFile(Long id) {
        return fileRepository.findById(id);
    }

    public byte[] getFileData(FileEntity fileEntity) throws IOException {
        File file = new File(fileEntity.getFilePath());
        return Files.readAllBytes(file.toPath());
    }
}