package com.example.giftsandroid.Fragments;


import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.giftsandroid.Adapter;
import com.example.giftsandroid.Database.DatabaseHelper;
import com.example.giftsandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LikedGiftsFragment extends Fragment {

    private List<Integer> giftId = new ArrayList<>();
    private List<String> name = new ArrayList<>();
    private List<String> descroption = new ArrayList<>();
    private List<Integer> price = new ArrayList<>();
    private List<Integer> image = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_gifts_list, container, false);
        final DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

        try {

            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor cursor = db.query("LIKED", new String[] {"GIFT_ID"}, null, null, null, null, null, null);

            while (cursor.moveToNext()){
                giftId.add(cursor.getInt(0));
            }

            for (int id: giftId){
                cursor = db.query("GIFTS", new String[] {"NAME", "DESCRIPTION", "PRICE", "IMAGE"}, "_id = ?", new String[] {Integer.toString(id)}, null, null, null, null);

                if (cursor.moveToFirst()){
                    name.add(cursor.getString(0));
                    descroption.add(cursor.getString(1));
                    price.add(cursor.getInt(2));
                    image.add(cursor.getInt(3));
                }
            }
        }
        catch (SQLException e){
            Toast toast = Toast.makeText(getContext(), "Database unavilable", Toast.LENGTH_SHORT);
            toast.show();
        }
        Adapter adapter = new Adapter(giftId, name, descroption, price, image);
        recyclerView.setAdapter(adapter);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter.setListener(new Adapter.Listener() {
            @Override
            public void onClick(int giftId) {
                if (databaseHelper.checkGift(giftId)){
                    databaseHelper.deleteFromLiked(giftId);
                    Toast deleted = Toast.makeText(getContext(), "Подарок удален из понравившихся", Toast.LENGTH_SHORT);
                    deleted.show();
                } else {
                    databaseHelper.addToLiked(giftId);
                    Toast added = Toast.makeText(getContext(), "Подарок добавлен в понравившиеся", Toast.LENGTH_SHORT);
                    added.show();
                }
            }
        });


        return recyclerView;
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.card_view, new LikedGiftsFragment());
    }

    public void showList() {

    }

}
