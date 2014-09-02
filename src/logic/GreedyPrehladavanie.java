package logic;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import javax.swing.JTextArea;

public class GreedyPrehladavanie {
	
	JTextArea area;

	public GreedyPrehladavanie(JTextArea textArea) {
		this.area = textArea;
	}

	// funkcia na vytvorenie kopie vstupne pola
	public Integer[] vytvorKopiu(Integer[] vstup) {

		if (vstup == null)
			return null;

		Integer[] kopia = new Integer[vstup.length];

		for (int i = 0; i < kopia.length; i++) {
			kopia[i] = vstup[i];
		}

		return kopia;
	}

	// funkcia, ktore vrati nove pole, v ktorom budu vymenene prvky medzi
	// poziciami pos1 a pos2
	public Integer[] vymen(Integer[] rozlozenie, int pos1, int pos2) {

		int i1 = rozlozenie[pos1];
		int i2 = rozlozenie[pos2];

		Integer[] kopia = vytvorKopiu(rozlozenie);

		kopia[pos1] = i2;
		kopia[pos2] = i1;

		return kopia;
	}

	// funkcia, ktora v danom poli-hlavolame posunie policko, nachadzajuce sa
	// vlavo od prazdneho miesta, doprava
	public Integer[] vpravo(Integer[] rozlozenie, int puzzleSizeX,
			int puzzleSizeY) {

		int mPosition = Arrays.asList(rozlozenie).indexOf(0);

		if (mPosition % puzzleSizeX == 0)
			return null;
		else
			return vymen(rozlozenie, mPosition - 1, mPosition);
	}

	// funkcia, ktora v danom poli-hlavolame posunie policko, nachadzajuce sa
	// vpravo od prazdneho miesta, dolava
	public Integer[] vlavo(Integer[] rozlozenie, int puzzleSizeX,
			int puzzleSizeY) {

		int mPosition = Arrays.asList(rozlozenie).indexOf(0);

		if (mPosition % puzzleSizeX == puzzleSizeX - 1)
			return null;
		else
			return vymen(rozlozenie, mPosition + 1, mPosition);
	}

	// funkcia, ktora v danom poli-hlavolame posunie policko, nachadzajuce sa
	// dole od prazdneho miesta, hore
	public Integer[] hore(Integer[] rozlozenie, int puzzleSizeX, int puzzleSizeY) {

		int mPosition = Arrays.asList(rozlozenie).indexOf(0);

		if (mPosition + puzzleSizeX >= (puzzleSizeX * puzzleSizeY))
			return null;
		else

			return vymen(rozlozenie, mPosition + puzzleSizeX, mPosition);

	}

	// funkcia, ktora v danom poli-hlavolame posunie policko, nachadzajuce sa
	// hore od prazdneho miesta, dole
	public Integer[] dole(Integer[] rozlozenie, int puzzleSizeX, int puzzleSizeY) {

		int mPosition = Arrays.asList(rozlozenie).indexOf(0);

		if (mPosition - puzzleSizeX < 0)
			return null;
		else
			return vymen(rozlozenie, mPosition - puzzleSizeX, mPosition);
	}

	// hlavna funkcia pre najdenie riesenia pomocou heuristiky odhadu medzi
	// svojimi aktualnymi polohami a konecnymi polohami
	public void hladajCestu(Integer[] rozlozenie, Integer[] konecneRozlozenie,
			HeuristikaInterface heuristika, int puzzleSizeX, int puzzleSizeY) {

		Integer[] aktualneRozlozenie;
		Integer[] noveRozlozenie;
		int pocetKrokov = 0;
		int maxHlbka = 0;
		HashMap<List<Integer>, Integer> hashMapaNavstivenychstavov;
		
		// hashmapa, do ktorej si ukladam navstivene stavy
		hashMapaNavstivenychstavov = new HashMap<List<Integer>, Integer>();
		// comparator, pomocou ktoreho porovnavam stavy v prioritnom fronte
		Comparator<Stav> comparator = new StavComparator();
		// prioritny front, v ktorom si ukladam stavy a zoraduju sa podla ceny
		// heuristiky odhadu
		PriorityQueue<Stav> frontStav = new PriorityQueue<>(4000000, comparator);

		long timeStart = System.nanoTime();

		// inicializacia stavu
		Stav aktualnyStav = new Stav(rozlozenie, 0, null, null, 0);
		frontStav.add(aktualnyStav);
		hashMapaNavstivenychstavov.put(Arrays.asList(rozlozenie), 1);

		// cyklus pre prehladavanie stavov
		while (frontStav.peek() != null
				&& !Arrays.equals(frontStav.peek().rozlozenie,
						konecneRozlozenie)) {

			aktualnyStav = frontStav.remove();
			aktualneRozlozenie = aktualnyStav.rozlozenie;
			if (aktualnyStav.hlbka > maxHlbka)
				maxHlbka = aktualnyStav.hlbka;

			// -----vseobnecne pre kazdy z operatorov VLAVO, VPRAVO, HORE, DOLE
			// 1. podmienka - zistujem, ci predchadzajuci pouzitim operatora sa
			// nedostanem do predchadzajuceho stavu

			// 2. podmienka - zistujem, ci mozem pouzit operator na dane
			// rozlozenie policok algoritmu a ci som v danom stave uz niekedy
			// bol - pozeram sa do hash mapy a hladam stav podla kluca - v
			// pripade, ze mi najde stav podla kluca v hash mape, dany stav som
			// navstivil, nic s nim nerobim a idem dalej v cykle

			// nakoniec samotne pridanie stavu do prioritneho frontu
			// --------------------------------------------------------------

			if (!"VLAVO".equals(aktualnyStav.operator)) {
				noveRozlozenie = vpravo(aktualneRozlozenie, puzzleSizeX,
						puzzleSizeY);
				if (noveRozlozenie != null
						&& hashMapaNavstivenychstavov.get(Arrays
								.asList(noveRozlozenie)) == null) {
					hashMapaNavstivenychstavov.put(
							Arrays.asList(noveRozlozenie), 1);
					frontStav.add(new Stav(noveRozlozenie, heuristika.getOdhad(
							noveRozlozenie, konecneRozlozenie, puzzleSizeX,
							puzzleSizeY), "VPRAVO", aktualnyStav,
							aktualnyStav.hlbka + 1));
				}
			}

			if (!"VPRAVO".equals(aktualnyStav.operator)) {
				noveRozlozenie = vlavo(aktualneRozlozenie, puzzleSizeX,
						puzzleSizeY);
				if (noveRozlozenie != null
						&& hashMapaNavstivenychstavov.get(Arrays
								.asList(noveRozlozenie)) == null) {
					hashMapaNavstivenychstavov.put(
							Arrays.asList(noveRozlozenie), 1);
					frontStav.add(new Stav(noveRozlozenie, heuristika.getOdhad(
							noveRozlozenie, konecneRozlozenie, puzzleSizeX,
							puzzleSizeY), "VLAVO", aktualnyStav,
							aktualnyStav.hlbka + 1));
				}
			}

			if (!"DOLE".equals(aktualnyStav.operator)) {
				noveRozlozenie = hore(aktualneRozlozenie, puzzleSizeX,
						puzzleSizeY);
				if (noveRozlozenie != null
						&& hashMapaNavstivenychstavov.get(Arrays
								.asList(noveRozlozenie)) == null) {
					hashMapaNavstivenychstavov.put(
							Arrays.asList(noveRozlozenie), 1);
					frontStav.add(new Stav(noveRozlozenie, heuristika.getOdhad(
							noveRozlozenie, konecneRozlozenie, puzzleSizeX,
							puzzleSizeY), "HORE", aktualnyStav,
							aktualnyStav.hlbka + 1));
				}
			}

			if (!"HORE".equals(aktualnyStav.operator)) {
				noveRozlozenie = dole(aktualneRozlozenie, puzzleSizeX,
						puzzleSizeY);
				if (noveRozlozenie != null
						&& hashMapaNavstivenychstavov.get(Arrays
								.asList(noveRozlozenie)) == null) {
					hashMapaNavstivenychstavov.put(
							Arrays.asList(noveRozlozenie), 1);
					frontStav.add(new Stav(noveRozlozenie, heuristika.getOdhad(
							noveRozlozenie, konecneRozlozenie, puzzleSizeX,
							puzzleSizeY), "DOLE", aktualnyStav,
							aktualnyStav.hlbka + 1));
				}
			}

			// pocet krokov znaci kolko stavov som uz presiel
			pocetKrokov++;

		}

		long timeEnd = System.nanoTime();
		double time = (double) (timeEnd - timeStart) / 1000000;
		area.append("Cas vykonavania prehladavania: " + time + " ms" + "\n");

		// front obsahuje konecny stav alebo je prazdny

		// ak je front prazdny, tak riesenie neexistuje
		if (frontStav.peek() == null) {
			area.append("Cesta pre dane riesenie pomocu heuristiky neexistuje"
					+ "\n");
			area.append("Dosiahnuta maximalna hlbka: " + maxHlbka + "\n");
			return;
		}

		Stav stav = frontStav.remove();
		if (stav.hlbka > maxHlbka)
			maxHlbka = stav.hlbka;

		area.append("Pocet navstivenych stavov: " + pocetKrokov + "\n");
		area.append("Dosiahnuta maximalna hlbka: " + maxHlbka + "\n");

		// backtracking pouzitych operatorov a nasledne reverz a vypis pouzitych
		// operatorov v poradi, v akom sa pouzili
		List<String> kroky = new LinkedList<>();
		while (stav.operator != null) {
			kroky.add(stav.operator);
			stav = stav.previousStav;
		}
		Collections.reverse(kroky);

		area.append("Pocet pouzitych operatorov / hlbka: " + kroky.size()
				+ "\n" + kroky + "\n");
	}

}
