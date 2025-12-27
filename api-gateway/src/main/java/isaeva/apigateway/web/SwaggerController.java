package isaeva.apigateway.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwaggerController {

    @GetMapping(value = "/swagger", produces = MediaType.TEXT_HTML_VALUE)
    public String swagger() {
        return "<html><body><h1>API Docs</h1><ul><li><a href='/swagger/user/swagger-ui/index.html'>User Swagger</a>" +
                "</li><li><a href='/swagger/task/swagger-ui/index.html'>Task Swagger</a></li></ul></body></html>";

    }
}
