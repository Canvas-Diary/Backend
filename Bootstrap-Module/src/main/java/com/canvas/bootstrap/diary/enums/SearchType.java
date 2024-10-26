package com.canvas.bootstrap.diary.enums;

import com.canvas.application.diary.exception.DiaryException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum SearchType {
    TAG("tag"),
    CONTENT("content");

    private final String value;

    public static SearchType parse(String value) {
        return Arrays.stream(SearchType.values())
                .filter(searchType -> searchType.getValue().equals(value))
                .findFirst()
                .orElseThrow(DiaryException.DiaryBadRequestException::new);
    }
}
