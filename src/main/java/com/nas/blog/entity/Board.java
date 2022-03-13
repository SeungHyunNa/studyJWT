package com.nas.blog.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob // 대용량
    private String Content;

    @ColumnDefault("0")
    private int views; // 조회수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board")
    private List<Reply> reply = new ArrayList<>();

    @Builder
    public Board(Long id, String title, String content, int views, User user, List<Reply> reply) {
        this.id = id;
        this.title = title;
        Content = content;
        this.views = views;
        this.user = user;
        this.reply = reply;
    }
}
