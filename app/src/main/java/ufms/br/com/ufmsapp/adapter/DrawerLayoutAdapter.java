package ufms.br.com.ufmsapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.pojo.DrawerItem;


public class DrawerLayoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    Context mContext;
    private LayoutInflater inflater;
    private List<DrawerItem> data = Collections.emptyList();
    //OnDrawerClickListener listener;

    public DrawerLayoutAdapter(Context context, List<DrawerItem> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.mContext = context;
    }

    //public void setClickListener(OnDrawerClickListener listener) {
    //this.listener = listener;
    //}

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_HEADER) {
            View view = inflater.inflate(R.layout.drawer_header, parent, false);
            return new HeaderHolder(view);
        } else {
            View view = inflater.inflate(R.layout.navigation_drawer_adapter_row, parent, false);
            return new ItemHolder(view);
        }


    }

   /* public void deleteFromList(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }*/

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof HeaderHolder) {

            //HeaderHolder headerHolder = (HeaderHolder) holder;

        } else {

            ItemHolder itemHolder = (ItemHolder) holder;
            DrawerItem drawerItem = data.get(position - 1);

            itemHolder.drawerText.setText(drawerItem.getItemTitle());
            itemHolder.drawerIcon.setImageResource(drawerItem.getIconRes());
        }


    }

    @Override
    public int getItemCount() {

        return data.size() + 1;
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        ImageView drawerIcon;
        TextView drawerText;

        public ItemHolder(View itemView) {
            super(itemView);

            //itemView.setOnClickListener(this);

            drawerIcon = (ImageView) itemView.findViewById(R.id.drawer_icon);
            drawerText = (TextView) itemView.findViewById(R.id.drawer_text);

            //drawerIcon.setOnClickListener(this);
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {


        public HeaderHolder(View itemView) {
            super(itemView);

        }

    }

}
