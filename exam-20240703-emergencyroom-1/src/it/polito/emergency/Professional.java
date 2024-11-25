package it.polito.emergency;

import java.time.LocalDate;
import java.time.LocalTime;

public class Professional {

    private String id;
    private String name;
    private String surname;
    private String specialization;
    private String workingHours;
    private LocalTime start;
    private LocalTime end;
    private String period;
    private LocalDate startDay;
    private LocalDate endDay;

    public Professional(String id, String name, String surname, String specialization, String period){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.specialization = specialization;
        this.workingHours = "0-24";
        this.start = LocalTime.parse("00:00");
        this.end = LocalTime.parse("23:59");
        this.period = period;
        String[] parti = period.split(" ");
        this.startDay = LocalDate.parse(parti[0]);
        this.endDay = LocalDate.parse(parti[2]);
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getSpecialization() {
        return this.specialization;
    }

    public String getPeriod() {
        return this.period;
    }

    public String getWorkingHours() {
        return this.workingHours;
    }
    public LocalTime getStartHour(){
        return this.start;
    }
    public LocalTime getEndHour(){
        return this.end;
    }
    public LocalDate getStartDay(){
        return this.startDay;
    }
    public LocalDate getEndDay(){
        return this.endDay;
    }
}
