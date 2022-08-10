package perfume.webservice.perfume.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.common.entity.BaseTimeEntity;
import perfume.webservice.perfume.domain.dto.save.PerfumeSaveDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
public class Perfume extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "perfume_id")
    private Long id;

    @Column(name = "perfume_name")
    private String name;

    @Column(name = "perfume_desc")
    private String description;

    @JsonManagedReference
    @OneToMany(mappedBy = "perfume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FragranceGroup> fragranceGroup = new ArrayList<>();

    @OneToMany(mappedBy = "perfume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PerfumeKeyword> perfumeKeyword = new ArrayList<>();

 
    public void addFragrance(Fragrance fragrance, int percentage) {
        FragranceGroup fragraceGroup = FragranceGroup.builder()
                .perfume(this)
                .fragrance(fragrance)
                .containPercentage(percentage)
                .build();

        this.fragranceGroup.add(fragraceGroup);
        fragraceGroup.setPerfumeRelation(this);
    }

    private void addKeyword(PerfumeKeyword perfumeKeyword) {
        this.perfumeKeyword.add(perfumeKeyword);
        perfumeKeyword.setPerfumeRelation(this);
    }

    private void setKeyword(List<PerfumeKeyword> perfumeKeywords) {
        this.perfumeKeyword = perfumeKeywords;
        for (PerfumeKeyword keyword : perfumeKeywords) {
            keyword.setPerfumeRelation(this);
        }
    }

    private void setFragranceGroup(List<FragranceGroup> fragranceGroup) {
        if (fragranceGroup != null) {
            this.fragranceGroup = fragranceGroup;
            for (FragranceGroup group : fragranceGroup) {
                group.setPerfumeRelation(this);
            }
        }
    }

    @Builder
    public Perfume( String name, String description, List<FragranceGroup> fragranceGroup, List<PerfumeKeyword> perfumeKeyword) {

        this.name = name;
        this.description = description;
        this.setFragranceGroup(fragranceGroup);
        this.setKeyword(perfumeKeyword);
    }

    public void update(PerfumeSaveDto perfumeSaveDto) {
        this.name = name;
        this.description = description;
        this.setFragranceGroup(perfumeSaveDto.getFragranceGroup());
        this.setKeyword(perfumeSaveDto.getKeyword());

    }

//
//    public void updateKeyword(List<PerfumeKeyword> perfumeKeyword) {
//        for (PerfumeKeyword keyword : perfumeKeyword) {
//            this.addKeyword(keyword);
//        }
//    }



}
