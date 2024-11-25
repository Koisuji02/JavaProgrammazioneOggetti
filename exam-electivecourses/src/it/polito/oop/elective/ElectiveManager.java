package it.polito.oop.elective;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Manages elective courses enrollment.
 * 
 *
 */
public class ElectiveManager {

    public class Teaching{
        private String name;
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
        public void reducePosti(){
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
        private String id;
        private Student s;
        private Collection<String> corsi = new ArrayList<>();
        private int nScelte;
        public Enroll(String id, List<String> corsi, int nScelte, Student s){
            this.id =  id;
            this.corsi = corsi;
            this.nScelte = nScelte;
            this.s = s;
        }
        public String getStudent(){
            return this.id;
        }
        public Student student(){
            return this.s;
        }
        public Collection<String> getCorsi(){
            return this.corsi;
        }
        public int getNScelte(){
            return this.nScelte;
        }
        public String getPrimaScelta(){
            for(String s: this.corsi){
                return s;
            }
            return null;
        }
        public String getSecondaScelta(){
            int counter = 0;
            for(String s: this.corsi){
                if(counter == 1) return s;
                counter += 1;
            }
            return null;
        }
        public String getTerzaScelta(){
            int counter = 0;
            for(String s: this.corsi){
                if(counter == 2) return s;
                counter += 1;
            }
            return null;
        }
    }

    public Collection<Teaching> insegnamenti = new ArrayList<>();
    public Collection<Student> studenti = new ArrayList<>();
    public Collection<Enroll> scelteStudenti = new ArrayList<>();
    public Map<String,List<String>> classi = new TreeMap<>();
    public List<Notifier> listener = new ArrayList<>();;

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
        SortedSet<String> finale = new TreeSet<>(this.insegnamenti.stream().map(entry->entry.getName()).sorted((s1,s2)->s1.compareTo(s2)).collect(Collectors.toSet()));
        return finale;
    }
    
    /**
     * Adds a new student info.
     * 
     * @param id : the id of the student
     * @param gradeAverage : the grade average
     */
    public void loadStudent(String id, double gradeAverage){
        if(!this.studenti.stream().map(entry->entry.getId()).collect(Collectors.toList()).contains(id)) {
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
        if(courses.size()<1 || courses.size()>3) throw new ElectiveException();
        if(!this.studenti.stream().map(entry->entry.getId()).collect(Collectors.toList()).contains(id)) throw new ElectiveException();
        int counter = 0;
        for(String s: courses){
            for(String k: this.insegnamenti.stream().map(entry->entry.getName()).collect(Collectors.toList())){
                if(s.equals(k)) counter += 1;
            }
        }
        if(counter != courses.size()) throw new ElectiveException();

        Student tmp = null;
        for(Student s: this.studenti){
            if(s.getId().equals(id)) tmp = s;
        }
        Enroll nuovo = new Enroll(id, courses, courses.size(), tmp);
        this.scelteStudenti.add(nuovo);

        //R4
        for(Notifier n: this.listener){
            n.requestReceived(id);
        }
        
        return courses.size();
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
        for(String s: this.insegnamenti.stream().map(entry->entry.getName()).collect(Collectors.toList())){
            long counter1 = 0;
            long counter2 = 0;
            long counter3 = 0;
            for(Enroll e: this.scelteStudenti){
                int index = 0;
                for(String k: e.getCorsi()){
                    if(s.equals(k)){
                        if(index == 0) counter1 += 1;
                        if(index == 1) counter2 += 1;
                        if(index == 2) counter3 += 1;
                    }
                    index += 1;
                }
            }
            List<Long> tmp = new ArrayList<>();
            tmp.add(counter1);tmp.add(counter2);tmp.add(counter3);
            finale.put(s, tmp);
        }
        return finale;
    }
    
    
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
        this.scelteStudenti = this.scelteStudenti.stream().sorted((s1,s2)->Double.compare(s2.student().getMedia(), s1.student().getMedia())).collect(Collectors.toList());
        long daTogliere = 0;

        //! DEBUG
        /* for(Enroll e: this.scelteStudenti){
            System.out.println(e.student().getId()+" "+e.student().getMedia());
        } */

        // metto gli insegnamenti
        for(String k: this.insegnamenti.stream().map(entry->entry.getName()).collect(Collectors.toList())){
            List<String> tmp = new ArrayList<>();
            this.classi.put(k, tmp);
        }
        //
        for(Enroll e: this.scelteStudenti){
            boolean flag = false;
            for(String s: e.getCorsi()){
                for(Teaching t: this.insegnamenti){
                    if(s.equals(t.getName()) && t.getPostiRimanenti() > 0){
                        this.classi.get(s).add(e.getStudent());
                        t.reducePosti();
                        daTogliere += 1;
                        flag = true;
                        break;
                    }
                }
                if(flag == true) break;
            }
        }

        //R4
        for(Map.Entry<String,List<String>> e: this.classi.entrySet()){
            for(String s: e.getValue()){
                for(Notifier n: this.listener){
                    n.assignedToCourse(s, e.getKey());
                }
            }
        }

        return this.scelteStudenti.size()-daTogliere;
    }
    
    
    /**
     * Returns the students assigned to each course.
     * 
     * @return the map course name vs. student id list.
     */
    public Map<String,List<String>> getAssignments(){
        return this.classi;
    }
    
    
    /**
     * Adds a new notification listener for the announcements
     * issues by this course manager.
     * 
     * @param listener : the new notification listener
     */
    public void addNotifier(Notifier listener) {
        this.listener.add(listener);
    }
    
    /**
     * Computes the success rate w.r.t. to first 
     * (second, third) choice.
     * 
     * @param choice : the number of choice to consider.
     * @return the success rate (number between 0.0 and 1.0)
     */
    public double successRate(int choice){
        if(choice<1 && choice>3) return -1; //! errore nel parametro
        int counter = 0;
        for(Map.Entry<String,List<String>> entry: this.classi.entrySet()){
            for(String s: entry.getValue()){
                for(Enroll e: this.scelteStudenti){
                    if(e.getStudent().equals(s)){
                        if(e.getCorsi().size()>0 && e.getPrimaScelta().equals(entry.getKey()) && choice == 1) counter +=1 ;
                        else if(e.getCorsi().size()>1 && e.getSecondaScelta().equals(entry.getKey()) && choice == 2) counter +=1 ;
                        else if(e.getCorsi().size()>2 && e.getTerzaScelta().equals(entry.getKey()) && choice == 3) counter +=1 ;
                    }
                }
            }
        }
        double tasso = (double) counter/this.studenti.size();
        return tasso;
    }

    
    /**
     * Returns the students not assigned to any course.
     * 
     * @return the student id list.
     */
    public List<String> getNotAssigned(){
        List<String> finale = new ArrayList<>();
        for(Student s: this.studenti){
            if(!this.classi.entrySet().stream().flatMap(entry->entry.getValue().stream()).collect(Collectors.toList()).contains(s.getId())){
                finale.add(s.getId());
            }
        }
        return finale;
    }
    
    
}
