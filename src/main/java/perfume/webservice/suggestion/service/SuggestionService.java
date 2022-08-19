package perfume.webservice.suggestion.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perfume.webservice.keyword.domain.entity.KeywordMaster;
import perfume.webservice.keyword.repository.KeywordRepository;
import perfume.webservice.suggestion.domain.entity.UserKeywordSelectHistory;
import perfume.webservice.suggestion.repository.UserKeywordSelectHistoryRepository;
import perfume.webservice.user.api.entity.user.User;
import perfume.webservice.user.api.repository.user.UserRepository;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class SuggestionService {

    private final UserKeywordSelectHistoryRepository keywordSelectHistoryRepository;
    private final UserRepository userRepository;
    private final KeywordRepository keywordRepository;

    public void saveUserKeyword(String email, Long keyword) {

        User user = userRepository.findByEmail(email);
        KeywordMaster keywordMaster = keywordRepository.getReferenceById(keyword);

        keywordSelectHistoryRepository.save(UserKeywordSelectHistory.builder()
                .keywordMaster(keywordMaster)
                .selectedDate(LocalDateTime.now())
                .user(user).build());
    }
}
