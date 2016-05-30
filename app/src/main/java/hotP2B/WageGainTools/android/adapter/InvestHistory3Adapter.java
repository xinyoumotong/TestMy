package hotP2B.WageGainTools.android.adapter;

import java.util.List;

import org.kymjs.kjframe.widget.AdapterHolder;
import org.kymjs.kjframe.widget.KJAdapter;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.bean.InvestHistory3.InvestHistory3Item;
import hotP2B.WageGainTools.android.ui.widget.zrclistview.ZrcListView;

public class InvestHistory3Adapter extends ZrcAdapter<InvestHistory3Item> {

    private FlowBtnClickListener listener;

    public interface FlowBtnClickListener {
        public void onClick(InvestHistory3Item item);
    }

    public InvestHistory3Adapter(ZrcListView view, List<InvestHistory3Item> mDatas, FlowBtnClickListener listener) {
        super(view, mDatas, R.layout.item_invest_history3);
        this.listener = listener;

    }

    @Override
    public void convert(AdapterHolder helper, final InvestHistory3Item item, boolean isScrolling) {
        helper.setText(R.id.item_invest_history3_BidName, item.getBidName());
        helper.setText(R.id.item_invest_history3_CreateTime, item.getCreateTime().substring(0,item.getCreateTime().indexOf(" ")));

        helper.setText(R.id.item_invest_history3_TransAmt, item.getTransAmt());

        TextView tv_btn_flow = (TextView) helper.getView(R.id.item_invest_history3_btn_flow);
        LinearLayout item_invest_history3_ll = (LinearLayout) helper.getView(R.id.item_invest_history3_ll);

        if (item.getState() == 1) {

            helper.setText(R.id.item_invest_history3_tv_flow, "未撤标");
            ((TextView) helper.getView(R.id.item_invest_history3_tv_flow)).setTextColor(0xff999999);
            tv_btn_flow.setVisibility(View.VISIBLE);
            item_invest_history3_ll.setVisibility(View.VISIBLE);

        } else if (item.getState() == 4) {

            helper.setText(R.id.item_invest_history3_tv_flow, "已撤标");
            ((TextView) helper.getView(R.id.item_invest_history3_tv_flow)).setTextColor(0xffe63054);
            tv_btn_flow.setVisibility(View.GONE);
            item_invest_history3_ll.setVisibility(View.GONE);

        } else {
            helper.setText(R.id.item_invest_history3_tv_flow, "--");
            ((TextView) helper.getView(R.id.item_invest_history3_tv_flow)).setTextColor(0xffe63054);
            tv_btn_flow.setVisibility(View.GONE);
            item_invest_history3_ll.setVisibility(View.GONE);

        }

        tv_btn_flow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(item);
                }
            }
        });
    }
}