package hotP2B.WageGainTools.android.adapter;

import android.widget.AbsListView;

import org.kymjs.kjframe.widget.AdapterHolder;
import org.kymjs.kjframe.widget.KJAdapter;

import java.util.Collection;

import hotP2B.WageGainTools.android.HelpItem;
import hotP2B.WageGainTools.android.R;

/**
 * Created by hll on 2016/5/6.
 */
public class HelpAdapter extends KJAdapter {
    public HelpAdapter(AbsListView view, Collection mDatas) {
        super(view, mDatas, R.layout.item_help);
    }

    @Override
    public void convert(AdapterHolder adapterHolder, Object o, boolean b) {
        int position = adapterHolder.getPosition();
        adapterHolder.setText(R.id.item_help_qustion_tv, (String) o);
    }
}
