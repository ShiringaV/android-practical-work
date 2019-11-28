package ua.slonotik.xnote;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import ua.slonotik.xnote.Database.AppDatabase;
import ua.slonotik.xnote.Database.Notes;
import ua.slonotik.xnote.Database.NotesDataSource;
import ua.slonotik.xnote.Database.NotesRepository;


public class MainActivity extends AppCompatActivity {

    private ListView lstNote;
    private FloatingActionButton fab;

    //TODO Тестовый boolean, удалить после подключения DF
    private boolean deleteAnswer = false;

    //Адаптер
    List<Notes> notesList = new ArrayList<>();
    ArrayAdapter adapter;

    //БД
    private CompositeDisposable compositeDisposable;
    private NotesRepository notesRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Инициализация

        compositeDisposable = new CompositeDisposable();

        //Инициализируем View
        lstNote =(ListView) findViewById(R.id.lstNotes);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, notesList);
        registerForContextMenu(lstNote);
        lstNote.setAdapter(adapter);

        //База Данных
        AppDatabase notesDatabase = AppDatabase.getInstance(this);
        notesRepository = NotesRepository.getInstance(NotesDataSource.getInstance(notesDatabase.notesDao()));

        //Загрузска все данных с БД
        loadData();

        //Обработка события
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO -- Тест --
                //Добавление рандомных значений
                Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                        Notes notes = new Notes("Tests", "А тут наша заметочка");
                        notesRepository.insertNotes(notes);
                        emitter.onComplete();
                    }
                })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer() {
                            @Override
                            public void accept(Object o) throws Exception {
                                //TODO После теста изменить
                                Toast.makeText(MainActivity.this, "запись добавлена", Toast.LENGTH_SHORT).show();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(MainActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {
                                //Обновление данных
                                loadData();
                            }
                        });
            }
        });
    }

    private void loadData(){
        //Используем RXJava
        Disposable disposable = notesRepository.getAllNotes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Notes>>() {
                               @Override
                               public void accept(List<Notes> notes) throws Exception {
                                   onGetAllNotesSuccess(notes);
                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   Toast.makeText(MainActivity.this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
                               }
                           }
                );
        compositeDisposable.add(disposable);
    }

    private void onGetAllNotesSuccess(List<Notes> notes) {
        notesList.clear();
        notesList.addAll(notes);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case  R.id.menu_clear:
                deleateAllNotes();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleateAllNotes() {
        if (deleteAnswer == true) {
            Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                    notesRepository.deleteAllNotes();
                    emitter.onComplete();
                }
            })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer() {
                        @Override
                        public void accept(Object o) throws Exception {

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Toast.makeText(MainActivity.this, "" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }, new Action() {
                        @Override
                        public void run() throws Exception {
                            //Обновление данных
                            loadData();
                        }
                    });
        } else {
            Toast.makeText(MainActivity.this, "Нажмите еще раз, что бы все удалить", Toast.LENGTH_SHORT).show();
            deleteAnswer = true;
        }
    }

}
