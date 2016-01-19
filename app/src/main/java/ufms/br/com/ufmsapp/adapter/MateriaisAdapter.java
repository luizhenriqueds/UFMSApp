package ufms.br.com.ufmsapp.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.Material;

public class MateriaisAdapter extends RecyclerView.Adapter<MateriaisAdapter.MateriaisViewHolder> implements View.OnClickListener {

    Material upload;
    private ArrayList<Material> uploads;
    LayoutInflater inflater;
    View itemView;
    private int lastPosition = -1;
    private OnMaterialClickListener mListener;
    VolleySingleton volleySingleton;
    ImageLoader imageLoader;

    //private DateFormat df = new SimpleDateFormat("dd MMM", new Locale("pt", "br"));


    public MateriaisAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    @Override
    public int getItemCount() {
        return uploads.size();
    }

    public void setMaterialClickListener(OnMaterialClickListener listener) {
        mListener = listener;
    }


    @Override
    public MateriaisViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.fragment_materiais_adapter_row, viewGroup, false);
        MateriaisViewHolder vh = new MateriaisViewHolder(itemView);
        //itemView.setTag(vh);
        vh.rootOpenDocument.setTag(vh);
        vh.btnFileDownload.setTag(vh);

        vh.rootOpenDocument.setOnClickListener(this);
        vh.btnFileDownload.setOnClickListener(this);
        //itemView.setOnClickListener(this);

        return vh;
    }

    public void setUploadList(ArrayList<Material> uploads) {
        this.uploads = uploads;
        notifyItemRangeChanged(0, uploads.size());
    }


    @Override
    public void onBindViewHolder(MateriaisViewHolder materiaisViewHolder, int i) {
        upload = uploads.get(i);


        materiaisViewHolder.uploadName.setText(upload.getPathMaterial().replace("/uploads/", ""));


        setAnimation(materiaisViewHolder.cardRoot, i);

    }

    @Override
    public void onClick(final View view) {
        if (mListener != null) {
            MateriaisViewHolder vh = (MateriaisViewHolder) view.getTag();
            int position = vh.getAdapterPosition();
            mListener.onMaterialClick(view, position, uploads.get(position));
        }

    }

    public interface OnMaterialClickListener {
        void onMaterialClick(View v, int position, Material material);
    }


    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    public static class MateriaisViewHolder extends RecyclerView.ViewHolder {
        protected LinearLayout rootOpenDocument;
        protected CardView cardRoot;
        protected TextView uploadName;
        protected ImageButton btnFileDownload;


        public MateriaisViewHolder(View v) {
            super(v);
            cardRoot = (CardView) v.findViewById(R.id.card_root);
            rootOpenDocument = (LinearLayout) v.findViewById(R.id.root_open_document);
            uploadName = (TextView) v.findViewById(R.id.txt_upload_descricao);
            btnFileDownload = (ImageButton) v.findViewById(R.id.btn_file_download);


        }
    }

}
