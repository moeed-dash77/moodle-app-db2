package de.unidue.inf.is.domain;

public class RandomSubmission {
	private String Kurs;
	private String Aufgabe;
	private String Beschreibung;
	private String Abgabetext;
	
	public RandomSubmission() {
		
	}
	public RandomSubmission(String Kurs,String Aufgabe,String Beschreibung,String Abgabetext){
		this.Kurs = Kurs;
		this.Aufgabe = Aufgabe;
		this.Beschreibung = Beschreibung;
		this.Abgabetext = Abgabetext;
	}
	
	public void setKurs(String Kurs) {
		this.Kurs = Kurs;
	}
	public void setAufgabe(String Aufgabe) {
		this.Aufgabe = Aufgabe;
	}
	public void setBeschreibung(String Beschreibung) {
		this.Beschreibung = Beschreibung;
	}
	public void setAbgabetext(String Abgabetext) {
		this.Abgabetext = Abgabetext;
	}
	public String getKurs() {
		return Kurs;
	}
	public String getAufgabe() {
		return Aufgabe;
	}
	public String getBeschreibung() {
		return Beschreibung;
	}
	public String getAbgabetext() {
		return Abgabetext;
	}
}
