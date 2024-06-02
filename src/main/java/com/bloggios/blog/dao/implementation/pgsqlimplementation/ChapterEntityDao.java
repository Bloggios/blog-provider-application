package com.bloggios.blog.dao.implementation.pgsqlimplementation;

import com.bloggios.blog.dao.PgAbstractDao;
import com.bloggios.blog.dao.repository.postgres.ChapterEntityRepository;
import com.bloggios.blog.modal.ChapterEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Owner - Rohit Parihar and Bloggios
 * Author - rohit
 * Project - blog-provider-application
 * Package - com.bloggios.blog.dao.implementation.pgsqlimplementation
 * Created_on - June 01 - 2024
 * Created_at - 20:34
 */

@Component
public class ChapterEntityDao extends PgAbstractDao<ChapterEntity, ChapterEntityRepository> {

    protected ChapterEntityDao(ChapterEntityRepository repository) {
        super(repository);
    }

    public Optional<ChapterEntity> findByChapterId(String chapterId) {
        return repository.findById(chapterId);
    }

    public Optional<ChapterEntity> findByUserIdAndChapterName(String userId, String chapterName) {
        return repository.findByUserIdAndChapterNameIgnoreCase(userId, chapterName);
    }
}
