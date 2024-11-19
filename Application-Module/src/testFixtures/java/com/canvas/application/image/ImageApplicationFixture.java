package com.canvas.application.image;

import com.canvas.application.common.enums.Style;
import com.canvas.application.image.port.in.AddImageUseCase;
import com.canvas.application.image.port.in.RemoveImageUseCase;
import com.canvas.application.image.port.in.SetMainImageUseCase;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.DiaryComplete;
import com.canvas.domain.diary.entity.Image;
import com.canvas.domain.user.entity.User;

public class ImageApplicationFixture {
    public static AddImageUseCase.Command.Add getAddImageCommand(DiaryComplete diary) {
        return new AddImageUseCase.Command.Add(
                diary.getId().toString(),
                Style.PHOTOREALISTIC
        );
    }

    public static AddImageUseCase.Command.Create getCreateImageCommand() {
        return new AddImageUseCase.Command.Create(
                DomainId.generate().toString(),
                "content",
                "joinedWeightedContents",
                Style.PHOTOREALISTIC
        );
    }

    public static RemoveImageUseCase.Command getRemoveImageCommand(User user, Image image) {
        return new RemoveImageUseCase.Command(
                user.getId().toString(),
                image.getDiaryId().toString(),
                image.getId().toString()
        );
    }

    public static SetMainImageUseCase.Command getSetMainImageCommand(User user, Image image) {
        return new SetMainImageUseCase.Command(
                user.getId().toString(),
                image.getId().toString()
        );
    }
}
