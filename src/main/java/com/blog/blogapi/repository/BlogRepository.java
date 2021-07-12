package com.blog.blogapi.repository;

import com.blog.blogapi.models.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog,Integer> {
    Blog findBlogsById(Integer blogId);
    List<Blog> findBlogsByUserId(Integer userId);
    void deleteBlogsById(Integer blogId);
}
