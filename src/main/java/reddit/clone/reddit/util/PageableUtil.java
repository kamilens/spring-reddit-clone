package reddit.clone.reddit.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;


@UtilityClass
public class PageableUtil {

    public <C extends Collection<P>, P> ResponseEntity<C> getPageResponse(C page) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(page.size()));
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(page);
    }

}
