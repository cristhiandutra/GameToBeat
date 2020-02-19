package com.cristhian.gametobeat.ui.list;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cristhian.gametobeat.R;
import com.cristhian.gametobeat.database.Game;
import com.cristhian.gametobeat.ui.detail.GameDetailActivity;
import com.cristhian.gametobeat.util.IgdbUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GameCardAdapter extends RecyclerView.Adapter<GameCardAdapter.GameListHolder> {

    private Context mContext;
    private List<Game> mGames;

    public GameCardAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public GameListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_game_card, viewGroup, false);

        return new GameListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameListHolder holder, int position) {

        final Game game = mGames.get(position);

        if (game.getCover() != null) {
            Glide.with(mContext).load(IgdbUtil.getUrlImage(game.getCover().getImageId())).into(holder.mCover);
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

        @BindView(R.id.iv_cover)
        ImageView mCover;

        @Nullable
        @BindView(R.id.cv_game)
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
