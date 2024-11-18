package com.canvas.bootstrap.keyword.controller;

import com.canvas.application.keyword.port.in.AddKeywordsUseCase;
import com.canvas.bootstrap.keyword.dto.SaveKeywordRequest;
import com.canvas.bootstrap.keyword.api.KeywordApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KeywordController implements KeywordApi {

    private final AddKeywordsUseCase addKeywordUseCase;

    @Override
    public void saveKeyword(String userId, String diaryId, SaveKeywordRequest request) {
        addKeywordUseCase.addKeywords(new AddKeywordsUseCase.Command(
                userId,
                diaryId,
                request.keywords()
        ));
    }

}
