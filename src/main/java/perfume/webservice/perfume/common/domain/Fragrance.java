package perfume.webservice.perfume.common.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.common.entity.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
public class Fragrance extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fragrance_id")
    private Long id;

    @OneToMany(mappedBy = "fragrance")
    private List<FragranceGroup> fragranceGroup = new ArrayList<>();

    private String fragranceName;
    private String fragranceDesc;

    public void addPerfume(FragranceGroup fragrance) {
        this.fragranceGroup.add(fragrance);
        fragrance.setFragranceRelation(this);
    }

    @Builder
    public Fragrance(String fragranceName, String fragranceDesc, List<FragranceGroup> fragranceGroup) {
        this.fragranceName = fragranceName;
        this.fragranceDesc = fragranceDesc;
        if (fragranceGroup == null) {
            this.fragranceGroup = new ArrayList<>();
        } else {
            for (FragranceGroup mapping : fragranceGroup) {
                this.addPerfume(mapping);
            }
        }
    }
}
