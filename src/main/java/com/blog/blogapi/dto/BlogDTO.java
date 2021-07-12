package com.blog.blogapi.dto;

import com.blog.blogapi.models.Blog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BlogDTO {
    private Blog blog;
}
