package logic;

public class HeuristikaPocetNespravnychPolicok implements HeuristikaInterface {

	// heuristicka funkcia, ktora zisti pocet policok, ktore nelezia na svojom
	// mieste
	public int getOdhad(Integer[] rozlozenie, Integer[] konecneRozlozenie,
			int puzzleSizeX, int puzzleSizeY) {

		int aktualnaCena = 0;

		for (int i = 0; i < rozlozenie.length; i++)
			if (rozlozenie[i] != konecneRozlozenie[i])
				aktualnaCena++;

		return aktualnaCena;
	}

}
