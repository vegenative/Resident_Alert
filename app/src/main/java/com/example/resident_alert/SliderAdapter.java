package com.example.resident_alert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){

        this.context = context;

    }

    public int[] slide_images = {
            R.drawable.human,
            R.drawable.hammer,
            R.drawable.setting
    };

    public String[] slide_header = {
            "Zarzadzej usterkami",
            "Naprawa zostanie zgłoszona w 15 sekund",
            "Sprawdź historie swoich zgłoszeń"
    };

    public String[] slide_description = {
            "Lorem ipsum dolor sit amet tempor posuere, odio. Morbi dignissim, libero et ultrices et, accumsan ullamcorper eleifend ac, felis. Duis neque dui, porta ligula. Vivamus justo. Nulla nec mauris et nisl. Curabitur fringilla aliquet. Aliquam vel wisi.",
            "Lorem ipsum dolor sit amet tempor posuere, odio. Morbi dignissim, libero et ultrices et, accumsan ullamcorper eleifend ac, felis. Duis neque dui, porta ligula. Vivamus justo. Nulla nec mauris et nisl. Curabitur fringilla aliquet. Aliquam vel wisi.",
            "Lorem ipsum dolor sit amet tempor posuere, odio. Morbi dignissim, libero et ultrices et, accumsan ullamcorper eleifend ac, felis. Duis neque dui, porta ligula. Vivamus justo. Nulla nec mauris et nisl. Curabitur fringilla aliquet. Aliquam vel wisi."
    };

    @Override
    public int getCount() {
        return slide_header.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeader = (TextView) view.findViewById(R.id.slide_header);
        //TextView slideDescription = (TextView) view.findViewById(R.id.slide_description);

        slideImageView.setImageResource(slide_images[position]);
        slideHeader.setText(slide_header[position]);
       // slideDescription.setText(slide_description[position]);

        container.addView(view);

        return view;


    };

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}

