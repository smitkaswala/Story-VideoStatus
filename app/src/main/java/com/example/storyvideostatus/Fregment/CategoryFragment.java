package com.example.storyvideostatus.Fregment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.storyvideostatus.CategoryActivitys.AttitudeActivity;
import com.example.storyvideostatus.CategoryActivitys.BirthdayActivity;
import com.example.storyvideostatus.CategoryActivitys.DanceActivity;
import com.example.storyvideostatus.CategoryActivitys.DialogueActivity;
import com.example.storyvideostatus.CategoryActivitys.FestivalActivity;
import com.example.storyvideostatus.CategoryActivitys.FriendshipActivity;
import com.example.storyvideostatus.CategoryActivitys.FunnyActivity;
import com.example.storyvideostatus.CategoryActivitys.GreetingActivity;
import com.example.storyvideostatus.CategoryActivitys.InspirationlActivity;
import com.example.storyvideostatus.CategoryActivitys.LoveActivity;
import com.example.storyvideostatus.CategoryActivitys.NientyActivity;
import com.example.storyvideostatus.CategoryActivitys.RomanticActivity;
import com.example.storyvideostatus.CategoryActivitys.SadActivity;
import com.example.storyvideostatus.CategoryActivitys.TvSerialActivity;
import com.example.storyvideostatus.LanguageActivitys.EnglishActivity;
import com.example.storyvideostatus.LanguageActivitys.GujratiActivity;
import com.example.storyvideostatus.LanguageActivitys.HindiActivity;
import com.example.storyvideostatus.LanguageActivitys.मराठीActivity;
import com.example.storyvideostatus.LanguageActivitys.राजस्थानीActivity;
import com.example.storyvideostatus.LanguageActivitys.हरयाणवीActivity;
import com.example.storyvideostatus.LanguageActivitys.বাংলাActivity;
import com.example.storyvideostatus.LanguageActivitys.ਪੰਜਾਬੀActivity;
import com.example.storyvideostatus.LanguageActivitys.தமிழ்Activity;
import com.example.storyvideostatus.R;


public class CategoryFragment extends Fragment {

    TextView text_1,text_2,text_3,text_4,text_5,text_6,text_7,text_8,text_9;
    RelativeLayout category_1,category_2,category_3,category_4,category_5,
            category_6,category_7,category_8,category_9,category_10,category_11,
            category_12,category_13,category_14;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        text_1 = view.findViewById(R.id.text_1); text_2 = view.findViewById(R.id.text_2);
        text_3 = view.findViewById(R.id.text_3); text_4 = view.findViewById(R.id.text_4);
        text_5 = view.findViewById(R.id.text_5); text_6 = view.findViewById(R.id.text_6);
        text_7 = view.findViewById(R.id.text_7); text_8 = view.findViewById(R.id.text_8);
        text_9 = view.findViewById(R.id.text_9);

        category_1 = view.findViewById(R.id.category_1); category_2 = view.findViewById(R.id.category_2);
        category_3 = view.findViewById(R.id.category_3); category_4 = view.findViewById(R.id.category_4);
        category_5 = view.findViewById(R.id.category_5); category_6 = view.findViewById(R.id.category_6);
        category_7 = view.findViewById(R.id.category_7); category_8 = view.findViewById(R.id.category_8);
        category_9 = view.findViewById(R.id.category_9); category_10 = view.findViewById(R.id.category_10);
        category_11 = view.findViewById(R.id.category_11); category_12 = view.findViewById(R.id.category_12);
        category_13 = view.findViewById(R.id.category_13); category_14 = view.findViewById(R.id.category_14);

        text_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HindiActivity.class);
                startActivity(intent);
            }
        }); text_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ਪੰਜਾਬੀActivity.class);
                startActivity(intent);
            }
        });

        text_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EnglishActivity.class);
                startActivity(intent);
            }
        }); text_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GujratiActivity.class);
                startActivity(intent);
            }
        });

        text_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), தமிழ்Activity.class);
                startActivity(intent);
            }
        }); text_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), मराठीActivity.class);
                startActivity(intent);
            }
        });

        text_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), राजस्थानीActivity.class);
                startActivity(intent);
            }
        }); text_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), हरयाणवीActivity.class);
                startActivity(intent);
            }
        });

        text_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), বাংলাActivity.class);
                startActivity(intent);
            }
        });

        category_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoveActivity.class);
                startActivity(intent);
            }
        });

        category_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SadActivity.class);
                startActivity(intent);
            }
        });

        category_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NientyActivity.class);
                startActivity(intent);
            }
        });

        category_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TvSerialActivity.class);
                startActivity(intent);
            }
        });

        category_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AttitudeActivity.class);
                startActivity(intent);
            }
        });

        category_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DanceActivity.class);
                startActivity(intent);
            }
        });

        category_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InspirationlActivity.class);
                startActivity(intent);
            }
        });

        category_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RomanticActivity.class);
                startActivity(intent);
            }
        });

        category_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FriendshipActivity.class);
                startActivity(intent);
            }
        });

        category_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FestivalActivity.class);
                startActivity(intent);
            }
        });

        category_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GreetingActivity.class);
                startActivity(intent);
            }
        });

        category_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DialogueActivity.class);
                startActivity(intent);
            }
        });

        category_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FunnyActivity.class);
                startActivity(intent);
            }
        });

        category_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BirthdayActivity.class);
                startActivity(intent);
            }
        });

        return view;

    }

}