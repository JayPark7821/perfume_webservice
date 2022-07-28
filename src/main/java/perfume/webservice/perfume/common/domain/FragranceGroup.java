package perfume.webservice.perfume.common.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.common.entity.BaseTimeEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@IdClass(FragranceGroupID.class)
public class FragranceGroup extends BaseTimeEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfume_id")
    @JsonBackReference
    private Perfume perfume;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fragrance_id")
    @JsonManagedReference
    private Fragrance fragrance;


    private int containPercentage;



    @Builder
    public FragranceGroup(Perfume perfume, Fragrance fragrance, int containPercentage) {
        this.perfume = perfume;
        this.fragrance = fragrance;
        this.containPercentage = containPercentage;

    }
    public void setPerfumeRelation(Perfume perfume) {
        this.perfume = perfume;
    }
    public void setFragranceRelation(Fragrance fragrance) {
        this.fragrance = fragrance;
    }


}
