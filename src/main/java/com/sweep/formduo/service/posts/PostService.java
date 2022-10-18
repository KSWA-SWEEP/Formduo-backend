package com.sweep.formduo.service.posts;

import com.sweep.formduo.domain.posts.Posts;
import com.sweep.formduo.domain.posts.PostsRepository;
import com.sweep.formduo.web.dto.posts.PostsResponseDto;
import com.sweep.formduo.web.dto.posts.PostsSaveRequestDto;
import com.sweep.formduo.web.dto.posts.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. id =" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id){
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id ="+ id));

        return new PostsResponseDto(entity);}
}
