package com.canvas.application.diary.service;

import com.canvas.application.diary.exception.DiaryException;
import com.canvas.application.diary.port.in.AddDiaryUseCase;
import com.canvas.application.diary.port.in.ModifyDiaryUseCase;
import com.canvas.application.diary.port.in.RemoveDiaryUseCase;
import com.canvas.application.diary.port.out.DiaryEmotionExtractPort;
import com.canvas.application.diary.port.out.DiaryManagementPort;
import com.canvas.application.image.port.in.AddImageUseCase;
import com.canvas.domain.common.DomainId;
import com.canvas.domain.diary.entity.DiaryComplete;
import com.canvas.domain.diary.entity.Image;
import com.canvas.domain.diary.enums.Emotion;
import com.canvas.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static com.canvas.application.diary.DiaryApplicationFixture.*;
import static com.canvas.domain.fixture.DiaryFixture.PUBLIC_MY_DIARY;
import static com.canvas.domain.fixture.UserFixture.MYSELF;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DiaryCommandServiceTest {

    @Mock
    private DiaryManagementPort diaryManagementPort;
    @Mock
    private DiaryEmotionExtractPort diaryEmotionExtractPort;
    @Mock
    private AddImageUseCase addImageUseCase;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @InjectMocks
    private DiaryCommandService diaryCommandService;

    @Test
    @DisplayName("일기 생성 성공")
    void addSuccessTest() {
        // given
        User user = MYSELF.getUser();
        DiaryComplete diary = PUBLIC_MY_DIARY.getDiaryComplete();
        AddDiaryUseCase.Command command = getAddDiaryCommand(user, diary);
        String generatedImageUrl = "imageUrl";

        given(diaryManagementPort.existsByWriterIdAndDate(user.getId(), diary.getDate()))
                .willReturn(false);
        given(diaryEmotionExtractPort.emotionExtract(command.content()))
                .willReturn(diary.getEmotion());
        given(addImageUseCase.create(any(AddImageUseCase.Command.Create.class)))
                .willReturn(new AddImageUseCase.Response.Create(generatedImageUrl));


        // when
        AddDiaryUseCase.Response response = diaryCommandService.add(command);

        // then
        ArgumentCaptor<DiaryComplete> captor = ArgumentCaptor.forClass(DiaryComplete.class);
        verify(diaryManagementPort).save(captor.capture());

        DiaryComplete savedDiary = captor.getValue();
        assertThat(savedDiary.getId()).isEqualTo(DomainId.from(response.diaryId()));
        assertThat(savedDiary.getWriterId()).isEqualTo(diary.getWriterId());
        assertThat(savedDiary.getContent()).isEqualTo(diary.getContent());
        assertThat(savedDiary.getEmotion()).isEqualTo(diary.getEmotion());
        assertThat(savedDiary.getDate()).isEqualTo(diary.getDate());
        assertThat(savedDiary.getIsPublic()).isEqualTo(diary.getIsPublic());
        assertThat(savedDiary.getImages())
                .hasSize(1)
                .extracting(Image::getImageUrl)
                .containsExactly(generatedImageUrl);
    }

    @Test
    @DisplayName("일기 생성 실패")
    void addFailureTest() {
        // given
        User user = MYSELF.getUser();
        DiaryComplete diary = PUBLIC_MY_DIARY.getDiaryComplete();
        AddDiaryUseCase.Command command = getAddDiaryCommand(user, diary);

        given(diaryManagementPort.existsByWriterIdAndDate(user.getId(), diary.getDate()))
                .willReturn(true);

        // when
        // then
        assertThatThrownBy(() -> diaryCommandService.add(command))
                .isInstanceOf(DiaryException.DiaryBadRequestException.class);
    }

    @Test
    @DisplayName("일기 공개 여부만 수정")
    void modifyPublicTest() {
        // given
        User user = MYSELF.getUser();
        DiaryComplete diary = PUBLIC_MY_DIARY.getDiaryComplete();
        ModifyDiaryUseCase.Command command = getModifyDiaryCommand(user, diary, diary.getContent(), !diary.getIsPublic());

        given(diaryManagementPort.getByIdAndWriterId(diary.getId(), diary.getWriterId()))
                .willReturn(diary);

        // when
        diaryCommandService.modify(command);

        // then
        verify(diaryEmotionExtractPort, never()).emotionExtract(command.content());
        verify(diaryManagementPort).save(diary);
        assertThat(diary.getContent()).isEqualTo(command.content());
        assertThat(diary.getIsPublic()).isEqualTo(command.isPublic());
    }

    @Test
    @DisplayName("일기 내용, 공개 여부 모두 수정")
    void modifyContentTest() {
        // given
        User user = MYSELF.getUser();
        DiaryComplete diary = PUBLIC_MY_DIARY.getDiaryComplete();
        String newContent = "새로운 내용";
        Emotion newEmotion = Emotion.ANGER;
        ModifyDiaryUseCase.Command command = getModifyDiaryCommand(user, diary, newContent, !diary.getIsPublic());

        given(diaryManagementPort.getByIdAndWriterId(diary.getId(), diary.getWriterId()))
                .willReturn(diary);
        given(diaryEmotionExtractPort.emotionExtract(command.content()))
                .willReturn(newEmotion);

        // when
        diaryCommandService.modify(command);

        // then
        verify(diaryEmotionExtractPort).emotionExtract(command.content());
        verify(diaryManagementPort).save(diary);
        assertThat(diary.getContent()).isEqualTo(command.content());
        assertThat(diary.getIsPublic()).isEqualTo(command.isPublic());
    }

    @Test
    @DisplayName("일기 삭제 성공")
    void removeSuccessTest() {
        // given
        User user = MYSELF.getUser();
        DiaryComplete diary = PUBLIC_MY_DIARY.getDiaryComplete();
        RemoveDiaryUseCase.Command command = getRemoveDiaryCommand(user, diary);

        given(diaryManagementPort.existsByIdAndWriterId(diary.getId(), user.getId()))
                .willReturn(true);

        // when
        diaryCommandService.remove(command);

        // then
        verify(diaryManagementPort).deleteById(diary.getId());
    }

    @Test
    @DisplayName("일기 삭제 실패")
    void removeFailureTest() {
        // given
        User user = MYSELF.getUser();
        DiaryComplete diary = PUBLIC_MY_DIARY.getDiaryComplete();
        RemoveDiaryUseCase.Command command = getRemoveDiaryCommand(user, diary);

        given(diaryManagementPort.existsByIdAndWriterId(diary.getId(), user.getId()))
                .willReturn(false);

        // when
        // then
        assertThatThrownBy(() -> diaryCommandService.remove(command))
                .isInstanceOf(DiaryException.DiaryForbiddenException.class);
        verify(diaryManagementPort, never()).deleteById(diary.getId());
    }

}