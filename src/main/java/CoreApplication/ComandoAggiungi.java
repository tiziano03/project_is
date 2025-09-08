package CoreApplication;

import Model.*;

import java.util.Map;

public class ComandoAggiungi implements Command {
    private final Libreria libreria;


    public ComandoAggiungi(Libreria libreria) {
        this.libreria = libreria;
    }


    @Override
    public void execute(RichiestaComando input) {
        if (input == null) throw new IllegalArgumentException();


        Map<NomeParametro, String> mappa = input.getArgomenti();
        String isbn = mappa.get(NomeParametro.ISBN);
        String titolo = mappa.get(NomeParametro.TITOLO);
        String autore = mappa.get(NomeParametro.AUTORE);
        String genere = mappa.get(NomeParametro.GENERE);
        String statoLettura = mappa.get(NomeParametro.STATOLETTURA);
        String valutazione = mappa.get(NomeParametro.VALUTAZIONE);


        if (!(mappa.size() == 6) || isbn == null || titolo == null || autore == null ||
                genere == null || statoLettura == null || valutazione == null) {
            throw new IllegalArgumentException("Comando di inserimento malformato");
        }


        Genere genereEnum = Genere.getGenere(genere);
        StatoLettura statoLetturaEnum = StatoLettura.getStatoLettura(statoLettura);
        Valutazione valutazioneEnum = Valutazione.getValutazione(valutazione);
        Isbn trueIsbn = Isbn.factory(isbn);


        Libro libro = new Libro(trueIsbn, titolo, autore, valutazioneEnum, genereEnum, statoLetturaEnum);
        libreria.aggiungiLibro(libro);

    }
}
