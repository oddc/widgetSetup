package main;

public class run {
	public static void main(String[] args) { 
		System.out.println("START");

		TemplateCreator creator = new TemplateCreator("manifest.xml");
		
		if(creator.createTemplate()) System.out.println("-> index.html wurde erstellt!");
		else System.out.println("-> index.html konnte nicht erstellt werden!");
		
		if(creator.createServerJS()) System.out.println("-> server.js wurde erstellt!");
		else System.out.println("-> server.js konnte nicht erstellt werden!");
		
		System.out.println("ENDE");
		
    } 
	
}
