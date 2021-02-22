package net.jesusramirez.contentprovider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import net.jesusramirez.contentprovider.data.Usuario;
import net.jesusramirez.contentprovider.data.daoUsuarios;
import net.jesusramirez.contentprovider.data.database;

public class ActividadRegistrarUser extends AppCompatActivity {
    EditText txtNombre;
    EditText txtPass;
    EditText txtEmail;
    EditText txtTelefono;
    Button btnRegistrar;
    Button btnCancelar;
    String[] paises;
    Spinner spnPaises;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_registrar);

        txtNombre = findViewById(R.id.txtNombreUser);
        txtPass = findViewById(R.id.txtPassUser);
        txtEmail = findViewById(R.id.txtEmailUser);
        txtTelefono = findViewById(R.id.txtTelefonoUser);
        btnRegistrar = findViewById(R.id.btnGuardarUser);
        btnCancelar = findViewById(R.id.btnCancelUser);

        btnRegistrar.setOnClickListener(
                v -> {
                    Usuario nuevoUsuario = new Usuario(txtNombre.getText().toString(), txtEmail.getText().toString(), txtPass.getText().toString(), txtTelefono.getText().toString());

                    daoUsuarios dao = new daoUsuarios(getApplicationContext());
                    //insertar el usuario, validar exito
                    if (dao.insert(nuevoUsuario) != -1) {
                        //INSERTAR EL OBJETO Usuario AL OBJETO Intent
                        Intent intent = new Intent();
                        intent.putExtra("Usuario", nuevoUsuario);

                        //Toast.makeText(this, "Objeto insertado al intent", Toast.LENGTH_LONG).show();
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    } else {
                        setResult(Activity.RESULT_CANCELED);
                        finish();
                    }
                }
        );

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        paises = getResources().getStringArray(R.array.paises);;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, paises);
        spnPaises = findViewById(R.id.spnPaises);
        spnPaises.setAdapter(adapter);
    }
}
