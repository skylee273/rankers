package com.project.rankers.widget;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.rankers.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<Dictionary> mList;

    public CustomAdapter(ArrayList<Dictionary> mList) {
        this.mList = mList;
    }
    /**
     * CustomAdapter sub class
     * Textview 등의 View를 관리하는 클래스
     */
    public class CustomViewHolder extends RecyclerView.ViewHolder{
        protected TextView id;
        protected TextView english;
        protected TextView korean;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.id = (TextView)itemView.findViewById(R.id.item_contest_widget_number_textview);
            this.english = (TextView)itemView.findViewById(R.id.item_contest_widget_english_textview);
            this.korean = (TextView)itemView.findViewById(R.id.item_contest_widget_korean_textview);
        }
    }


    /**
     * onCreateViewHolder : 만들어놓은 item layout을 여러 개의 view로 활용하기위한 함수
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contest_widget, parent, false );
        CustomViewHolder viewHolder = new CustomViewHolder(view); // 위에서 만든 내 서브클래스. 아이템 내의 뷰를 관리한다.

        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.id.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        holder.english.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        holder.korean.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);


        holder.id.setText(mList.get(position).getId());
        holder.english.setText(mList.get(position).getId());
        holder.korean.setText(mList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
