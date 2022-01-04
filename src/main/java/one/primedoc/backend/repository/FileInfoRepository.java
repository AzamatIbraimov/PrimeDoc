package one.primedoc.backend.repository;

import one.primedoc.backend.entity.statics.FileInfoEntity;
import one.primedoc.backend.enums.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileInfoRepository extends JpaRepository<FileInfoEntity, Long> {
    public FileInfoEntity findByCode(String code);

    Optional<FileInfoEntity> findByDocumentType(DocumentType valueOf);
}
