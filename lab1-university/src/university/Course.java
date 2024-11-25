package university;

public class Course {
    private String title;
    private String teacher;
    private int code;
    private Student[] studenti = new Student[100];
    private int pos = 0;
    private Exam[] esami = new Exam[100];
    private int indexEsami = 0;

    public Course(String title, String teacher, int code){
        this.title = title;
        this.teacher = teacher;
        this.code = code;
    }

    public int getCode(){
        return this.code;
    }

    public String getTitle(){
        return this.title;
    }

    public String toString(){
        return this.code+","+this.title+","+this.teacher;
    }

    public void addStudent(Student student){
        this.studenti[pos] = student;
        this.pos += 1;
    }

    public String printStudents(){
        String stringa = "";
        for(int i = 0; i < this.pos; ++i){
            stringa += this.studenti[i].toString()+"\n";
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
                tmp += this.esami[i].getGrade();
            }
            media = tmp/this.indexEsami;
        }
        return media;
    }
}
