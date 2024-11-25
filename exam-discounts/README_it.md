Discounts
=========

Il programma simula la gestione degli sconti in un grande magazzino.
Tutte le classi si trovano nel package `it.polito.oop.discounts`. La
classe principale è `Discounts`. La classe `TestApp` nel package
example contiene un esempio. Le eccezioni sono lanciate mediante la
classe `DiscountsException`.

La documentazione JDK è accessibile all'URL
<https://oop.polito.it/api/index.html>.

R1: Carte
---------

Per usufruire degli sconti occorre avere una carta. Una carta si ottiene
con il metodo `issueCard()` che riceve il nome del cliente e fornisce
un numero intero progressivo a partire da 1 compreso.

Per sapere il nome dell'intestatario della carta si usa il metodo
`cardHolder()` che restituisce il nome del cliente dato il numero
della carta.

Per ottenere il numero di carte fornite si usa il metodo `nOfCards()`.

R2: Prodotti
------------

Per registrare un tipo di prodotto nel grande magazzino si usa il metodo
`addProduct(String, String, double)`. Il primo parametro è il codice
della categoria di appartenenza, il secondo è il codice del prodotto e
il terzo è il prezzo. Il metodo lancia un'eccezione se il codice del
prodotto è già definito.

Il metodo `getPrice(String)` fornisce il prezzo dato il codice del
prodotto; se il codice del prodotto è indefinito lancia un'eccezione.

Il metodo `getAveragePrice(String)` fornisce il prezzo medio
arrotondato dei tipi di prodotti relativi alla categoria passata come
parametro; se la categoria è indefinita (ovvero non contiene prodotti)
lancia un'eccezione.

R3: Sconti
----------

Gli sconti si possono definire per le categorie e valgono per tutti i
tipi di prodotti relativi. Inizialmente le categorie hanno uno sconto
pari a 0%.

Il metodo `setDiscount(String, int)` assegna una percentuale di sconto
(valore intero tra 0 e 50 estremi inclusi) alla categoria indicata dal
primo parametro; lancia un'eccezione se la categoria è indefinita o il
valore non è compreso tra 0 e 50 estremi inclusi. Il metodo
`getDiscount(String)` fornisce lo sconto associato alla categoria
data.

R4: Acquisti
------------

Gli acquisti si possono effettuare con o senza carta. Nel primo caso si
usa il metodo `addPurchase(int, String..)` che ha come primo parametro
il codice di una carta. Nel secondo caso si usa il metodo
`addPurchase(String..)`. Il parametro `String...` indica gli item
comprati. Un item è costituito dal codice del prodotto e dal n. di unità
separati dal carattere `":"` senza spazi intermedi. I metodi
forniscono un codice intero progressivo (a partire da 1 compreso)
dell'acquisto.

I metodi suddetti scrivono nell'acquisto l'importo complessivo che è
pari alla somma degli importi degli item; l'importo di un item è dato
dal prezzo del tipo di prodotto moltiplicato per il numero di unità. Nel
caso di acquisto con carta, il metodo applica uno sconto ai prezzi dei
tipi dei prodotti: lo sconto si legge nelle categorie corrispondenti. I
metodi lanciano un'eccezione se includono un codice di prodotto
indefinito.

Il metodo `getAmount(int)` dato il codice dell'acquisto, fornisce
l'importo dell'acquisto dopo l'eventuale sconto.

Il metodo `getDiscount(int)` fornisce lo sconto applicato
all'acquisto dato il codice dell'acquisto.

Il metodo `getNofUnits(int)` fornisce il numero di unità presenti
nell'acquisto.

R5: Statistiche
---------------

Il metodo `productIdsPerNofUnits()` fornisce la lista dei codici
prodotto per numero di unità risultanti dagli acquisti fatti. Sono
considerati i prodotti che compaiono almeno in un acquisto. Le chiavi
(numero di unità) sono ordinate per valori crescenti e le liste dei
codici prodotto sono ordinate alfabeticamente.

Il metodo `totalPurchasePerCard()` fornisce l'importo totale degli
acquisti per carta. Sono considerate soltanto le carte con le quali sono
stati fatti degli acquisti. Le chiavi sono ordinate per valori
crescenti.

Il metodo `totalPurchaseWithoutCard()` fornisce l'importo totale degli acquisti fatti senza carte.

Il metodo `totalDiscountPerCard()` fornisce lo sconto totale per carta. Sono considerate soltanto le carte che hanno
beneficiato di sconti. Le chiavi sono ordinate per valori crescenti.
