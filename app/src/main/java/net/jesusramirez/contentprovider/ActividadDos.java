package net.jesusramirez.contentprovider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import net.jesusramirez.contentprovider.data.daoUsuarios;
import net.jesusramirez.contentprovider.data.Usuario;

public class ActividadDos extends AppCompatActivity {
    private static final int ACTIVITY_REGISTRO = 1000;
    Button btnOk, btnCancel, btnRegistrar;
    EditText txtUser, txtPass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_second_activity);

        txtPass = (EditText) findViewById(R.id.txtPass);
        txtUser = (EditText) findViewById(R.id.txtUser);
        btnCancel = findViewById(R.id.btnCancel);
        btnOk = findViewById(R.id.btnOk);
        btnOk.setOnClickListener(v -> {
            daoUsuarios dao = new daoUsuarios(getApplicationContext());
            Usuario usuarioAutenticado = dao.autenticar(new Usuario(txtUser.getText().toString(), txtPass.getText().toString()));
            if (usuarioAutenticado.getID() != 0) {
                Intent intent = new Intent(ActividadDos.this, MainActivity.class);
                intent.putExtra("usuario", usuarioAutenticado);
                startActivity(intent);
            } else {
                Toast.makeText(getBaseContext(),
                        txtUser.getText().toString() + " SIN ACCESO",
                        Toast.LENGTH_LONG).show();
            }
        });

        btnCancel.setOnClickListener(v -> {
            finish();
        });

        Log.d("ciclovida", "paso por onCreate");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ACTIVITY_REGISTRO:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                    Usuario usnu = (Usuario) data.getSerializableExtra("Usuario");
                    txtUser.setText(usnu.getEmail());
                } else {
                    Toast.makeText(this, "Usuario NO Registrado", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2000:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ciclovida", "paso por onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("ciclovida", "paso por onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ciclovida", "paso por onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("ciclovida", "paso por onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("ciclovida", "paso por onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ciclovida", "paso por onDestroy");
    }

    public void btnRegistrar_click(View view) {
        Intent intent = new Intent(getBaseContext(), ActividadRegistrarUser.class);

        startActivityForResult(intent, ACTIVITY_REGISTRO);

    }
}

