package io.shinmen.springrest.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerErrorResponse {

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private ZonedDateTime timestamp;
    private int statusCode;
    private String path;
    private String message;

}
