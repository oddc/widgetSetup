package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class XMLReader {
	
	
	private File xmlFile;
	private Document doc;
	private Element root;
	private Boolean ok;
	
	
	public XMLReader(String path) {
		this.xmlFile = new File(path);	
		this.doc = null;
		this.root = null;
		this.ok = false;
		
		if(xmlFile.exists()) {
	        try {
	            SAXBuilder builder = new SAXBuilder();
	            this.doc = builder.build(this.xmlFile);
	            this.root = this.doc.getRootElement();
	            this.ok = true;
	        }
	        catch(Exception ex) {
	        	System.err.println(ex);
	        }
		}
            
	}
	
	
	public Boolean isOK() {
		return this.ok;
	}
	
	
	public Element getRoot() {
		return this.root;
	}
	
	
	public Element getRessource() {
		if(!this.ok) return null;
		return this.getRoot().getChildren("ressources").get(0);
	}
	
	
	public Element getInformation() {
		if(!this.ok) return null;
		return this.getRoot().getChildren("information").get(0);
	}
	
	
	public List<String> getStyles() {
		if(!this.ok) return null;
		
		List<Element> res = this.getRessourceList("styles").get(0).getChildren();
		List<String> values = new ArrayList<String>();
		
		for(Element e : res) {
			values.add(e.getValue());
		}
		
		return values;
	}
	
	
	public List<String> getScripts() {
		if(!this.ok) return null;
		
		List<Element> res = this.getRessourceList("scripts").get(0).getChildren();
		List<String> values = new ArrayList<String>();
		
		for(Element e : res) {
			values.add(e.getValue());
		}
		
		return values;
	}
	
	
	private List<Element> getRessourceList(String res) {
		if(!this.ok) return null;
		
		Element templates = this.getRessource().getChildren("templates").get(0);
		Element template = templates.getChildren("template").get(0);
		
		return template.getChildren(res);
	}
	
	
	public String getMarkupPath() {
		if(!this.ok) return null;
		
		Element templates = this.getRessource().getChildren("templates").get(0);
		Element template = templates.getChildren("template").get(0);
		
		return template.getAttributeValue("markup");
	}
	
	
	public String getWidgetId() {
		if(!this.ok) return null;
		
		return this.getRoot().getAttributeValue("id");
	}
	
	
	public String getWidgetbuilderVersion() {
		if(!this.ok) return null;
		
		return this.getRoot().getAttributeValue("builder");
	}
	
	
	public String getWidgetName() {
		if(!this.ok) return null;
		
		return this.getInformation().getChildren("name").get(0).getValue();				 
	}
	
	
	public String getMainColor() {
		if(!this.ok) return null;
		
		String result = this.getInformation().getChildren("appearance").get(0).getChildren("main-color").get(0).getValue();
		return result;
	}
	
	
	public String getTextAppearance() {
		if(!this.ok) return null;
		
		String result = this.getInformation().getChildren("appearance").get(0).getChildren("text-appearance").get(0).getValue();
		return result;
	}
}

