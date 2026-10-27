package it.unicam.cs.mpgc.rpg130278.model.eventi;

import it.unicam.cs.mpgc.rpg130278.model.Giocatore;
import it.unicam.cs.mpgc.rpg130278.model.Combattimento;

import it.unicam.cs.mpgc.rpg130278.model.Mostro;

public class EventoCombattimento implements Evento {

    private final Mostro mostro;

    public EventoCombattimento(Mostro mostro) {
        if (mostro == null) {
            throw new IllegalArgumentException("Il mostro non può essere null.");
        }
        this.mostro = mostro;
    }

    public Mostro getMostro() {
        return mostro;
    }

    public Combattimento avvia(Giocatore giocatore) {
        if (giocatore == null) {
            throw new IllegalArgumentException("Il giocatore non può essere null.");
        }
        return new Combattimento(giocatore, mostro);
    }

    @Override
    public RisultatoEvento esegui(Giocatore giocatore) {
        if (giocatore == null) {
            throw new IllegalArgumentException("Il giocatore non può essere null.");
        }

        StringBuilder logCombattimento = new StringBuilder();
        logCombattimento.append("INIZIO COMBATTIMENTO contro ").append(mostro.getNome())
                .append(" (HP Mostro: ").append(mostro.getPuntiVita())
                .append(" | Danno Mostro: ").append(mostro.getDanno()).append(")\n");

        int turno = 1;
        while (!giocatore.isMorto() && !mostro.isMorto()) {
            logCombattimento.append("  [Turno ").append(turno).append("]\n");

            // 1. Il giocatore attacca il mostro
            int dannoGiocatore = giocatore.getSpada().getDanno();
            int hpMostroPrima = mostro.getPuntiVita();
            mostro.subisciDanno(dannoGiocatore);
            logCombattimento.append("  -> Attacchi ").append(mostro.getNome())
                    .append(" infliggendo ").append(dannoGiocatore).append(" danni. ")
                    .append("(HP Mostro: ").append(hpMostroPrima).append(" -> ").append(mostro.getPuntiVita()).append(")\n");

            // 2. Se il mostro è ancora vivo, contrattacca
            if (!mostro.isMorto()) {
                int dannoMostro = mostro.getDanno();
                int hpGiocatorePrima = giocatore.getPuntiVita();
                giocatore.subisciDanno(dannoMostro);
                logCombattimento.append("  <- ").append(mostro.getNome())
                        .append(" ti contrattacca infliggendo ").append(dannoMostro).append(" danni. ")
                        .append("(I tuoi HP: ").append(hpGiocatorePrima).append(" -> ").append(giocatore.getPuntiVita()).append(")\n");
            }
            turno++;
        }

        boolean vittoria = !giocatore.isMorto();
        if (vittoria) {
            logCombattimento.append("ESITO: Hai sconfitto ").append(mostro.getNome()).append("!");
        } else {
            logCombattimento.append("ESITO: Sei stato sconfitto da ").append(mostro.getNome()).append("...");
        }

        return new RisultatoEvento(vittoria, logCombattimento.toString());
    }

}