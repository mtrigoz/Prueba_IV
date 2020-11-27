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


public class Inicio extends AppCompatActivity {

    EditText editCorreo,editContraseña;
    Button btnIniciarL,btnRegistrarNuevoU;
    ProgressBar Chequiando;
    FirebaseAuth fAuth;
    FirebaseDatabase BD;
    DatabaseReference RefereniaBD;

////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        //FirebaseAuth
        fAuth = FirebaseAuth.getInstance();

////////////////////////////////////////////////////////////////////////////////////////////////////
        editCorreo = (EditText)findViewById(R.id.editCorreo);
        editContraseña = (EditText)findViewById(R.id.editContraseña);
        btnIniciarL = (Button)findViewById(R.id.btnIniciarL);
        btnRegistrarNuevoU = (Button)findViewById(R.id.btnRegistrarNuevoU);
        Chequiando = (ProgressBar)findViewById(R.id.Chequiando);

////////////////////////////////////////////////////////////////////////////////////////////////////
        //IR A REGISTRAR
        btnRegistrarNuevoU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegistroU.class);
                startActivity(intent);
            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////////

        btnIniciarL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                IngresarUsuarioBD();
            }
        });

        iniciarFirebase();
    }

////////////////////////////////////////////////////////////////////////////////////////////////////

    private void iniciarFirebase() {
        FirebaseApp.initializeApp(this);
        BD = FirebaseDatabase.getInstance();
        RefereniaBD = BD.getReference();
    }



    private void IngresarUsuarioBD()
    {

        Chequiando.setVisibility(View.VISIBLE);


        String correo, contraseña;
        correo = editCorreo.getText().toString();
        contraseña = editContraseña.getText().toString();

        if (TextUtils.isEmpty(correo)) {
            Toast.makeText(getApplicationContext(),
                    "Porfavor ingrese su Correo", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(contraseña)) {
            Toast.makeText(getApplicationContext(),
                    "Porfavor ingrese Contraseña",Toast.LENGTH_LONG).show();
            return;
        }

        fAuth.signInWithEmailAndPassword(correo, contraseña)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(
                            @NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),
                                            "Inicio Correctamente!",Toast.LENGTH_LONG).show();
                                    // hide the progress bar
                                    Chequiando.setVisibility(View.GONE);
                                    Intent intent = new Intent(Inicio.this,PerfilUsiarios.class);
                                    startActivity(intent);
                                }
                                else {
                                    // sign-in failed
                                    Toast.makeText(getApplicationContext(),
                                            "No hay conexion a las bases de datos",Toast.LENGTH_LONG).show();
                                    // hide the progress bar
                                    Chequiando.setVisibility(View.GONE);
                                }
                            }
                        });
    }
}