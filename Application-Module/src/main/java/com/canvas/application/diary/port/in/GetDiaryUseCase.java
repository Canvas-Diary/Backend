package com.canvas.application.diary.port.in;

import com.canvas.domain.diary.enums.Emotion;

import java.time.LocalDate;
import java.util.List;

public interface GetDiaryUseCase {
    Response.ById getById(Command.ById command);
    Response.ByDate getByUserIdAndDate(Command.ByDate command);

    sealed interface Command
            permits Command.ById, Command.ByDate {
        record ById(
                String userId,
                String diaryId
        ) implements Command {
        }

        record ByDate(
                String userId,
                LocalDate date
        ) implements Command {
        }
    }

    sealed interface Response
            permits Response.ById, Response.ByDate {
        record ById(
                String diaryId,
                String content,
                Emotion emotion,
                Integer likeCount,
                Boolean isLiked,
                List<Image> images
        ) implements Response {
            public record Image(
                    String imageId,
                    String imageUrl
            ) {
            }
        }

        record ByDate(List<Diary> diaries) implements Response {
            public record Diary(
                    String diaryId,
                    LocalDate date,
                    Emotion emotion) {
            }
        }
    }
}
