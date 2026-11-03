package it.unicam.cs.mpgc.rpg130278.view;

import it.unicam.cs.mpgc.rpg130278.controller.GestorePartita;
import it.unicam.cs.mpgc.rpg130278.controller.StatoPartita;
import it.unicam.cs.mpgc.rpg130278.model.Giocatore;
import it.unicam.cs.mpgc.rpg130278.model.Spada;
import it.unicam.cs.mpgc.rpg130278.model.eventi.RisultatoEvento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ControllerPartita {

    private static final int DANNO_SPADA_INIZIALE = 10;

    @FXML
    private Label etichettaPiano;

    @FXML
    private Label etichettaStato;

    @FXML
    private Label messaggio;

    @FXML
    private HBox rigaPorte;

    @FXML
    private VBox torre;

    private GestorePartita gestore;

    public void initialize() {
        gestore = new GestorePartita(new Giocatore(new Spada(DANNO_SPADA_INIZIALE)));
        for (int numero = GestorePartita.NUMERO_PIANI; numero >= 1; numero--) {
            Label piano = new Label("Piano " + numero);
            piano.getStyleClass().add("piano");
            torre.getChildren().add(piano);
        }
        aggiorna();
    }

    @FXML
    private void apriPorta(ActionEvent evento) {
        int indice = rigaPorte.getChildren().indexOf(evento.getSource());
        RisultatoEvento risultato = gestore.scegliPorta(indice);
        messaggio.setText(risultato.messaggio());
        aggiorna();
    }



    private void aggiorna() {
        Giocatore giocatore = gestore.getGiocatore();

        etichettaPiano.setText("Piano " + gestore.getPianoAttuale().getNumeroPiano()
                + " di " + GestorePartita.NUMERO_PIANI);
        etichettaStato.setText("Punti vita: " + giocatore.getPuntiVita()
                + " | Danno spada: " + giocatore.getSpada().getDanno());

        for (Node porta : rigaPorte.getChildren()) {
            porta.setDisable(gestore.getStato() != StatoPartita.IN_CORSO);
        }
        int pianoAttuale = gestore.getPianoAttuale().getNumeroPiano();
        for (int i = 0; i < torre.getChildren().size(); i++) {
            Node nodo = torre.getChildren().get(i);
            nodo.getStyleClass().remove("piano-attuale");
            if (GestorePartita.NUMERO_PIANI - i == pianoAttuale) {
                nodo.getStyleClass().add("piano-attuale");
            }
        }
    }
}