package com.canvas.application.diary.port.in;

import com.canvas.domain.diary.enums.Emotion;

import java.time.LocalDate;
import java.util.List;

public interface GetDiaryUseCase {
    Response.ByDiaryId getDiaryById(Command.ByDiaryId command);
    Response.ByUserIdAndDate getDiariesByUserIdAndDate(Command.ByUserIdAndDate command);

    sealed interface Command
            permits Command.ByDiaryId, Command.ByUserIdAndDate {
        record ByDiaryId(
                String diaryId
        ) implements Command {
        }

        record ByUserIdAndDate(
                Long userId,
                LocalDate date
        ) implements Command {
        }
    }

    sealed interface Response
            permits Response.ByDiaryId, Response.ByUserIdAndDate {
        record ByDiaryId(
                String diaryId,
                String content,
                Emotion emotion,
                Integer likeCount,
                Boolean isLiked,
                List<Image> images
        ) implements Response {
            public record Image(
                    Long imageId,
                    String imageUrl
            ) {
            }
        }

        record ByUserIdAndDate(List<Diary> diaries) implements Response {
            public record Diary(
                    Long diaryId,
                    LocalDate date,
                    Emotion emotion) {
            }
        }
    }
}
