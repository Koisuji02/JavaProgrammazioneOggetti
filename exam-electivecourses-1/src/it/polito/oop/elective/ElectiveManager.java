package it.polito.oop.elective;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Manages elective courses enrollment.
 * 
 *
 */
public class ElectiveManager {

    public class Teaching{
        private String name; // univoco
        private int posti;
        private int postiRimanenti;
        public Teaching(String name, int posti){
            this.name = name;
            this.posti = posti;
            this.postiRimanenti = posti;
        }
        public String getName(){
            return this.name;
        }
        public int getPosti(){
            return this.posti;
        }
        public int getPostiRimanenti(){
            return this.postiRimanenti;
        }
        public void reducePostiRimanenti(){
            this.postiRimanenti -= 1;
        }
    }

    public class Student{
        private String id;
        private double media;
        public Student(String id, double media){
            this.id = id;
            this.media = media;
        }
        public String getId(){
            return this.id;
        }
        public double getMedia(){
            return this.media;
        }
        public void setMedia(double media){
            this.media = media;
        }
    }

    public class Enroll{
        private Student s;
        private List<Teaching> insegnamentiScelti = new ArrayList<>();
        private boolean assegnato;
        public Enroll(Student s, List<Teaching> insegnamentiScelti){
            this.s = s;
            this.insegnamentiScelti = insegnamentiScelti;
            this.assegnato = false;
        }
        public Student getStudent(){
            return this.s;
        }
        public List<Teaching> getInsegnamenti(){
            return this.insegnamentiScelti;
        }
        public boolean getAssegnato(){
            return this.assegnato;
        }
        public void setAssegnato(){
            this.assegnato = true;
        }
    }

    public class Classe{
        private Teaching t;
        private List<Student> studentiCorso = new ArrayList<>();
        public Classe(Teaching t, List<Student> studenti){
            this.t = t;
            this.studentiCorso = studenti;
        }
        public Teaching getCorso(){
            return this.t;
        }
        public List<Student> getStudenti(){
            return this.studentiCorso;
        }
    }


    public Collection<Teaching> insegnamenti = new ArrayList<>();
    public Collection<Student> studenti = new ArrayList<>();
    public Collection<Enroll> richieste = new ArrayList<>();
    public Collection<Classe> classi = new ArrayList<>();
    public Collection<Notifier> notifiers = new ArrayList<>();
    public Collection<Student> studentiNonAssegnati = new ArrayList<>();

    /**
     * Define a new course offer.
     * A course is characterized by a name and a number of available positions.
     * 
     * @param name : the label for the request type
     * @param availablePositions : the number of available positions
     */
    public void addCourse(String name, int availablePositions) {
        Teaching nuovo = new Teaching(name, availablePositions);
        this.insegnamenti.add(nuovo);
    }
    
    /**
     * Returns a list of all defined courses
     * @return
     */
    public SortedSet<String> getCourses(){
        SortedSet<String> finale = new TreeSet<>();
        finale = this.insegnamenti.stream().map(entry->entry.getName()).sorted((s1,s2)->s1.compareTo(s2)).collect(TreeSet::new, (set,s)->set.add(s),TreeSet::addAll);
        return finale;
    }
    
    /**
     * Adds a new student info.
     * 
     * @param id : the id of the student
     * @param gradeAverage : the grade average
     */
    public void loadStudent(String id, double gradeAverage){
        if(!this.studenti.stream().map(entry->entry.getId()).collect(Collectors.toList()).contains(id)){
            Student nuovo = new Student(id, gradeAverage);
            this.studenti.add(nuovo);
        } else{
            for(Student s: this.studenti){
                if(s.getId().equals(id)) s.setMedia(gradeAverage);
            }
        }
    }

    /**
     * Lists all the students.
     * 
     * @return : list of students ids.
     */
    public Collection<String> getStudents(){
        return this.studenti.stream().map(entry->entry.getId()).collect(Collectors.toList());
    }
    
    /**
     * Lists all the students with grade average in the interval.
     * 
     * @param inf : lower bound of the interval (inclusive)
     * @param sup : upper bound of the interval (inclusive)
     * @return : list of students ids.
     */
    public Collection<String> getStudents(double inf, double sup){
        Collection<String> finale = new ArrayList<>();
        for(Student s: this.studenti){
            if(s.getMedia()>=inf && s.getMedia()<=sup) finale.add(s.getId());
        }
        return finale;
    }

    //R2
    /**
     * Adds a new enrollment request of a student for a set of courses.
     * <p>
     * The request accepts a list of course names listed in order of priority.
     * The first in the list is the preferred one, i.e. the student's first choice.
     * 
     * @param id : the id of the student
     * @param selectedCourses : a list of of requested courses, in order of decreasing priority
     * 
     * @return : number of courses the user expressed a preference for
     * 
     * @throws ElectiveException : if the number of selected course is not in [1,3] or the id has not been defined.
     */
    public int requestEnroll(String id, List<String> courses)  throws ElectiveException {
        if(!this.studenti.stream().map(entry->entry.getId()).collect(Collectors.toList()).contains(id)){
            throw new ElectiveException();
        }
        if(courses.size()<1 || courses.size()>3){
            throw new ElectiveException();
        }
        for(String s: courses){
            if(!this.insegnamenti.stream().map(entry->entry.getName()).collect(Collectors.toList()).contains(s)){
                throw new ElectiveException();
            }
        }

        Student tmp = null;
        for(Student s: this.studenti){
            if(s.getId().equals(id)) tmp = s;
        }
        List<Teaching> iTmp = new ArrayList<>();
        for(String s: courses){
            for(Teaching t: this.insegnamenti){
                if(t.getName().equals(s)) iTmp.add(t);
            }
        }
        Enroll nuovo = new Enroll(tmp, iTmp);
        this.richieste.add(nuovo);
        for(Notifier n: this.notifiers){
            n.requestReceived(id);
        }
        return iTmp.size();
    }
    
    /**
     * Returns the number of students that selected each course.
     * <p>
     * Since each course can be selected as 1st, 2nd, or 3rd choice,
     * the method reports three numbers corresponding to the
     * number of students that selected the course as i-th choice. 
     * <p>
     * In case of a course with no requests at all
     * the method reports three zeros.
     * <p>
     * 
     * @return the map of list of number of requests per course
     */
    public Map<String,List<Long>> numberRequests(){
        Map<String,List<Long>> finale = new HashMap<>();
        for(Teaching t: this.insegnamenti){
            long counter1 = 0;
            long counter2 = 0;
            long counter3 = 0;
            for(Enroll e: this.richieste){
                int indice = 0;
                for(String s: e.getInsegnamenti().stream().map(entry->entry.getName()).collect(Collectors.toList())){
                    if(t.getName().equals(s)){
                        if(indice == 0) counter1 += 1;
                        if(indice == 1) counter2 += 1;
                        if(indice == 2) counter3 += 1;
                    }
                    indice += 1;
                }
            }
            List<Long> tmp = new ArrayList<>();
            tmp.add(counter1);tmp.add(counter2);tmp.add(counter3);
            finale.put(t.getName(),tmp);
        }
        return finale;
    }
    
    //R3
    /**
     * Make the definitive class assignments based on the grade averages and preferences.
     * <p>
     * Student with higher grade averages are assigned to first option courses while they fit
     * otherwise they are assigned to second and then third option courses.
     * <p>
     *  
     * @return the number of students that could not be assigned to one of the selected courses.
     */
    public long makeClasses() {
        this.richieste = this.richieste.stream().sorted((s1,s2)->Double.compare(s2.getStudent().getMedia(), s1.getStudent().getMedia())).collect(Collectors.toList());
        for(Teaching t: this.insegnamenti){
            List<Student> tmp = new ArrayList<>();
            Classe nuovo = new Classe(t, tmp);
            this.classi.add(nuovo);
        }
        for(Enroll e: this.richieste){
            boolean flag = false;
            for(Teaching t: e.getInsegnamenti()){
                for(Teaching k: this.insegnamenti){
                    if(t.getName().equals(k.getName()) && k.getPostiRimanenti()>0){
                        for(Classe c: this.classi){
                            if(c.getCorso().getName().equals(t.getName())){
                                c.getStudenti().add(e.getStudent());
                                k.reducePostiRimanenti();
                                flag = true;
                                for(Notifier n: this.notifiers){
                                    n.assignedToCourse(e.getStudent().getId(), c.getCorso().getName());
                                }
                                break;
                            }
                        } if(flag == true) break;
                    }
                } if(flag==true) break;
            }
            if(flag == false){
                this.studentiNonAssegnati.add(e.getStudent());
            }
        }
        return this.studentiNonAssegnati.size();
    }
    
    
    /**
     * Returns the students assigned to each course.
     * 
     * @return the map course name vs. student id list.
     */
    public Map<String,List<String>> getAssignments(){
        for(Classe c: this.classi){
            c.getStudenti().sort((s1,s2)->Double.compare(s2.getMedia(),s1.getMedia()));
        }
        Map<String,List<String>> finale = new HashMap<>();
        for(Classe c: this.classi){
            String corso = c.getCorso().getName();
            List<String> studenti = c.getStudenti().stream().map(entry->entry.getId()).collect(Collectors.toList());
            finale.put(corso,studenti);
        }
        return finale;
    }
    
    
    /**
     * Adds a new notification listener for the announcements
     * issues by this course manager.
     * 
     * @param listener : the new notification listener
     */
    public void addNotifier(Notifier listener) {
        this.notifiers.add(listener);
    }
    
    /**
     * Computes the success rate w.r.t. to first 
     * (second, third) choice.
     * 
     * @param choice : the number of choice to consider.
     * @return the success rate (number between 0.0 and 1.0)
     */
    public double successRate(int choice){
        int totale = 0;
        for(Classe c: this.classi){
            for(String s: c.getStudenti().stream().map(entry->entry.getId()).collect(Collectors.toList())){
                for(Enroll e: this.richieste){
                    int indice = 0;
                    for(Teaching t: e.getInsegnamenti()){
                        if(e.getStudent().getId().equals(s)){
                            if(t.getName().equals(c.getCorso().getName())){
                                if(indice == 0 && choice == 1){
                                    totale+=1;
                                }
                                else if(indice == 1 && choice == 2){
                                    totale+=1;
                                }
                                else if(indice == 2 && choice == 3){
                                    totale+=1;
                                }
                                indice+=1;
                            }
                        }
                    }
                }
            }
        }
        return (double)totale/(double) this.studenti.size();
    }

    
    /**
     * Returns the students not assigned to any course.
     * 
     * @return the student id list.
     */
    public List<String> getNotAssigned(){
        return this.studentiNonAssegnati.stream().map(entry->entry.getId()).collect(Collectors.toList());
    }
    
    
}
