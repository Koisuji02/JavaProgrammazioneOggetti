package it.polito.oop.futsal;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Represents a infrastructure with a set of playgrounds, it allows teams
 * to book, use, and  leave fields.
 *
 */
public class Fields{
    
    public class Field implements FieldOption{
        private Features f;
        private int number;
        private int occupazione;
        public Field(Features f, int number){
            this.f = f;
            this.number = number;
            this.occupazione = 0;
        }
        public Features getFeature(){
            return this.f;
        }
        public int getNumber(){
            return this.number;
        }
        public int getOccupazione(){
            return this.occupazione;
        }
        public void setOccupazione(int occupazione){
            this.occupazione = occupazione;
        }
        @Override
        public int getOccupation(){
            return this.occupazione;
        }
        @Override
        public int getField(){
            return this.number;
        }
        public boolean isOK(Features required){
            boolean flag = true;
            if(required.getIndoor() == true && this.getFeature().getIndoor() == false) flag = false;
            if(required.getAc() == true && this.getFeature().getAc() == false) flag = false;
            if(required.getHeating() == true && this.getFeature().getHeating() == false) flag = false;
            return flag;
        }
    }

    public static class Features {
        private boolean indoor;
        private boolean heating;
        private boolean ac;
        public Features(boolean indoor, boolean heating, boolean ac){
            this.indoor = indoor;
            this.heating = heating;
            this.ac = ac;
        }
        public boolean getIndoor(){
            return this.indoor;
        }
        public boolean getHeating(){
            return this.heating;
        }
        public boolean getAc(){
            return this.ac;
        }
    }

    public class Prenotation{
        private Field c;
        private Associate a;
        private LocalTime start;
        private boolean occupato;
        public Prenotation(Field c, Associate a, LocalTime start){
            this.c = c;
            this.a = a;
            this.start = start;
            this.occupato = true;
        }
        public Field getField(){
            return this.c;
        }
        public Associate getAssociate(){
            return this.a;
        }
        public LocalTime getStart(){
            return this.start;
        }
        public boolean isOccupato(){
            return this.occupato;
        }
        public void setOccupato(){
            this.occupato = true;
        }
    }

    public class Associate{
        private String first;
        private String last;
        private String phone;
        private int id;
        public Associate(String first, String last, String phone, int id){
            this.first = first;
            this.last = last;
            this.phone = phone;
            this.id = id;
        }
        public String getFirst(){
            return this.first;
        }
        public String getLast(){
            return this.last;
        }
        public String getPhone(){
            return this.phone;
        }
        private int getId(){
            return this.id;
        }
    }
    
    public int numero = 1;
    public int numAssociato = 1;
    public Collection<Features> features = new ArrayList<>();
    public Collection<Field> campi = new ArrayList<>();
    public LocalTime apertura;
    public LocalTime chiusura;
    public Collection<Associate> soci = new ArrayList<>();
    public Collection<Prenotation> prenotazioni = new ArrayList<>();
	
    public void defineFields(Features... features) throws FutsalException {
        for(Features f: features){
            if((f.getAc()==true || f.getHeating()==true) && f.getIndoor()==false){
                throw new FutsalException();
            }
            Field nuovo = new Field(f, numero);
            this.campi.add(nuovo);
            numero += 1;
        }
    }
    
    public long countFields() {
       return this.campi.size();
    }

    public long countIndoor() {
        long counter = 0;
        for(Field c: this.campi){
            if(c.getFeature().getIndoor()==true) counter += 1;
        }
        return counter;
    }
    
    public String getOpeningTime() {
        return this.apertura.toString();
    }
    
    public void setOpeningTime(String time) {
        this.apertura = LocalTime.parse(time);
    }
    
    public String getClosingTime() {
        return this.chiusura.toString();
    }
    
    public void setClosingTime(String time) {
        this.chiusura = LocalTime.parse(time);
    }

    //R2
    public int newAssociate(String first, String last, String mobile) {
        Associate nuovo = new Associate(first, last, mobile, numAssociato);
        this.soci.add(nuovo);
        numAssociato+=1;
        return numAssociato-1;
    }
    
    public String getFirst(int associate) throws FutsalException {
        if(!this.soci.stream().map(entry->entry.getId()).collect(Collectors.toList()).contains(associate)){
            throw new FutsalException();
        }
        for(Associate a: this.soci){
            if(a.getId() == associate) return a.getFirst();
        }
        return null;
    }
    
    public String getLast(int associate) throws FutsalException {
        if(!this.soci.stream().map(entry->entry.getId()).collect(Collectors.toList()).contains(associate)){
            throw new FutsalException();
        }
        for(Associate a: this.soci){
            if(a.getId() == associate) return a.getLast();
        }
        return null;
    }
    
    public String getPhone(int associate) throws FutsalException {
        if(!this.soci.stream().map(entry->entry.getId()).collect(Collectors.toList()).contains(associate)){
            throw new FutsalException();
        }
        for(Associate a: this.soci){
            if(a.getId() == associate) return a.getPhone();
        }
        return null;
    }
    
    public int countAssociates() {
        return this.soci.size();
    }
    
    //R3
    public void bookField(int field, int associate, String time) throws FutsalException {
        if(!this.campi.stream().map(entry->entry.getNumber()).collect(Collectors.toList()).contains(field)){
            throw new FutsalException();
        }
        if(!this.soci.stream().map(entry->entry.getId()).collect(Collectors.toList()).contains(associate)){
            throw new FutsalException();
        }
        if(((LocalTime.parse(time).getHour()*60+LocalTime.parse(time).getMinute())-(this.apertura.getHour()*60+this.apertura.getMinute()))%60!=0){
            throw new FutsalException();
        }
        Field tmp = null;
        for(Field f: this.campi){
            if(f.getNumber() == field) tmp = f;
        }
        Associate aTmp = null;
        for(Associate a: this.soci){
            if(a.getId() == associate) aTmp = a;
        }
        Prenotation nuovo = new Prenotation(tmp, aTmp, LocalTime.parse(time));
        this.prenotazioni.add(nuovo);
    }

    public boolean isBooked(int field, String time) {
        boolean flag = false;
        for(Prenotation p: this.prenotazioni){
            if(p.getField().getNumber() == field && p.getStart().toString().equals(time)){
                flag = true;
            }
        }
        return flag;
    }
    
    // R4
    public int getOccupation(int field) {
        int counter = 0;
        for(Prenotation p: this.prenotazioni){
            if(p.getField().getNumber() == field) counter+=1;
        }
        for(Prenotation p: this.prenotazioni){
            if(p.getField().getNumber() == field) p.getField().setOccupazione(counter);
        }
        return counter;
    }
    
    public List<FieldOption> findOptions(String time, Features required){
        List<FieldOption> finale = new ArrayList<>();
        if(((LocalTime.parse(time).getHour()*60+LocalTime.parse(time).getMinute())-(this.apertura.getHour()*60+this.apertura.getMinute()))%60==0){
            for(Prenotation p: this.prenotazioni){
                if(p.getField().isOK(required) && !this.isBooked(p.getField().getNumber(), time)) finale.add(p.getField());
            }
        }
        finale.sort((s1,s2)->s1.getField()-s2.getField());
        finale.sort((s1,s2)->s2.getOccupation()-s1.getOccupation());
        return finale;
    }
    
    // R5
    public long countServedAssociates() {
        return this.prenotazioni.stream().map(entry->entry.getAssociate().getId()).distinct().collect(Collectors.counting());
    }
    
    public Map<Integer,Long> fieldTurnover() {
        return this.prenotazioni.stream().collect(Collectors.groupingBy(entry->entry.getField().getNumber(), Collectors.counting()));
    }
    
    public double occupation() {
        long counter = 0;
        LocalTime tmp = null;
        for(tmp = this.apertura; tmp.isBefore(chiusura); tmp = tmp.plusHours(1)){
            counter += 1;
        }
        long counterTot = this.campi.size()*counter;
        return (double)this.prenotazioni.size()/(double)counterTot;
    }
    
 }
