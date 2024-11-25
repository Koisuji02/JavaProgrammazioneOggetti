package it.polito.oop.books;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class ExerciseChapter {

    private String title;
    private int numPages;
    private List<Question> domande = new ArrayList<>();

    public ExerciseChapter(String title, int numPages){
        this.title = title;
        this.numPages = numPages;
    }

    public List<Topic> getTopics() {
        return this.domande.stream().map(entry->entry.getMainTopic()).distinct().sorted((s1,s2)->s1.getKeyword().compareTo(s2.getKeyword())).collect(Collectors.toList());
	};
	

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    public int getNumPages() {
        return this.numPages;
    }
    
    public void setNumPages(int newPages) {
        this.numPages = newPages;
    }
    

	public void addQuestion(Question question) {
        this.domande.add(question);
	}	
    
    public List<Question> getQuestions(){
        return this.domande;
    }
}
