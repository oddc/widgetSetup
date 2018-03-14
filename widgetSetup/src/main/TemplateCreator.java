package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateCreator {

	private XMLReader xml;
	private Boolean ok;
	
	
	public TemplateCreator(String path) {
		this.xml = new XMLReader(path);
		this.ok  = this.xml.isOK();
	}
	
	
	public Boolean isOK() {
		return this.ok;
	}
	
	
	public Boolean createTemplate() {
		if(!this.isOK()) return false;
		
		try {

			InputStream in = getClass().getResourceAsStream("/resources/index.dat");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			
			FileWriter fw = new FileWriter("index.html");
	        BufferedWriter bw = new BufferedWriter(fw);
			
			
			String line = "";
			while((line = br.readLine()) != null) {
				Pattern pattern = Pattern.compile("\\{\\{[a-zA-Z0-9]+\\}\\}");
				Matcher matcher = pattern.matcher(line);
				
				if (matcher.find()) {
					String placeholder = matcher.group();

				    
				    if(placeholder.equals("{{script}}")) {
				    	for(String script : xml.getScripts()) {
				    		bw.write("\t<script src=\"" + script + "\"></script>");
				    		bw.newLine();
				    	}
				    }
				    else if(placeholder.equals("{{styles}}")) {
				    	for(String styles : xml.getStyles()) {
				    		bw.write("\t<link rel=\"stylesheet\" href=\"" + styles + "\">");
				    		bw.newLine();
				    	}
				    }
				    else if(placeholder.equals("{{markup}}")) {
				    	this.writeMarkup(bw);
				    }
				    else if(placeholder.equals("{{title}}")) {
				    	line = line.replace("{{title}}", xml.getWidgetName());
				    	bw.write(line);
				    	bw.newLine();
				    }
				    else if(placeholder.equals("{{widgetbuilder}}")) {
				    	line = line.replace("{{widgetbuilder}}", xml.getWidgetbuilderVersion());
				    	bw.write(line);
				    	bw.newLine();
				    }
				    else if(placeholder.equals("{{widgetid}}")) {
				    	line = line.replace("{{widgetid}}", xml.getWidgetId());
				    	bw.write(line);
				    	bw.newLine();
				    }
				    else if(placeholder.equals("{{maincolor}}")) {
				    	line = line.replace("{{maincolor}}", xml.getMainColor());
				    	bw.write(line);
				    	bw.newLine();
				    }
				    else if(placeholder.equals("{{textappearance}}")) {
				    	line = line.replace("{{textappearance}}", xml.getTextAppearance());
				    	bw.write(line);
				    	bw.newLine();
				    }

				} 
				else {
					bw.write(line);
					bw.newLine();
				}
			}
			
			
			br.close();
			bw.close();
		}
		catch(Exception ex) {
			System.err.println(ex);
			return false;
		}
        
		
		return true;
	}
	
	
	public Boolean createServerJS() {
		try {
			InputStream in = getClass().getResourceAsStream("/resources/server.dat");
			byte[] buffer = new byte[in.available()];
		    in.read(buffer);
		 
		    File targetFile = new File("server.js");
		    FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
			fileOutputStream.write(buffer);
			fileOutputStream.close();
		}
		catch(Exception ex) {
			System.out.println(ex);
			return false;
		}
		
		return true;
	}
	
	
	private void writeMarkup(BufferedWriter fHandle) throws Exception {
		String path = xml.getMarkupPath();
		if(path.charAt(0) == '/') {
			path = path.substring(1);
		}
    	
    	FileReader file = new FileReader(path);
		BufferedReader br = new BufferedReader(file);
		
		String line = "";
		while((line = br.readLine()) != null) {
			fHandle.write("\t" + line);
			fHandle.newLine();
		}
		
		br.close();
	}
	
}
