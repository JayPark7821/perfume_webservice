package perfume.webservice.perfume.domain.entity;

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
import lombok.ToString;
import perfume.webservice.common.entity.BaseTimeEntity;
import perfume.webservice.perfume.domain.dto.save.FragranceSaveDto;

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

	public void update(FragranceSaveDto fragranceSaveDto) {
		this.name = fragranceSaveDto.getName();
		this.description = fragranceSaveDto.getDesc();
	}

	@Builder
	public Fragrance(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
