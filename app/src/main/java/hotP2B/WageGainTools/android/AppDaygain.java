package hotP2B.WageGainTools.android;

import java.util.ArrayList;
import org.kymjs.kjframe.ui.BindView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.graphics.Color;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

public class AppDaygain extends AppTitleBar {

	@BindView(id=R.id.day_gain_tv_lastday_income)
	private TextView m_day_gain_tv_lastday_income;
	
	@BindView(id=R.id.day_gain_tv_all_income)
	private TextView m_day_gain_tv_all_income;
	
	@BindView(id=R.id.day_gain_linechart)
    private LineChart m_day_gain_linechart;
	
	@BindView(id=R.id.day_gain_barchart)
	private BarChart m_day_gain_barchart;
	
	@BindView(id=R.id.day_gain_rb_bar1,click=true)
	private RadioButton m_day_gain_rb_bar1;
	@BindView(id=R.id.day_gain_rb_bar2,click=true)
	private RadioButton m_day_gain_rb_bar2;
	
	@Override
	public void setRootView() 
	{
       this.setContentView(R.layout.aty_day_gain);
	}
	
	@Override
    public void initData() 
    {
        super.initData();
    }

    @Override
    public void initWidget() 
    {
        super.initWidget();  
        initChart();
    
        refresh(1);
    }
    
    @Override
    public void widgetClick(View v) 
    {
        super.widgetClick(v);
        
        switch (v.getId()) 
        {
        case R.id.day_gain_rb_bar1:
        	refresh(1);
        	break;
        case R.id.day_gain_rb_bar2:
        	refresh(2);
        default:
            break;
        }
    }
    
    private void initChart()
    {
    	
       //linechart
	   m_day_gain_linechart.setDescription("");
       m_day_gain_linechart.setDrawGridBackground(true);
       m_day_gain_linechart.setScaleEnabled(false);
  
       XAxis xAxis = m_day_gain_linechart.getXAxis();
       xAxis.setPosition(XAxisPosition.BOTTOM);
       xAxis.setDrawGridLines(true);
       xAxis.setDrawAxisLine(true);
       xAxis.setLabelsToSkip(0);
       xAxis.setAxisLineColor(Color.BLACK);

       
       YAxis leftAxis =m_day_gain_linechart.getAxisLeft();
       leftAxis.setDrawGridLines(false);
       
       YAxis rightAxis =m_day_gain_linechart.getAxisRight();
       rightAxis.setDrawLabels(false);;  
       
      
       //barchart
       m_day_gain_barchart.setDescription("");
       m_day_gain_barchart.setDrawGridBackground(false);
       m_day_gain_barchart.setScaleEnabled(false);

       XAxis xAxis2 = m_day_gain_barchart.getXAxis();
       xAxis2.setPosition(XAxisPosition.BOTTOM);
       xAxis2.setDrawGridLines(false);
       xAxis2.setDrawAxisLine(true);
       xAxis2.setLabelsToSkip(0);
       xAxis2.setAxisLineColor(Color.BLACK);
       
       YAxis leftAxis2 = m_day_gain_barchart.getAxisLeft();
       leftAxis2.setDrawGridLines(false);
      
       YAxis rightAxis2 =m_day_gain_barchart.getAxisRight();
       rightAxis2.setDrawLabels(false);
    }
    
    private LineData generateDataLine(int cnt) 
    {

        ArrayList<Entry> e1 = new ArrayList<Entry>();

        for (int i = 0; i < cnt; i++) {
            e1.add(new Entry((float) (Math.random()*2+2.525f), i));
        }
        LineDataSet d1 = new LineDataSet(e1, "七日年化收益率(%)");
        d1.setLineWidth(2.5f);
        d1.setCircleSize(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(true);
        d1.setValueTextSize(10f);
        d1.setColor(Color.RED);
        d1.setCircleColor(Color.RED);
   
        ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();
        sets.add(d1);

        
       
        LineData cd = new LineData(getMonths(), sets);
        return cd;
    }
    
    private BarData generateDataBar(int cnt)
    {
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < cnt; i++) {
            entries.add(new BarEntry((float) Math.random(), i));
        }

        BarDataSet d = new BarDataSet(entries, "万份收益(元)");
        d.setBarSpacePercent(20f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setHighLightAlpha(255);
        d.setValueTextSize(10f);
        
        BarData cd = new BarData(getMonths(), d);
        return cd;
    }
    
    
    private ArrayList<String> getMonths() {

        ArrayList<String> m = new ArrayList<String>();
        m.add("12-01");
        m.add("12-02");
        m.add("12-03");
        m.add("12-04");
        m.add("12-05");
        m.add("12-06");
        m.add("12-07");
        return m;
    }
    private void refresh(int type) 
    {
    	if(type==1)
    	{
    	  this.m_day_gain_linechart.setData(generateDataLine(7));
    	  this.m_day_gain_linechart.setVisibility(View.VISIBLE);
    	  this.m_day_gain_barchart.setVisibility(View.GONE);
    	  this.m_day_gain_linechart.animateX(750);
    	}
    	else if(type==2)
    	{
    	  this.m_day_gain_barchart.setData(generateDataBar(7));
    	  this.m_day_gain_linechart.setVisibility(View.GONE);
    	  this.m_day_gain_barchart.setVisibility(View.VISIBLE);
    	  this.m_day_gain_barchart.animateX(750);
    	}
	}

	@Override
	public void initTitleBar()
	{
		this.mImgBack.setVisibility(View.VISIBLE);
		this.mTvTitle.setText("日日薪");
	}
    
    @Override
    protected void onResume() 
    {
    	super.onResume();

    }
    
    @Override
	public void onBackClick() 
	{
	    super.onBackClick();
		this.finish();	
	}

}
