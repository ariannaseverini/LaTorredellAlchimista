package it.unicam.cs.mpgc.rpg130278.model;

public class Combattimento {
    private final Giocatore giocatore;
    private final Mostro mostro;
    private boolean attaccoPotenziato;

    public Combattimento(Giocatore giocatore, Mostro mostro) {
        if (giocatore == null) {
            throw new IllegalArgumentException("Il giocatore non può essere null.");
        }
        if (mostro == null) {
            throw new IllegalArgumentException("Il mostro non può essere null.");
        }
        this.giocatore = giocatore;
        this.mostro = mostro;
        this.attaccoPotenziato = false;
    }

    public Giocatore getGiocatore() {
        return giocatore;
    }

    public Mostro getMostro() {
        return mostro;
    }

    public boolean isAttaccoPotenziato() {
        return attaccoPotenziato;
    }

    public boolean isFinito() {
        return giocatore.isMorto() || mostro.isMorto();
    }

    public boolean isVinto() {
        return mostro.isMorto();
    }

    public String eseguiTurno(Azione azione) {
        if (azione == null) {
            throw new IllegalArgumentException("L'azione non può essere null.");
        }
        if (isFinito()) {
            throw new IllegalStateException("Lo scontro è già terminato.");
        }

        StringBuilder racconto = new StringBuilder();

        if (azione == Azione.ATTACCA) {
            racconto.append(attaccaMostro());
        } else {
            attaccoPotenziato = true;
            racconto.append("Ti metti in guardia: il prossimo attacco farà il doppio dei danni.");
        }

        if (!mostro.isMorto()) {
            racconto.append("\n").append(contrattaccoDelMostro(azione == Azione.DIFENDI));
        }
        return racconto.toString();
    }

    private String attaccaMostro() {
        int danno = giocatore.getSpada().getDanno();
        if (attaccoPotenziato) {
            danno *= 2;
            attaccoPotenziato = false;
        }
        mostro.subisciDanno(danno);
        return "Colpisci " + mostro.getNome() + " per " + danno
                + " danni (HP rimasti: " + mostro.getPuntiVita() + ").";
    }

    private String contrattaccoDelMostro(boolean giocatoreInDifesa) {
        int danno = mostro.getDanno();
        if (giocatoreInDifesa) {
            danno = Math.max(1, danno / 2);
        }
        giocatore.subisciDanno(danno);
        return mostro.getNome() + " ti colpisce per " + danno
                + " danni (i tuoi HP: " + giocatore.getPuntiVita() + ").";
    }
}