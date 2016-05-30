package hotP2B.WageGainTools.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.kymjs.kjframe.widget.AdapterHolder;
import org.kymjs.kjframe.widget.KJAdapter;

import java.util.Collection;
import java.util.List;

import hotP2B.WageGainTools.android.HelpItem;
import hotP2B.WageGainTools.android.R;

/**
 * Created by hll on 2016/5/12.
 */
public class HelpDetailsAdapter extends BaseAdapter {

    private Context context;
    private List<HelpItem> datas;
    private ViewHolder viewHolder = null;
    private HelpDetailItemClickListener helpDetailItemClickListener;

    public interface HelpDetailItemClickListener{
        void helpDetailItemClick(View parent,View view,int position);
    }

    public void setHelpDetailItemClickListener(HelpDetailItemClickListener helpDetailItemClickListener) {
        this.helpDetailItemClickListener = helpDetailItemClickListener;
    }

    public HelpDetailsAdapter(Context context, List<HelpItem> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_help_type_details, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final HelpItem helpItem = datas.get(position);
        viewHolder.item_help_type_question_tv.setText(helpItem.getQuestion());
        viewHolder.item_help_type_answer_tv.setText(helpItem.getAnswer());
        viewHolder.item_help_type_guid_fl.setTag(convertView);
        viewHolder.item_help_type_guid_fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View tag = (View) v.getTag();
                helpDetailItemClickListener.helpDetailItemClick(tag,v,position);
            }
        });
        return convertView;
    }

    class ViewHolder {
        private TextView item_help_type_question_tv;
        private TextView item_help_type_answer_tv;
        private ImageView item_help_type_guid_iv;
        private LinearLayout item_help_type_answer_ll;
        private RelativeLayout item_help_type_guid_fl;

        public ViewHolder(View view) {
            item_help_type_question_tv = (TextView) view.findViewById(R.id.item_help_type_question_tv);
            item_help_type_answer_tv = (TextView) view.findViewById(R.id.item_help_type_answer_tv);
            item_help_type_guid_iv = (ImageView) view.findViewById(R.id.item_help_type_guid_iv);
            item_help_type_answer_ll = (LinearLayout) view.findViewById(R.id.item_help_type_answer_ll);
            item_help_type_guid_fl = (RelativeLayout) view.findViewById(R.id.item_help_type_guid_fl);
        }
    }
}
