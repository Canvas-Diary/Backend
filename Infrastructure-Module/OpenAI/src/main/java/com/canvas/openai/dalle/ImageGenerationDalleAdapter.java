package com.canvas.openai.dalle;

import com.canvas.application.common.enums.Style;
import com.canvas.application.image.port.out.ImageGenerationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
@RequiredArgsConstructor
public class ImageGenerationDalleAdapter implements ImageGenerationPort {

    private final DalleClient dalleClient;

    @Override
    public String generate(String prompt, Style style) {
        return dalleClient.generateImage(style.getValue() + ", " + prompt + ", vertically aligned");
    }

}
