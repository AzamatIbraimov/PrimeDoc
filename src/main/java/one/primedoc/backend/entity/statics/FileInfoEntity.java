package one.primedoc.backend.entity.statics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.primedoc.backend.enums.DocumentType;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity(name = "file_info")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoEntity extends BaseEntity{
    @Id
    @SequenceGenerator(name = "file_seq", sequenceName = "file_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_seq")
    private Long id;

    private String fileName;

    private String code;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type")
    private DocumentType documentType;

    private String url;
}