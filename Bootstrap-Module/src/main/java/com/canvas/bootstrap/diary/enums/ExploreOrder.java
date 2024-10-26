package com.canvas.bootstrap.diary.enums;

import com.canvas.application.diary.exception.DiaryException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum ExploreOrder {
    LATEST("latest"),
    POPULARITY("popularity"),
    LIKED_DATE("likedDate");

    private final String value;

    public static ExploreOrder parse(String value) {
        return Arrays.stream(ExploreOrder.values())
                .filter(order -> order.getValue().equals(value))
                .findFirst()
                .orElseThrow(DiaryException.DiaryBadRequestException::new);
    }
}
