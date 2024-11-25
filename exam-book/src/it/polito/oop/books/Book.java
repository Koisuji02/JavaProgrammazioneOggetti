package it.polito.oop.books;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Book {

	public Collection<Topic> argomenti = new ArrayList<>();
	public Collection<Question> domande = new ArrayList<>();
	public Collection<TheoryChapter> capitoliTeoria = new ArrayList<>();
	public Collection<ExerciseChapter> capitoliEsercizi = new ArrayList<>();
	public Collection<Assignment> compiti = new ArrayList<>();

    /**
	 * Creates a new topic, if it does not exist yet, or returns a reference to the
	 * corresponding topic.
	 * 
	 * @param keyword the unique keyword of the topic
	 * @return the {@link Topic} associated to the keyword
	 * @throws BookException
	 */

	public Topic getTopic(String keyword) throws BookException {
	    if(keyword == null || keyword.isEmpty()) throw new BookException();
		if(!this.argomenti.stream().map(entry->entry.getKeyword()).collect(Collectors.toList()).contains(keyword)){
			Topic nuovo = new Topic(keyword);
			this.argomenti.add(nuovo);
			return nuovo;
		} else{
			for(Topic t: this.argomenti){
				if(t.getKeyword().equals(keyword)) return t;
			}
		}
		return null;
	}

	public Question createQuestion(String question, Topic mainTopic) {
        Question nuovo = new Question(question, mainTopic);
		this.domande.add(nuovo);
		return nuovo;
	}

	public TheoryChapter createTheoryChapter(String title, int numPages, String text) {
        TheoryChapter nuovo = new TheoryChapter(title, numPages, text);
		this.capitoliTeoria.add(nuovo);
		return nuovo;
	}

	public ExerciseChapter createExerciseChapter(String title, int numPages) {
        ExerciseChapter nuovo = new ExerciseChapter(title, numPages);
		this.capitoliEsercizi.add(nuovo);
		return nuovo;
	}

	public List<Topic> getAllTopics() {
        List<Topic> finale = new ArrayList<>();
		for(ExerciseChapter e: this.capitoliEsercizi){
			for(Topic t: e.getTopics()){
				finale.add(t);
			}
		}
		for(TheoryChapter e: this.capitoliTeoria){
			for(Topic t: e.getTopics()){
				finale.add(t);
			}
		}
		return finale.stream().sorted((s1,s2)->s1.getKeyword().compareTo(s2.getKeyword())).distinct().collect(Collectors.toList());
	}

	public boolean checkTopics() {
        for(Topic e: this.capitoliEsercizi.stream().flatMap(entry->entry.getTopics().stream()).collect(Collectors.toList())){
			if(!this.capitoliTeoria.stream().flatMap(entry->entry.getTopics().stream()).collect(Collectors.toList()).contains(e)) return false;
		}
		return true;
	}

	public Assignment newAssignment(String ID, ExerciseChapter chapter) {
        Assignment nuovo = new Assignment(ID, chapter);
		this.compiti.add(nuovo);
		return nuovo;
	}
	
    /**
     * builds a map having as key the number of answers and 
     * as values the list of questions having that number of answers.
     * @return
     */
    public Map<Long,List<Question>> questionOptions(){
        return this.domande.stream().collect(Collectors.groupingBy(Question::numAnswers));
    }
}
