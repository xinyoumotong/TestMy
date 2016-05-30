package hotP2B.WageGainTools.android.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import hotP2B.WageGainTools.android.R;
import android.view.View;

public class SafetyKeyBoard extends LinearLayout implements View.OnClickListener 
{
	private int type=-1;
	private EditText e;
	private Button keyboard_other;
	private ImageButton keyboard_delete;
	
	private Context context;
	private FinishListener finishListener;
	

	public SafetyKeyBoard(Context context) {
		super(context);
		this.context=context;
		init();
	}

	public SafetyKeyBoard(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		init();
	}

	public SafetyKeyBoard(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context=context;
		init();
	}
	


	private void init() 
	{
		((LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.keyboard,this);
		Button button1 = (Button)findViewById(R.id.keyboard_0);
	    Button button2 = (Button)findViewById(R.id.keyboard_1);
	    Button button3 = (Button)findViewById(R.id.keyboard_2);
	    Button button4 = (Button)findViewById(R.id.keyboard_3);
	    Button button5 = (Button)findViewById(R.id.keyboard_4);
	    Button button6 = (Button)findViewById(R.id.keyboard_5);
	    Button button7 = (Button)findViewById(R.id.keyboard_6);
	    Button button8 = (Button)findViewById(R.id.keyboard_7);
	    Button button9 = (Button)findViewById(R.id.keyboard_8);
	    Button button10 = (Button)findViewById(R.id.keyboard_9);
	    this.keyboard_other=(Button)findViewById(R.id.keyboard_other);
	    this.keyboard_delete=(ImageButton)findViewById(R.id.keyboard_del);
	    Button key_board_ok= (Button)findViewById(R.id.keyboard_ok);
	    
	    button1.setOnClickListener(this);
	    button2.setOnClickListener(this);
	    button3.setOnClickListener(this);
	    button4.setOnClickListener(this);
	    button5.setOnClickListener(this);
	    button6.setOnClickListener(this);
	    button7.setOnClickListener(this);
	    button8.setOnClickListener(this);
	    button9.setOnClickListener(this);
	    button10.setOnClickListener(this);
	    this.keyboard_other.setOnClickListener(this);
	    this.keyboard_delete.setOnClickListener(this);
	    key_board_ok.setOnClickListener(this);
	   
	    this.type=1;
	    
	}
	
    public String getText()
    {
      return this.e.getText().toString().trim();
    }

	@Override
	public void onClick(View v) 
	{
		if((this.e == null) || (this.type == -1))
		{
			return;
		}
	    Editable editable=this.e.getText();
        int k = this.e.getSelectionStart();
        String str = editable.toString();
        switch(v.getId())
        {
	        case R.id.keyboard_0:
	        	editable.insert(k,"0");
	        	break;
	        case R.id.keyboard_1:
	        	editable.insert(k,"1");
	        	break;
	        case R.id.keyboard_2:
	        	editable.insert(k,"2");
	        	break;
	        case R.id.keyboard_3:
	        	editable.insert(k,"3");
	        	break;
	        case R.id.keyboard_4:
	        	editable.insert(k,"4");
	        	break;
	        case R.id.keyboard_5:
	        	editable.insert(k,"5");
	        	break;
	        case R.id.keyboard_6:
	        	editable.insert(k,"6");
	        	break;
	        case R.id.keyboard_7:
	        	editable.insert(k,"7");
	        	break;
	        case R.id.keyboard_8:
	        	editable.insert(k,"8");
	        	break;
	        case R.id.keyboard_9:
	        	editable.insert(k,"9");
	        	break;
	        case R.id.keyboard_other:
	        {
	           if(this.type == 1)
	           {
	              editable.insert(k, ".");
	              return;
	           }
	        }
	        break; 	
	        case R.id.keyboard_del:
	        {
	        	if((TextUtils.isEmpty(str)) || (this.e.length() <= 0) || (k <= 0)) return;
	        	editable.delete(k-1, k);
	        }
	        break;
	        case R.id.keyboard_ok:
	        {
	        	if(this.finishListener!=null)
	        	{
	        		finishListener.onFinish();
	        	}
	        }
	        break;
	        	
        }
		
	}
	
	public void setEdit(EditText editText, int type)
	{
	    this.e = editText;
	    this.type = type;
	    
	    if (type == 1)
	    {
	      this.keyboard_other.setText(".");
	      this.keyboard_other.setBackgroundResource(R.drawable.keyboard_number);
	    }
	    else if (type == 2)
	    {
	        this.keyboard_other.setText("");
	        this.keyboard_other.setBackgroundColor(Color.parseColor("#e1e2e6"));
	    }

	}

	public void setFinishListener(FinishListener listener)
	{
	    this.finishListener = listener;
	}


    public static abstract interface FinishListener
	{
	  public abstract void onFinish();
	}

}
