package one.primedoc.backend.service.impl;

import one.primedoc.backend.entity.statics.FileInfoEntity;
import one.primedoc.backend.enums.DocumentType;
import one.primedoc.backend.exception.DeleteFileException;
import one.primedoc.backend.exception.DuplicateException;
import one.primedoc.backend.exception.RecordNotFoundException;
import one.primedoc.backend.repository.FileInfoRepository;
import one.primedoc.backend.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.zwobble.mammoth.DocumentConverter;
import org.zwobble.mammoth.Result;

import javax.print.Doc;
import javax.swing.text.Document;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private final FileInfoRepository fileInfoRepository;

    @Value("${docs.file.storage.path}")
    private String uploadPath;

    @Value("${docs.file.download.url}")
    private String downloadUrl;

    public FileServiceImpl(FileInfoRepository fileInfoRepository) {
        this.fileInfoRepository = fileInfoRepository;
    }

    @Override
    public FileInfoEntity uploadFile(MultipartFile file, DocumentType type) throws IOException {
        fileInfoRepository.findByDocumentType(type).ifPresent(ex -> {
            throw new DuplicateException("(File) Record already exists with type: " + type);
        });
        String code = UUID.randomUUID().toString();
        file.transferTo(new File(uploadPath + code + "_" + Objects.requireNonNull(file.getOriginalFilename())));
        DocumentConverter converter = new DocumentConverter();
        Result<String> result = converter.convertToHtml(new File(uploadPath + code + "_" + Objects.requireNonNull(file.getOriginalFilename())));
        String html = result.getValue(); // The generated HTML
        File htmlFile = new File((uploadPath + "html/" + code + "_" + file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf(".")) + ".html"));
        FileWriter writer = new FileWriter(htmlFile);
        writer.write(html);
        writer.close();
        return fileInfoRepository.save(FileInfoEntity.builder().fileName(file.getOriginalFilename()).code(code).documentType(type).url(downloadUrl).build());
    }

    @Override
    public ResponseEntity<Resource> loadFile(String code) throws FileNotFoundException {
        FileInfoEntity fileInfoEntity = fileInfoRepository.findByCode(code);
        File file = new File(uploadPath + code + "_" + fileInfoEntity.getFileName().replaceAll(" ", "_"));
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"" + fileInfoEntity.getFileName() + "\"");
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

    @Override
    public List<FileInfoEntity> getAllInfo() {
        return fileInfoRepository.findAll();
    }

    @Override
    public FileInfoEntity getInfoById(Long id) {
        return fileInfoRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("(File) Record not found with id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        fileInfoRepository.deleteById(id);
    }

    @Override
    public String getHTML(String type) {
        StringBuilder response = new StringBuilder();
        try {
            FileInfoEntity fileInfoEntity = fileInfoRepository.findByDocumentType(DocumentType.valueOf(type)).orElseThrow(() -> new RecordNotFoundException("(File) Record not found with type: " + type));
            File htmlFileName = new File(fileInfoEntity.getCode() + "_" + fileInfoEntity.getFileName().substring(0, fileInfoEntity.getFileName().indexOf(".")) + ".html");
            String htmlFilePath = uploadPath + "html/" + htmlFileName;
            File file = new File(htmlFilePath);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                response.append(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return response.toString();
    }

    public void deleteFile(Path path) {
        try {
            Files.delete(path);
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFileByType(DocumentType type) {
        FileInfoEntity file = fileInfoRepository.findByDocumentType(type).orElseThrow(() -> new RecordNotFoundException("(File) Record not found with type: " + type));
        Path htmlFilePath = Paths.get(uploadPath + "html/" + file.getCode() + "_" + file.getFileName().substring(0, file.getFileName().indexOf(".")) + ".html");
        Path docFilePath = Paths.get(uploadPath + file.getCode() + "_" + file.getFileName());
        deleteFile(htmlFilePath);
        deleteFile(docFilePath);
        fileInfoRepository.delete(file);

    }
}
