package ua.slonotik.xnote.Database;

import java.util.List;
import io.reactivex.Flowable;

public interface INotesDataSource {

    Flowable<Notes> getNotesById(int id);
    Flowable<List<Notes>> getAllNotes();
    void insertNotes(Notes... notes);
    void updateNotes(Notes... notes);
    void deleteNotes(Notes... notes);
    void deleteAllNotes();
}
