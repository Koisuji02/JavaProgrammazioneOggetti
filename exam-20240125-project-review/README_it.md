Revisione di Progetti
=====================

Scrivi un programma per supportare la pianificazione delle revisioni di progetti di studenti. 
Le classi si trovano nel package `it.polito.project`; la classe principale è `ReviewServer`. La classe `TestApp` nel package `example` mostra esempi di utilizzo dei metodi principali ed esempi dei controlli richiesti. Implementa solo i controlli richiesti. Le eccezioni nei metodi descritti di seguito sono del tipo `ReviewException`.

La documentazione del JDK (https://oop.polito.it/api/) si trova sul server locale.

R1: Gruppi e Revisioni
----------------------

Il metodo `addGroups()` consente di aggiungere gruppi di studenti all'elenco dei gruppi. Accetta i gruppi in ingresso e li aggiunge all'elenco.

Il metodo `getGroups()` recupera l'elenco dei gruppi disponibili.

Il metodo `addReview()` aggiunge una nuova revisione con un gruppo specificato. Prende in ingresso il titolo, l'argomento e il gruppo della revisione e restituisce un ID univoco per la revisione. Se il gruppo specificato non esiste, viene generata un'eccezione `ReviewException`.

Il metodo `getReviews()` recupera l'elenco delle revisioni con un determinato gruppo. Prende in ingresso il gruppo e restituisce una collezione di ID delle revisioni.

Il metodo `getReviewTitle()` recupera il titolo di una revisione in base al suo ID. Prende in ingresso l'ID della revisione e restituisce il titolo corrispondente.

Il metodo `getReviewTopic()` recupera l'argomento di una revisione in base al suo ID. Prende in ingresso l'ID della revisione e restituisce l'argomento corrispondente.


R2: Orari delle revisioni
-------------------------

La classe `ReviewServer` fornisce metodi aggiuntivi per gestire i possibili orari per una revisione.

Per aggiungere un nuovo possibile orario per una revisione, si può utilizzare il metodo `addOption()`. Questo metodo richiede l'ID della revisione, la data, l'ora d'inizio e l'ora di fine come parametri. Si assicura che il nuovo orario non si sovrapponga con eventuali altri orari esistenti per la stesso revisione. Se l'ID della revisione fornito non è valido, viene generata un'eccezione `ReviewException`. Il metodo calcola la durata dell'intervallo orario in ore e restituisce questo valore.

Se serve recuperare informazioni sugli orari disponibili per una revisione specifica, si utilizza il metodo `showSlots()`. Il valore restituito è una mappa in cui ogni data è una chiave e il valore corrispondente è un elenco di orari descritti nel formato "hh:mm-hh:mm". Questo formato indica l'ora d'inizio e di fine di ogni slot orario.


R3: Preferenze
--------------

La classe `ReviewServer` fornisce un insieme di metodi per gestire le preferenze degli studenti per le revisioni.

Per dichiarare una revisione aperta per la raccolta di preferenze, si utilizza il metodo `openPoll()`, che abilita la raccolta di preferenze per la revisione con il dato ID.

Quando gli studenti desiderano registrare le proprie preferenze per un orario di una revisione, possono utilizzare il metodo `selectPreference()`. Questo metodo richiede l'email, il nome, il cognome, l'ID del revisione, la data dello slot selezionato e l'intervallo di tempo dello slot orario. Le preferenze possono essere registrate solo per revisioni che hanno un sondaggio aperto. Il metodo aggiunge la preferenza e restituisce il numero totale di preferenze registrate per quello slot. Se viene fornito un ID di revisione o uno slot non validi, viene generata un'eccezione `ReviewException`.

Infine, per recuperare l'elenco delle preferenze espresse per una revisione, si utilizza il metodo `listPreferences()`. Fornendo l'ID della revisione, il metodo restituisce una collezione di preferenze espresse per quella revisione. Ogni preferenza è rappresentata come una stringa nel formato "YYYY-MM-DDThh:mm-hh:mm=EMAIL", dove la data, l'intervallo di tempo e l'email dello studente sono separati da "T" e "=".

R4: Chiusura del sondaggio
--------------------------

Per chiudere il sondaggio associato a una revisione e determinare le opzioni preferite, si utilizza il metodo `closePoll()`. Fornendo l'ID della revisione, il metodo identifica gli orari preferiti e disabilita il sondaggio, indicando che non possono essere registrate ulteriori preferenze per quella revisione.

Il metodo restituisce una collezione di stringhe che rappresentano le opzioni preferite. Ogni opzione è formattata come "YYYY-MM-DDThh:mm-hh:mm=##", dove la data, l'intervallo di tempo e il conteggio delle preferenze (`##`) sono separati da "T" e "=".

R5: Statistiche
---------------

Ulteriori metodi forniscono informazioni utili per analizzare le preferenze delle revisioni.

Il metodo `reviewPreferences()` recupera il conteggio delle preferenze per ogni slot di una revisione. Prende in ingresso l'ID del revisione e restituisce una mappa in cui ogni chiave rappresenta una data associata alla revisione e il valore corrispondente è un elenco di orari con le rispettive preferenze. Ogni orario è descritto come una stringa nel formato "hh:mm-hh:mm=###", che indica l'intervallo di tempo e il numero di preferenze per quell'orario, ad esempio `"14:00-15:30=2"`. Questo metodo fornisce una panoramica delle preferenze espresse per ogni orario della revisione.

Il metodo `preferenceCount()` calcola il numero totale di preferenze raccolte per ogni revisione. Non richiede parametri d'ingresso e restituisce una mappa in cui ogni chiave rappresenta l'ID del revisione e il valore corrisponde al conteggio delle preferenze espresse per quella revisione.
