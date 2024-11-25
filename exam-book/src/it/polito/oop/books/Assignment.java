package it.polito.oop.books;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import it.polito.oop.books.Question.Answer;


public class Assignment {

    private String student;
    private ExerciseChapter e;

    public class RisposteDomanda{
        private Question q;
        private List<String> answers = new ArrayList<>();
        private double score;
        public RisposteDomanda(Question q, List<String> answers){
            this.q = q;
            this.answers = answers;
        }
        public Question getQuestion(){
            return this.q;
        }
        public List<String> getAnswers(){
            return this.answers;
        }
        public void setScore(double score){
            this.score = score;
        }
        public double getScore(){
            return this.score;
        }
    }

    private List<RisposteDomanda> risposteDomande = new ArrayList<>();
    
    public Assignment(String student, ExerciseChapter e){
        this.student = student;
        this.e = e;
    }

    public String getID() {
        return this.student;
    }

    public List<RisposteDomanda> getRisposteDomande(){
        return this.risposteDomande;
    }

    public ExerciseChapter getChapter() {
        return this.e;
    }

    public double addResponse(Question q,List<String> answers) {
        RisposteDomanda nuovo = new RisposteDomanda(q, answers);
        long N = q.numAnswers();
        int FP = 0;
        int FN = 0;
        List<Answer> messe = new ArrayList<>();
        for(Answer a: q.getAnswers()){
            //! DEBUG
            // System.out.println(a.getRisposta() + " " + a.getGiusto());
            for(String s: answers){
                if(a.getRisposta().equals(s)){
                    if(a.getGiusto() == false) FP += 1;
                    messe.add(a);
                }
            }
        }

        //! DEBUG
        /* for(Answer c: messe){
            System.out.println(c.getRisposta() + " " + c.getGiusto());
        } */

        for(Answer a: q.getAnswers()){
            if((!messe.contains(a)) && a.getGiusto()==true) FN += 1;
        }
        double media = ((double)N-(double)FP-(double)FN)/(double)N;
        System.out.println(media);
        nuovo.setScore(media);
        this.risposteDomande.add(nuovo);
        return media;
    }
    
    public double totalScore() {
        double totale = 0.0;
        for(RisposteDomanda r: this.risposteDomande){
            totale += r.getScore();
        }
        return totale;
    }

}
