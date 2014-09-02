package logic;

//trieda reprezentujuca stav
public class Stav {

	Integer[] rozlozenie;
	int aktualnaCena;
	String operator;
	Stav previousStav;
	int hlbka;
	int prejdenaVziadelenost;
	int id;

	public Stav(Integer[] rozlozenie, String operator, Stav previousStav,
			int hlbka, int id) {
		this.rozlozenie = rozlozenie;
		this.operator = operator;
		this.previousStav = previousStav;
		this.hlbka = hlbka;
		this.id = id;
	}

	public Stav(Integer[] rozlozenie, int aktualnaCena, String operator,
			Stav previousStav, int hlbka) {
		this.rozlozenie = rozlozenie;
		this.aktualnaCena = aktualnaCena;
		this.operator = operator;
		this.previousStav = previousStav;
		this.hlbka = hlbka;
	}

	public Stav(Integer[] noveRozlozenie, String operator, Stav previousStav,
			int hlbka) {
		this.rozlozenie = noveRozlozenie;
		this.operator = operator;
		this.previousStav = previousStav;
		this.hlbka = hlbka;
	}

}
