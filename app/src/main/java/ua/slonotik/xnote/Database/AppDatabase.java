package ua.slonotik.xnote.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import static ua.slonotik.xnote.Database.AppDatabase.DATABASE_VERSION;

@Database(entities = {Notes.class}, version = DATABASE_VERSION)
public abstract class AppDatabase extends RoomDatabase {
    protected static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Notes-X-Database_Room";

    public abstract NotesDao notesDao();

    private static AppDatabase mInstance;

    public static AppDatabase getInstance(Context context){
        if(mInstance == null)
        {
            mInstance = Room.databaseBuilder(context,AppDatabase.class,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return mInstance;
    }


}
