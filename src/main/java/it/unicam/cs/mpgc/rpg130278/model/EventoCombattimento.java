package it.unicam.cs.mpgc.rpg130278.model;
//evento: combattimento contro un mostro
public class EventoCombattimento implements Evento{
    private final Mostro mostro;
    public EventoCombattimento(Mostro mostro){
        if(mostro == null) throw new IllegalArgumentException();
        this.mostro = mostro;
    }

    public Mostro getMostro() {
        return mostro;
    }

    @Override
    public RisultatoEvento esegui(Giocatore giocatore){
        if(giocatore == null) throw new IllegalArgumentException();
        while(!giocatore.isMorto() && !mostro.isMorto()){
            mostro.subisciDanno(giocatore.getSpada().getDanno());
            if(!mostro.isMorto()){
                giocatore.subisciDanno(mostro.getDanno());
            }
        }
        if(giocatore.isMorto()) return new RisultatoEvento(false, "Sei stato sconfitto da " + mostro.getNome());
        else return new RisultatoEvento(true, "Hai sconfitto " + mostro.getNome());
    }
}
