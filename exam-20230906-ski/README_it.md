Ski
===

Sviluppare un'applicazione che consenta di gestire gli impianti di risalita e le piste di un comprensorio sciistico.

Tutte le classi devono essere nel package `it.polito.ski`; la classe principale è `SkiArea`.  La classe `TestExample` nel package `example` mostra esempi di utilizzo per i metodi principali ed esempi dei controlli richiesti.  È necessario implementare solo i controlli richiesti. Le eccezioni nei metodi descritti di seguito sono di tipo `InvalidLiftException`.

La [documentazione JDK](https://oop.polito.it/api/) si trova sul server locale.

La versione inglese di questi requisiti è disponibile in [README.md](README.md).


R1 - Tipo Impianti
------------------

L'interazione con il sistema avviene tramite la classe `SkiArea` che viene creata tramite un costruttore che riceve il nome del comprensorio.
Il nome può essere visto tramite il getter `getName()`.

È possibile definire un tipo di impianto tramite il metodo `liftType()` che riceve come parametri il codice, la categoria (es. skilift, seggiovia, cabinovia) e il numero di posti di ogni singola unità (capacità). In caso di tipo duplicato il metodo lancia un'eccezione

I metodi `getCategory()` e `getCapacity()` accettano un tipo e restituiscono rispettivamente la categoria e la capacità.
Se il tipo fornito non è stato definito viene lanciata un'eccezione.

È possibile ottenere la lista di tutti i tipi definiti tramite il metodo `types()` che restituisce una collezione dei nomi dei tipi definiti.


R2 - Impianti
-------------

La definizione di un nuovo impianto di risalita viene fatta tramite il metodo `createLift()` che riceve come parametri il nome e il codice del tipo.
Se il nome del tipo non è stato definito viene lanciata un'eccezione.

Il metodo `getType()` restituisce il tipo dell'impianto dato.
Il metodo `getLifts()` restituisce la collezione degli impianti ordinati per nome.


R3 - Piste
---------
Per descrivere una nuova pista si usa il metodo `createSlope()` che riceve come parametri il nome della pista, la difficoltà ("verde",
"blu", "rossa", "nera"), il nome dell'impianto da cui parte.
Se il nome dell'impianto non corrisponde a un impianto noto, viene generata un'eccezione

I metodi `getDifficulty()` e `getStartLift()` accettano il nome di una pista e restituiscono rispettivamente la difficoltà e il nome dell'impianto di partenza.

Deve essere possibile conoscere tutte le piste tramite il metodo `getSlopes()` che restituisce la collezione delle piste.

Inoltre dato un impianto deve essere possibile, tramite il metodo `getSlopesFrom()`, conoscere tutte le piste che partono da un dato impianto.


R4 - Parcheggi
--------------

Per descrivere i parcheggi si usa il metodo `createParking()` che riceve come parametri il nome del parcheggio e la capienza in posti.

Il metodo `getParkingSlots()` riceve il nome di un parcheggio e restituisce il numero di posti disponibili nel parcheggio.

È possibile indicare quali impianti partono dal parcheggio tramite il metodo `liftServedByParking()` che riceve come riceve il nome dell'impianto e del parcheggio. Tale metodo può essere invocato più volte per aggiungere più impianti.

Il metodo `servedLifts()` riceve il nome di un parcheggio e restituisce la collezione di tutti gli impianti serviti da esso.

Il metodo `isParkingProportionate()` verifica se la dimensione di un dato parcheggio è proporzionata alla portata degli impianti che partono da esso, in particolare restituisce `true` se il numero di posti del parcheggio diviso la somma dei numeri dei posti degli impianti che da esso partono è inferiore a 30.


R5 - Lettura da file
--------------------

Il metodo `readLifts()` permette di leggere da un file la descrizione dei tipi di impianto e degli impianti. Il file è organizzato per linee, ogni linea inizia con una lettera che indica se si tratta di
un tipo di impianto (T) o di un impianto (L). Il tipo di impianto è descritto da codice, categoria e numero posti. L'impianto è descritto dal nome e dal tipo impianto.

Le varie informazioni su una linea sono separate da ";" ed eventuali spazi attorno al separatore sono ignorati.

Esempio:

```
T ; S4P; seggiovia; 4
T;S;skilift;1
L;Fraiteve;S4P
L;Baby;S
```

Il metodo deve propagare eventuali eccezioni di IO e deve essere in grado d'ignorare linee errate (con numero di informazioni non corretto) proseguendo la lettura.

