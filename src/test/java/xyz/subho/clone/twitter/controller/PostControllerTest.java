package xyz.subho.clone.twitter.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import xyz.subho.clone.twitter.exception.ErrorSavingEntityToDatabaseException;
import xyz.subho.clone.twitter.exception.ResourceNotFoundException;
import xyz.subho.clone.twitter.model.PostModel;
import xyz.subho.clone.twitter.service.PostService;
import xyz.subho.clone.twitter.utils.JacksonParserUtils;

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
@ActiveProfiles("test")
public class PostControllerTest {

  @Autowired
  private WebApplicationContext context;

  @MockBean
  private PostService postService;

  @Autowired
  private ObjectMapper objectMapper;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
  }

  @Test
  public void testBadRequest() throws Exception {
    MvcResult result = this.mockMvc.perform(get("/posts/" + UUID.randomUUID() + "/like"))
        .andExpect(status().is4xxClientError())
        .andReturn();
    MockHttpServletResponse response = result.getResponse();
    String contentAsString1 = response.getContentAsString();
    Assertions.assertThat(contentAsString1).isNotNull();
  }

  @Test
  public void testErrorSavingEntityToDatabaseException() throws Exception {
    PostModel postModel = new PostModel();
    postModel.setUserId(UUID.randomUUID());
    postModel.setId(UUID.randomUUID());
    Mockito.when(postService.addLike(Mockito.any(), Mockito.any()))
        .thenThrow(new ErrorSavingEntityToDatabaseException("Cannot Save to Database"));
    MvcResult result = this.mockMvc.perform(
        put("/posts/" + UUID.randomUUID() + "/like").contentType(MediaType.APPLICATION_JSON_VALUE)
            .content(
                JacksonParserUtils.getJsonStringValue(postModel)))
        .andExpect(status().isUnprocessableEntity())
        .andReturn();
    MockHttpServletResponse response = result.getResponse();
    String contentAsString1 = response.getContentAsString();
    Assertions.assertThat(contentAsString1).isNotNull();
    Assert.assertTrue(
        JsonPath.read(contentAsString1, "$.message").toString()
            .contains("Cannot Save to Database"));
  }

  @Test
  public void testResourceNotFoundException() throws Exception {
    Mockito.when(postService.getPost(Mockito.any()))
        .thenThrow(new ResourceNotFoundException("Post ID is Invalid"));
    MvcResult result = this.mockMvc.perform(
        get("/posts/" + UUID.randomUUID()))
        .andExpect(status().isNotFound())
        .andReturn();
    MockHttpServletResponse response = result.getResponse();
    String contentAsString1 = response.getContentAsString();
    Assertions.assertThat(contentAsString1).isNotNull();
    Assert.assertTrue(
        JsonPath.read(contentAsString1, "$.message").toString()
            .contains("Post ID is Invalid"));
  }


}
