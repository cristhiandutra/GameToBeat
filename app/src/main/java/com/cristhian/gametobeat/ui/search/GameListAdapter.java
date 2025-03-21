package com.cristhian.gametobeat.ui.search;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cristhian.gametobeat.R;
import com.cristhian.gametobeat.database.Game;
import com.cristhian.gametobeat.ui.detail.GameDetailActivity;
import com.cristhian.gametobeat.util.IgdbUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameListAdapter extends RecyclerView.Adapter<GameListAdapter.GameListHolder> {

    private Context mContext;
    private List<Game> mGames;

    public GameListAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public GameListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_game_list, viewGroup, false);

        return new GameListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameListHolder holder, int position) {

        final Game game = mGames.get(position);
        holder.mName.setText(game.getName());

        if (game.getCover() != null) {
            Glide.with(mContext).load(IgdbUtil.getUrlImage(game.getCover().getImageId())).into(holder.mCover);
        }

        if (game.getRating() != null) {
            String ratingFormatted = String.format("%.2f", game.getRating());
            holder.mRating.setText(ratingFormatted);
        }

        holder.mGameCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, GameDetailActivity.class);
                intent.putExtra(GameDetailActivity.PARAM_GAME_DETAIL, game);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGames == null ? 0 : mGames.size();
    }

    class GameListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView mName;

        @BindView(R.id.iv_cover)
        ImageView mCover;

        @BindView(R.id.tv_rating)
        TextView mRating;

        @BindView(R.id.cv_game_list)
        CardView mGameCard;

        public GameListHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public void setGames(List<Game> mGames) {
        this.mGames = mGames;
    }
}
