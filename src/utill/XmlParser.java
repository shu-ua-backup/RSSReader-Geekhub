package utill;

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
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    if (qName.equalsIgnoreCase("item")) {
                       if (art != null && isItem ) {
                           ArtCollection.add(art);
                           art = new Article();
                       }
                        isItem = true;
                        }
                        if (qName.equalsIgnoreCase("enclosure")) {
                            art.setImgLink(attributes.getValue("url"));
                        }

                }


                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    super.endElement(uri, localName, qName);
                    if (isItem) {
                        if (qName.equalsIgnoreCase("title")) {
                            art.setTitle(Element);
                        } else if (qName.equalsIgnoreCase("pheedo:origLink")) {
                            art.setLink(Element);
                        } else if (qName.equalsIgnoreCase("description")) {
                            art.setContent(Element);
                        } else if (qName.equalsIgnoreCase("pubDate")) {
                            art.setPubDate(Element);
                        }
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    Element = new String(ch, start, length);
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

        return ArtCollection;
    }
}
