package perfume.webservice.perfume.common.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import perfume.webservice.common.entity.BaseTimeEntity;

@Getter
@NoArgsConstructor
@Entity
public class Fragrance extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fragrance_id")
	private Long id;

	@Column(name = "fragrance_name")
	private String name;

	@Column(name = "fragrance_desc")
	private String description;

	@JsonBackReference
	@OneToMany(mappedBy = "fragrance")
	private List<FragranceGroup> fragranceGroup = new ArrayList<>();

	public void addPerfume(FragranceGroup fragrance) {
		this.fragranceGroup.add(fragrance);
		fragrance.setFragranceRelation(this);
	}

	public void update(String name, String description) {
		this.name = name;
		this.description = description;
	}

	@Builder
	public Fragrance(String name, String description, List<FragranceGroup> fragranceGroup) {
		this.name = name;
		this.description = description;
		if (fragranceGroup == null) {
			this.fragranceGroup = new ArrayList<>();
		} else {
			for (FragranceGroup mapping : fragranceGroup) {
				this.addPerfume(mapping);
			}
		}
	}
}
