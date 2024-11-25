package it.polito.oop.books;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Question {
	
	private Topic argomento;
	private String testo;

	public class Answer{
		private String risposta;
		private boolean giusto;
		public Answer(String risposta, boolean giusto){
			this.risposta = risposta;
			this.giusto = giusto;
		}
		public String getRisposta(){
			return this.risposta;
		}
		public boolean getGiusto(){
			return this.giusto;
		}
	}

	public Collection<Answer> risposte = new ArrayList<>();

	public String getQuestion() {
		return this.testo;
	}
	
	public Topic getMainTopic() {
		return this.argomento;
	}

	public Question(String testo, Topic argomento){
		this.testo = testo;
		this.argomento = argomento;
	}

	public void addAnswer(String answer, boolean correct) {
		Answer nuovo = new Answer(answer, correct);
		this.risposte.add(nuovo);
	}
	
    @Override
    public String toString() {
        return this.testo+" ("+this.argomento.getKeyword()+")";
    }

	public long numAnswers() {
	    return this.risposte.size();
	}

	public Set<String> getCorrectAnswers() {
		Set<String> finale = new HashSet<>();
		for(Answer a: this.risposte){
			if(a.getGiusto()==true) finale.add(a.getRisposta());
		}
		return finale;
	}

	public Set<String> getIncorrectAnswers() {
        Set<String> finale = new HashSet<>();
		for(Answer a: this.risposte){
			if(a.getGiusto()==false) finale.add(a.getRisposta());
		}
		return finale;
	}

	public Set<Answer> getAnswers(){
		return this.risposte.stream().collect(Collectors.toSet());
	}
}
