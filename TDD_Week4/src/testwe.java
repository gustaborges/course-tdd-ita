import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class testwe {

	public testwe() throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();;

		//Create the root Customers element
		Element rootElement = doc.createElement("Customers");
		doc.appendChild(rootElement);

		//Create Marker element
		Element markerElement = doc.createElement("Marker");
		markerElement.setAttribute("element", "Legal");
		rootElement.appendChild(markerElement);
	}

}
