package it.unicam.cs.mpgc.rpg130278.persistenza;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ArchivioJsonTest {

    //@TempDir dà una cartella temporanea che JUnit cancella da sola alla fine, quindi nessun test tocca un salvataggio vero.
    @TempDir
    Path cartella;

    private Path file;
    private ArchivioJson archivio;

    @BeforeEach
    void setUp() {
        file = cartella.resolve("salvataggio.json");
        archivio = new ArchivioJson(file);
    }

    @Test
    void salvareCreaIlFile() {
        archivio.salva(new SalvataggioPartita(4, 63, 17));

        assertTrue(Files.exists(file));
    }

    @Test
    void salvareUnSalvataggioNulloLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> archivio.salva(null));
    }

    @Test
    void costruttoreConFileNulloLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new ArchivioJson(null));
    }
    @Test
    void caricareUnSalvataggioScrittoRestituisceLoStessoSalvataggio() {
        SalvataggioPartita salvataggio = new SalvataggioPartita(4, 63, 17);
        archivio.salva(salvataggio);

        assertEquals(Optional.of(salvataggio), archivio.carica());
    }

    @Test
    void caricareSenzaFileRestituisceOptionalVuoto() {
        assertTrue(archivio.carica().isEmpty());
    }

    @Test
    void caricareUnFileVuotoRestituisceOptionalVuoto() throws Exception {
        Files.writeString(file, "");

        assertTrue(archivio.carica().isEmpty());
    }

    @Test
    void caricareUnFileCheNonEJsonRestituisceOptionalVuoto() throws Exception {
        Files.writeString(file, "ciao come stai");

        assertTrue(archivio.carica().isEmpty());
    }

    @Test
    void caricareUnSalvataggioConValoriNonValidiRestituisceOptionalVuoto() throws Exception {
        Files.writeString(file, "{\"piano\":0,\"puntiVita\":50,\"dannoSpada\":10}");

        assertTrue(archivio.carica().isEmpty());
    }

    @Test
    void salvareDueVolteTieneSoloIlSecondoSalvataggio() {
        archivio.salva(new SalvataggioPartita(2, 80, 12));
        archivio.salva(new SalvataggioPartita(5, 40, 20));

        assertEquals(Optional.of(new SalvataggioPartita(5, 40, 20)), archivio.carica());
    }
    @Test
    void eliminareDopoUnSalvataggioRimuoveIlFile() {
        archivio.salva(new SalvataggioPartita(4, 63, 17));

        archivio.elimina();

        assertFalse(Files.exists(file));
    }

    @Test
    void eliminareDopoUnSalvataggioFaRestituireOptionalVuotoAlCaricamento() {
        archivio.salva(new SalvataggioPartita(4, 63, 17));

        archivio.elimina();

        assertTrue(archivio.carica().isEmpty());
    }

    @Test
    void eliminareSenzaFileNonLanciaEccezioni() {
        assertDoesNotThrow(() -> archivio.elimina());
    }
}