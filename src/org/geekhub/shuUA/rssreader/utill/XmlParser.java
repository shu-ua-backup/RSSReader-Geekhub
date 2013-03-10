package org.geekhub.shuUA.rssreader.utill;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;
import android.util.Log;
import org.geekhub.shuUA.rssreader.db.ArticleTable;
import org.geekhub.shuUA.rssreader.db.DatabaseHelper;
import org.geekhub.shuUA.rssreader.object.Article;
import org.geekhub.shuUA.rssreader.object.Const;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class XmlParser {

    int i=0;
    boolean isItem = false;
    String Element;
    Article art = new Article();

    public void parseXml(final Context context) {

        try {
            final SQLiteDatabase database;
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            DatabaseHelper dbHelper = new DatabaseHelper(context);
            database = dbHelper.getWritableDatabase();
            DefaultHandler handler = new DefaultHandler() {

                StringBuffer buffer = null;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("item")) {
                        if (art != null && isItem ) {
                            Cursor cursor = database.query(ArticleTable.TABLE_ARTICLES, null,
                                    ArticleTable.COLUMN_TITLE + " in ('" + art.getTitle() + "')",
                                    null,null,null,null);

                            if (cursor.getCount() == 0) {
                                art.saveToDB(context);
                            }

                            art = new Article();
                        }
                        isItem = true;
                    }

                    buffer = new StringBuffer();
                }


                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (qName.equals("title")) {
                        art.setTitle(buffer.toString());
                        art.setLike(false);
                    } else if (qName.equals("pubDate")) {
                        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
                        try {
                            Time now = new Time();
                            now.format("EEE, dd MMM yyyy HH:mm:ss z");
                            now.setToNow();
                            //art.setPubDate(format.parse(now.toString()));
                            Date date = format.parse(buffer.toString());
                            //long mls = System.currentTimeMillis() - date.getTime();
                            //date = new Date(mls);
                            art.setPubDate(date);
                        } catch (ParseException e) {
                            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                        }
                    } else if (qName.equals("description")) {
                        int start = buffer.indexOf("http://hoopscsdn.s3.amazonaws.com");
                        int end = buffer.indexOf(".jpg");
                        int tag_end = buffer.indexOf("/>");
                        if (start != -1 && end != -1) {
                            art.setImgLink(buffer.toString().substring(start, end + ".jpg".length()));
                            art.setContent(buffer.toString().substring(tag_end + "/>".length()));
                        }
                    } else if (qName.equals("link")) {
                        art.setLink(buffer.toString());
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    if (buffer != null) {
                        buffer.append(ch,start, length);
                    }
                }
            };
            saxParser.parse(Const.FEED_URL,handler);
            Cursor cursor = database.query(ArticleTable.TABLE_ARTICLES, null,
                    ArticleTable.COLUMN_TITLE + " in ('" + art.getTitle() + "')",
                    null,null,null,null);
            if (cursor.getCount() == 0) {
                art.saveToDB(context);
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            Log.e("1e13",e.toString());
        }


    }
}
