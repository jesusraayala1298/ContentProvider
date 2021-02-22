package net.jesusramirez.contentprovider.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

//clase para representar los usuarios
public class daoUsuarios {
    Context context;
    database db; //database
    SQLiteDatabase ad; //acceso a datos

    public daoUsuarios(Context context) {
        this.context = context;
        db = new database(context); //inicializar la BD
        ad = db.getWritableDatabase(); //devuelve objeto database que tiene insert, update, delete
    }

    //metodo para insertar un usuario a la BD
    public long insert(Usuario usuario) {
        //ContentValues coleccion llave valor
        ContentValues cv = new ContentValues();

        //cv.put(nombre_columna, valor);
        cv.put(db.COLUMNS_TABLE_USUARIOS[1], usuario.getNombre());
        cv.put(db.COLUMNS_TABLE_USUARIOS[2], usuario.getEmail());
        cv.put(db.COLUMNS_TABLE_USUARIOS[3], usuario.getContrasenia());
        cv.put(db.COLUMNS_TABLE_USUARIOS[4], usuario.getTelefono());

        //insertar registro a la tabla, retorna el nuevo que inserto, -1 si hay error
        return ad.insert(db.TABLE_USUARIOS_NAME, null, cv);
    }

    //metodo para insertar un usuario a la BD
    public long insert(ContentValues cv) {
        //insertar registro a la tabla, retorna el nuevo que inserto, -1 si hay error
        return ad.insert(db.TABLE_USUARIOS_NAME, null, cv);
    }

    public boolean update(Usuario usuario) {
        //ContentValues coleccion llave valor
        ContentValues cv = new ContentValues();

        //cv.put(nombre_columna, valor);
        //CONTIENE LAS COLUMNAS QUE VA A ACTUALIZAR Y CON QUE VALOR
        cv.put(db.COLUMNS_TABLE_USUARIOS[1], usuario.getNombre());
        cv.put(db.COLUMNS_TABLE_USUARIOS[2], usuario.getEmail());
        cv.put(db.COLUMNS_TABLE_USUARIOS[3], usuario.getContrasenia());
        cv.put(db.COLUMNS_TABLE_USUARIOS[4], usuario.getTelefono());

        //actualiza un registro
        //devuelve las filas actualizadas i suppose¿?
        return ad.update(
                db.TABLE_USUARIOS_NAME, //nombre de la tabla
                cv, //content value que tenga los valores actualizados
                "_id=?", //clausula where, el ? se pasa aca ->
                new String[]{String.valueOf(usuario.getID())} //obtiene los valores para ?
        ) > 0;
    }

    public int update(ContentValues cv, long id) {
        //actualiza un registro
        //devuelve las filas actualizadas i suppose¿?
        return ad.update(
                db.TABLE_USUARIOS_NAME, //nombre de la tabla
                cv, //content value que tenga los valores actualizados
                "_id=?", //clausula where, el ? se pasa aca ->
                new String[]{String.valueOf(id)} //obtiene los valores para ?
        );
    }

    public boolean deleteB(long id) {
        //elimina registros en la bd
        //devuelve entero con lineas afectadas
        return ad.delete(
                db.TABLE_USUARIOS_NAME, //nombre de la tabla
                "_id=?", //clausula where
                new String[]{String.valueOf(id)} //parametros enviados
        ) > 0;
    }

    public int delete(long id) {
        //elimina registros en la bd
        //devuelve entero con lineas afectadas
        return ad.delete(
                db.TABLE_USUARIOS_NAME, //nombre de la tabla
                "_id=?", //clausula where
                new String[]{String.valueOf(id)} //parametros enviados
        );
    }

    //aparentemente no funciona
    public Cursor getAllByName(String nombre) {
        //para todas las sentencias
        //retorna un Cursor
        return ad.rawQuery(
                "select * from " + db.TABLE_USUARIOS_NAME +
                        " where nombre like ?",
                new String[]{"\\%" + nombre + "\\%"}
        );
    }

    public Cursor getAllCursor() {
        return ad.query(db.TABLE_USUARIOS_NAME, db.COLUMNS_TABLE_USUARIOS,
                null, null, null, null, null);
    }

    //obtener un elemento de acuerdo al ID dado
    public Usuario getOneByID(long id) {
        Cursor cursor = null;
        Usuario usuario = null;

        //recibe una sentencia sql pero es un SELECT, devuelve un cursor
        cursor = ad.rawQuery(
                "select * from " + db.TABLE_USUARIOS_NAME + " where " +
                        db.COLUMNS_TABLE_USUARIOS[0] + "=?",
                new String[]{String.valueOf(id)} //valores del ?
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) { //se va a la primera fila
                usuario = new Usuario(
                        cursor.getInt(0), //columna 0
                        cursor.getString(1), //columna 1 ... etc
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );
            }
        }

        return usuario;
    }

    public Cursor getOneByIDCursor(long id) {
        Cursor cursor = null;

        //recibe una sentencia sql pero es un SELECT, devuelve un cursor
        cursor = ad.rawQuery(
                "select * from " + db.TABLE_USUARIOS_NAME + " where " +
                        db.COLUMNS_TABLE_USUARIOS[0] + "=?",
                new String[]{String.valueOf(id)} //valores del ?
        );

        return cursor;
    }

    public List<Usuario> getAll() {
        List<Usuario> usuariosList = new ArrayList<Usuario>();

        //select * from
        Cursor cursor = ad.query(db.TABLE_USUARIOS_NAME, db.COLUMNS_TABLE_USUARIOS,
                null, null, null, null, null);

        //getCount devuelve el numero de filas en el cursor
        if (cursor.getCount() > 0) {
            //mientras haya elementos en el cursor, llenar la lista
            while (cursor.moveToNext()) {
                usuariosList.add(new Usuario(
                        cursor.getInt(0), //columna 0
                        cursor.getString(1), //columna 1 ... etc
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                ));
            }
        }

        return usuariosList;
    }

    //autenticar usuario
    public Usuario autenticar(Usuario usuario) {
        //metodo que permite construir consultas
        //la consulta retorna un cursor, es el resultado de la consulta
        Cursor c = ad.query(db.TABLE_USUARIOS_NAME, //nombre de la tabla
                db.COLUMNS_TABLE_USUARIOS, //columnas en la tabla que va a retornar
                "email=? and password=?", //where
                new String[]{usuario.getEmail(), usuario.getContrasenia()}, //los valores del usuario son para los ?, en orden
                null, null, null); //nulos no groupBy, no having, no orderBy


        if (c.moveToFirst() //devuelbe true si puede irse al primer elemento
        ) {
            //obtener el ID que está en la columna 0 (el ID)
            usuario.setID(c.getLong(0));
            //el nombre esta en la columna 1
            usuario.setNombre(c.getString(1));
            //establecer el telefono columna 4
            usuario.setTelefono(c.getString(4));
        }

        return usuario;
    }
}
