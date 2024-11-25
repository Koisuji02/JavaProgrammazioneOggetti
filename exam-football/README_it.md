## Campi da Calcio a 5

Sviluppare una sistema che assista nella gestione delle prenotazioni,
di un impianto sportivo di campi da calcio a 5 (futsal).

Tutte le classi devono essere nel package `it.polito.oop.futsal`. 
La classe *facade* attraverso cui tutte le operazioni sono eseguite è `Fields`.

La classe `TestApp` nel default package contiene una sintetico test per l'applicazione..

La documentazione JDK è accessibile all'URL <http://softeng.polito.it/courses/docs/api/index.html>. 


## R1: Campi

L'impianto è composto da una serie di campi con diverse caratteristiche
che possono essere inizializzati tramite il metodo `defineFields(Features...)` che accetta una serie di descrittori di campi.

Il descrittore è un oggetto di classe `Features` che include tre attributi booleani:

- `indoor`: il campo è coperto;
- `heating`: il campo ha un impianto di riscaldamento, per default non c'è;
- `ac`: il campo ha un impianto di aria condizionata, per default non è presente;

Riscaldamento e aria condizionata possono essere presenti solo per un campo coperto. Se questa condizione non è verificata viene lanciata un'eccezione di `FutsalException`.

Ai campi viene assegnata una numerazione che parte da 1 secondo l'ordine con cui sono stati definiti nella chiamata del metodo.

È possibile conoscere il numero totale di campi con il metodo `countFields()` ed il numero totale di campi indoor con il metodo  `countIndoor()`.

L'impianto sportivo ha un orario di apertura ed uno di chiusura che sono accessibili tramite i metodi getter e setter: `getOpeningTime()`, `setOpeningTime()`, `getClosingTime()`, `setClosingTime()`.
Gli orari sono rappresentati da stringhe col formato `"hh:mm"`, dove `hh` rappresenta le ore e `mm` i minuti.
Si assuma che l'orario di chiusura sia entro la mezzanotte.


## R2: Soci

I soci dell'impianto vengono registrati tramite il metodo `newAssociate(String, String, String)` che riceve come parametri nome, cognome e numero di telefono. Il metodo restituisce un codice univoco (`int`).

È possibile conoscere, dato il codice univoco, nome, cognome e telefono tramite i metodi `getFirst()`, `getLast()` e `getPhone()`.
A fronte di un codice inesistente il metodo lancia un'eccezione di `FutsalException`.

Inoltre il metodo `countAssociates()` restituisce il numero di soci registrati.


## R3: Prenotazione Campi.

I vari campi possono essere prenotati per blocchi di 1 ora, a partire dall'ora di aperture fino all'ora di chiusura.

La prenotazione di un campo avviene tramite il metodo `bookField()` che riceve come parametri il numero del campo, il codice univoco del campo e l'ora di inizio.

L'ora di inizio della prenotazione deve essere allineata all'orario di apertura (ovvero la differenza in minuti tra la prenotazione e l'apertura deve essere un multiplo di 60).

> Ad esempio se l'orario di aperture sono le `"14:30"`, è possibile prenotare solo alle mezze ore, ovvero `"18:30"` è valido come orario mentre `"20:15"` non lo è.

Se il numero del campo, il codice del socio o l'orario non sono validi viene lanciata un'eccezione di `FutsalException`.

Dato un campo ed un orario è possibile sapere se è prenotato tramite il metodo `isBooked()` che riceve come parametri il numero del campo e l'orario e restituisce un valore booleano.


## R4: Disponibilità e occupazione campi

È possibile sapere qual'è il livello di occupazione di un campo (ovvero il numero di prenotazioni) tramite il metodo `getOccupation()` che accetta il numero di un campo e restituisce il numero di prenotazioni fatte.


Prima di effettuare una prenotazione è possibile verificare la disponibilità dei campi che possiedono certe caratteristiche.

Il metodo `findOptions()` accetta come parametri un orario ed un oggetto `Features` e restituisce una serie di opzioni corrispondenti ai campi che hanno le caratteristiche richieste e sono liberi all'ora specificata.
La lista è ordinata per occupazione decrescente e poi per numero di campo crescente.

Il parametro delle caratteristiche richieste è da interpretare come segue:

- `true` significa che la caratteristica è richiesta,
- `false` significa che è indifferente che la caratteristica sia presente o meno.

Il metodo restituisce una lista di alternative rappresentate da oggetti che implementano l'interfaccia `FieldOption`.

L'interfaccia offre i metodi getter `getField()`, e `getOccupation()` che restituiscono il numero del campo e l'occupazione.



## R5: Statistiche

Sono definiti i seguenti metodi per raccogliere statistiche:

- `countServedAssociates()` restituisce il numero totale di associati
    che hanno effettuato almeno una prenotazione.

- `occupation()` restituisce il livello di occupazione dell'impianto
  in termini di percentuale. Viene calcolato come il rapporto tra il
  numero di prenotazioni in tutti i campi e il numero di blocchi da
  un'ora disponibili tra l'orario di apertura e quello di chiusura.
    
- `fieldTurnover()` restituisce una mappa che riporta come chiavi
  i numeri dei campi e come valori il numero di prenotazioni presso
  tali campi.