package ru.sqwk.ssn.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@Getter
@NoArgsConstructor
public class CommentDTO {
    private String commentContent;
}
