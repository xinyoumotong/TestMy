package hotP2B.WageGainTools.android.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kymjs.kjframe.utils.StringUtils;
import org.kymjs.kjframe.widget.AdapterHolder;
import org.kymjs.kjframe.widget.KJAdapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.bean.Salary.SalaryItem;
import hotP2B.WageGainTools.android.utils.CommonUtils;

public class SalaryAdapter extends BaseAdapter implements View.OnClickListener {

    private SalaryItemClickListener salaryItemClickListener;

    private Context context;
    private List<SalaryItem> datas;

    public SalaryAdapter(Context context, List<SalaryItem> datas) {
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
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;


        if (convertView == null) {

            convertView = View.inflate(context, R.layout.item_salary, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) convertView.getTag();

        SalaryItem item = datas.get(position);

        if (!StringUtils.isEmpty(item.getWagestime())) {
            Date d = StringUtils.toDate(item.getWagestime(), new SimpleDateFormat("yyyy-MM-dd"));
            if (d != null) {
                viewHolder.item_salary_tv_month.setText((d.getMonth() + 1) + "");
                viewHolder.item_salary_tv_year.setText((d.getYear() + 1900) + "");
            }
        } else {
            viewHolder.item_salary_tv_month.setText("");
            viewHolder.item_salary_tv_year.setText("");
        }

        if (!StringUtils.isEmail(item.getCreditedwages())) {
            String creditedwages = item.getCreditedwages();
            creditedwages = CommonUtils.keep2Decimal(creditedwages);

            int indexOf = creditedwages.indexOf(".");

            String integerNum = creditedwages.substring(0, indexOf);
            String decimalsNum = creditedwages.substring(indexOf);

            viewHolder.item_salary_tv_salary_integer.setText(integerNum);
            viewHolder.item_salary_tv_salary_decimals.setText(decimalsNum);

        } else {
            viewHolder.item_salary_tv_salary_integer.setText("0.00");
        }
        if (!StringUtils.isEmpty(item.getPaidwages())) {
            String paidwages = CommonUtils.keep2Decimal(item.getPaidwages());
            viewHolder.item_salary_tv_salary_raw.setText(paidwages);
        } else {
            viewHolder.item_salary_tv_salary_raw.setText("0.00");
        }
        if (!StringUtils.isEmpty(item.getPluseages())) {
            String pluseages = CommonUtils.keep2Decimal(item.getPluseages());
            viewHolder.item_salary_tv_salary_add.setText("+" + pluseages);
        } else {
            viewHolder.item_salary_tv_salary_add.setText("+0.00");
        }
        if (!StringUtils.isEmpty(item.getDebit())) {
            String debit = CommonUtils.keep2Decimal(item.getDebit());
            viewHolder.item_salary_tv_salary_subtract.setText("-" + debit);
        } else {
            viewHolder.item_salary_tv_salary_subtract.setText("-0.00");
        }

        if (position == 0) {
            viewHolder.item_salary_empty_flag.setVisibility(View.GONE);
        } else {
            viewHolder.item_salary_empty_flag.setVisibility(View.VISIBLE);
        }

        viewHolder.item_go_salary_details_rl.setTag(position);
        viewHolder.item_go_salary_details_rl.setOnClickListener(this);
        Log.e("", "-------------position----" + position);

        return convertView;

    }

    class ViewHolder {
        private TextView item_salary_tv_month; //月
        private TextView item_salary_empty_flag; //空
        private TextView item_salary_tv_year;  //年
        private TextView item_salary_tv_salary_raw;  //应发合计
        private TextView item_salary_tv_salary_add;  //奖励合计
        private TextView item_salary_tv_salary_subtract;  //扣款合计
        private TextView item_salary_tv_salary_integer;   //总 整数
        private TextView item_salary_tv_salary_decimals;  //总 小数

        private RelativeLayout item_go_salary_details_rl;  //点击跳转条目

        public ViewHolder(View view) {
            item_salary_tv_month = (TextView) view.findViewById(R.id.item_salary_tv_month);
            item_salary_empty_flag = (TextView) view.findViewById(R.id.item_salary_empty_flag);
            item_salary_tv_year = (TextView) view.findViewById(R.id.item_salary_tv_year);
            item_salary_tv_salary_raw = (TextView) view.findViewById(R.id.item_salary_tv_salary_raw);
            item_salary_tv_salary_add = (TextView) view.findViewById(R.id.item_salary_tv_salary_add);
            item_salary_tv_salary_subtract = (TextView) view.findViewById(R.id.item_salary_tv_salary_subtract);
            item_salary_tv_salary_integer = (TextView) view.findViewById(R.id.item_salary_tv_salary_integer);
            item_salary_tv_salary_decimals = (TextView) view.findViewById(R.id.item_salary_tv_salary_decimals);

            item_go_salary_details_rl = (RelativeLayout) view.findViewById(R.id.item_go_salary_details_rl);
        }
    }


    public interface SalaryItemClickListener {
        void salaryItemClcik(View v, int position);
    }

    public void setSalaryItemClickListener(SalaryItemClickListener itemClickListener) {
        this.salaryItemClickListener = itemClickListener;
    }

    public void refresh(List<SalaryItem> list) {
        if (list == null) {
            list = new ArrayList<>();
        }

        this.datas = list;
        this.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        Log.e("", "-------------current----" + tag);
        salaryItemClickListener.salaryItemClcik(v, tag);
    }
}