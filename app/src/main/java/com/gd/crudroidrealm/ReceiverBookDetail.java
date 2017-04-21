package com.gd.crudroidrealm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
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

        final Livro livroRealm = realm.where(Livro.class)
                .equalTo("titulo",getIntent().getStringExtra("livro_titulo")).findFirst();

        EditText titulo = (EditText) findViewById(R.id.titulo);
        EditText autor = (EditText) findViewById(R.id.autor);
        EditText isbn = (EditText) findViewById(R.id.isbn);

        titulo.setText(livroRealm.getTitulo());
        autor.setText(livroRealm.getAutor());
        isbn.setText(livroRealm.getIsbn());

        Button btndel = (Button) findViewById(R.id.btndel);
        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        final Livro l =  livroRealm;
                        l.deleteFromRealm();

                        finish();
                    }
                });
            }
        });
    }
}
