package www.runa.registrofbs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PerfilUsiarios extends AppCompatActivity {

    EditText editNombrePKM, editAtaque, editVida, editDefensa;
    Button btnConsultar, btnConsultarNombrePKM, btnAgregar, btnEditar, btnEliminarPKM;
    RecyclerView rvPKMS;

    DatabaseReference databaseReference;
    List<FichaPokemon> avistamientos = new ArrayList<>();
    Pokedex pkdex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usiarios);

        editNombrePKM = (EditText) findViewById(R.id.editNombrePKM);
        editAtaque = (EditText) findViewById(R.id.editAtaque);
        editVida = (EditText) findViewById(R.id.editVida);
        editDefensa = (EditText) findViewById(R.id.editDefensa);
////////////////////////////////////////////////////////////////////////////////////////////////////
        btnConsultar = (Button) findViewById(R.id.btnConsultar);
        btnConsultarNombrePKM = (Button) findViewById(R.id.btnConsultarNombrePKM);
        btnAgregar = (Button) findViewById(R.id.btnAgregar);
        btnEditar = (Button) findViewById(R.id.btnEditar);
        btnEliminarPKM = (Button) findViewById(R.id.btnEliminarPKM);
////////////////////////////////////////////////////////////////////////////////////////////////////
        rvPKMS = (RecyclerView) findViewById(R.id.rvPKMS);
        rvPKMS.setLayoutManager(new GridLayoutManager(this, 1));

        databaseReference = FirebaseDatabase.getInstance().getReference();
        obtenerPKM();
////////////////////////////////////////////////////////////////////////////////////////////////////
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarPKM();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editarPKM();
            }
        });

        btnEliminarPKM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarPKM();
            }
        });

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerPKMS();
            }
        });

        btnConsultarNombrePKM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerPKM();
            }
        });
    }
////////////////////////////////////////////////////////////////////////////////////////////////////

    public void agregarPKM() {
        avistamientos.clear();
        FichaPokemon fichapkm = new FichaPokemon(
                editNombrePKM.getText().toString(),
                editAtaque.getText().toString(),
                editVida.getText().toString(),
                editDefensa.getText().toString()
        );

        databaseReference.child("Pokemon").push().setValue(fichapkm,
                new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        Toast.makeText(PerfilUsiarios.this, "Pokemon Añadido.", Toast.LENGTH_SHORT).show();
                    }
                });

        limpiarCampos();
    }

////////////////////////////////////////////////////////////////////////////////////////////////////

    public void obtenerPKM() {
        avistamientos.clear();
        String Pokemon = editNombrePKM.getText().toString();

        Query query = databaseReference.child("Pokemon").orderByChild("Pokemon").equalTo(Pokemon);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot objeto : dataSnapshot.getChildren()) {
                    avistamientos.add(objeto.getValue(FichaPokemon.class));
                }

                pkdex = new Pokedex(PerfilUsiarios.this, avistamientos);
                rvPKMS.setAdapter(pkdex);

                limpiarCampos();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

////////////////////////////////////////////////////////////////////////////////////////////////////

    public void obtenerPKMS() {
            avistamientos.clear();
            databaseReference.child("Pokemon").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                        avistamientos.add(objeto.getValue(FichaPokemon.class));
                    }

                    pkdex = new Pokedex(PerfilUsiarios.this, avistamientos);
                    rvPKMS.setAdapter(pkdex);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            limpiarCampos();
    }

////////////////////////////////////////////////////////////////////////////////////////////////////

    public void editarPKM() {
        avistamientos.clear();

        final FichaPokemon fichaPKM = new FichaPokemon(
                editNombrePKM.getText().toString(),
                editAtaque.getText().toString(),
                editVida.getText().toString(),
                editDefensa.getText().toString()
        );

        Query query = databaseReference.child("Pokemon").orderByChild("Pokemon").equalTo(fichaPKM.getNombrePKM());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String key = "";
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    key = ds.getKey(); //
                }

                databaseReference.child("Pokemon").child(key).setValue(fichaPKM);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        limpiarCampos();
    }

////////////////////////////////////////////////////////////////////////////////////////////////////

    public void eliminarPKM() {
        avistamientos.clear();
        String Pokemon = editNombrePKM.getText().toString();
        Query query = databaseReference.child("Pokemon").orderByChild("Pokemon").equalTo(Pokemon);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot objeto : dataSnapshot.getChildren()) {
                objeto.getRef().removeValue();
            }
            Toast.makeText(PerfilUsiarios.this, "Se elimino el Pokemon", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

    limpiarCampos();
}

////////////////////////////////////////////////////////////////////////////////////////////////////
    public void limpiarCampos() {
        editNombrePKM.setText("");
        editAtaque.setText("");
        editVida.setText("");
        editDefensa.setText("");

    }
}
