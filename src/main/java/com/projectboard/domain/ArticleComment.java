package com.projectboard.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleComment extends AuditingField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @ManyToOne(optional = false, fetch = LAZY) private Article article;
    @Setter @Column(nullable = false, length = 500) private String content;

    private ArticleComment(Article article, String content) {
        this.article = article;
        this.content = content;
    }

    public ArticleComment create(Article article, String content) {
        return new ArticleComment(article, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleComment that = (ArticleComment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
