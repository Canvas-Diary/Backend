package com.canvas.application.keyword.port.in;

import java.util.List;

public interface AddKeywordsUseCase {
    void addKeywords(Command command);

    record Command(
        String userId,
        String diaryId,
        List<String> keywords
    ){

    }
}
