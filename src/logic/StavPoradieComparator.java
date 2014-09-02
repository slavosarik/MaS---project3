package logic;

import java.util.Comparator;

public class StavPoradieComparator implements Comparator<Stav> {
	@Override
	public int compare(Stav s1, Stav s2) {

		if (s1.id < s2.id) {
			return -1;
		}
		if (s1.id > s2.id) {
			return 1;
		}
		return 0;
	}
}
