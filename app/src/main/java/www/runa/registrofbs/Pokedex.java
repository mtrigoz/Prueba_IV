package www.runa.registrofbs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Pokedex extends RecyclerView.Adapter<Pokedex.UsuarioViewHolder>{
    Context context;
    List<FichaPokemon> avistamientos;

    public Pokedex(Context context, List<FichaPokemon> avistamientos) {
        this.context = context;
        this.avistamientos = avistamientos;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemones, null, false);
        return new Pokedex.UsuarioViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        holder.NombrePKM.setText(avistamientos.get(position).getNombrePKM());
        holder.Ataque.setText(avistamientos.get(position).getAtaque());
        holder.Vida.setText(avistamientos.get(position).getVida());
        holder.Defensa.setText(avistamientos.get(position).getDefensa());
    }

    @Override
    public int getItemCount() {
        return avistamientos.size();
    }

    public class UsuarioViewHolder extends RecyclerView.ViewHolder {

        TextView NombrePKM, Ataque, Vida, Defensa;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);

            NombrePKM = itemView.findViewById(R.id.NombrePKM);
            Ataque = itemView.findViewById(R.id.Ataque);
            Vida = itemView.findViewById(R.id.Vida);
            Defensa = itemView.findViewById(R.id.Defensa);
        }
    }
}