package zavrsni;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Knjiga {
	
	private HashMap<String,Integer> reci;
	private Recnik recnik;

	public Knjiga(Recnik r) {
		reci=new HashMap<String,Integer>();
		recnik=r;
	}
	
	public void ukloniZnakeIPopuniListuReci() {     //uklanjam znake interpukcije
		try {
			BufferedReader br=new BufferedReader(new FileReader("src\\knjiga"));
			
			String linija=br.readLine();
			
			while(linija!=null) {
				if(linija.isEmpty()) {
					linija=br.readLine();
					continue;
				}
				if(linija.matches(".*((- )|(-))$")) {
					linija=linija.replaceAll("((- )|(-))$", "");
						linija+=br.readLine();
						continue;
				}
				linija=linija.replaceAll("[^a-zA-Z -]", "");
				String[] nizReci = linija.split(" ");
				for(String rec:nizReci) {
					if(!reci.containsKey(rec.toLowerCase()))reci.put(rec.toLowerCase(), 1);
					else {
						int brPonavljanja=reci.get(rec.toLowerCase());
						brPonavljanja++;
						reci.replace(rec.toLowerCase(), brPonavljanja);
					}
				}
				linija=br.readLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//pronalazim reci koje postoje u knjizi, a ne postoje u recniku
	public ArrayList<String> pronaciNoveReci(){  
		ArrayList<String> reci2=new ArrayList<String>();
		for(String rec: reci.keySet()) {
			if(!recnik.pronadjiRec(rec)) {
				reci2.add(rec);   
			}
		}
		return reci2;
	}
	
	public void dvadesetNajcescihReci() {
	      LinkedList<Map.Entry<String, Integer>> lista = new LinkedList<>(reci.entrySet());
	      Collections.sort(lista, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue().compareTo(o1.getValue()));
			}
		});
	      
	      for(int i =0 ; i< 20; i++) {
	    	  System.out.println(i+1+"."+lista.get(i).getKey() + ":" + lista.get(i).getValue());
	      }
	     
	} 
	
	public void upisatiUFajlSveReci() {
		try {
			FileWriter fw=new FileWriter("sve_reci.txt");
			
			ArrayList<String> reciSortirane = new ArrayList<>(reci.keySet());
			Collections.sort(reciSortirane);
			
			for(String s: reciSortirane)fw.write(s + "\n");
			
			fw.flush();
			fw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public void nadjiJednuRec (String rec) {
		int brPonavljanja = 0;
		for ( String r : reci.keySet()) {
			if ( r.equals(rec)) {
				brPonavljanja = reci.get(r);
				break;
			}
		}
		rec = rec.toUpperCase();
		if ( brPonavljanja == 0) {
			System.out.println("Rec ne postoji u knjizi.");
		} else {
			System.out.println("Broj ponavljanja reci "+rec+" je: " + brPonavljanja);
		}
	}
	
	
}
