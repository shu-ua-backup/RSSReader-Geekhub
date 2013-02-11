package org.geekhub.shuUA.rssreader.utill;

import android.util.Log;
import org.geekhub.shuUA.rssreader.object.Article;
import org.geekhub.shuUA.rssreader.object.Const;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Vector;

public class XmlParser {

    Vector<Article> ArtCollection = new Vector<Article>();
    int i=0;
    boolean isItem = false;
    String Element;
    Article art = new Article();

    public Vector<Article> parseXml() {

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();


            DefaultHandler handler = new DefaultHandler() {

                StringBuffer buffer = null;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("item")) {
                       if (art != null && isItem ) {
                           ArtCollection.add(art);
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
                    } else if (qName.equals("pubDate")) {
                        art.setPubDate(buffer.toString());
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
           ArtCollection.isEmpty();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (SAXException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
           Log.e("1e13",e.toString());
        }
        ArtCollection.add(art);
        return ArtCollection;
    }
}
