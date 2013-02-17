package org.geekhub.shuUA.rssreader.db;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class ArticleDAO extends BaseDaoImpl<ArticleDB, Integer> {

    protected ArticleDAO(ConnectionSource connectionSource, Class<ArticleDB> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    //===========Query's:
    public List<ArticleDB> getAllArticles() throws SQLException {
        return this.queryForAll();
    }

    public List<ArticleDB> getIdArticle(Integer id) throws SQLException {
        QueryBuilder<ArticleDB, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq(ArticleDB.ARTICLE_TITLE_FIELD_NAME, id);
        PreparedQuery<ArticleDB> preparedQuery = queryBuilder.prepare();
        List<ArticleDB> goalList =query(preparedQuery);
        return goalList;
    }

    public List<ArticleDB> getAllLike() throws SQLException {
        QueryBuilder<ArticleDB, Integer> queryBuilder = queryBuilder();
        queryBuilder.where().eq(ArticleDB.ARTICLE_ISCHECK_FIELD, true);
        PreparedQuery<ArticleDB> preparedQuery = queryBuilder.prepare();
        List<ArticleDB> goalList =query(preparedQuery);
        return goalList;
    }
}