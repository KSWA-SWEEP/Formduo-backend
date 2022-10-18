//package com.example.jpa_demo.posts;
//
//import com.example.jpa_demo.entity.posts.Posts;
//import com.example.jpa_demo.entity.posts.PostsRepository;
//import org.junit.After;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class PostsRepositoryTest {
//
//    @Autowired
//    PostsRepository postsRepository;
//
//    @After
//    public void cleanup(){
//        postsRepository.deleteAll();
//    }
//
//    @Test
//    public void 게시글저장_불러오기() {
//        String title = "test title";
//        String content = "test con";
//
//        postsRepository.save(Posts.builder()
//                        .title(title)
//                        .content(content)
//                        .author("chunpense")
//                                .build());
//
//        List<Posts> postsList = postsRepository.findAll();
//        Posts posts = postsList.get(0);
//
//        assertThat(posts.getTitle()).isEqualTo(title);
//        assertThat(posts.getContent()).isEqualTo(content);
//    }
//}
