package com.canvas.application.image.service;

import com.canvas.application.diary.port.out.DiaryManagementPort;
import com.canvas.application.image.exception.ImageException;
import com.canvas.application.image.port.in.AddImageUseCase;
import com.canvas.application.image.port.in.RemoveImageUseCase;
import com.canvas.application.image.port.in.SetMainImageUseCase;
import com.canvas.application.image.port.out.ImageGenerationPort;
import com.canvas.application.image.port.out.ImageManagementPort;
import com.canvas.application.image.port.out.ImagePromptGeneratePort;
import com.canvas.application.image.port.out.ImageUploadPort;
import com.canvas.domain.diary.entity.DiaryComplete;
import com.canvas.domain.diary.entity.DiaryOverview;
import com.canvas.domain.diary.entity.Image;
import com.canvas.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.canvas.application.image.ImageApplicationFixture.*;
import static com.canvas.domain.fixture.DiaryFixture.PUBLIC_MY_DIARY;
import static com.canvas.domain.fixture.ImageFixture.PUBLIC_MY_DIARY_IMAGE2;
import static com.canvas.domain.fixture.UserFixture.MYSELF;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageCommandServiceTest {

    @Mock
    private DiaryManagementPort diaryManagementPort;
    @Mock
    private ImageGenerationPort imageGenerationPort;
    @Mock
    private ImageManagementPort imageManagementPort;
    @Mock
    private ImagePromptGeneratePort imagePromptGeneratePort;
    @Mock
    private ImageUploadPort imageUploadPort;

    @Spy
    @InjectMocks
    private ImageCommandService imageCommandService;

    @Test
    @DisplayName("이미지 추가")
    void addTest() {
        // given
        DiaryComplete diary = PUBLIC_MY_DIARY.getDiaryComplete();
        Image image = PUBLIC_MY_DIARY_IMAGE2.getImage();

        AddImageUseCase.Command.Add command = getAddImageCommand(diary);

        given(diaryManagementPort.getById(diary.getId())).willReturn(diary);
        doReturn(new AddImageUseCase.Response.Create(image.getImageUrl())).when(imageCommandService).create(any());

        // when
        AddImageUseCase.Response.Add response = imageCommandService.add(command);

        // then
        verify(imageCommandService).create(any(AddImageUseCase.Command.Create.class));
        verify(imageManagementPort).save(any(Image.class));
        assertThat(response.isMain()).isFalse();
        assertThat(response.imageUrl()).isEqualTo(image.getImageUrl());
    }

    @Test
    @DisplayName("이미지 생성")
    void createTest() {
        // given
        AddImageUseCase.Command.Create command = getCreateImageCommand();

        given(imagePromptGeneratePort.generatePrompt(command.content(), command.joinedWeightedContents())).willReturn("prompt");
        given(imageGenerationPort.generate("prompt", command.style())).willReturn("generatedImageUrl");
        given(imageUploadPort.upload("generatedImageUrl")).willReturn("uploadedImageUrl");

        // when
        AddImageUseCase.Response.Create response = imageCommandService.create(command);

        // then
        assertThat(response.imageUrl()).isEqualTo("uploadedImageUrl");
    }

    @Test
    @DisplayName("이미지 삭제 성공")
    void removeSuccessTest() {
        // given
        User user = MYSELF.getUser();
        Image image = PUBLIC_MY_DIARY_IMAGE2.getImage();
        RemoveImageUseCase.Command command = getRemoveImageCommand(user, image);

        given(imageManagementPort.existsByIdAndUserId(image.getId(), user.getId())).willReturn(true);

        // when
        imageCommandService.remove(command);

        // then
        verify(imageManagementPort).deleteById(image.getId());
    }

    @Test
    @DisplayName("이미지 삭제 실패")
    void removeFailureTest() {
        // given
        User user = MYSELF.getUser();
        Image image = PUBLIC_MY_DIARY_IMAGE2.getImage();
        RemoveImageUseCase.Command command = getRemoveImageCommand(user, image);

        given(imageManagementPort.existsByIdAndUserId(image.getId(), user.getId())).willReturn(false);

        // when
        // then
        assertThatThrownBy(() -> imageCommandService.remove(command))
                .isInstanceOf(ImageException.ImageNotFoundException.class);
        verify(imageManagementPort, never()).deleteById(image.getId());
    }

    @Test
    @DisplayName("메인 이미지 변경 성공")
    void setMainImageSuccessTest() {
        // given
        DiaryOverview diary = PUBLIC_MY_DIARY.getDiaryOverview();
        Image image = PUBLIC_MY_DIARY_IMAGE2.getImage();
        User user = MYSELF.getUser();
        SetMainImageUseCase.Command command = getSetMainImageCommand(user, image);

        given(imageManagementPort.getAllInDiaryByIdAndWriterId(image.getId(), diary.getWriterId()))
                .willReturn(diary.getImages());

        // when
        imageCommandService.setMain(command);

        // then
        verify(imageManagementPort).saveAll(diary.getImages());
        assertThat(diary.getImages())
                .anySatisfy(img -> {
                    assertThat(img.getId()).isEqualTo(image.getId());
                    assertThat(img.isMain()).isTrue();
                })
                .allSatisfy(img -> {
                    if (!img.getId().equals(image.getId())) {
                        assertThat(img.isMain()).isFalse();
                    }
                });
    }

    @Test
    @DisplayName("메인 이미지 변경 실패")
    void setMainFailureTest() {
        // given
        DiaryOverview diary = PUBLIC_MY_DIARY.getDiaryOverview();
        Image image = PUBLIC_MY_DIARY_IMAGE2.getImage();
        User user = MYSELF.getUser();
        SetMainImageUseCase.Command command = getSetMainImageCommand(user, image);

        given(imageManagementPort.getAllInDiaryByIdAndWriterId(image.getId(), diary.getWriterId()))
                .willReturn(List.of());

        // when
        // then
        assertThatThrownBy(() -> imageCommandService.setMain(command))
                .isInstanceOf(ImageException.ImageNotFoundException.class);
    }

}