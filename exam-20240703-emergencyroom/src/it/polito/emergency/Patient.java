package it.polito.emergency;

import it.polito.emergency.EmergencyApp.*;

public class Patient {

    private String fiscalCode;
    private String name;
    private String surname;
    private String dateBirth;
    private String reason;
    private String dateTimeAccepted;
    private PatientStatus status;

    public Patient(String fiscalCode, String name, String surname, String dateOfBirth, String reason, String dateTimeAccepted){
        this.fiscalCode = fiscalCode;
        this.name = name;
        this.surname = surname;
        this.dateBirth = dateOfBirth;
        this.reason = reason;
        this.dateTimeAccepted = dateTimeAccepted;
        this.status = PatientStatus.ADMITTED;
    }

    public String getFiscalCode() {
        return this.fiscalCode;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getDateOfBirth() {
        return this.dateBirth;
    }

    public String getReason() {
        return this.reason;
    }

    public String getDateTimeAccepted() {
        return this.dateTimeAccepted;
    }

    public PatientStatus getStatus() {
        return this.status;
    }
    @Override
    public String toString(){
        return this.surname+this.name;
    }
    public void setStatus(PatientStatus status){
        this.status = status;
    }
}
