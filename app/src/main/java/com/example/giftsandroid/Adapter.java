package com.example.giftsandroid;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Integer> giftId = new ArrayList<>();
    private List<String> name = new ArrayList<>();
    private List<String> descroption = new ArrayList<>();
    private List<Integer> price = new ArrayList<>();
    private List<Integer> image = new ArrayList<>();

    private Listener listener;

    public interface Listener {
        void onClick(int giftId);
    }

    public Adapter(List<Integer> giftId, List<String> name, List<String> descroption, List<Integer> price, List<Integer> image) {
        this.giftId = giftId;
        this.name = name;
        this.descroption = descroption;
        this.price = price;
        this.image = image;
    }

    @Override
    public int getItemCount() {
        return giftId.size();
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewTepe) {

        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.gift_card, parent, false);
        return new ViewHolder(cv);
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardView cardView = holder.cardView;

        TextView _name = (TextView) cardView.findViewById(R.id.name);
        _name.setText(name.get(position));

        TextView _description = (TextView) cardView.findViewById(R.id.description);
        _description.setText(descroption.get(position));

        TextView _price = (TextView) cardView.findViewById(R.id.price);
        _price.setText(price.get(position).toString() + " руб.");

        ImageView _image = (ImageView) cardView.findViewById(R.id.image);
        _image.setImageResource(image.get(position));


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(giftId.get(position));
                }
            }
        });
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }

    }
}
