package com.example.mytv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapter  extends SliderViewAdapter<SliderAdapter.SliderViewHolder> {
    public SliderAdapter(int[] images) {
        this.images = images;
    }

    int[] images;
    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item,null);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
    viewHolder.imageview.setImageResource(images[position]);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    public class SliderViewHolder extends SliderViewAdapter.ViewHolder {
        ImageView imageview;
        public SliderViewHolder(View itemView) {
            super(itemView);
            imageview=itemView.findViewById(R.id.slider_image);
        }
    }
}
