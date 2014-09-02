package logic;

import java.util.Arrays;

public class HeuristikaVzdialenosti implements HeuristikaInterface {

	// funkcia, ktora zistuje vzdialenost medzi aktualnou pozicou policka a
	// konecnou pozicou policka
	public int zistiVzdialenost(int aktPozicia, int konecnaPozicia,
			int puzzleSizeX, int puzzleSizeY) {

		// usporiadam si cisla tak, aby mi vzdialenost vysla v kladnych cislach
		int pos1 = Math.max(aktPozicia, konecnaPozicia);
		int pos2 = Math.min(aktPozicia, konecnaPozicia);

		// rozdiel = sucet horizontalnej vzdialenosti + vertikalnej
		int vzdialenost = Math.abs(pos1 % puzzleSizeX - pos2 % puzzleSizeX)
				+ Math.abs(pos1 / puzzleSizeY - pos2 / puzzleSizeY);

		return vzdialenost;
	}

	// heuristicka funkcia, ktore zisti sucet vzdialenosti v hlavolame medzi
	// aktualnou
	// poziciou a konecnou pozicou policok
	public int getOdhad(Integer[] rozlozenie, Integer[] konecneRozlozenie,
			int puzzleSizeX, int puzzleSizeY) {

		int aktualnaCena = 0;

		// zistujem, ci policko lezi na svohom mieste
		for (int i = 0; i < rozlozenie.length; i++) {
			if (rozlozenie[i] != konecneRozlozenie[i])
				aktualnaCena += zistiVzdialenost(
						i,
						Arrays.asList(konecneRozlozenie).indexOf(rozlozenie[i]),
						puzzleSizeX, puzzleSizeY);
		}

		return aktualnaCena;
	}
}
