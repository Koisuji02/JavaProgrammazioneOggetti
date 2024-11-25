package it.polito.emergency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class EmergencyApp {

    public enum PatientStatus {
        ADMITTED,
        DISCHARGED,
        HOSPITALIZED
    }

    public class Department{
        private String name;
        private int maxPatients;
        private int postiRimasti;
        private Collection<Patient> pazientiDipartimento = new ArrayList<>();
        public Department(String name, int maxPatients){
            this.name = name;
            this.maxPatients = maxPatients;
            this.postiRimasti = maxPatients;
        }
        public String getName(){
            return this.name;
        }
        public int getMaxPatients(){
            return this.maxPatients;
        }
        public int getPostiRimasti(){
            return this.postiRimasti;
        }
        public void reducePostiRimasti(){
            this.postiRimasti-=1;
        }
        public Collection<Patient> getPazientiDipartimento(){
            return this.pazientiDipartimento;
        }
        public void addPazienteDipartimento(Patient p){
            this.pazientiDipartimento.add(p);
        }
    }

    public class PatientProfessional{
        private Patient paziente;
        private Professional professionista;
        public PatientProfessional(Patient paziente, Professional professionista){
            this.paziente = paziente;
            this.professionista = professionista;
        }
        public Patient getPatient(){
            return this.paziente;
        }
        public Professional getProfessional(){
            return this.professionista;
        }
    }
    

    public Collection<Professional> professionisti = new ArrayList<>();
    public Collection<Department> dipartimenti = new ArrayList<>();
    public Collection<Patient> pazienti = new ArrayList<>();
    public Collection<PatientProfessional> pazientiProfessionisti = new ArrayList<>();
    public Collection<Report> reports = new ArrayList<>();
    public String nReports = "1";

    /**
     * Add a professional working in the emergency room
     * 
     * @param id
     * @param name
     * @param surname
     * @param specialization
     * @param period
     * @param workingHours
     */
    public void addProfessional(String id, String name, String surname, String specialization, String period) {
        Professional nuovo = new Professional(id, name, surname, specialization, period);
        this.professionisti.add(nuovo);
    }

    /**
     * Retrieves a professional utilizing the ID.
     *
     * @param id The id of the professional.
     * @return A Professional.
     * @throws EmergencyException If no professional is found.
     */    
    public Professional getProfessionalById(String id) throws EmergencyException {
        if(!this.professionisti.stream().map(entry->entry.getId()).collect(Collectors.toList()).contains(id)){
            throw new EmergencyException("No professional is found!");
        }
        for(Professional p: this.professionisti){
            if(p.getId().equals(id)) return p;
        }
        return null;
    }

    /**
     * Retrieves the list of professional IDs by their specialization.
     *
     * @param specialization The specialization to search for among the professionals.
     * @return A list of professional IDs who match the given specialization.
     * @throws EmergencyException If no professionals are found with the specified specialization.
     */    
    public List<String> getProfessionals(String specialization) throws EmergencyException {
        List<String> finale = new ArrayList<>();
        for(Professional p: this.professionisti){
            if(p.getSpecialization().equals(specialization)) finale.add(p.getId());
        }
        if(finale.isEmpty()){
            throw new EmergencyException("No professional is found!");
        }
        return finale;
    }

    /**
     * Retrieves the list of professional IDs who are specialized and available during a given period.
     *
     * @param specialization The specialization to search for among the professionals.
     * @param period The period during which the professional should be available, formatted as "YYYY-MM-DD to YYYY-MM-DD".
     * @return A list of professional IDs who match the given specialization and are available during the period.
     * @throws EmergencyException If no professionals are found with the specified specialization and period.
     */    
    public List<String> getProfessionalsInService(String specialization, String period) throws EmergencyException {
        List<String> finale = new ArrayList<>();
        String[] parti = period.split(" ");
        LocalDate inizio = LocalDate.parse(parti[0]);
        LocalDate fine = LocalDate.parse(parti[2]);
        for(Professional p: this.professionisti){
            if(p.getSpecialization().equals(specialization) && ((p.getStartDay().isAfter(inizio) && p.getStartDay().isBefore(fine)) || (p.getEndDay().isAfter(inizio) && p.getEndDay().isBefore(fine)) || (p.getStartDay().isBefore(inizio) && p.getEndDay().isAfter(fine)) || (p.getPeriod().equals(period)) || (p.getStartDay().isEqual(inizio) && (p.getEndDay().isAfter(fine) || p.getEndDay().isEqual(fine))) || (p.getEndDay().isEqual(inizio) && (p.getStartDay().isBefore(inizio) || p.getStartDay().isEqual(inizio))))) finale.add(p.getId());
        }
        if(finale.isEmpty()){
            throw new EmergencyException("No professional is found!");
        }
        return finale;
    }

    /**
     * Adds a new department to the emergency system if it does not already exist.
     *
     * @param name The name of the department.
     * @param maxPatients The maximum number of patients that the department can handle.
     * @throws EmergencyException If the department already exists.
     */
    public void addDepartment(String name, int maxPatients){
        Department nuovo = new Department(name, maxPatients);
        this.dipartimenti.add(nuovo);
    }

    /**
     * Retrieves a list of all department names in the emergency system.
     *
     * @return A list containing the names of all registered departments.
     * @throws EmergencyException If no departments are found.
     */
    public List<String> getDepartments() throws EmergencyException {
        List<String> finale = new ArrayList<>();
        for(Department d: this.dipartimenti){
            finale.add(d.getName());
        }
        if(finale.isEmpty()){
            throw new EmergencyException("No department is found!");
        } return finale;
    }

    /**
     * Reads professional data from a CSV file and stores it in the application.
     * Each line of the CSV should contain a professional's ID, name, surname, specialization, period of availability, and working hours.
     * The expected format of each line is: matricola, nome, cognome, specializzazione, period, orari_lavoro
     * 
     * @param reader The reader used to read the CSV file. Must not be null.
     * @return The number of professionals successfully read and stored from the file.
     * @throws IOException If there is an error reading from the file or if the reader is null.
     */
    public int readFromFileProfessionals(Reader reader) throws IOException {
        if(reader==null){
            throw new IOException();
        }
        try(BufferedReader br = new BufferedReader(reader)){
            String line = null;
            boolean flag = false;
            int counter = 0;
            while((line = br.readLine()) != null){
                if(flag == true){
                    String[] parti = line.split(",");
                    this.addProfessional(parti[0], parti[1], parti[2], parti[3], parti[4]);
                    counter += 1;
                }
                flag = true; // per skippare la prima riga di intestazione
            }
            return counter;
        } catch(IOException e){
            throw new IOException();
        }
    }

    /**
     * Reads department data from a CSV file and stores it in the application.
     * Each line of the CSV should contain a department's name and the maximum number of patients it can accommodate.
     * The expected format of each line is: nome_reparto, num_max
     * 
     * @param reader The reader used to read the CSV file. Must not be null.
     * @return The number of departments successfully read and stored from the file.
     * @throws IOException If there is an error reading from the file or if the reader is null.
     */    
    public int readFromFileDepartments(Reader reader) throws IOException {
        if(reader==null){
            throw new IOException();
        }
        try(BufferedReader br = new BufferedReader(reader)){
            String line = null;
            boolean flag = false;
            int counter = 0;
            while((line = br.readLine()) != null){
                if(flag == true){
                    String[] parti = line.split(",");
                    parti[1] = parti[1].trim();
                    this.addDepartment(parti[0],Integer.parseInt(parti[1]));
                    counter += 1;
                }
                flag = true; // per skippare la prima riga di intestazione
            }
            return counter;
        } catch(IOException e){
            throw new IOException();
        }
    }

    //R2
    /**
     * Registers a new patient in the emergency system if they do not exist.
     * 
     * @param fiscalCode The fiscal code of the patient, used as a unique identifier.
     * @param name The first name of the patient.
     * @param surname The surname of the patient.
     * @param dateOfBirth The birth date of the patient.
     * @param reason The reason for the patient's visit.
     * @param dateTimeAccepted The date and time the patient was accepted into the emergency system.
     */
    public Patient addPatient(String fiscalCode, String name, String surname, String dateOfBirth, String reason, String dateTimeAccepted) {
        Patient nuovo = new Patient(fiscalCode, name, surname, dateOfBirth, reason, dateTimeAccepted);
        this.pazienti.add(nuovo);
        return nuovo;
    }

    /**
     * Retrieves a patient or patients based on a fiscal code or surname.
     *
     * @param identifier Either the fiscal code or the surname of the patient(s).
     * @return A single patient if a fiscal code is provided, or a list of patients if a surname is provided.
     *         Returns an empty collection if no match is found.
     */    
    public List<Patient> getPatient(String identifier) throws EmergencyException {
        List<Patient> selezionati =  new ArrayList<>();
        boolean flag = false;
        for(Patient p: this.pazienti){
            if(p.getFiscalCode().equals(identifier)){
                selezionati.add(p);
                flag = true;
                break;
            }
        }
        if(flag == true){
            return selezionati;
        } else{
            for(Patient p: this.pazienti){
                if(p.getSurname().equals(identifier)){
                    selezionati.add(p);
                }
            }
        }
        return selezionati;
    }

    /**
     * Retrieves the fiscal codes of patients accepted on a specific date, 
     * sorted by acceptance time in descending order.
     *
     * @param date The date of acceptance to filter the patients by, expected in the format "yyyy-MM-dd".
     * @return A list of patient fiscal codes who were accepted on the given date, sorted from the most recent.
     *         Returns an empty list if no patients were accepted on that date.
     */
    public List<String> getPatientsByDate(String date) {
        List<Patient> finale = new ArrayList<>();
        for(Patient p: this.pazienti){
            if(p.getDateTimeAccepted().equals(date)) finale.add(p);
        }
        return finale.stream().sorted((s1,s2)->s1.toString().compareTo(s2.toString())).map(entry->entry.getFiscalCode()).collect(Collectors.toList());
    }

    //R3
    /**
     * Assigns a patient to a professional based on the required specialization and checks availability during the request period.
     *
     * @param fiscalCode The fiscal code of the patient.
     * @param specialization The required specialization of the professional.
     * @return The ID of the assigned professional.
     * @throws EmergencyException If the patient does not exist, if no professionals with the required specialization are found, or if none are available during the period of the request.
     */
    public String assignPatientToProfessional(String fiscalCode, String specialization) throws EmergencyException {
        if(!this.pazienti.stream().map(entry->entry.getFiscalCode()).collect(Collectors.toList()).contains(fiscalCode)){
            throw new EmergencyException("No patient is found!");
        }
        Patient pTmp = null;
        for(Patient pa: this.pazienti){
            if(pa.getFiscalCode().equals(fiscalCode)) pTmp = pa;
        }
        if(!this.professionisti.stream().map(entry->entry.getSpecialization()).collect(Collectors.toList()).contains(specialization)){
            throw new EmergencyException("No professional is found with specialization!");
        }
        Collection<Professional> selezionati = new ArrayList<>();
        for(Professional p: this.professionisti){
            if(p.getSpecialization().equals(specialization) && (LocalDate.parse(pTmp.getDateTimeAccepted()).isAfter(p.getStartDay()) || LocalDate.parse(pTmp.getDateTimeAccepted()).isEqual(p.getStartDay())) && (LocalDate.parse(pTmp.getDateTimeAccepted()).isBefore(p.getEndDay()) || LocalDate.parse(pTmp.getDateTimeAccepted()).isEqual(p.getEndDay()))) selezionati.add(p);
        }
        if(selezionati.isEmpty()){
            throw new EmergencyException("No professional is found!");
        }
        List<String> finale = new ArrayList<>();
        if(selezionati.size()>1){
            finale = selezionati.stream().sorted((s1,s2)->s1.getId().compareTo(s2.getId())).map(entry->entry.getId()).collect(Collectors.toList());
        } else{
            finale = selezionati.stream().map(entry->entry.getId()).collect(Collectors.toList());
        }
        for(String s: finale){
            Professional prTmp = null;
            for(Professional pr: this.professionisti) if(pr.getId().equals(s)) prTmp = pr;
            PatientProfessional nuovo = new PatientProfessional(pTmp, prTmp); this.pazientiProfessionisti.add(nuovo);
            return s;
        }
        return null;
    }

    public Report saveReport(String professionalId, String fiscalCode, String date, String description) throws EmergencyException {
        if(!this.professionisti.stream().map(entry->entry.getId()).collect(Collectors.toList()).contains(professionalId)){
            throw new EmergencyException("No professional is found!");
        }
        Patient paTmp = null;
        for(Patient pa: this.pazienti){
            if(pa.getFiscalCode().equals(fiscalCode)) paTmp = pa;
        }
        Professional prTmp = null;
        for(Professional pr: this.professionisti){
            if(pr.getId().equals(professionalId)) prTmp = pr;
        }
        Report nuovo = new Report(prTmp, paTmp, date, description, this.nReports);
        int id = Integer.parseInt(this.nReports);
        id+=1;
        this.nReports = Integer.toString(id);
        this.reports.add(nuovo);
        return nuovo;
    }

    //R4
    /**
     * Either discharges a patient or hospitalizes them depending on the availability of space in the requested department.
     * 
     * @param fiscalCode The fiscal code of the patient to be discharged or hospitalized.
     * @param departmentName The name of the department to which the patient might be admitted.
     * @throws EmergencyException If the patient does not exist or if the department does not exist.
     */
    public void dischargeOrHospitalize(String fiscalCode, String departmentName) throws EmergencyException {
        if(!this.pazienti.stream().map(entry->entry.getFiscalCode()).collect(Collectors.toList()).contains(fiscalCode)){
            throw new EmergencyException("No patient is found!");
        }
        if(!this.dipartimenti.stream().map(entry->entry.getName()).collect(Collectors.toList()).contains(departmentName)){
            throw new EmergencyException("No department is found!");
        }
        Patient pTmp = null;
        for(Patient p: this.pazienti){
            if(p.getFiscalCode().equals(fiscalCode)) pTmp = p;
        }
        Department dTmp = null;
        for(Department d: this.dipartimenti){
            if(d.getName().equals(departmentName)) dTmp = d;
        }
        if(dTmp.getPostiRimasti()>0){
            dTmp.addPazienteDipartimento(pTmp);
            dTmp.reducePostiRimasti();
            pTmp.setStatus(PatientStatus.HOSPITALIZED);
        } else{
            pTmp.setStatus(PatientStatus.DISCHARGED);
        }

    }

    /**
     * Checks if a patient is currently hospitalized in any department.
     *
     * @param fiscalCode The fiscal code of the patient to verify.
     * @return 0 if the patient is currently hospitalized, -1 if not hospitalized or discharged.
     * @throws EmergencyException If no patient is found with the given fiscal code.
     */
    public int verifyPatient(String fiscalCode) throws EmergencyException{
        if(!this.pazienti.stream().map(entry->entry.getFiscalCode()).collect(Collectors.toList()).contains(fiscalCode)){
            throw new EmergencyException("No patient is found!");
        }
        for(Patient p: this.pazienti){
            if(p.getFiscalCode().equals(fiscalCode)){
                if(p.getStatus().equals(PatientStatus.ADMITTED) || p.getStatus().equals(PatientStatus.DISCHARGED)) return 0;
                else if(p.getStatus().equals(PatientStatus.HOSPITALIZED)) return 1;
            }
        }
        return -1;
    }

    //R5
    /**
     * Returns the number of patients currently being managed in the emergency room.
     *
     * @return The total number of patients in the system.
     */    
    public int getNumberOfPatients() {
        int counter = 0;
        for(Patient p: this.pazienti){
            if(p.getStatus().equals(PatientStatus.ADMITTED)) counter+=1;
        }
        return counter;
    }

    /**
     * Returns the number of patients admitted on a specified date.
     *
     * @param dateString The date of interest provided as a String (format "yyyy-MM-dd").
     * @return The count of patients admitted on that date.
     */
    public int getNumberOfPatientsByDate(String date) {
        int counter = 0;
        for(Patient p: this.pazienti){
            if(p.getDateTimeAccepted().equals(date)) counter+=1;
        }
        return counter;
    }

    public int getNumberOfPatientsHospitalizedByDepartment(String departmentName) throws EmergencyException {
        if(!this.dipartimenti.stream().map(entry->entry.getName()).collect(Collectors.toList()).contains(departmentName)){
            throw new EmergencyException("No department is found!");
        }
        int counter = 0;
        for(Department d: this.dipartimenti){
            for(Patient p: d.getPazientiDipartimento()){
                counter+=1;
            }
        }
        return counter;
    }

    /**
     * Returns the number of patients who have been discharged from the emergency system.
     *
     * @return The count of discharged patients.
     */
    public int getNumberOfPatientsDischarged() {
        int counter = 0;
        for(Patient p: this.pazienti){
            if(p.getStatus().equals(PatientStatus.DISCHARGED)) counter+=1;
        }
        return counter;
    }

    /**
     * Returns the number of discharged patients who were treated by professionals of a specific specialization.
     *
     * @param specialization The specialization of the professionals to filter by.
     * @return The count of discharged patients treated by professionals of the given specialization.
     */
    public int getNumberOfPatientsAssignedToProfessionalDischarged(String specialization) {
        int counter = 0;
        for(PatientProfessional p: this.pazientiProfessionisti){
            if(p.getProfessional().getSpecialization().equals(specialization) && p.getPatient().getStatus().equals(PatientStatus.DISCHARGED)) counter += 1;
        }
        return counter;
    }
}
