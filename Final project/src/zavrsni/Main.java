package zavrsni;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		

		Recnik r=new Recnik();
		r.connect();
		r.popuniRecnik();
		System.out.println("Recnik popunjen");
		
		Knjiga k=new Knjiga(r);
		k.ukloniZnakeIPopuniListuReci();
		System.out.println("Znaci uklonjeni i lista reci popunjena");
		ArrayList<String> noveReci=k.pronaciNoveReci();
		r.dodajNoveReci(noveReci);
		System.out.println("Broj novih reci: "+noveReci.size());
		System.out.println("Nove reci dodate u bazu");
		System.out.println("20 najcescih reci: ");
		k.dvadesetNajcescihReci();
		k.upisatiUFajlSveReci();
		Scanner sc=new Scanner(System.in);
		System.out.println("Unesite rec: ");
		String rec=sc.next();
		k.nadjiJednuRec(rec);
		r.disconnect();
	}

}
