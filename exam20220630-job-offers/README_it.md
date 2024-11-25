Job Offers
==========

(La versione inglese è disponibile nel file [README.md](README.md)).


Il programma simula la gestione di offerte di lavoro da parte di
un\'azienda. Tutte le classi si trovano nel pacakge **jobOffers**. La
classe principale è **JobOffers**. La classe **TestApp** nel pacakge
*examples* e contiene i principali casi di test ma non tutti. Le
eccezioni sono lanciate tramite la classe **JOException**; occorre
eseguire solamente i controlli esplicitamente richiesti e non tutti
quelli possibili. Quando un metodo lancia un\'eccezione, non viene fatta
alcuna modifica nei dati della classe.\
Suggerimento: dopo aver implementato un metodo eseguite la *TestApp* per
verificare il risultato.

La documentazione JDK è disponibile all\'URL
<https://oop.polito.it/api/>.

R1: Capacità e posti
--------------------

Il metodo **int addSkills (String \... skills)** inserisce alcune
capacità, come ad esempio java, python, ecc. La capacità duplicate sono
ignorate. Il metodo restituisce il numero di capacità totali inserite
finora.

Il metodo **int addPosition (String role, String \... skillLevels)**
inserisce un posto di lavoro, come ad esempio *juniorProgrammer*, con le
capacità richieste e il livello richiesto di conoscenza (si vedano gli
esempi in *TestApp*). Una capacità è seguita da un *\":\"* e dal livello
di conoscenza, compreso tra 4 e 8, inclusi. Il metodo lancia
un\'eccezione se: il posto è già stato inserito, una o più delle
capacità non sono state definite oppure se il livello non è tra 4 e 8.
Il risultato è la media dei livelli.

R2: Candidati e candidature
---------------------------

Il metodo **int addCandidate (String name, String \... skills)**
inserisce il nome di un candidato con la sua lista di capacità. Il meodo
lancia un\'eccezione se il candidato è già stato aggiunto o se una
capacità non è stata definita. Il risultato è il numero di capacità.

Il metodo **addApplications (String name, String \... positions)**
permette ad un candidato di candidarsi per uno o più posti di lavoro. Il
metodo lancia un\'eccesione se: il candidato non è stato definito, non
tutti i posti di lavoro sono stati definiti, il candidato non ha le
capacità richieste per i posti di lavoro. Il metodo restituisce una
lista di candidature; una candidatura è una stringa che contiene il nome
del candidato seguito da *\":\"* e dal nome di un posto di lavoro. Le
stringhe sono ordinate per candidato e posto.

Il metodo **getCandidatesForPositions()** restituisce una mappa le cui
chiavi sono i nomi dei posti di lavoro in ordine alfabetico; i valori
sono le liste ordinate dei candidati che si sono candidati al posto. I
posti di lavoro se non hanno candidati non devono comparire.

R3: Consulenti e punteggi
-------------------------

Il metodo **int addConsultant (String name, String \... skills)**
inserisce il nome di un consulente con le relative capacità. Il metodo
lancia un\'eccezione se il consulente è già stato definito o se una
capacità non è stata definita. Il risultato è il numero di capacità.

Il metodo **Integer addRatings (String consultant, String candidate,
String\... skillRatings)** permette a un consulente di assegnare un
punteggio a ciascuna della capacità indicate da un candidato. Un esempio
di punteggio è il seguente: *\[\"java:8\", \"databases:6\"\]*. Ogni
stringa è formata dalla capacità seguita da due-punti e il punteggio. Il
punteggio è tra 4 e 10 inclusi. Il metodo lancia un\'eccezione se il
consulente o il candidato non sono stati definite, se le capacità del
consulente non includono tutte quelle dichiarate dal candidato, o se ci
sono dei punteggi fuori dai limiti. Il metodo restituisce la media dei
punteggi.

R4: Scarto candidature e selezione delle ammissibili
----------------------------------------------------

Il metodo **discardApplications()** scarta le candidature dei candidati
non ammissibili. Un candidato è considerato non ammissibile se una o più
delle sue capacità hanno un punteggio inferiore al livello richiesto per
il posto di lavoro. I punteggi sono quelli precedentemente inseriti
tramite il metodo *addRatings()*. Il metodo restituisce la lista delle
candidature scartate, le candidature sono espresse da stringhe con il
nome del candidato seguito da *\":\"* ed il nome del posto. La lista è
ordinata alfabeticamente per candidati e posizioni. La lista può essere
vuota.

Il metodo **getEligibleCandidates(String position)** seleziona le
candidature ammissibili per un posto. I candidati devono essersi
candidati alla posizione, le loro capacità devono includere quelle del
posto, e inoltre i punteggi delle capacità (forniti dai consulenti) non
devono essere inferiori ai livelli definiti col metodo *addPosition*. Il
risultato è una lista di candidati ammissibili in ordine alfabetico, la
lista può essere vuota.
