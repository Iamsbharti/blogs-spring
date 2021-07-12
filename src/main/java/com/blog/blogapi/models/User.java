package com.blog.blogapi.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import java.util.List;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String password;

    @JsonBackReference
    @OneToMany(targetEntity = Blog.class,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name="user_id",referencedColumnName = "id")
    private List<Blog> blogs;

    public User() {
    }
    public User(Integer id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(Integer id, String name, String email, String password,List blogs) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.blogs=blogs;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }
}
