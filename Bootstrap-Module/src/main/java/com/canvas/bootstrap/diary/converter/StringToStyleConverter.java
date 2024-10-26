package com.canvas.bootstrap.diary.converter;

import com.canvas.application.common.enums.Style;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToStyleConverter implements Converter<String, Style> {
    @Override
    public Style convert(String source) {
        return Style.parse(source);
    }
}
