package site.kobatomo.akushukai.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import site.kobatomo.akushukai.R;

/**
 * Created by tomoya on 2018/05/01.
 */

public class ZenkokuAdapter extends RecyclerView.Adapter<ZenkokuAdapter.ViewHolder>  {
    private List<String> dataArray;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public TextView member_zenkoku;

        public ViewHolder(View v) {
            super(v);
            member_zenkoku = (TextView)v.findViewById(R.id.member_zenkoku);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ZenkokuAdapter(List<String> dataset) {
        dataArray = dataset;
        Log.d("感とか","さんとか");
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_zenkoku_event, parent, false);

        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.member_zenkoku.setText(dataArray.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataArray.size();
    }
}




