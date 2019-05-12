package com.example.giftsandroid.Fragments;


import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.giftsandroid.Adapter;
import com.example.giftsandroid.Database.DatabaseHelper;
import com.example.giftsandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GiftsListFragment extends Fragment {

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

            SQLiteDatabase db  = databaseHelper.getReadableDatabase();
            Cursor cursor = db.query("GIFTS", new String[] {"_id", "NAME", "DESCRIPTION", "PRICE", "IMAGE"}, null,null,null,null,null, null);

            while (cursor.moveToNext()) {
                giftId.add(cursor.getInt(0));
                name.add(cursor.getString(1));
                descroption.add(cursor.getString(2));
                price.add(cursor.getInt(3));
                image.add(cursor.getInt(4));

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

}
