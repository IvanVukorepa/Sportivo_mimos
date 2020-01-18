package com.example.sportivo.reservation_screen;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.sportivo.R;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.List;

public class ItemAdapter extends ExpandableRecyclerViewAdapter<BaseViewHolder, ItemViewHolder> {
    public ItemAdapter(List<? extends ExpandableGroup> groups){
        super(groups);
    }

    @Override
    public BaseViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rs_baseview, parent,false);
        return new BaseViewHolder(v);
    }

    @Override
    public ItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rs_itemview, parent,false);

        Button btn = v.findViewById(R.id.res_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("blabla", "button clicked");
            }
        });
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("blabla", "wewqwqqfrwqefewq");
            }
        });
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindChildViewHolder(ItemViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {

        final Item item= (Item) group.getItems().get(childIndex);
        holder.bind(item);
    }

    @Override
    public void onBindGroupViewHolder(BaseViewHolder holder, int flatPosition, ExpandableGroup group) {
        final Base base=(Base) group;
        holder.bind(base);

    }
}
