package one.primedoc.backend.controller;

import one.primedoc.backend.entity.statics.FileInfoEntity;
import one.primedoc.backend.enums.DocumentType;
import one.primedoc.backend.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/docs")
public class DocFileRestController {

    private final FileService fileService;

    public DocFileRestController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/")
    public List<FileInfoEntity> getAllInfo() {
        return fileService.getAllInfo();
    }

    @DeleteMapping("/{type}")
    public void deleteByType(@PathVariable("type") String type) {
        fileService.deleteFileByType(DocumentType.valueOf(type));
    }

    @GetMapping("/{id}")
    public FileInfoEntity getAllInfo(@PathVariable("id") Long id) {
        return fileService.getInfoById(id);
    }

    @GetMapping("/download/{code}")
    public ResponseEntity<Resource> getAllInfo(@PathVariable("code") String code) throws FileNotFoundException {
        return fileService.loadFile(code);
    }

    @GetMapping("/html/{type}")
    public String getHTML(@PathVariable("type") String type) {
        return fileService.getHTML(type);
    }

    @PostMapping("/upload/{type}")
    public ResponseEntity<FileInfoEntity> uploadDocFile(@NotNull @RequestParam("file") MultipartFile file, @PathVariable("type") String type) throws IOException {
        return ResponseEntity.ok().body(fileService.uploadFile(file, DocumentType.valueOf(type)));
    }
}
