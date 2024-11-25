package it.polito.oop.books;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class TheoryChapter {

    private String title;
    private int numPages;
    private String text;
    private List<Topic> argomenti = new ArrayList<>();
    public TheoryChapter(String title, int numPages, String text){
        this.title = title;
        this.numPages = numPages;
        this.text = text;
    }

    public String getText() {
		return this.text;
	}

    public void setText(String newText) {
        this.text = newText;
    }


	public List<Topic> getTopics() { //ricorsiva sarebbe meglio
        List<Topic> finale = new ArrayList<>();
        for(Topic t: this.argomenti){
            if(!finale.stream().map(entry->entry.getKeyword()).collect(Collectors.toList()).contains(t.getKeyword())) finale.add(t);
            for(Topic s: t.getSubTopics()){
                if(!finale.stream().map(entry->entry.getKeyword()).collect(Collectors.toList()).contains(s.getKeyword())) finale.add(s);
                for(Topic k: s.getSubTopics()){
                    if(!finale.stream().map(entry->entry.getKeyword()).collect(Collectors.toList()).contains(k.getKeyword())) finale.add(k);
                    for(Topic l: k.getSubTopics()){
                        if(!finale.stream().map(entry->entry.getKeyword()).collect(Collectors.toList()).contains(l.getKeyword())) finale.add(l);
                        
                    }
                }
            }
        }
        finale.sort((s1,s2)->s1.getKeyword().compareTo(s2.getKeyword()));
        return finale;
	}

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
    
    public void addTopic(Topic topic) {
        this.argomenti.add(topic);
    }
    
}
