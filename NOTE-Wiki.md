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

**Test:** punti vita iniziali, spada nulla, `getSpada`, danno normale, danno negativo, danno zero, danno uguale ai punti vita, danno maggiore dei punti vita, giocatore vivo, giocatore morto.

**Da fare:** metodo per sostituire la spada trovata nel forziere.