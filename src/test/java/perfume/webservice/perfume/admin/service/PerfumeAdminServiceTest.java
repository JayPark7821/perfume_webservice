package perfume.webservice.perfume.admin.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import perfume.webservice.perfume.domain.entity.Fragrance;
import perfume.webservice.perfume.domain.entity.FragranceGroup;
import perfume.webservice.perfume.domain.entity.Perfume;
import perfume.webservice.perfume.repository.FragranceGroupRepository;
import perfume.webservice.perfume.repository.FragranceRepository;
import perfume.webservice.perfume.repository.PerfumeRepository;

@SpringBootTest
@Rollback(value = false)
class PerfumeAdminServiceTest {

	@Autowired
	PerfumeRepository perfumeRepository;

	@Autowired
	FragranceRepository fragranceRepository;

	@Autowired
	FragranceGroupRepository fragranceGroupRepository;

	//    @AfterEach
	//    void cleanup() {
	//        perfumeRepository.deleteAll();
	//        fragranceRepository.deleteAll();
	//        fragranceGroupRepository.deleteAll();
	//    }

	@Test
	void perfumeSaveTest() throws Exception {
		//given
		String name = "santal33";
		String desc = "santal33 perfume";
		Perfume perfume1 = perfumeRepository.save(createPerfume(name, desc));

		String fragName = "woody";
		String fragDsc = "woody~~";
		Fragrance fragrance1 = fragranceRepository.save(createFragrance(fragName, fragDsc));

		//        String fragName2 = "fresh";
		//        String fragDsc2 = "fresh~~";
		//        fragranceRepository.save(createFragrance(fragName2, fragDsc2));

		//when
		Perfume perfume = perfumeRepository.findById(perfume1.getId())
			.orElseThrow(() -> new IllegalStateException("id=" + perfume1.getId()));
		Fragrance fragrance = fragranceRepository.findById(fragrance1.getId())
			.orElseThrow(() -> new IllegalStateException("eee"));
		FragranceGroup pfm = createPFM(perfume, fragrance, 10);
		fragranceGroupRepository.save(pfm);

		//        perfumeRepository.deleteById(perfume.getId());

		//then
	}

	private FragranceGroup createPFM(Perfume perfume, Fragrance fragrance, int containPercentage) {
		return FragranceGroup.builder()
			.perfume(perfume)
			.fragrance(fragrance)
			.containPercentage(containPercentage)
			.build();
	}

	private Perfume createPerfume(String name, String desc) {
		return Perfume.builder()
			.name(name)
			.description(desc)
			.build();
	}

	private Fragrance createFragrance(String name, String desc) {
		return Fragrance.builder()
			.name(name)
			.description(desc)
			.build();
	}
}