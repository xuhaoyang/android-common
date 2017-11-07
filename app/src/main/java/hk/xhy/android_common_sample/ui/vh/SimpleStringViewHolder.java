package hk.xhy.android_common_sample.ui.vh;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import hk.xhy.android.common.bind.ViewById;
import hk.xhy.android.common.ui.vh.OnListItemClickListener;
import hk.xhy.android.common.ui.vh.ViewHolder;
import hk.xhy.android_common_sample.R;

/**
 * Created by xuhaoyang on 2017/4/16.
 */

public class SimpleStringViewHolder extends ViewHolder {

    public SimpleStringViewHolder(View itemView) {
        super(itemView);
    }

    @ViewById(R.id.cv_item)
    CardView cardView;
    @ViewById(R.id.item_title)
    TextView item_tilte;

    public void bind(String string, final OnListItemClickListener listener){
        if (string!=null){
            item_tilte.setText(string);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        listener.OnListItemClick(getAdapterPosition());
                    }
                }
            });
        }
    }
}
