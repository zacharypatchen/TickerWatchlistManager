package com.example.tickerwatchlistmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TickerListAdapter extends RecyclerView.Adapter<TickerListAdapter.TickerViewHolder> {
    private List<String> tickers = new ArrayList<>();


    @NonNull
    @Override
    public TickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticker, parent, false);
        return new TickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TickerViewHolder holder, int position) {
        String ticker = tickers.get(position);
        holder.bind(ticker);
    }

    @Override
    public int getItemCount() {
        return tickers.size();
    }

    public void setTickers(List<String> tickers) {
        this.tickers = tickers;
        notifyDataSetChanged();
    }
    public void addTickers(List<String> tickers){
        for(String t : this.tickers){
            tickers.add(t);
            //notifyDataSetChanged();
        }
        tickers.remove(6);
        this.tickers = tickers;
        notifyDataSetChanged();
    }
    public String getItem(int position) {
        return tickers.get(position);
    }

    class TickerViewHolder extends RecyclerView.ViewHolder {
        private TextView tickerTextView;

        TickerViewHolder(View itemView) {
            super(itemView);
            tickerTextView = itemView.findViewById(R.id.textViewTicker);
        }

        void bind(String ticker) {
            tickerTextView.setText(ticker);
        }
    }
}