package perfume.webservice.suggestion.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import perfume.webservice.suggestion.service.SuggestionService;
import perfume.webservice.user.oauth.token.AuthTokenProvider;
import perfume.webservice.user.utils.HeaderUtil;
import perfume.webservice.category.domain.dto.response.BaseCategoryGroupResponseDto;
import perfume.webservice.category.service.CategoryService;
import perfume.webservice.common.dto.ApiResponses;
import perfume.webservice.keyword.domain.dto.response.KeywordOptionResponseDto;
import perfume.webservice.keyword.service.KeywordMasterService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class SuggestionController {

    private final CategoryService categoryService;
    private final KeywordMasterService keywordMasterService;
    private final AuthTokenProvider tokenProvider;
    private final SuggestionService suggestionService;

    @GetMapping("/categorygroup")
    public ApiResponses<List<BaseCategoryGroupResponseDto>> findBaseCategoryGroup() {
        return ApiResponses.success(categoryService.findBaseCategory());
    }

    @GetMapping("/categorygroup/{group}/{keyword}/{level}")
    public ApiResponses<List<KeywordOptionResponseDto>> findKeywordsFromCategory(HttpServletRequest request,
                                                                                 @PathVariable("group") Long group,
                                                                                 @PathVariable("keyword") Long keyword,
                                                                                 @PathVariable("level") int level ) {
        String userId = tokenProvider.convertAuthToken(HeaderUtil.getAccessToken(request)).getTokenClaims(request).getSubject();
        suggestionService.saveUserKeyword(userId, keyword);
        return ApiResponses.success( keywordMasterService.findKeywordsFromCategory(group, level));
    }


}
