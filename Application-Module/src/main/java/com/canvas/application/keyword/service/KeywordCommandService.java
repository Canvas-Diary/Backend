package com.canvas.application.keyword.service;

import com.canvas.application.keyword.port.in.AddKeywordsUseCase;
import com.canvas.application.keyword.port.out.DiaryKeywordManagementPort;
import com.canvas.application.keyword.port.out.KeywordManagementPort;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.keyword.DiaryKeyword;
import com.canvas.domain.keyword.Keyword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class KeywordCommandService implements AddKeywordsUseCase {

    private final KeywordManagementPort keywordManagementPort;
    private final DiaryKeywordManagementPort diaryKeywordManagementPort;

    @Override
    public void addKeywords(Command command) {
        // 이미 db에 존재하는 키워드
        List<Keyword> existingKeywords = keywordManagementPort.findAllByNames(command.keywords());
        List<String> existingKeywordNames = existingKeywords.stream()
                .map(Keyword::getKeyword)
                .toList();

        // db에 존재하지 않는 키워드
        List<Keyword> newKeywords = command.keywords().stream()
                .filter(keyword -> !existingKeywordNames.contains(keyword))
                .map(keyword -> new Keyword(
                        DomainId.generate(),
                        keyword,
                        new ArrayList<>()
                ))
                .toList();

        // 존재하지 않는 키워드만 저장
        keywordManagementPort.saveAllKeywords(newKeywords);

        // 전제 키워드 ID 추출
        List<DomainId> keywordIds = Stream.concat(existingKeywords.stream(), newKeywords.stream()).map(Keyword::getId).toList();

        // 키워드 ID를 통해 DiaryKeyword 저장
        diaryKeywordManagementPort.saveAllDiaryKeywords(
                keywordIds.stream()
                        .map(keywordId -> new DiaryKeyword(
                                DomainId.generate(),
                                DomainId.from(command.diaryId()),
                                keywordId
                        )).toList());
    }

}
