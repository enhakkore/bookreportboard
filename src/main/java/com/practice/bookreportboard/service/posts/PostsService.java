package com.practice.bookreportboard.service.posts;

import com.practice.bookreportboard.domain.posts.Posts;
import com.practice.bookreportboard.domain.posts.PostsRepository;
import com.practice.bookreportboard.web.dto.PostsListResponseDto;
import com.practice.bookreportboard.web.dto.PostsResponseDto;
import com.practice.bookreportboard.web.dto.PostsSaveRequestDto;
import com.practice.bookreportboard.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto postsSaveRequestDto){
        return postsRepository.save(postsSaveRequestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto postsUpdateRequestDto){
        Posts post = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 id가 없습니다. id = " + id));
        post.update(postsUpdateRequestDto.getTitle(), postsUpdateRequestDto.getContent());
        return id;
    }

    public PostsResponseDto get(Long id){
        Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 id가 없습니다. id = " + id));
        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream().map(PostsListResponseDto::new).collect(toList());
    }

    @Transactional
    public void delete(Long id) {
        Posts post = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 id가 없습니다. id = "+id));
        postsRepository.delete(post);
    }
}
