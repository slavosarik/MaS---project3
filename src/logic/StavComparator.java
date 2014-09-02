package logic;

import java.util.Comparator;

public class StavComparator implements Comparator<Stav> {
	@Override
	public int compare(Stav s1, Stav s2) {

		if (s1.aktualnaCena < s2.aktualnaCena) {
			return -1;
		}
		if (s1.aktualnaCena > s2.aktualnaCena) {
			return 1;
		}
		return 0;
	}
}
