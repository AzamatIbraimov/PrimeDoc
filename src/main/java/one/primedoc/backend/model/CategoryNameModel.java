package one.primedoc.backend.model;


import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryNameModel {
    private Long id;
    private String name;

}
