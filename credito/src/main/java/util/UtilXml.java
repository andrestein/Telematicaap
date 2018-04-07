package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class UtilXml {
	
	public static Document createXmlDoc() throws ParserConfigurationException {
		Document doc;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.newDocument();
		return doc;
	}
	
	public static Document loadXmlDoc(String source) throws Exception {
		// Cargar Documento
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(false);
		DocumentBuilder builder;
		builder = factory.newDocumentBuilder();
		return builder.parse(new FileInputStream(source));
	}
	
	public static Document loadXmlString(String xml) throws Exception {
		// Cargar Documento
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(false);
		DocumentBuilder builder;
		builder = factory.newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(xml));
		return builder.parse(is);
	}
	

	public static void saveXML(Element node, String path_filename) {
		Result output = new StreamResult(new File(path_filename));
		Transformer transformer;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(new DOMSource(node), output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getXPath(Element e, String path) {
		XPathFactory xFactory = XPathFactory.newInstance();
		XPath xpath = xFactory.newXPath();
		try {
			return (String) xpath.evaluate(path, e, XPathConstants.STRING);
		} catch (XPathExpressionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "0";
		}
	}

	public static void addElement(Element e, String name, Object val) {
		Element child = e.getOwnerDocument().createElement(name);
		// e.setNodeValue(val.toString());
		if (val != null)
			child.setTextContent(val.toString());
		// child.appendChild(e.getOwnerDocument().createTextNode(val.toString()));

		e.appendChild(child);

	}
	


	public static double getElement(Element e, String path) {
		String s = "*";
		try {
			NodeList list = e.getElementsByTagName(path);
			if (list != null && list.getLength() > 0) {
				s = list.item(0).getTextContent();
				return Double.parseDouble(s);
			} else
				return 0;
		} catch (Exception err) {
			System.out.println("No se puede convertir " + s);
			err.printStackTrace();
			return 0;
		}

	}
	
	public static String getElementText(Element e, String path) {
		String s = "*";
		try {
			NodeList list = e.getElementsByTagName(path);
			if (list != null && list.getLength() > 0) {
				return list.item(0).getTextContent();
			} else
				return s;
		} catch (Exception err) {
			System.out.println("No se puede convertir " + s);
			// err.printStackTrace();
			return s;
		}
	}



}
