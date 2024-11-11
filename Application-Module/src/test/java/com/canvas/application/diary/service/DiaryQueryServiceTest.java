package com.canvas.application.diary.service;

import com.canvas.application.diary.exception.DiaryException;
import com.canvas.application.diary.port.in.GetDiaryUseCase;
import com.canvas.application.diary.port.out.DiaryManagementPort;
import com.canvas.domain.diary.entity.DiaryBasic;
import com.canvas.domain.diary.entity.DiaryComplete;
import com.canvas.domain.fixture.DiaryFixture;
import com.canvas.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static com.canvas.application.diary.DiaryApplicationFixture.getGetDiaryQuery;
import static com.canvas.application.diary.DiaryApplicationFixture.getHomeCalendarQuery;
import static com.canvas.domain.fixture.DiaryFixture.PRIVATE_OTHER_DIARY;
import static com.canvas.domain.fixture.DiaryFixture.PUBLIC_MY_DIARY;
import static com.canvas.domain.fixture.UserFixture.MYSELF;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DiaryQueryServiceTest {

    @Mock
    private DiaryManagementPort diaryManagementPort;

    @InjectMocks
    private DiaryQueryService diaryQueryService;

    @Test
    @DisplayName("일기 단일 조회 성공")
    void getDiarySuccessTest() {
        // given
        User user = MYSELF.getUser();
        DiaryComplete diary = PUBLIC_MY_DIARY.getDiaryComplete();
        GetDiaryUseCase.Query.Diary query = getGetDiaryQuery(user, diary);

        given(diaryManagementPort.getById(diary.getId())).willReturn(diary);

        // when
        GetDiaryUseCase.Response.DiaryInfo response = diaryQueryService.getDiary(query);

        // then
        assertThat(response.diaryId()).isEqualTo(diary.getId().toString());
        assertThat(response.content()).isEqualTo(diary.getContent());
        assertThat(response.emotion()).isEqualTo(diary.getEmotion());
        assertThat(response.likeCount()).isEqualTo(diary.getLikeCount());
        assertThat(response.isLiked()).isEqualTo(diary.isLiked(user.getId()));
        assertThat(response.isMine()).isEqualTo(diary.isWriter(user.getId()));
        assertThat(response.date()).isEqualTo(diary.getDate());
        assertThat(response.isPublic()).isEqualTo(diary.getIsPublic());
        assertThat(response.images()).containsExactlyInAnyOrderElementsOf(diary.getImages().stream()
                .map(image -> new GetDiaryUseCase.Response.DiaryInfo.ImageInfo(
                        image.getId().toString(),
                        image.getIsMain(),
                        image.getImageUrl()
                )).toList());

    }

    @Test
    @DisplayName("일기 단일 조회 실패")
    void getDiaryFailureTest() {
        // given
        User user = MYSELF.getUser();
        DiaryComplete diary = PRIVATE_OTHER_DIARY.getDiaryComplete();
        GetDiaryUseCase.Query.Diary query = getGetDiaryQuery(user, diary);

        given(diaryManagementPort.getById(diary.getId())).willReturn(diary);

        // when
        // then
        assertThatThrownBy(() -> diaryQueryService.getDiary(query))
                .isInstanceOf(DiaryException.DiaryForbiddenException.class);
    }

    @Test
    @DisplayName("홈 달력 조회")
    void getHomeCalendarTest() {
        // given
        User user = MYSELF.getUser();
        LocalDate date = LocalDate.now();
        GetDiaryUseCase.Query.HomeCalendar query = getHomeCalendarQuery(user, date);
        List<DiaryBasic> diaries = DiaryFixture.getAllDiaryBasicByUser(MYSELF);

        given(diaryManagementPort.getByUserIdAndMonth(user.getId(), date))
                .willReturn(diaries);

        // when
        GetDiaryUseCase.Response.HomeCalendar response = diaryQueryService.getHomeCalendar(query);

        // then
        assertThat(response.diaries())
                .hasSameSizeAs(diaries)
                .allSatisfy(diaryInfo -> {
                    DiaryBasic matchingDiary = diaries.stream()
                                                      .filter(diary -> diary.getId().toString().equals(diaryInfo.diaryId()))
                                                      .findFirst()
                                                      .orElseThrow();

                    assertThat(diaryInfo.date()).isEqualTo(matchingDiary.getDate());
                    assertThat(diaryInfo.emotion()).isEqualTo(matchingDiary.getEmotion());
                });
    }

}