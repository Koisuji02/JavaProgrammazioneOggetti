package it.polito.emergency;

public class Report {

    private String id;
    private Professional pr;
    private Patient pa;
    private String date;
    private String description;
    public Report(Professional pr, Patient pa, String date, String description, String id){
        this.id = id;
        this.pr = pr;
        this.pa = pa;
        this.date = date;
        this.description = description;
    }

    public String getId() {
        return this.id;
    }

    public String getProfessionalId() {
        return this.pr.getId();
    }

    public String getFiscalCode() {
        return this.pa.getFiscalCode();
    }

    public String getDate() {
        return this.date;
    }


    public String getDescription() {
        return this.description;
    }
    public Professional getProfessional(){
        return this.pr;
    }
    public Patient getPatient(){
        return this.pa;
    }
}
