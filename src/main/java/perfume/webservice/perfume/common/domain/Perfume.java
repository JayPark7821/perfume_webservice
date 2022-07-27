package perfume.webservice.perfume.common.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import perfume.webservice.common.entity.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@ToString
public class Perfume extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "perfume_id")
    private Long id;

    @Column(name = "perfume_name")
    private String name;

    @Column(name = "perfume_desc")
    private String description;

    @OneToMany(mappedBy = "perfume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FragranceGroup> fragranceGroup = new ArrayList<>();

    public void addFragrance(FragranceGroup fragrance) {
        this.fragranceGroup.add(fragrance);
        fragrance.setPerfumeRelation(this);
    }

    @Builder
    public Perfume(String name, String description, List<FragranceGroup> fragranceGroup) {
        this.name = name;
        this.description = description;
        if (fragranceGroup == null) {
            this.fragranceGroup = new ArrayList<>();
        } else{
            for (FragranceGroup mapping : fragranceGroup) {
                this.addFragrance(mapping);
            }
        }

    }

    public void update(String name, String description, List<FragranceGroup> fragranceGroup) {
        this.name = name;
        this.description = description;
        if (fragranceGroup == null) {
            this.fragranceGroup = new ArrayList<>();
        } else{
            for (FragranceGroup fragrance : fragranceGroup) {
                this.addFragrance(fragrance);
            }
        }


    }



}
