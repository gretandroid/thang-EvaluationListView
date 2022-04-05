package com.example.evaluationlistview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ListActivity {

    static int ON_TAG_CODE = 1;

    String[] evaluations = {
            "No Evalution",
            "Je suis déçu",
            "J'aime un peu",
            "J'aime",
            "J'aime bien",
            "J'adore",

    };

    String[] products = {
            "Lait",
            "Tele",
            "Orange",
            "Banana",
            "Ananas",
            "Cougette",
            "Lait",
            "Tele",
            "Orange",
            "Banana",
            "Ananas",
            "Cougette"

    };
     int[] starIds = {
             R.id.star1Image,
             R.id.star2Image,
             R.id.star3Image,
             R.id.star4Image,
             R.id.star5Image,
     };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setListAdapter(new CustomAdapter(this, R.layout.row, R.id.productText, products));
    }


    class CustomAdapter extends ArrayAdapter<String> {

        public CustomAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull String[] objects) {
            super(context, resource, textViewResourceId, objects);
        }

        // avoid cycle rotate on list when many items depasse screen
        @Override
        public int getItemViewType(int position) {
            return position;
        }

        // avoid cycle rotate on list when many items depasse screen
        @Override
        public int getViewTypeCount() {
            return getCount();
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            // avoid cycle rotate on list when many items depasse screen
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.row, parent, false);
            }

            final View lineView = super.getView(position, convertView, parent);

            // register traitement on onClick event of each line
            for (int starId : starIds) {
                ImageView starImage = lineView.findViewById(starId);
                starImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // toggle tag of clicked image
                        ImageView starImage = (ImageView) view;
                        if(starImage.getTag(starImage.getId()) == Boolean.TRUE) {
                            starImage.setTag(starImage.getId(), Boolean.FALSE);
                        }
                        else {
                            starImage.setTag(starImage.getId(), Boolean.TRUE);
                        }

                        // counting TRUE tag
                        int numberStar = 0;
                        for (int starId : starIds) {
                            ImageView star = lineView.findViewById(starId);
                            if (star.getTag(starId) == Boolean.TRUE) {
                                numberStar++;
                            }
                        }

                        // set star on each image view for the first numberStar image view
                        int counter = 0;
                        for (int starId : starIds) {
                            ImageView star = lineView.findViewById(starId);
                            if (counter < numberStar) {
                                star.setImageResource(android.R.drawable.star_on);
                                star.setTag(starId, Boolean.TRUE);
                            }
                            else {
                                star.setImageResource(android.R.drawable.star_off);
                                star.setTag(starId, Boolean.FALSE);
                            }
                            counter++;
                        }


                        // update text
                        TextView evaluationText = lineView.findViewById(R.id.evaluationText);
                        evaluationText.setText(evaluations[numberStar]);

                    }
                });
            }

            return lineView;
        }
    }


}