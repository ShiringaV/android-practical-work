package ua.slonotik.xnote.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface NotesDao {
    @Query("SELECT * FROM notes WHERE id = :id")
    Flowable<Notes> getNotesById(int id);

    @Query("SELECT * FROM notes")
    Flowable<List<Notes>> getAllNotes();

    @Insert
    void insertNotes(Notes... notes);

    @Update
    void updateNotes(Notes... notes);

    @Delete
    void deleteNotes(Notes... notes);

    @Query("DELETE FROM notes")
    void deleteAllNotes();
}
