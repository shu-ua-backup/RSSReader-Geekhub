package org.geekhub.shuUA.rssreader.db;


import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

public class ArticleDB {

    public final static String ARTICLE_TITLE_FIELD_NAME = "title";
    public final static String ARTICLE_TEXT_FIELD_NAME = "text";
    public final static String ARTICLE_ID_FIELD = "id";
    public final static String ARTICLE_ISCHECK_FIELD = "ischeck";
    public final static String ARTICLE_DATE_FIELD = "date";
    public final static String ARTICLE_LINK_FIELD = "link";



    public ArticleDB() {
        super();
    }


    @DatabaseField(canBeNull = false, columnName = ARTICLE_ID_FIELD,unique = true,generatedId = true)
    private Integer id;

    @DatabaseField(canBeNull = false, columnName = ARTICLE_TITLE_FIELD_NAME, useGetSet= true)
    private String title;

    @DatabaseField(canBeNull = false, columnName = ARTICLE_DATE_FIELD, useGetSet= true)
    private Date date;

    @DatabaseField(canBeNull = false, columnName = ARTICLE_LINK_FIELD, useGetSet= true)
    private String link;

    @DatabaseField(canBeNull = false, columnName = ARTICLE_TEXT_FIELD_NAME, useGetSet= true)
    private String text;

    @DatabaseField(canBeNull = false, columnName = ARTICLE_ISCHECK_FIELD, useGetSet= true, defaultValue= "false")
    private Boolean ischeck;


    public ArticleDB(String title, String text) {
        super();
        this.title = title;
        this.text = text;
        this.ischeck= false;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setIscheck(Boolean ischeck) {
        this.ischeck = ischeck;
    }

    public Boolean getIscheck() {
        return ischeck;
    }

    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}