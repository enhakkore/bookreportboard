package com.practice.bookreportboard.service.posts;

import com.practice.bookreportboard.domain.posts.Posts;
import com.practice.bookreportboard.domain.posts.PostsRepository;
import com.practice.bookreportboard.web.dto.PostsResponseDto;
import com.practice.bookreportboard.web.dto.PostsSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto postsSaveRequestDto){
        return postsRepository.save(postsSaveRequestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsSaveRequestDto postsSaveRequestDto){
        Posts post = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 id가 없습니다. id = " + id));
        post.update(postsSaveRequestDto.getTitle(), postsSaveRequestDto.getContent());
        return id;
    }

    public PostsResponseDto get(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 id가 없습니다. id = " + id));
        return new PostsResponseDto(entity);
    }
}
