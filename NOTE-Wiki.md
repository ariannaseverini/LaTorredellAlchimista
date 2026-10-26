# Note per la Wiki - La Torre dell'Alchimista

Package principale: `it.unicam.cs.mpgc.rpg130278`
Sotto-package `model`: logica del gioco, senza nessuna dipendenza dalla grafica.

## Spada (model)

**Responsabilità:** rappresenta l'arma del giocatore. Contiene solo il danno che infligge in combattimento. Non sa chi la crea né come viene usata.

**Scelte:**
- `danno` è `final`: una volta creata la spada non cambia. Se nel forziere si trova una spada migliore, è un oggetto nuovo che sostituisce quello vecchio.
- Il costruttore rifiuta un danno minore o uguale a 0 con `IllegalArgumentException`, perché un danno negativo curerebbe il nemico.

**Test:** creazione con danno valido, danno negativo, danno zero.

## Giocatore (model)

**Responsabilità:** tiene lo stato del giocatore (punti vita e spada) e fa rispettare le regole su di essi. Gli attributi sono privati e si modificano solo con metodi che controllano i valori, quindi nessuno può impostare punti vita non validi dall'esterno.

**Scelte:**
- I punti vita iniziali sono 100, definiti nella costante `MAX_PUNTI_VITA`, per non avere un numero senza significato dentro il costruttore.
- La spada è obbligatoria: il costruttore rifiuta `null` con `IllegalArgumentException`, così l'errore si vede subito e non durante un combattimento. Il giocatore riceve la spada da chi lo crea e non decide da solo quanto danno fa quella iniziale.
- `subisciDanno(int)` rifiuta danni minori o uguali a 0 e porta i punti vita a 0 al minimo, mai sotto zero.
- `isMorto()` restituisce `true` quando i punti vita sono 0. Per ricominciare una partita si crea un nuovo `Giocatore`.
- `equipaggiaSpada(Spada)` sostituisce l'arma corrente con quella passata come parametro. Rifiuta valori `null` con `IllegalArgumentException`.

**Test:** punti vita iniziali, spada nulla, `getSpada`, danno normale, danno negativo, danno zero, danno uguale ai punti vita, danno maggiore dei punti vita, giocatore vivo, giocatore morto, equipaggiamento nuova spada valida, equipaggiamento spada nulla.

## Mostro (model)

**Responsabilità:** rappresenta un nemico affrontabile nei combattimenti. Mantiene il nome, i punti vita attuali e il danno inflitto ad ogni attacco.

**Scelte:**
- Il costruttore rifiuta parametri non validi (nome `null` o vuoto, punti vita <= 0, danno <= 0) lanciando `IllegalArgumentException`.
- `subisciDanno(int)` scala i punti vita fermandosi al minimo a 0.
- `isMorto()` restituisce `true` quando i punti vita raggiungono lo 0.

**Test:** creazione con parametri validi e non validi, gestione del danno subito, stato di morte.

## RisultatoEvento (model)

**Responsabilità:** rappresenta l'esito immutabile dell'interazione con un qualsiasi evento della torre.

**Scelte:**
- Implementato come `record` Java contenente `successo` (`boolean`) e `messaggio` (`String`).
- Il costruttore compatto controlla la validità del messaggio e rifiuta valori `null`, vuoti o composti solo da spazi con `IllegalArgumentException`.

**Test:** creazione con valori validi, messaggio `null`, messaggio vuoto o di soli spazi.

## Evento (model)

**Responsabilità:** interfaccia che definisce il contratto comune per tutti i tipi di eventi che si possono trovare dietro una porta.

**Scelte:**
- Espone il solo metodo `RisultatoEvento esegui(Giocatore giocatore)`.
- Permette di sfruttare il polimorfismo, scollegando la logica di gestione della torre dal tipo specifico di stanza.

## EventoCombattimento (model)

**Responsabilità:** gestisce la logica di uno scontro a turni tra il giocatore e un mostro.

**Scelte:**
- Il costruttore riceve il `Mostro` da combattere e rifiuta valori `null`.
- Il metodo `esegui(Giocatore)` rifiuta un giocatore `null`.
- Turni: il giocatore attacca per primo. Se il mostro sopravvive, contrattacca. Il ciclo continua finché uno dei due non muore.
- Restituisce `RisultatoEvento` con `successo = false` se il giocatore muore, `successo = true` se il mostro viene sconfitto.

**Test:** vittoria del giocatore con calcolo vita residua, sconfitta del giocatore, validazione input `null`.

## EventoTrappola (model)

**Responsabilità:** infligge un danno diretto e immediato al giocatore appena entra nella stanza.

**Scelte:**
- Il costruttore richiede il valore del `danno` e rifiuta valori <= 0 con `IllegalArgumentException`.
- `esegui(Giocatore)` infligge il danno al giocatore e restituisce `RisultatoEvento` negativo se la trappola è mortale, positivo se il giocatore sopravvive.

**Test:** danno subito e sopravvivenza, trappola mortale, danno <= 0, giocatore `null`.

## EventoForziere (model)

**Responsabilità:** offre al giocatore una nuova spada trovata nel forziere.

**Scelte:**
- Il costruttore richiede la `nuovaSpada` e rifiuta valori `null`.
- Logica di upgrade: equipaggia la spada tramite `giocatore.equipaggiaSpada(...)` solo se il danno della nuova spada è strettamente maggiore di quello dell'arma attuale (`nuovaSpada.getDanno() > attuale`). In caso contrario mantiene l'arma equipaggiata.
- Restituisce sempre `RisultatoEvento` con `successo = true` descrivendo l'azione avvenuta.

**Test:** equipaggiamento nuova spada se migliore, mantenimento spada attuale se la nuova è peggiore o pari, parametri `null`.

## Porta (model)

**Responsabilità:** rappresenta una scelta presente all'interno di un piano della torre. Incapsula un `Evento` e ne delega l'esecuzione al momento dell'apertura.

**Scelte:**
Il costruttore richiede l'oggetto `Evento` e rifiuta parametri `null` con `IllegalArgumentException`.

- `apri(Giocatore)` riceve il giocatore che interagisce con la porta e delega l'esecuzione tramite `evento.esegui(giocatore)`. Rifiuta valori null con `IllegalArgumentException`.

- `getEvento()` consente di ispezionare l'evento contenuto.

**Test:** creazione con evento valido e `null`, apertura della porta con giocatore valido e `null`, verifica dell'esito restituito dalla delegazione.

## PianoDellaTorre (model)

**Responsabilità:** rappresenta un singolo livello della torre. Gestisce esattamente tre porte fisse e la generazione casuale o custom dei loro contenuti.

**Scelte:**

Dimensione fissa: definita tramite la costante `NUMERO_PORTE = 3`.

Delegazione tra costruttori (Constructor Chaining): il costruttore standard `PianoDellaTorre(int)` genera casualmente 3 porte bilanciando la difficoltà in base al numero del piano, richiamando il costruttore custom a due parametri `this(numeroPiano, generaPorteCasuali(...))`.

Il costruttore a due parametri `PianoDellaTorre(int, Porta[])` permette di passare un array di porte predefinite per consentire test unitari deterministici (TDD).

Validazione stringente: rifiuta `numeroPiano <= 0`, array `porte` nullo, lunghezza dell'array diversa da 3 (`porte.length != NUMERO_PORTE`) e singoli elementi dell'array nulli con `IllegalArgumentException`.

Protezione dello stato (Copia Difensiva): sia il costruttore che il metodo `getPorte()` utilizzano `.clone()` sull'array di porte per evitare mutazioni esterne indesiderate.

- `getPorta(int)` restituisce una singola porta controllando che l'indice rientri tra `0` e `NUMERO_PORTE - 1`.

**Test:** creazione piano standard a 3 porte, creazione piano custom per TDD, numero piano <= 0, array porte `null`, array di lunghezza errata (< 3 o > 3), array con elementi `null`, accesso a porta con indice non valido, verifica dell'immutabilità tramite copia difensiva `.clone()`.

## GestorePartita (controller)

**Resposabilità:** coordina il flusso di gioco, gestisce l'interazione con le porte di ciascun piano, fa avanzare il giocatore ai piani successivi in caso di successo e controlla lo stato di Game Over.

**Scelte:**

- Separazione delle responsabilità (MVC): collocato nel sotto-package `controller` per disaccoppiare la logica di avanzamento sia dal modello del dominio (`model`) sia dall'interfaccia utente (CLI/GUI).

- Validazione input: il costruttore richiede l'oggetto Giocatore e rifiuta valori null con IllegalArgumentException.

-Inizializzazione: all'avvio della partita crea automaticamente il primo piano della torre (`PianoDellaTorre` al piano 1) e imposta lo stato di `gameOver` a `false`.