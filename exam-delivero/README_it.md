Delivero
========

Il programma simula il back-end di un portale di consegna di cibo a domicilio.

Tutte le classi si trovano nel package `delivero`. La classe principale è `Delivery`. La classe `TestApp` nel package `example` contiene un test con esempi di uso (che non copre tutti i casi di test).
Le eccezioni sono lanciate mediante la classe `DeliveryException`.

È possibile accedere alla [documentazione delle API Java](https://oop.polito.it/api).

R1 Ristoranti
-------------

I ristoranti che si appoggiano al sito Delivero sono etichettati con una
categoria (es., tradizionale, fast food, etnico, cinese, ecc.). 
Il metodo `addCategory(String category)` permette di aggiungere una
categoria alla lista delle categorie gestite dal servizio. Il metodo
lancia un'eccezione se la categoria é giá presente.

Il metodo `getCategories()` restituisce la lista dei nomi delle categorie definite.

Il metodo `addRestaurant(String name, String category)` permette di
aggiungere un nuovo ristorante (il cui nome è univoco) e di assegnargli
una categoria. Il metodo lancia un'eccezione se la categoria non é
definita.

Il metodo `getRestaurantsForCategory(String category)` fornisce una
lista ordinata alfabeticamente dei nomi dei ristoranti etichettati con
una certa categoria. Restituisce una lista vuota nel caso non ci sia
nessun ristorante nella categoria oppure la categoria non esista.

R2 Piatti
---------

Il metodo `addDish(String name, String restaurantName, float price)`
aggiunge un piatto alla lista dei piatti forniti da un ristorante. Per
ogni piatto é definito un prezzo. Il metodo lancia un'eccezione se
esiste giá un piatto con quel nome presso lo stesso ristorante.

Il metodo `getDishesByPrice(float minprice, float maxprice)`
restituisce una mappa che associa al nome di ogni ristorante la lista di
nomi di piatti il cui prezzo è compreso tra `minprice` e `maxprice`
(estremi inclusi). Se un ristorante non ha piatti inclusi nella fascia
di prezzo specificata non deve comparire nella mappa.

Il metodo `getDishesForRestaurant(String restaurantName)` restituisce
la lista ordinata in ordine alfabetico dei nomi dei piatti venduti da
ristorante dato. Se il ristorante non esiste oppure non fornisce piatti
il metodo restituisce una lista vuota.

Il metodo `getDishesByCategory(String category)` restituisce la lista
di tutti i nomi dei piatti forniti da tutti i ristoranti appartenenti
alla categoria indicata. Se la categoria non è definita o non ci sono
ristoranti in quella categoria il metodo restituisce una lista vuota.

R3 Ordini
---------

Il metodo `addOrder(String dishName[], int quantity[], String customerName, String restaurantName, int deliveryTime, int deliveryDistance)` crea un ordine di consegna per un ristorante. Ogni
ordine puó contenere piú di un piatto con le rispettive quantità. Il
tempo di consegna (`deliveryTime`) è indicato con un numero intero
compreso tra 8 e 23. La distanza di consegna (`deliveryDistance`) è
espressa in chilometri. Si assuma che il numero i parametri passati al
metodo siano sempre corretti. Deve essere mantenuta l'informazione
dell'ordine di arrivo delle richieste.

Il metodo `addOrderd()` restituisce un numero progressivo d'ordine, il
primo ordine ha numero 1.

Il metodo `scheduleDelivery(int deliveryTime, int maxDistance, int maxOrders)` permette di schedulare le consegne. Il sistema delivero ha
vari tipi di fattorini, alcuni in bicicletta, altri motorizzati. Per
ottimizzare le consegne in base alle capacità del fattorino il metodo
restituisce i numeri d'ordine dei primi `maxOrders` (seguendo l'ordine
di arrivo degli ordini) schedulati per il tempo di consegna uguale a
`deliveryTime`, la cui distanza di consefgna è minore o uguale a
`maxDistance`.Una volta restituiti dal metodo gli ordini vanno marcati
come assegnati in modo che non siano più considerati nelle chiamate
successive. Il metodo ritorna una lista vuota nel caso non ci siano
ordini (non ancora assegnati) che soddisfino i requisiti.

Il metodo `getPendingOrders()` restituisce il numero di ordini che
devono ancora essere assegnati.


R4 Valutazioni
--------------

Il metodo `setRatingForRestaurant(String restaurantName, int rating)`
permette di registrare la valutazione (un numero da 0 a 5 inclusi) di un
ristorante da parte di un cliente.Le valutazioni fuori dall'intervallo
di validità sono scartate.

Il metodo `restaurantsAverageRating()` restituisce una lista ordinata
di ristoranti in ordine decrescente di valutazione media. La valutazione
media di un ristorante è calcolata come la somma delle valutazioni
diviso il numero delle valutazioni. Se il ristorante non ha ricevuto
alcuna valutazione non compare nella lista.


R5 Statistiche
--------------

Il metodo `ordersPerCategory()` restituisce una mappa che associa a
ogni categoria il numero di ordini effettuati in ristoranti di quella
categoria. Anche le categorie che non hanno ricevuto ordini devono
essere incluse nel risultato.

Il metodo `bestRestaurant` restituisce il nome del ristorante che ha
ottenuto la valutazione media più alta. Si ignori il problema di più
ristoranti a pari punteggio.
