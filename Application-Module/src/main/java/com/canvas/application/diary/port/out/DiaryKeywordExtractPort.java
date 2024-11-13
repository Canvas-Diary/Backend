package com.canvas.application.diary.port.out;

import java.util.List;

public interface DiaryKeywordExtractPort {
    List<String> keywordExtract(String content);
}
