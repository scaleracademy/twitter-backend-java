package xyz.subho.clone.twitter.config;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
public class ApplicationErrorController extends BasicErrorController {
	
	/**
	 * @param errorAttributes
	 * @param serverProperties
	 */
	public ApplicationErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties)	{
		super(errorAttributes, serverProperties.getError());
	}
	
	/**
	 * @param request
	 * @return ResponseEntity for Errors
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> jsonError(HttpServletRequest request) {
        
		Map<String, Object> body = getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.APPLICATION_JSON));
        
        body.put("jsonkey", "New JSON");
        HttpStatus status = getStatus(request);
        
        return new ResponseEntity<>(body, status);
    }

}
