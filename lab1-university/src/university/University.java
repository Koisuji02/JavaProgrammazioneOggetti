package university;
import java.util.logging.Logger;
import java.util.Arrays;

/**
 * This class represents a university education system.
 * 
 * It manages students and courses.
 *
 */
public class University {
	// attributi
	private String name;
	private String rectorFirst;
	private String rectorLast;
	private Student[] studenti = new Student[100];
	private int index = 0;
	private Course[] corsi = new Course[50];
	private int indexCorsi = 0;

// R1
	/**
	 * Constructor
	 * @param name name of the university
	 */
	public University(String name){
		this.name = name;
		logger.info("Creating extended university object");
	}
	
	/**
	 * Getter for the name of the university
	 * 
	 * @return name of university
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Defines the rector for the university
	 * 
	 * @param first first name of the rector
	 * @param last	last name of the rector
	 */
	public void setRector(String first, String last){
		this.rectorFirst = first;
		this.rectorLast = last;
	}
	
	/**
	 * Retrieves the rector of the university with the format "First Last"
	 * 
	 * @return name of the rector
	 */
	public String getRector(){
		return this.rectorFirst+" "+this.rectorLast;
	}
	
// R2
	/**
	 * Enroll a student in the university
	 * The university assigns ID numbers 
	 * progressively from number 10000.
	 * 
	 * @param first first name of the student
	 * @param last last name of the student
	 * 
	 * @return unique ID of the newly enrolled student
	 */
	public int enroll(String first, String last){
		int id = 10000+index;
		this.studenti[index] = new Student(first, last, id);
		this.index += 1;
		logger.info("New student enrolled: " + id + ", " + first + " " + last);
		return id;
	}
	
	/**
	 * Retrieves the information for a given student.
	 * The university assigns IDs progressively starting from 10000
	 * 
	 * @param id the ID of the student
	 * 
	 * @return information about the student
	 */
	public String student(int id){
		for(int i = 0; i < this.index; ++i){
			if(this.studenti[i].getId() == id){
				return this.studenti[i].toString();
			}
		}
		return "Non trovato studente richiesto!";
	}
	
// R3
	/**
	 * Activates a new course with the given teacher
	 * Course codes are assigned progressively starting from 10.
	 * 
	 * @param title title of the course
	 * @param teacher name of the teacher
	 * 
	 * @return the unique code assigned to the course
	 */
	public int activate(String title, String teacher){
		int id = 10+indexCorsi;
		this.corsi[indexCorsi] = new Course(title, teacher, id);
		this.indexCorsi += 1;
		logger.info("New course activated: " + id + ", " + title + " " + teacher);
		return id;
	}
	
	/**
	 * Retrieve the information for a given course.
	 * 
	 * The course information is formatted as a string containing 
	 * code, title, and teacher separated by commas, 
	 * e.g., {@code "10,Object Oriented Programming,James Gosling"}.
	 * 
	 * @param code unique code of the course
	 * 
	 * @return information about the course
	 */
	public String course(int code){
		for(int i = 0; i < this.indexCorsi; ++i){
			if(this.corsi[i].getCode() == code){
				return this.corsi[i].toString();
			}
		}
		return "Non trovato corso richiesto!";
	}
	
// R4
	/**
	 * Register a student to attend a course
	 * @param studentID id of the student
	 * @param courseCode id of the course
	 */
	public void register(int studentID, int courseCode){
		for(Student student: this.studenti){
			if(student != null){
				for(Course corso: this.corsi){
					if(corso != null){
						if(student.getId() == studentID && corso.getCode() == courseCode){
							student.addCourse(corso);
							corso.addStudent(student);
						}
					}
				}
			}
		}
		logger.info("Student " + studentID + " signed up for course " + courseCode);
	}
	
	/**
	 * Retrieve a list of attendees.
	 * 
	 * The students appear one per row (rows end with `'\n'`) 
	 * and each row is formatted as describe in in method {@link #student}
	 * 
	 * @param courseCode unique id of the course
	 * @return list of attendees separated by "\n"
	 */
	public String listAttendees(int courseCode){
		String stringa = "";
		for(Course corso: this.corsi){
			if(corso != null){
				if(corso.getCode() == courseCode){
					stringa = corso.printStudents();
				}
			}
		}
		return stringa;
		
	}

	/**
	 * Retrieves the study plan for a student.
	 * 
	 * The study plan is reported as a string having
	 * one course per line (i.e. separated by '\n').
	 * The courses are formatted as describe in method {@link #course}
	 * 
	 * @param studentID id of the student
	 * 
	 * @return the list of courses the student is registered for
	 */
	public String studyPlan(int studentID){
		String stringa = "";
		for(Student student: this.studenti){
			if(student != null){
				if(student.getId() == studentID){
					stringa = student.printCourses();
				}
			}
		}
		return stringa;
	}

// R5
	/**
	 * records the grade (integer 0-30) for an exam can 
	 * 
	 * @param studentId the ID of the student
	 * @param courseID	course code 
	 * @param grade		grade ( 0-30)
	 */
	public void exam(int studentId, int courseID, int grade) {
		Exam esame;
		for(int i = 0; i < this.index; i++){
			if(this.studenti[i].getId() == studentId){
				for(int j = 0; j < this.indexCorsi; j++){
					if(this.corsi[j].getCode() == courseID){
						esame = new Exam(this.studenti[i], this.corsi[j], grade);
						this.studenti[i].addExam(esame);
						this.corsi[j].addExam(esame);
					}
				}
			}
		}
		logger.info("Student " + studentId + " took an exam in course " + courseID + " with grade " + grade);
	}

	/**
	 * Computes the average grade for a student and formats it as a string
	 * using the following format 
	 * 
	 * {@code "Student STUDENT_ID : AVG_GRADE"}. 
	 * 
	 * If the student has no exam recorded the method
	 * returns {@code "Student STUDENT_ID hasn't taken any exams"}.
	 * 
	 * @param studentId the ID of the student
	 * @return the average grade formatted as a string.
	 */
	public String studentAvg(int studentId) {
		String stringa = "";
		float media = -1;
		for(int i = 0; i < this.index; ++i){
			if(this.studenti[i].getId() == studentId){
				media = this.studenti[i].checkAvg();
			}
		}
		if(media == -1) stringa = "Student "+studentId+" hasn't taken any exams!";
		else stringa = "Student "+studentId+" : "+media;
		return stringa;
	}
	
	/**
	 * Computes the average grades of all students that took the exam for a given course.
	 * 
	 * The format is the following: 
	 * {@code "The average for the course COURSE_TITLE is: COURSE_AVG"}.
	 * 
	 * If no student took the exam for that course it returns {@code "No student has taken the exam in COURSE_TITLE"}.
	 * 
	 * @param courseId	course code 
	 * @return the course average formatted as a string
	 */
	public String courseAvg(int courseId) {
		String stringa = "";
		float media = -1;
		for(int i = 0; i < this.indexCorsi; ++i){
			if(this.corsi[i].getCode() == courseId){
				media = this.corsi[i].checkAvg();
				if(media == -1) stringa = "No student has taken the exam in "+this.corsi[i].getTitle();
				else stringa = "The average for the course "+this.corsi[i].getTitle()+" is: "+media;
			}
		}
		return stringa;
	}
	

// R6
	/**
	 * Retrieve information for the best students to award a price.
	 * 
	 * The students' score is evaluated as the average grade of the exams they've taken. 
	 * To take into account the number of exams taken and not only the grades, 
	 * a special bonus is assigned on top of the average grade: 
	 * the number of taken exams divided by the number of courses the student is enrolled to, multiplied by 10.
	 * The bonus is added to the exam average to compute the student score.
	 * 
	 * The method returns a string with the information about the three students with the highest score. 
	 * The students appear one per row (rows are terminated by a new-line character {@code '\n'}) 
	 * and each one of them is formatted as: {@code "STUDENT_FIRSTNAME STUDENT_LASTNAME : SCORE"}.
	 * 
	 * @return info on the best three students.
	 */
	public String topThreeStudents() {
		String stringa = "";
		float[] punteggi = new float[this.index];
		int k;
		if (this.index == 1) k = 1;
		else if(this.index == 2) k = 2;
		else k = 3;

		// aggiungo i punteggi agli studenti
		for(int i = 0; i < this.index; ++i) this.studenti[i].addPoint(this.studenti[i].checkAvg()+this.studenti[i].bonus());

		for(int i = 0; i < this.index; ++i){
			punteggi[i] = this.studenti[i].getPoint();
		}
		Arrays.sort(punteggi);
		for (int j = this.index-1; j > this.index-1-k; --j){
			for(int i = 0; i < this.index; ++i){
				if(this.studenti[i].getPoint() == punteggi[j])
					stringa += this.studenti[i].getName()+" "+this.studenti[i].getSurname()+" "+punteggi[j]+"\n";
			}
		}
		return stringa;
	}

// R7
    /**
     * This field points to the logger for the class that can be used
     * throughout the methods to log the activities.
     */
    public static final Logger logger = Logger.getLogger("University");

}
