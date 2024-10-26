package com.canvas.bootstrap.diary.converter;

import com.canvas.bootstrap.diary.enums.SearchType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToSearchTypeConverter implements Converter<String, SearchType> {
    @Override
    public SearchType convert(String source) {
        return SearchType.parse(source);
    }
}
