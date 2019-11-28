package ua.slonotik.xnote.Database;

import java.util.List;
import io.reactivex.Flowable;

public class NotesDataSource implements INotesDataSource{

    private NotesDao notesDao;
    private static NotesDataSource mInstance;

    public NotesDataSource(NotesDao notesDao){
        this.notesDao = notesDao;
    }

    public static NotesDataSource getInstance(NotesDao notesDao){
        if(mInstance == null){
            mInstance = new NotesDataSource(notesDao);
        }
        return mInstance;
    }

    @Override
    public Flowable<Notes> getNotesById(int id) {
        return notesDao.getNotesById(id);
    }

    @Override
    public Flowable<List<Notes>> getAllNotes() {
        return notesDao.getAllNotes();
    }

    @Override
    public void insertNotes(Notes... notes) {
        notesDao.insertNotes(notes);
    }

    @Override
    public void updateNotes(Notes... notes) {
        notesDao.updateNotes(notes);
    }

    @Override
    public void deleteNotes(Notes... notes) {
        notesDao.deleteNotes(notes);
    }

    @Override
    public void deleteAllNotes() {
        notesDao.deleteAllNotes();
    }
}
