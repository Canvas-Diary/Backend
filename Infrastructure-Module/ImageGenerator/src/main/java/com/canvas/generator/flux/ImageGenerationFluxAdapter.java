package com.canvas.generator.flux;

import com.canvas.application.common.enums.Style;
import com.canvas.application.image.port.out.ImageGenerationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageGenerationFluxAdapter implements ImageGenerationPort {
    private final FluxClient fluxClient;

    @Override
    public String generate(String content, Style style) {
        String id = fluxClient.generateImage(style.getValue() + ", " + content);
        return fluxClient.getResult(id);
    }
}
