//package com.example.jpa_demo.web;
//
//
//import com.example.jpa_demo.entity.posts.Posts;
//import com.example.jpa_demo.entity.posts.PostsRepository;
//import com.example.jpa_demo.web.dto.posts.PostsSaveRequestDto;
//import com.example.jpa_demo.web.dto.posts.PostsUpdateRequestDto;
//import org.junit.After;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class PostsApiControllerTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Autowired
//    private PostsRepository postsRepository;
//
//    @After
//    public  void tearDown() throws Exception {
//        postsRepository.deleteAll();
//    }
//
//    @Test
//    public void Posts_등록된다() throws Exception {
//        String title = "title";
//        String content = "content";
//        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
//                .title(title)
//                .content(content)
//                .author("chunpense")
//                .build();
//
//        // 해당 주소로 post를 날린다.
//        String url = "http://localhost:" + port + "/api/v1/posts";
//        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);
//
//        // 디비에서 값을 가져와서 내가 보낸 것이랑 같은지 확인
//        List<Posts> all = postsRepository.findAll();
//        assertThat(all.get(0).getTitle()).isEqualTo(title);
//        assertThat(all.get(0).getContent()).isEqualTo(content);
//    }
//
//    @Test
//    public void Posts_수정된다() throws Exception {
//
//        Posts savePosts = postsRepository.save(Posts.builder()
//                        .title("title")
//                        .content("content")
//                .author("author")
//                .build());
//
//        Long updateId = savePosts.getId();
//        String expectedTitle = "title2";
//        String expectedContent = "content2";
//
//        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
//                .title(expectedTitle)
//                .content(expectedContent)
//                .build();
//
//        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;
//
//        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);
//
//        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);
//
//        List<Posts> all = postsRepository.findAll();
//        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
//        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
//
//    }
//}
