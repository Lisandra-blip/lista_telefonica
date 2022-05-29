package com.example.listatelefonica.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;


import com.example.listatelefonica.TelaPrincipal.Contato;
import com.example.listatelefonica.R;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ListaTelefonica";
    private static final String TABLE_CONTATO = "contatos";
    private static final String TABLE_TIPO = "tipo";

    private static final String CREATE_TABLE_CONTATO = "CREATE TABLE " + TABLE_CONTATO + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome VARCHAR(100), " +
            "celular VARCHAR(15), " +
            "id_tipo INTEGER, " +
            "CONSTRAINT fk_tipo_contato FOREIGN KEY (id_tipo) REFERENCES tipo (id))";

    private static final String CREATE_TABLE_TIPO = "CREATE TABLE " + TABLE_TIPO + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome VARCHAR(100), ";

    private static final String DROP_TABLE_CONTATO = "DROP TABLE IF EXISTS " + TABLE_CONTATO;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTATO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_CONTATO);
        onCreate(db);
    }

    /* Início CRUD Mãe */
    public long createContato (Contato c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", c.getNome());
        cv.put("id_tipo", c.getId_tipo());
        cv.put("celular", c.getCelular());
        long id = db.insert(TABLE_CONTATO, null, cv);
        db.close();
        return id;
    }

    public long updateContato (Contato c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", c.getNome());
        cv.put("id_tipo", c.getId_tipo());
        cv.put("celular", c.getCelular());
        long id = db.update(TABLE_CONTATO, cv, "_id = ?", new String[]{String.valueOf(c.getId())});
        db.close();
        return id;
    }

    public long deleteContato (Contato m) {
        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.delete(TABLE_CONTATO, "_id = ?", new String[]{String.valueOf(m.getId())});
        db.close();
        return id;
    }

    public Contato getByIdContato (int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "nome", "id_tipo", "celular"};
        Cursor data = db.query(TABLE_CONTATO, columns, "_id = ?", new String[]{String.valueOf(id)}, null, null, null);
        data.moveToFirst();
        Contato c = new Contato();
        c.setId(data.getInt(0));
        c.setNome(data.getString(1));
        c.setId_tipo(data.getInt(2));
        c.setCelular(data.getString(3));
        data.close();
        db.close();
        return c;
    }

    public void getAllContatos (Context context, ListView lv) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"_id", "nome", "celular"};
        Cursor data = db.query(TABLE_CONTATO, columns, null, null, null, null, null);
        int[] to = {R.id.textViewIdListContato, R.id.textViewNomeListContato, R.id.textViewNumeroListContato};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context, R.layout.contato_item_list_view, data, columns, to, 0);
        lv.setAdapter(simpleCursorAdapter);
        db.close();
    }

    public void getAllTipoContato (ArrayList<Integer> listTipoId, ArrayList<String> listTipoDs) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome"};
        Cursor data = db.query(TABLE_CONTATO, columns, null, null, null,
                null, "nome");
        while (data.moveToNext()) {
            int idColumnIndex = data.getColumnIndex("_id");
            listTipoId.add(Integer.parseInt(data.getString(idColumnIndex)));
            int nameColumnIndex = data.getColumnIndex("nome");
            listTipoDs.add(data.getString(nameColumnIndex));
        }
        db.close();
    }

    /* Fim CRUD Mãe */

    /* Início CRUD Bebê
    public long createBebe(Bebe b) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_mae", b.getId_mae());
        cv.put("id_medico", b.getId_medico());
        cv.put("nome", b.getNome());
        cv.put("data_nascimento", b.getData_nascimento());
        cv.put("peso", b.getPeso());
        cv.put("altura", b.getAltura());
        long id = db.insert(TABLE_BEBE, null, cv);
        db.close();
        return id;
    }

    public long updateBebe(Bebe b) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_mae", b.getId_mae());
        cv.put("id_medico", b.getId_medico());
        cv.put("nome", b.getNome());
        cv.put("data_nascimento", b.getData_nascimento());
        cv.put("peso", b.getPeso());
        cv.put("altura", b.getAltura());
        long rows = db.update(TABLE_BEBE, cv, "_id = ?", new String[]{String.valueOf(b.getId())});
        db.close();
        return rows;
    }

    public long deleteBebe(Bebe b) {
        SQLiteDatabase db = this.getWritableDatabase();
        long rows = db.delete(TABLE_BEBE, "_id = ?", new String[]{String.valueOf(b.getId())});
        db.close();
        return rows;
    }

    public void getAllBebe (Context context, ListView lv) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome"};
        Cursor data = db.query(TABLE_BEBE, columns, null, null, null,
                null, "nome");
        int[] to = {R.id.textViewIdListarBebe, R.id.textViewNomeListarBebe};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context,
                R.layout.bebe_item_list_view, data, columns, to, 0);
        lv.setAdapter(simpleCursorAdapter);
        db.close();
    }

    public Bebe getByIdBebe (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "id_mae", "id_medico", "nome", "data_nascimento", "peso", "altura"};
        String[] args = {String.valueOf(id)};
        Cursor data = db.query(TABLE_BEBE, columns, "_id = ?", args, null,
                null, null);
        data.moveToFirst();
        Bebe b = new Bebe();
        b.setId(data.getInt(0));
        b.setId_mae(data.getInt(1));
        b.setId_medico(data.getInt(2));
        b.setNome(data.getString(3));
        b.setData_nascimento(data.getString(4));
        b.setPeso(data.getFloat(5));
        b.setAltura(data.getInt(6));
        data.close();
        db.close();
        return b;
    }
     Fim CRUD Bebê */
}
