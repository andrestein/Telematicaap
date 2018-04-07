package converter;

import java.io.*;

import javax.xml.parsers.*;
// import org.xml.sax.*;

import org.json.*;

public class MapperTest {

	public static void main(String[] args) {
		try {
			JSONObject json = XJMapper.FileToJSON(
					"c:/Users/Creativos Digitales/Google Drive/CRD/Proyectos/Deterioro/auto/politicas/udem.xml");

			String xml = XJMapper.JSONToString(json);

			System.out.print(json.toString());
			System.out.print('\n');
			System.out.print('\n');
			System.out.print(xml);
			System.out.print('\n');
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	static public JSONObject readToJSON(InputStream stream) throws Exception {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		SAXParser parser = factory.newSAXParser();
		SAXJsonParser handler = new SAXJsonParser();
		parser.parse(stream, handler);
		return handler.getJson();
	}

}
