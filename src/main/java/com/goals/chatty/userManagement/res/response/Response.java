package com.goals.chatty.userManagement.res.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;



@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response <T>{
    public static <T> Response<T> delete(Class<?> clazz) {
        Response<T> response = new Response<>();
        response.setStatus(OK);
        response.setPayload((T) generateDeleteMessage(clazz.getSimpleName()));
        return response;
    }

    public static <T> Response<T> email() {
        Response<T> response = new Response<>();
        response.setStatus(OK);
        response.setPayload((T) "An email has been sent to your address!");
        return response;
    }

    private static String generateDeleteMessage(String entity) {
        return StringUtils.capitalize(entity) + " successfully deleted";
    }

    private static String generateSaveMessage(String entity) {
        return StringUtils.capitalize(entity) + " successfully saved";
    }

    public static <T> Response<T> ok() {
        Response<T> response = new Response<>();
        response.setStatus(OK);
        return response;
    }

    public static <T> Response<T> save(Class<?> clazz) {
        Response<T> response = new Response<>();
        response.setStatus(CREATED);
        response.setPayload((T) generateSaveMessage(clazz.getSimpleName()));
        return response;
    }

    private HttpStatus status;

    private T payload;
}
