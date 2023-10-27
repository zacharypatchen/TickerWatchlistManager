package com.example.tickerwatchlistmanager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class TickerListFragment extends Fragment {
    private RecyclerView recyclerView;
    private TickerListAdapter adapter;
    public TickerListFragment(){
        adapter = new TickerListAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticker_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewTickerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            public void onItemClick(View view, int position) {
                String selectedTicker = adapter.getItem(position);
                ((MainActivity) getActivity()).loadInfoWebFragment(selectedTicker);
            }
        }));
        return view;
    };
    public void updateTickers(List<String> tickers) {
        if(adapter.getItemCount()<=6 && adapter.getItemCount()>0) {
            adapter.addTickers(tickers);
        }
    }
    public void initiateTickers(List<String> tickers){
        adapter.setTickers(tickers);
    }
}