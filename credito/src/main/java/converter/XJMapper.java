/**
 * 
 */
package converter;

import java.io.*;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.json.JSONObject;

/**
 * @author Creativos Digitales
 *
 */
public class XJMapper {

	static public JSONObject StreamToJSON(InputStream stream) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		SAXParser parser = factory.newSAXParser();
		SAXJsonParser handler = new SAXJsonParser();
		parser.parse(stream, handler);
		return handler.getJson();
	}

	static public JSONObject FileToJSON(String filename) throws Exception {
		InputStream strm;
		strm = new FileInputStream(filename);
		return StreamToJSON(strm);
	}

	static public JSONObject StringToJSON(String xml, String charset) throws Exception {
		InputStream strm;
		strm = new ByteArrayInputStream(xml.getBytes(charset));
		return StreamToJSON(strm);
	}

	static public String JSONToString(JSONObject json) {
		return org.json.XML.toString(json);
	}

	static public String JSONToString(String json) {
		return org.json.XML.toString(new JSONObject(json));
	}
}
