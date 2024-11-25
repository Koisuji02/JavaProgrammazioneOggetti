package it.polito.oop.books;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Topic {

	private String keyword;
	private List<Topic> subTopics = new ArrayList<>();
	
	public Topic(String keyword){
		this.keyword = keyword;
	}

	public String getKeyword() {
        return this.keyword;
	}
	
	@Override
	public String toString() {
	    return this.keyword;
	}

	public boolean addSubTopic(Topic topic) {
		if(this.subTopics.stream().map(entry->entry.getKeyword()).collect(Collectors.toList()).contains(topic.getKeyword())) return false;
		else{
			this.subTopics.add(topic);
			return true;
		}
	}

	/*
	 * Returns a sorted list of subtopics. Topics in the list *MAY* be modified without
	 * affecting any of the Book topic.
	 */
	public List<Topic> getSubTopics() {
        this.subTopics.sort((s1,s2)->s1.getKeyword().compareTo(s2.getKeyword()));
		return this.subTopics;
	}
}
