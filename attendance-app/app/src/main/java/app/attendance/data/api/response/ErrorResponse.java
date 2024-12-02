package app.attendance.data.api.response;

import java.util.Collections;
import java.util.List;

public class ErrorResponse {
    private String message;
    private final List<String> errors = Collections.emptyList();

    public String getMessage() {
        if(!errors.isEmpty()){
            return errors.get(0);
        } else if(message != null) {
            return message;
        } else {
            return "An unknown error occurred.";
        }
    }
}
