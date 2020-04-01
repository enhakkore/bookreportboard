package com.practice.bookreportboard.domain.books.kakao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Meta {
    private Integer total_count;
    private Integer pageable_count;
    private Boolean is_end;
}
