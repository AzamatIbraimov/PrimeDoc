package one.primedoc.backend.service;

import one.primedoc.backend.entity.statics.FileInfoEntity;
import one.primedoc.backend.enums.DocumentType;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileService {
    public FileInfoEntity uploadFile(MultipartFile file, DocumentType type) throws IOException;
    public ResponseEntity<Resource> loadFile(String code) throws FileNotFoundException;
    public List<FileInfoEntity> getAllInfo();
    public FileInfoEntity getInfoById(Long id);
    public void deleteById(Long id);
    public String getHTML(String code);

    void deleteFileByType(DocumentType type);
}
