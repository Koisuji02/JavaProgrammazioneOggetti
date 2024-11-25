package university;

public class Exam {
    private Student studente;
    private Course corso;
    private int voto;

    public Exam(Student studente, Course corso, int voto){
        this.studente = studente;
        this.corso = corso;
        this.voto = voto;
    }

    public int getGrade(){
        return this.voto;
    }

}
