package www.runa.registrofbs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.UUID;

public class RegistroU extends AppCompatActivity  {

    Button btnRegresarInicio, btnGuardarNuevoU;
    EditText editRUuario, editRContraseña;
    FirebaseDatabase BD;
    DatabaseReference RefereniaBD;
    ProgressBar Chequiando;
    FirebaseAuth fAuth;

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_u);


        fAuth = FirebaseAuth.getInstance();

        Chequiando = (ProgressBar) findViewById(R.id.Chequiando);
////////////////////////////////////////////////////////////////////////////////////////////////////
        btnRegresarInicio = (Button)findViewById(R.id.btnRegresarInicio);
        btnGuardarNuevoU = (Button)findViewById(R.id.btnRegistrarNuevoU);
////////////////////////////////////////////////////////////////////////////////////////////////////
        editRContraseña = (EditText)findViewById(R.id.editRContraseña);
        editRUuario = (EditText)findViewById(R.id.editRUuario);
////////////////////////////////////////////////////////////////////////////////////////////////////
        btnRegresarInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Inicio.class);
                startActivity(intent);
            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////

        iniciarFirebase();
    }
    private void iniciarFirebase() {
        FirebaseApp.initializeApp(this);
        BD = FirebaseDatabase.getInstance();
        RefereniaBD = BD.getReference();
    }


    public void IngresarBD(View view) {


        final String Usuario = editRUuario.getText().toString().trim();
        final String Contraseña = editRContraseña.getText().toString().trim();

        if (TextUtils.isEmpty(Usuario)) {
            editRUuario.setError("El Usuario no se ingreso");
            return;
        }

        if (TextUtils.isEmpty(Contraseña)) {
            editRContraseña.setError("Su Contraseña no se ingreso");
            return;
        }

        if (Contraseña.length() < 6) {
            editRContraseña.setError("Su Contraseña tiene que ser mayor  4 Caracteres");
            return;
        }
        if (TextUtils.isEmpty(Contraseña)) {
            editRContraseña.setError("Su Contraseña no se ingreso");
            return;
        }

        Chequiando.setVisibility(View.VISIBLE);

        fAuth.createUserWithEmailAndPassword(Usuario, Contraseña)
                .addOnCompleteListener(RegistroU.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(RegistroU.this, "Las Credenciales se ceraron correctamente:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                        Chequiando.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegistroU.this, "No se pudo crear nada." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                            addUserTodatabase(Usuario, Contraseña);
                            startActivity(new Intent(RegistroU.this, Inicio.class));
                            finish();
                        }
                    }
                });
    }


        public void addUserTodatabase(String Usuario, String Contraseña) {


            UsuarioR ur = new UsuarioR();
            ur.setId(UUID.randomUUID().toString());
            ur.setUsuario(editRUuario.getText().toString());
            ur.setContraseña(editRContraseña.getText().toString());


            RefereniaBD.child("UsuarioR").child(ur.getId()).setValue(ur);

            FirebaseDatabase.getInstance().getReference("UsuarioR").push().setValue(ur);


            Toast.makeText(getApplicationContext(), "Usuario agregado en las Bases de Datos", Toast.LENGTH_SHORT).show();

        }

    @Override
    protected void onResume() {
        super.onResume();
        Chequiando.setVisibility(View.GONE);
    }



}






