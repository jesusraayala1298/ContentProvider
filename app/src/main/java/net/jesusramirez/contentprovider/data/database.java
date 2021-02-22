package net.jesusramirez.contentprovider.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "dbUsuarios";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_USUARIOS_NAME = "usuarios";
    public static final String[] COLUMNS_TABLE_USUARIOS =
            {"_id", "nombre", "email", "password", "telefono"};

    //script para definir la tabla
    private static final String SCRIPT_TABLE_USERS =
            "create table usuarios (" +
            "_id integer primary key autoincrement, " +
            "nombre text, " +
            "email text not null, " +
            "password text not null, " +
            "telefono text not null);";

    Context context;

    //constructor para definir el nombre y version de la BD
    public database(@Nullable Context context) {
        //llamando al constructor padre de SQLiteOpenHelper
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    //se ejecuta la primera vez, cuando no hay BD
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //ejecutar una secuencia SQL que no es un select (definicion de datos)
        sqLiteDatabase.execSQL(SCRIPT_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
