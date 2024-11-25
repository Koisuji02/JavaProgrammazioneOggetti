package university;

public class Student {
    private String name;
    private String surname;
    private int id;
    private Course[] corsi = new Course[25];
    private int indexCorsi = 0;
    private Exam[] esami = new Exam[25];
    private int indexEsami = 0;
    private float punteggio;

    public Student(String name, String surname, int id){
        this.name = name;
        this.surname = surname;
        this.id = id;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public String getSurname(){
        return this.surname;
    }

    public String toString(){
        return this.id+" "+this.name+" "+this.surname;
    }

    public void addCourse(Course corso){
        this.corsi[this.indexCorsi] = corso;
        this.indexCorsi += 1;
    }

    public String printCourses(){
        String stringa = "";
        for(int i = 0; i < this.indexCorsi; ++i){
            stringa += this.corsi[i].toString()+"\n";
        }
        return stringa;
    }

    public void addExam(Exam esame){
        this.esami[this.indexEsami] = esame;
        this.indexEsami += 1;
    }

    public float checkAvg(){
        float media = -1;
        float tmp = 0;
        if(this.indexEsami != 0){
            for(int i = 0; i < this.indexEsami; ++i){
                tmp += esami[i].getGrade();
            }
            media = tmp/indexEsami;
        }
        return media;
    }

    public float bonus(){
        return ((float)indexEsami)/((float)indexCorsi)*10;
    }

    public void addPoint(float val){
        this.punteggio = val;
    }

    public float getPoint(){
        return this.punteggio;
    }
}
