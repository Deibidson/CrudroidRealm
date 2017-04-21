package com.gd.crudroidrealm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.gd.crudroidrealm.Modelo.Livro;

import io.realm.Realm;
import io.realm.RealmResults;

public class ReceiverBookDetail extends AppCompatActivity {
    private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_book_detail);

        realm = Realm.getDefaultInstance();

        Livro livroRealm = realm.where(Livro.class)
                .equalTo("titulo",getIntent().getStringExtra("livro_titulo")).findFirst();

        EditText titulo = (EditText) findViewById(R.id.titulo);
        EditText autor = (EditText) findViewById(R.id.autor);
        EditText isbn = (EditText) findViewById(R.id.isbn);

        titulo.setText(livroRealm.getTitulo());
        autor.setText(livroRealm.getAutor());
        isbn.setText(livroRealm.getIsbn());
    }
}
