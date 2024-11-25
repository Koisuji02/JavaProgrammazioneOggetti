
# Gestione del Pronto Soccorso

Scrivere un programma in grado di gestire l'accoglienza dei pazienti al pronto soccorso, le visite degli specialisti, le dimissioni e i ricoveri. Le classi si trovano nel pacchetto it.polito.emergency. La classe principale è EmergencyApp, mentre nel pacchetto test si trova la classe TestExample che riporta degli esempi di utilizzo dei metodi principali ed esempi dei controlli richiesti. 
Implementa solo i controlli richiesti. 
Le eccezioni nei metodi descritti di seguito sono del tipo EmergencyException.

La documentazione JDK si trova sul [server locale](https://oop.polito.it/api/).

La versione Inglese di questi requisiti è disponibile in [README.md](README.md).

### R1. Professionisti e dipartimenti

Il pronto soccorso svolge la funzione di prima accoglienza dei pazienti, di ingaggio degli specialisti e invio dei pazienti presso i reparti dell'ospedale. 

Il metodo `addProfessional()` consente al pronto soccorso di aggiungere un medico specialista, in servizio presso il pronto soccorso. Per semplicità si assume che il professionista, durante il periodo, sia sempre disponibile (24 ore su 24). Il periodo viene indicato con il formato `"yyyy-MM-dd to yyyy-MM-dd"` dove `yyyy` rappresenta l'anno, `MM` il mese, `dd` il giorno.
Ogni professionista è caratterizzato da una specializzazione, da una matricola (ID), da un nome e un cognome.

Il metodo `getProfessionalById()` consente al pronto soccorso di recuperare l'anagrafica del professionista. Se l'ID non è in anagrafica, allora verrà generata una `EmergencyException`.

Il metodo `getProfessionals()` consente al pronto soccorso di recuperare le matricole degli specialisti con una determinata specializzazione. Se non è presente nessuno specialista, allora il programma dovrà lanciare `EmergencyException`.	

Il metodo `getProfessionalsInService()` consente al pronto soccorso di ottenere le matricole degli specialisti in servizio per il periodo richiesto data una determinata specializzazione. Il periodo viene specificato sempre con il formato `"yyyy-MM-dd to yyyy-MM-dd"`. Se non è presente nessuno specialista con la specializzazione richiesta nel il periodo richiesto, allora il programma dovrà lanciare `EmergencyException`. 

Il metodo `addDepartment()` consente al pronto soccorso di aggiungere i dipartimenti ospedalieri collegati al pronto soccorso caratterizzati da un nome e un numero massimo di pazienti e dalla lista pazienti in cura. Si assume che il nome del dipartimento sia univoco.

Il metodo `getDepartments()` consente al pronto soccorso di avere l'elenco completo dei nomi dei dipartimenti collegati con la struttura. Se nessun dipartimento è collegato al pronto soccorso, allora il programma dovrà lanciare `EmergencyException`.

Il metodo `readFromFileProfessionals()` riceve come parametro l'oggetto Reader riferito al file contenente l'elenco dei professionisti e delle loro disponibilità, salva i professionisti e restituisce il numero di professionisti letti da file. 
Il metodo `readFromFileDepartments()` riceve come parametro l'oggetto Reader riferito al file contenente l'elenco dei dipartimenti collegati al pronto soccorso, salva i dipartimenti collegati al pronto soccorso e restituisce il numero dei dipartimenti letti da file.
In entrambi i metodi, se il Reader è nullo, allora il programma lancia l'eccezione IOException.
Al fondo del readme, vengono forniti due file, professionals.csv e departments.csv, da utilizzare come esempio di documenti letti dai Reader.

### R2. Accoglienza Paziente

Il metodo `addPatient()` accredita un nuovo paziente utilizzando nome, cognome, data di nascita, il codice fiscale, la motivazione e la data in cui è stato accolto nel pronto soccorso. La data segue la formattazione `yyyy-MM-dd` dove `yyyy` rappresenta l'anno, `MM` il mese, `dd` il giorno. Il metodo restituisce l'oggetto paziente. Se il paziente esiste, allora restituire l'oggetto già presente nell'anagrafica. Il paziente assumerà lo stato di ammesso (`ADMITTED`). Hint: Si utilizzi l'enum `PatientStatus` come riferimento per gli stati assunti dal paziente.

Il metodo `getPatient()` accetta come parametro il codice fiscale o il cognome e restituisce una lista contenente il paziente o i pazienti che corrispondono alla richiesta. Nel caso di ricerca per cognome, se ci sono più pazienti con lo stesso cognome, allora il metodo restituirà tutti questi pazienti nella lista. Se nessun paziente è stato trovato, allora il programma restituirà una lista vuota.

Il metodo `getPatientsByDate()` restituisce il codice fiscale dei pazienti che hanno fatto l'accettazione nel giorno riportato come parametro, ordinati per cognome e nome. Se nessun paziente è presente, il programma restituirà una collezione vuota.

### R3. Assegnazione del Paziente allo Specialista e Registrazione Esito

Il metodo `assignPatientToProfessional()` riceve come parametri il codice fiscale del paziente e la specializzazione richiesta da parte del professionista. Il metodo selezionerà un professionista disponibile al momento dell'ammissione del paziente. Se più specialisti sono disponibili, si sceglierà il primo in ordine di ID. Se il codice fiscale non è presente tra i pazienti accolti, allora il programma dovrà lanciare `EmergencyException`. Se non c'è nessun professionista con la specializzazione richiesta o se nessun professionista con tale specializzazione è disponibile nella data di ammissione del paziente, allora il programma dovrà lanciare una `EmergencyException`.
In caso positivo, il metodo restituisce la matricola dello specialista.

Il metodo `saveReport()` riceve come parametri la matricola dello specialista, il codice fiscale del paziente, la data della visita, la descrizione del referto e restituisce il report.
Se la matricola dello specialista non fosse trovata, allora il programma dovrà lanciare l'eccezione `EmergencyException`

### R4. Dimissione del Paziente o Assegnazione ad un Reparto

Il metodo `dischargeOrHospitalize()` gestisce  l'assegnazione del paziente ad un reparto. Se un posto è disponibile in reporto, lo stato del paziente viene modificato in `HOSPITALIZED`. Se il posto non è disponibile, il paziente viene dimesso, cioè il suo stato viene modificato in `DISCHARGED`, mantenendo comunque i dati del paziente in anagrafica. Se il paziente o il reparto non esistono viene lanciata una `EmergencyException`.

Il metodo `verifyPatient()` riceve come parametri il codice fiscale del paziente e verifica lo stato del paziente, se è stato dimesso (`DISCHARGED`) o se è in un dipartimento (`HOSPITALIZED`). Ritorna 0 se è dimesso, 1 se è in reparto. Se il paziente non esiste, allora il programma dovrà lanciare `EmergencyException`.

### R5. Calcolo delle Statistiche di Presenza nel Pronto Soccorso

Il `metodo getNumberOfPatients()` restituisce il numero di pazienti accolti dal pronto soccorso e ancora in gestione (i.e., pazienti con stato `ADMITTED`). 

Il metodo `getNumberOfPatientsByDate()` restituisce il numero totale di pazienti accolti, sia già gestiti che non ancora gestiti, dal pronto soccorso un determinato giorno.

Il metodo `getNumberOfPatientsHospitalizedByDepartment()` restituisce il numero di pazienti accolti dal pronto soccorso e inviati al dipartimento richiesto o genera `EmergencyException` se il nome del dipartimento non esiste. 

Il metodo `getNumberOfPatientsDischarged()` restituisce il numero di pazienti accolti dal pronto soccorso e dimessi.

Il metodo `getNumberOfPatientsAssignedToProfessionalDischarged()` riceve come parametro una specializzazione e restituisce il numero dei pazienti dimessi dopo essere stati visitati da un medico con tale specializzazione.

## File di Input di Esempio

### specialisti.csv
```
matricola,nome,cognome,specializzazione,period
1,Mario,Rossi,Cardiologia,2024-06-11 to 2024-06-24
2,Laura,Bianchi,Ortopedia,2024-06-16 to 2024-06-30
```

### reparti.csv
```
nome_reparto,num_max
Cardiologia,20
Ortopedia,15
```