package com.canvas.application.diary.event;

import com.canvas.application.diary.port.out.DiaryKeywordExtractPort;
import com.canvas.application.keyword.port.out.DiaryKeywordManagementPort;
import com.canvas.application.keyword.port.out.KeywordManagementPort;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.keyword.DiaryKeyword;
import com.canvas.domain.keyword.Keyword;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class KeywordSaveEventListener {

    private final KeywordManagementPort keywordManagementPort;
    private final DiaryKeywordManagementPort diaryKeywordManagementPort;
    private final DiaryKeywordExtractPort diaryKeywordExtractPort;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addKeywordsEvent(KeywordSavedEvent event) {
        List<String> keywords = diaryKeywordExtractPort.keywordExtract(event.content());

        List<Keyword> existingKeywords = keywordManagementPort.findAllByNames(keywords);
        List<String> existingKeywordNames = existingKeywords.stream()
                .map(Keyword::getKeyword)
                .toList();

        // db에 존재하지 않는 키워드
        List<Keyword> newKeywords = keywords.stream()
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
                                DomainId.from(event.diaryId()),
                                keywordId
                        )).toList());
    }
}
