package be.ehb.valveandroid.view.viewutils;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import android.widget.ImageView;

import java.util.ArrayList;

import be.ehb.valveandroid.R;
import be.ehb.valveandroid.model.Game;
import be.ehb.valveandroid.model.Platform;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder>{

    private ArrayList<Game> gamesList;
    private Context context;
    private OnGameItemClickListener listener;
    private RequestManager glide;

    public interface OnGameItemClickListener {
        void onGameItemClick(Game game);

        void onGameItemClick(Game game, Platform platform);
    }

    public GameAdapter(ArrayList<Game> gamesList, RequestManager glide) {
        this.gameItem = gamesList;
        this.glide = glide;
    }

    public void setItems(ArrayList<Game> games) {
        this.gamesList = games;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_for_game, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = gameItem.get(position);
        holder.tvName.setText(game.getName());

        glide.load(game.getBackground_image())
                .load(game.getBackground_image()) // Provide the image URL here
                .placeholder(R.drawable.img_placeholder) // Placeholder image while loading
                .error(R.drawable.img_error) // Error image if loading fails
                .override(1200) // Image size
                .into(holder.tvImage);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onGameItemClick(game);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gameItem.size();
    }

    class GameViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName;
        final ImageView tvImage;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.game_name_textview);
            tvImage = itemView.findViewById(R.id.game_image);
        }
    }

    private void navigateToGameDetails(int gameId) {
        // Create a bundle to pass gameId to the details fragment
        Bundle bundle = new Bundle();
        bundle.putInt("gameId", gameId);

        // Navigate to the details fragment
        NavHostFragment.findNavController((NavHostFragment) ((FragmentActivity) context).getSupportFragmentManager()
                        .findFragmentById(R.id.action_gamesFragment_to_detailFragment))
                .navigate(R.id.detailFragment, bundle);
    }

    public void setOnGameItemClickListener(OnGameItemClickListener listener) {
        this.listener = listener;
    }


    private ArrayList<Game> gameItem;

    public GameAdapter() {
        this.gameItem = new ArrayList<>();
    }

    public void newItems(ArrayList<Game> newItems) {
        gameItem = newItems;
    }

    public OnGameItemClickListener getListener() {
        return listener;
    }

    public void setListener(OnGameItemClickListener listener) {
        this.listener = listener;
    }
}
