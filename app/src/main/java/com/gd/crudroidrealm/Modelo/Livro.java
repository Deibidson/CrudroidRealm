package com.gd.crudroidrealm.Modelo;

import io.realm.RealmObject;

/**
 * Created by deibi on 21/04/2017.
 */

public class Livro extends RealmObject {
    private String titulo;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
