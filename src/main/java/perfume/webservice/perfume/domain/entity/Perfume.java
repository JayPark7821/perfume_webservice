package perfume.webservice.perfume.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
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

    @JsonManagedReference
    @OneToMany(mappedBy = "perfume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PerfumeKeyword> perfumeKeyword = new ArrayList<>();



    public void addFragranceGroup(FragranceGroup fragranceGroup) {
        this.fragranceGroup.add(fragranceGroup);
        fragranceGroup.setPerfumeRelation(this);
    }


    public void addKeyword(PerfumeKeyword perfumeKeyword) {
        this.perfumeKeyword.add(perfumeKeyword);
        perfumeKeyword.setPerfumeRelation(this);
    }

    public void setKeyword(List<PerfumeKeyword> perfumeKeywords) {
        if (perfumeKeywords != null) {
            this.perfumeKeyword = perfumeKeywords;
            for (PerfumeKeyword keyword : perfumeKeywords) {
                keyword.setPerfumeRelation(this);
            }
        }
    }

    public void setFragranceGroup(List<FragranceGroup> fragranceGroup) {
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
        this.name = perfumeSaveDto.getName();
        this.description = perfumeSaveDto.getDescription();

        this.perfumeKeyword.clear();
        this.fragranceGroup.clear();

        if (perfumeSaveDto.getFragranceGroup() == null) {
            this.fragranceGroup = new ArrayList<>();
        } else{
            for (FragranceGroup fragranceGroup : perfumeSaveDto.getFragranceGroup()) {
                this.addFragranceGroup(fragranceGroup);
            }
        }
        if (perfumeSaveDto.getKeyword() == null) {
            this.perfumeKeyword = new ArrayList<>();
        } else{
            for (PerfumeKeyword perfumeKeyword : perfumeSaveDto.getKeyword()) {
                this.addKeyword(perfumeKeyword);
            }
        }

    }
}
