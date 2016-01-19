package net.andreu.subvencions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Subvencions de Nadal
 *
 */
public class App 
{
	private String arxiu = "src/main/resources/llista.txt";
	private ArrayList<String> regals = new ArrayList<String>();
	private HashMap<String, Integer> contador = new HashMap<String, Integer>();
	private int totalRegals=0;
	
    public static void main( String[] args )
    {
    	App a = new App();
		a.inici();
	}

	public void inici() {
		llegirFitxer();
		procesaFitxer();
		imprimeix();
	}

	private void imprimeix() {
		for(String clau:contador.keySet()){
			float persentatge;
			persentatge=((float)contador.get(clau)*100)/totalRegals;
			System.out.println(clau+": "+ String.format("%.2f",persentatge));
		}
		
	}

	public void llegirFitxer() {
		BufferedReader br = null;

		try {
			br = new BufferedReader(new FileReader(arxiu));
			String linia;
			while ((linia = br.readLine()) != null) {
				regals.add(linia);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void procesaFitxer() {
		Pattern nen = Pattern.compile("(.*?:) (.*)");
		Pattern personatges = Pattern.compile("([^:]+): ([^-]+)( - |$)");
		Pattern regal = Pattern.compile("(.*?,)");
		int tempContador;
		for(String linia: regals){
			Matcher n = nen.matcher(linia);
			n.find();
			Matcher p = personatges.matcher(n.group(2));
			while(p.find()){
				Matcher r = regal.matcher(p.group(2));
				tempContador=1;
				while(r.find()){
					tempContador++;
				}
				totalRegals=totalRegals+tempContador;
				if (contador.containsKey(p.group(1))){
					int valor=contador.get(p.group(1));
					contador.put(p.group(1), valor+tempContador);
				}else{
					contador.put(p.group(1), tempContador);
				}
			}
		}
		
	}
}
