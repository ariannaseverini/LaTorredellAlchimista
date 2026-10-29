package it.unicam.cs.mpgc.rpg130278.persistenza;

import com.google.gson.Gson;

import java.nio.file.Path;
import java.util.Optional;
import java.nio.file.Files;
import java.io.IOException;
import java.io.UncheckedIOException;


public class ArchivioJson implements ArchivioPartite {

    private final Path file;
    private final Gson gson = new Gson();

    public ArchivioJson(Path file) {
        if(file == null) throw new IllegalArgumentException("Il file non può essere null");
        this.file = file;
    }

    @Override
    public void salva(SalvataggioPartita salvataggio) {
        if(salvataggio == null) throw new IllegalArgumentException("Il salvataggio non può essere null");
        String json= gson.toJson(salvataggio);
        try {
            Files.writeString(file, json);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }

    @Override
    public Optional<SalvataggioPartita> carica() {
        if (!Files.exists(file)) return Optional.empty();
        try {
            String json = Files.readString(file);
            return Optional.ofNullable(gson.fromJson(json, SalvataggioPartita.class));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    @Override
    public void elimina() {
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}