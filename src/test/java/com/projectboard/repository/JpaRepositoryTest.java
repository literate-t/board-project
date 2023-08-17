package com.projectboard.repository;

import com.projectboard.config.JpaConfig;
import com.projectboard.domain.Article;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("testdb")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 설정한 testdb를 사용할 수 있도록
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class) // 내가 만든 config를 알려줘야지
@DataJpaTest
class JpaRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleCommentRepository articleCommentRepository;

    @PersistenceContext
    private EntityManager em;

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        // when
        List<Article> articles = articleRepository.findAll();

        // then
        assertThat(articles)
                .isNotNull()
                .hasSize(123);
    }
    
    @DisplayName("insert 테스트")
    @Test
    public void inertTest() {
        // given
        long previousCount = articleRepository.count();
        Article newArticle = Article.create("new article", "new content", "#spring");

        // when
        Article savedArticle = articleRepository.save(newArticle);

        // then
        assertThat(articleRepository.count())
                .isEqualTo(previousCount + 1);

    }

    @DisplayName("update 테스트")
    @Test
    public void updateTest() {
        // given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag);

        // when
        em.flush();
        em.clear();

        // then
        Article updatedArticle = articleRepository.findById(1L).orElseThrow();
        assertThat(updatedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @DisplayName("delete 테스트")
    @Test
    public void deleteTest() {
        // given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        int deletedCommentsSize = article.getArticleComments().size();

        // when
        articleRepository.delete(article);

        // then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentsSize);
    }
}
