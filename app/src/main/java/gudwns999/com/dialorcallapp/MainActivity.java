package gudwns999.com.dialorcallapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends Activity {
    private ListView aListView = null;
    private ListViewAdapter aAdapter = null;

    EditText writeNum;
    Button callBtn;
    String Num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        aListView = (ListView)findViewById(R.id.lv);
        aAdapter = new ListViewAdapter(this);
        aListView.setAdapter(aAdapter);
        writeNum = (EditText)findViewById(R.id.writeNum);
        callBtn = (Button)findViewById(R.id.button);

        Num = writeNum.getText().toString();
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+writeNum.getText().toString()));
                startActivity(intent);
            }
        });
        //sample
        aAdapter.addItem(getResources().getDrawable(R.drawable.sample), "형준반점", "010-4052-6503", "평점: 10");
        aAdapter.addItem(getResources().getDrawable(R.drawable.s1), "청록원", "02-914-0087", "평점: 9");
        aAdapter.addItem(getResources().getDrawable(R.drawable.s2), "삼성원", "02-911-5021", "평점: 8");
        aAdapter.addItem(getResources().getDrawable(R.drawable.s3), "보성각", "02-913-4050", "평점: 7");


        //List내용이 클릭되면 이벤트 -> 댓글확인
        aListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ListData mData = aAdapter.mListData.get(position);
            }
        });

    }
    private class ViewHolder {
        public ImageView mIcon;
        public TextView storeName;
        public TextView storeDial;
        public TextView star;
        public RatingBar rating;
        public Button button;
    }
    private class ListViewAdapter extends BaseAdapter {
        private Context mContext = null;
        private ArrayList<ListData> mListData = new ArrayList<ListData>();

        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }
        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void addItem(Drawable icon, String SN, String SD, String ST){
            ListData addInfo = null;
            addInfo = new ListData();
            addInfo.mIcon = icon;
            addInfo.storeName = SN;
            addInfo.storeDial = SD;
            addInfo.star = ST;
            mListData.add(addInfo);
        }

        public void remove(int position){
            mListData.remove(position);
            dataChange();
        }
        public void dataChange(){
            this.notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //에러나면 final 뺄 것.
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listview, null);

                holder.mIcon = (ImageView) convertView.findViewById(R.id.imageView);
                holder.storeName = (TextView) convertView.findViewById(R.id.storeText);
                holder.storeDial = (TextView) convertView.findViewById(R.id.storeDial);
                holder.star = (TextView) convertView.findViewById(R.id.tv01);
                holder.rating = (RatingBar) convertView.findViewById(R.id.ratingBar1);
                holder.button = (Button)convertView.findViewById(R.id.button3);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            final ListData mData = mListData.get(position);

            if (mData.mIcon != null) {
                holder.mIcon.setVisibility(View.VISIBLE);
                holder.mIcon.setImageDrawable(mData.mIcon);
            }else{
                holder.mIcon.setVisibility(View.GONE);
            }

            holder.storeName.setText(mData.storeName);
            holder.storeDial.setText(mData.storeDial);
            holder.star.setText(mData.star);

            holder.rating.setStepSize((float) 0.5);        //별 색깔이 1칸씩줄어들고 늘어남 0.5로하면 반칸씩 들어감
            holder.rating.setRating((float) 2.5);      // 처음보여줄때(색깔이 한개도없음) default 값이 0  이다
            holder.rating.setIsIndicator(false);           //true - 별점만 표시 사용자가 변경 불가 , false - 사용자가 변경가능

            holder.rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

           @Override
           public void onRatingChanged(RatingBar ratingBar, float rating,
                                       boolean fromUser) {
            holder.star.setText("평점 : " + rating);
           }
        });
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+mData.storeDial.toString()));
                    startActivity(intent);
                }
            });

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, More.class);
                    startActivity(intent);
                }
            });
            return convertView;

        }

    }
}
