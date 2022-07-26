package perfume.webservice.perfume.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.common.entity.BaseEntity;
import perfume.webservice.common.entity.BaseTimeEntity;
import perfume.webservice.common.utils.AuditorProvider;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Perfume  extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "perfume_id")
    private Long id;

    @Column(name = "perfume_name")
    private String name;

    @Column(name = "perfume_desc")
    private String description;

    @Builder
    public Perfume(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void update(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
