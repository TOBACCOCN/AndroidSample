package com.example.sample.databinding;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ListView;

import com.example.sample.BR;
import com.example.sample.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class FoodActivity extends AppCompatActivity {

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        ListView listView = findViewById(R.id.food);

        List<Food> foods = new ArrayList<>();
        String image = "https://pic1.zhimg.com/80/v2-8e355085242beb2ca2c9988d969ce9bf_qhd.jpg";
        String description = "苹果（Malus pumila Mill.），是落叶乔木，通常树木可高至15米，但栽培树木一般只高3-5米左右。树干呈灰褐色，树皮有一定程度的脱落。苹果树开花期是基于各地气候而定，但一般集中在4-5月份";
        String keywords = "[关键词] 苹果苹果苹果苹果苹果";
        Food food = new Food(image, description, keywords);
        foods.add(food);

        image = "https://pic2.zhimg.com/v2-bb3ea78bb517c3b16ee8d9341cfd3eae.webp";
        description = "香蕉（学名：Musa nana Lour.）是芭蕉科、芭蕉属植物。植株丛生，具匐匍茎，矮型的高3.5米以下，一般高不及2米，高型的高4-5米，假茎均浓绿而带黑斑，被白粉，尤以上部为多。叶片长圆形，长（1.5）2-2.2（2.5）米，宽60-70（85）厘米";
        keywords = "[关键词] 香蕉香蕉香蕉香蕉香蕉";
        Food food1 = new Food(image, description, keywords);
        foods.add(food1);

        image = "https://pica.zhimg.com/v2-739e8df5dcf16703c7722ec771c69fc8_qhd.jpg";
        description = "柑橘（Citrus reticulata Blanco）是芸香科、柑橘属植物。性喜温暖湿润气候，耐寒性较柚、酸橙、甜橙稍强";
        keywords = "[关键词] 柑橘柑橘柑橘柑橘柑橘";
        Food food2 = new Food(image, description, keywords);
        foods.add(food2);
        listView.setAdapter(new MyBaseAdapter(FoodActivity.this, foods, R.layout.food_item, BR.food));
    }

}