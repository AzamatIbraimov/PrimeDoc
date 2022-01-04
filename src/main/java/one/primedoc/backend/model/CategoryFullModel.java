package one.primedoc.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.primedoc.backend.entity.DoctorEntity;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryFullModel {
    private Long id;
    private String image;
    private String name;
    private String description;
    private String illness;
    private Set<DoctorShortModel> doctors;
}
