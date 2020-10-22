package com.tyf.baseproject.entity;

import javax.persistence.*;

/**
 * @description: 模板
 * @author: tyf
 * @create: 2020-07-27 10:58
 **/
@Entity
@Table(name = "mould")
public class Mould {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 20)
    private String name;
    @Column(length = 20)
    private String project;
    @Column
    private String content;

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

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
