package ua.slonotik.xnote.Database;

import java.util.List;
import io.reactivex.Flowable;

public class NotesRepository implements INotesDataSource {

    private INotesDataSource mLocalDataSource;
    private static NotesRepository mInstance;

    public NotesRepository(INotesDataSource mLocalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }

    public static NotesRepository getInstance(INotesDataSource mLocalDataSource){
        if(mInstance == null){
            mInstance = new NotesRepository(mLocalDataSource);
        }
        return mInstance;
    }



    @Override
    public Flowable<Notes> getNotesById(int id) {
        return mLocalDataSource.getNotesById(id);
    }

    @Override
    public Flowable<List<Notes>> getAllNotes() {
        return mLocalDataSource.getAllNotes();
    }

    @Override
    public void insertNotes(Notes... notes) {
        mLocalDataSource.insertNotes(notes);
    }

    @Override
    public void updateNotes(Notes... notes) {
        mLocalDataSource.updateNotes(notes);
    }

    @Override
    public void deleteNotes(Notes... notes) {
        mLocalDataSource.deleteNotes(notes);
    }

    @Override
    public void deleteAllNotes() {
        mLocalDataSource.deleteAllNotes();
    }
}
