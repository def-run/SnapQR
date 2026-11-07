package org.def.snapqr.dto;

import jakarta.validation.constraints.*;

public class StaticQRRequest {

    @NotBlank(message = "Content cannot be empty")
    @Size(max = 2000, message = "Content cannot be too long")
    private String content;

    @NotNull(message = "Size is required")
    @Min(value = 100, message = "Minimum size is 100")
    @Max(value = 1000, message = "Maximum size is 1000")
    private Integer size;

    @NotBlank(message = "Foreground color is required")
    private String foregroundColor;

    @NotBlank(message = "Background color is required")
    private String backgroundColor;

    public StaticQRRequest() {}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(String foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
