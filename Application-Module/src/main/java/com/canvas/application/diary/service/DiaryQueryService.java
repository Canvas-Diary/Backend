package com.canvas.application.diary.service;

import com.canvas.application.diary.port.in.GetDiaryUseCase;
import com.canvas.application.diary.port.out.DiaryManagementPort;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.Diary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryQueryService implements GetDiaryUseCase {

    private final DiaryManagementPort diaryManagementPort;

    @Override
    public Response.Diary getMyDiary(Command.Diary command) {
        Diary diary = diaryManagementPort.getByIdAndWriterId(
                DomainId.from(command.diaryId()),
                DomainId.from(command.userId())
        );

        return toResponseDiary(command.userId(), diary);
    }

    @Override
    public Response.Diary getOtherDiary(Command.Diary command) {
        Diary diary = diaryManagementPort.getPublicById(DomainId.from(command.diaryId()));

        return toResponseDiary(command.userId(), diary);
    }

    @Override
    public Response.HomeCalendar getHomeCalendar(Command.HomeCalendar command) {
        List<Diary> diaries = diaryManagementPort.getByUserIdAndMonth(
                DomainId.from(command.userId()),
                command.date()
        );

        return new Response.HomeCalendar(
                diaries.stream()
                        .map(diary -> new Response.HomeCalendar.Diary(
                                diary.getId().toString(),
                                diary.getDateTime().toLocalDate(),
                                diary.getDiaryContent().getEmotion()
                        )).toList()
        );
    }

    private static Response.Diary toResponseDiary(String userId, Diary diary) {
        return new Response.Diary(
                diary.getId().toString(),
                diary.getDiaryContent().getContent(),
                diary.getDiaryContent().getEmotion(),
                diary.getLikes().size(),
                diary.getLikes().stream()
                        .anyMatch(like -> like.getUserId().equals(DomainId.from(userId))),
                diary.getDiaryContent().getImages().stream()
                        .map(image -> new Response.Diary.Image(
                                image.getId().toString(),
                                image.getIsMain(),
                                image.getS3Uri()
                        )).toList()
        );
    }

}
