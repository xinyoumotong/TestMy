package hotP2B.WageGainTools.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class AppSafe extends AppTitleBar {

    @Override
    public void setRootView()
    {
        this.setContentView(R.layout.activity_app_safe);
    }

    @Override
    public void initTitleBar() {
        this.mImgBack.setVisibility(View.VISIBLE);
        this.mTvTitle.setText(R.string.my_item_accountSafe);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    protected void onBackClick() {
        super.onBackClick();
        this.finish();
    }
}
