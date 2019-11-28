package ua.slonotik.xnote.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "notes")
public class Notes {
    public int getId() {
        return id;
    }

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    @ColumnInfo(name="head")
    private String head;

    @ColumnInfo(name="body")
    private String body;

    public Notes() {

    }

    @Ignore
    public Notes(String head, String body) {
        this.head = head;
        this.body = body;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return new StringBuilder(head).append("\n").append(body).toString();
    }
}
