package reddit.clone.reddit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RedditException extends RuntimeException {

    public RedditException(String message) {
        super(message);
    }

}
