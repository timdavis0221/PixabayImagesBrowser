package com.pixabay.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pixabay.R;
import com.pixabay.data.Image;
import com.pixabay.net.ImageDownLoader;

import java.util.List;

public class PixabayRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String TAG = getClass().getSimpleName();
    private SparseArray<Image> imageSparseArray;

    private enum ITEMVIEW_TYPE{
        IMAGE,
        FOOTER
    }

    class PixabayViewHolder extends RecyclerView.ViewHolder{

        private TextView pixabayImageId;
        private ImageView pixabayImageView;

        public PixabayViewHolder(@NonNull View itemView) {
            super(itemView);
            pixabayImageId = itemView.findViewById(R.id.textView_imageId);
            pixabayImageView = itemView.findViewById(R.id.itemView_Image1);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder{

        private TextView footerView;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            footerView = itemView.findViewById(R.id.textView_Footer);
        }
    }

    public PixabayRecyclerAdapter(SparseArray<Image> imageSparseArray) {
        this.imageSparseArray = imageSparseArray;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parentView, int viewType) {

        RecyclerView.ViewHolder viewHolder = null;

        if(viewType == ITEMVIEW_TYPE.FOOTER.ordinal()){
            View view = LayoutInflater.from(parentView.getContext())
                    .inflate(R.layout.itemview_footer, parentView, false);
            viewHolder = new FooterViewHolder(view);
        }
        else if(viewType == ITEMVIEW_TYPE.IMAGE.ordinal()){
            View view = LayoutInflater.from(parentView.getContext())
                    .inflate(R.layout.itemview_image, parentView, false);
            viewHolder = new PixabayViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if(viewHolder instanceof PixabayViewHolder){

            PixabayViewHolder pixabayViewHolder = (PixabayViewHolder) viewHolder;

            Log.d(TAG, "onBindViewHolder: item view's Tag: " +
                    pixabayViewHolder.pixabayImageView.getTag() +
                    ", current position: " + position);

            pixabayViewHolder.pixabayImageId.setText(imageSparseArray.get(position).getId());
            pixabayViewHolder.pixabayImageView.setTag(imageSparseArray.get(position).getPreviewURL());

            new ImageDownLoader(pixabayViewHolder.pixabayImageView).
                    execute(imageSparseArray.get(position).getPreviewURL().toString());
        }
        else{
            // custom footer view
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        if(imageSparseArray.size() > 0)
            return imageSparseArray.size() + 1;
        else
            return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == getItemCount() - 1)
            return ITEMVIEW_TYPE.FOOTER.ordinal();
        else
            return ITEMVIEW_TYPE.IMAGE.ordinal();
    }

    @Override
    public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if(holder instanceof PixabayViewHolder){
            Log.d(TAG, "onViewRecycled: position " + holder.getAdapterPosition() + "," +
                    " TAG : " + ((PixabayViewHolder) holder).pixabayImageView.getTag() + " be recycled");
        }
        else
            Log.d(TAG, "onViewRecycled: position " + holder.getAdapterPosition() + " (footer view)");
    }

}
