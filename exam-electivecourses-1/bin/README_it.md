Gestione Insegnamenti a Scelta
==============================

Sviluppare un sistema che permetta di gestire le iscrizioni a
insegnamenti a scelta. Gli insegnamenti hanno con un numero di posti
limitati. Gli studenti possono scegliere diversi insegnamenti (con un
ordine di priorità). Il sistema effettua gli assegnamenti.

Tutte le classi devono trovarsi nel package `it.polito.oop.elective`.

La classe `Example` nel package `main` contiene esempi di uso del
metodi principali.

E' possibile accedere liberamente alla [Documentazione JDK](https://oop.polito.it/api).


R1: Corsi e Studenti
--------------------

La classe principale di facciata del sistema è `ElectiveManager`,
tramite essa vengono svolte tutte le operazioni.

Gli insegnamenti possono essere definiti tramite il metodo
`addCourse(String , int )` il quale riceve come parametri il nome (che è univoco) e il numero di posti disponibili.

È possibile ottenere l'elenco dei corsi definiti tramite il metodo
`getCourses()` che restituisce l'insieme dei nomi dei corsi definiti,
in ordine alfabetico.

Gli studenti possono essere registrati presso il sistema tramite
chiamate al metodo `loadStudent(String , double)` che riceve come
parametri l'id dello studente e la media dei voti. Se il metodo viene
chiamato più di una volta, per lo stesso studente, il risultato è
quello di aggiornare la media.

Il metodo `getStudents()` restituisce l'elenco degli id degli
studenti registrati. Per sapere quali studenti hanno una media compresa
in un dato intervallo (estremi inclusi) si usa il metodo
`getStudents(double, double)` che restituisce l'elenco degli id degli
studenti.


R2: Richieste di iscrizione
---------------------------

Quando uno studente vuole iscriversi agli insegnamenti a scelta può
indicare una lista di insegnamenti (da 1 a 3) in ordine di priorità. La
richiesta viene fatta tramite il metodo
`requestEnroll(String,List<String>)` che riceve come parametri
l'identificatore dello studente e l'insieme degli insegnamenti
desiderati in ordine di priorità, dal preferito a quello meno
desiderato. Il metodo restituisce il numero di insegnamenti per cui è
stata espressa una preferenza. Viene generata un'eccezione
`ElectiveException` se il numero di insegnamenti espressi non è da 1
a 3, se l'id non corrisponde ad uno studente già inserito, oppure se
gli insegnamenti non sono stati definiti.

È possibile conoscere il numero di studenti che hanno espresso una
preferenza tramite il metodo `numberRequests()` che restituisce una
mappa che associa ad ogni insegnamento il numero di studenti che
l'hanno selezionato come prima, seconda e terza scelta. La mappa ha
come chiave il nome dell'insegnamento e come valore una lista di tre
elementi che corrispondono alla 1^a, 2^a e 3^a scelta. La mappa deve
riportare anche gli insegnamenti che non sono stati scelti da nessuno
studente (in tal caso la lista conterrà tre zeri).


R3: Formazione classi
---------------------

Quando termina il periodo per esprimere le preferenze per gli
insegnamenti a scelta, l'ufficio offerta didattica avvia la procedura
di formazione delle classi. La formazione avviene tramite il metodo
`makeClasses()` che restituisce il numero di studenti non assegnati ad
alcun insegnamento.

Il principio seguito nell'assegnamento è che gli studenti con media più
elevata vengono soddisfatti per primi, fino ad esaurimento dei posti
negli insegnamenti selezionati.

Uno studente che non trova posto in nessuno degli insegnamenti viene
considerato come studente non assegnato.

È possibile conoscere gli assegnamenti tramite il metodo
`getAssignments()` che restituisce una mappa che associa ad ogni corso
l'elenco degli id degli studenti assegnati. La lista degli id è
ordinata in base alla media dei voti decrescenti.


R4: Notifica di Messaggi
------------------------

Il sistema di gestione invia delle notifiche che riguardano la procedura
di gestione degli insegnamenti a scelta.

Per inviare messaggi utilizza oggetti notificatori che implementano
l'interfaccia `Notifier`. Tali oggetti devono essere registrati
tramite il metodo `addNotifier(Notifier)`.

Tali oggetti e le relative classi sono fuori dal contesto dell'esame
(sono fornite da altri) e possono fornire, ad esempio, notifiche via
email o via sms.

Ogni volta che viene specificata una richiesta di iscrizione (tramite il
metodo `requestEnroll()`) il sistema notifica la presa in carico della
richiesta chiamando per tutti i notificatori registrati il metodo
`requestReceived(String)` che riceve come parametro l'id dello
studente.

Dopo aver composto le classi (tramite il metodo `makeClasses()`), per
ogni studente assegnato ad un corso (e per ogni notificatore) viene
chiamato il metodo `assignedToCourse(String,String)` che riceve come
parametri l'id dello studente ed il corso assegnato.

### Suggerimenti

-   L'interfaccia `Notifier` non deve essere implementata. Nel package
    `main` viene fornita un'implementazione di esempio per poter
    osservare il funzionamento.
-   Questo requisito non richiede di implementare altri metodi oltre a
    `addNotifier(Notifier)`, inoltre è necessario aggiungere delle
    funzionalità a metodi già descritti nei requisiti precedenti per
    inviare le notifiche.


R5: Statistiche
---------------

È possibile sapere quale percentuale di studenti è stata assegnata
all'insegnamento indicato come prima (seconda o terza) scelta tramite
il metodo: `successRate(int)` che riceve come parametro il numero
della scelta per cui calcolare il successo, il numero varia da 1 a 3. Il
tasso di successo viene calcolato considerando (al denominatore) tutti
gli studenti, anche quelli che non sono stati assegnati ad alcun
insegnamento.

L'elenco degli id degli studenti non assegnati ad alcun insegnamento
indicati è restituito dal metodo `getNotAssigned()`.

