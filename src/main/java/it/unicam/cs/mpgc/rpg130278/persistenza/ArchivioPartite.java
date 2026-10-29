package it.unicam.cs.mpgc.rpg130278.persistenza;

import java.util.Optional;


 //archivio che conserva l'ultimo salvataggio della partita.
public interface ArchivioPartite {

    //Memorizza il salvataggio, sostituendo quello precedente.
    void salva(SalvataggioPartita salvataggio);

    /*
    Optional. È una scatola che ha due stati: contiene un valore, oppure è vuota.
    Il metodo carica() non può sempre restituire un salvataggio: la prima volta che giochi il file non esiste
     */
    Optional<SalvataggioPartita> carica();


    void elimina();
}