package com.canvas.storage.diary.repository;

import com.canvas.domain.diary.model.Diary;
import com.canvas.domain.diary.model.Emotion;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.canvas.domain.diary.model.QDiary.diary;


@Repository
@RequiredArgsConstructor
public class CustomDiaryRepositoryImpl implements CustomDiaryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Diary> findAllByDateRangeAndContentAndEmotion(LocalDate from, LocalDate to, String content, Emotion emotion) {
        return query.select(diary)
                .from(diary)
                .where(
                        betweenDate(from, to),
                        containsContent(content),
                        eqEmotion(emotion)
                ).fetch();
    }

    private BooleanExpression betweenDate(LocalDate from, LocalDate to) {
        if (Objects.isNull(from) || Objects.isNull(to)) {
            return null;
        }
        return diary.date.between(from, to);
    }

    private BooleanExpression containsContent(String content) {
        if (Objects.isNull(content)) {
            return null;
        }
        return diary.content.contains(content);
    }

    private BooleanExpression eqEmotion(Emotion emotion) {
        if (Objects.isNull(emotion)) {
            return null;
        }
        return diary.emotion.eq(emotion);
    }

}
