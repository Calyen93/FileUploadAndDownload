package co.develhope.fileUploadAndDownload.repositories;

import co.develhope.fileUploadAndDownload.models.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
