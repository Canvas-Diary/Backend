package com.canvas.bootstrap.diary.converter;

import com.canvas.bootstrap.diary.enums.ExploreOrder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToExploreOrderConverter implements Converter<String, ExploreOrder> {
    @Override
    public ExploreOrder convert(String source) {
        return ExploreOrder.parse(source);
    }
}
