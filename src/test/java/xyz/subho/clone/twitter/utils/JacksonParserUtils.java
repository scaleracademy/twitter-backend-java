package xyz.subho.clone.twitter.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonParserUtils {

  public static String getJsonStringValue(Object input) throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(input);
  }


}
