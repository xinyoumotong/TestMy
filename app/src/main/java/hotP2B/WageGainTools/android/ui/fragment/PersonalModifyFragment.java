package hotP2B.WageGainTools.android.ui.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.StringUtils;

import hotP2B.WageGainTools.android.AppContext;
import hotP2B.WageGainTools.android.R;
import hotP2B.WageGainTools.android.bean.response.HttpBaseCallBack;
import hotP2B.WageGainTools.android.bean.response.PersonalResponse;
import hotP2B.WageGainTools.android.bean.response.UserpkidResponse;
import hotP2B.WageGainTools.android.dialog.CustomProgressDialog;
import hotP2B.WageGainTools.android.ui.widget.EditTextWithDel;
import hotP2B.WageGainTools.android.ui.widget.EmptyLayout;
import hotP2B.WageGainTools.android.utils.HttpUtils;
import hotP2B.WageGainTools.android.utils.UserUtils;

public class PersonalModifyFragment extends TitleBarFragment {

    @BindView(id = R.id.item_modify_personal_bt_confirm)
    private Button item_modify_personal_bt_confirm;
    //空布局
    @BindView(id = R.id.item_modify_personal_empty_layout)
    private EmptyLayout item_modify_personal_empty_layout;

    //真实姓名
    @BindView(id = R.id.item_modify_person_name_rl)
    private RelativeLayout item_modify_person_name_rl;
    @BindView(id = R.id.item_modify_personal_et_realname)
    private EditText item_modify_personal_et_realname;

    @BindView(id = R.id.item_modify_personal_tv_realname)
    private TextView item_modify_personal_tv_realname;

    //身份证号
    @BindView(id = R.id.item_modify_person_idNumber_rl)
    private RelativeLayout item_modify_person_idNumber_rl;
    @BindView(id = R.id.item_modify_personal_et_idNumber)
    private EditText item_modify_personal_et_idNumber;

    @BindView(id = R.id.item_modify_personal_tv_idNumber_show)
    private TextView item_modify_personal_tv_idNumber_show;

    private PersonalResponse personalResponse = null;


    @Override
    protected View inflaterView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
        View view = View.inflate(this.outsideAty, R.layout.view_modify_personal, null);
        return view;
    }

    @Override
    protected void setActionBarRes(ActionBarRes actionBarRes) {
        actionBarRes.titleId = R.string.realauth_no2;
        actionBarRes.backImageId = R.mipmap.titlebar_back;
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);

        item_modify_personal_bt_confirm.setOnClickListener(this);
        item_modify_person_name_rl.setOnClickListener(this);
        item_modify_person_idNumber_rl.setOnClickListener(this);
        item_modify_personal_empty_layout.setOnLayoutClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                refresh();
            }
        });

        refresh();// 刷新数据
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void widgetClick(View v) {
//        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.item_modify_person_name_rl:
                if (item_modify_personal_tv_realname.getVisibility() == View.VISIBLE) {
                    item_modify_personal_tv_realname.setVisibility(View.GONE);
                    item_modify_personal_et_realname.setVisibility(View.VISIBLE);
                    item_modify_personal_et_realname.setSelection(item_modify_personal_et_realname.length());
                    item_modify_personal_et_realname.requestFocus();

                }
                break;
            case R.id.item_modify_person_idNumber_rl:
                if (item_modify_personal_tv_idNumber_show.getVisibility() == View.VISIBLE) {
                    item_modify_personal_tv_idNumber_show.setVisibility(View.GONE);
                    item_modify_personal_et_idNumber.setVisibility(View.VISIBLE);
                    item_modify_personal_et_idNumber.setText("");
                    item_modify_personal_et_idNumber.setSelection(item_modify_personal_et_idNumber.length());
                    item_modify_personal_et_idNumber.requestFocus();

                }
                break;
            case R.id.item_modify_personal_bt_confirm:
                if (item_modify_personal_tv_idNumber_show.getVisibility() == View.VISIBLE && item_modify_personal_tv_realname.getVisibility() == View.VISIBLE) {
                    ViewInject.toast("信息未更改");
                } else {
                    checkModifyInfo();
                }
                break;
            default:
                break;
        }
    }

    private void checkModifyInfo() {
        if (personalResponse == null) return;
        if (!personalResponse.getAuthentication().equals("1") || !personalResponse.getAuthentcustid().equals("0")) {
            return;
        }
            doModify(item_modify_personal_et_realname.getText().toString().trim(), item_modify_personal_et_idNumber.getText().toString().trim());

    }

    @Override
    public void onBackClick() {
        this.outsideAty.finish();
    }

    @Override
    public void onMenuClick() {
        super.onMenuClick();
    }

    private void doModify(final String realname, final String idNumber) {
        if (StringUtils.isEmpty(realname) || StringUtils.isEmpty(idNumber)) {
            ViewInject.toast("真实姓名或身份证号不能为空");
            return;
        }

        if (realname.equals(personalResponse.getUsertruename()) && idNumber.equals(personalResponse.getUseridnumber())) {
            ViewInject.toast("更改信息与原有信息相同！");
            return;
        }

        if (idNumber.length() != 18) {
            ViewInject.toast("身份证号长度必须为18位");
            return;
        }

        if (idNumber.contains("*")) {
            ViewInject.toast("身份证号不能含有*字符");
            return;
        }


        final CustomProgressDialog dialog = new CustomProgressDialog(this.outsideAty, "正在修改信息,请稍等...", false);
        dialog.show();

        HttpUtils.modifyPersonalFromServer(this.outsideAty, AppContext.m_CurrentAccount.getUserpkid(), realname, idNumber, UserpkidResponse.class, new HttpBaseCallBack<UserpkidResponse>() {

            @Override
            public void onSuccess(UserpkidResponse loginResponse) {
				fillUI(realname,idNumber);
                ViewInject.toast("修改成功");
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                ViewInject.toast("修改个人信息失败,错误代码:" + errorNo + ",错误信息:" + strMsg);
            }

            @Override
            public void onFinish() {
                dialog.dismiss();
            }
        });
    }


    private void refresh() {
        item_modify_personal_empty_layout.setErrorType(EmptyLayout.NETWORK_LOADING);
        HttpUtils.getPersonalFromServer(this.outsideAty, AppContext.m_CurrentAccount.getUserpkid(), PersonalResponse.class, new HttpBaseCallBack<PersonalResponse>() {

            @Override
            public void onSuccess(PersonalResponse response) {
                item_modify_personal_empty_layout.dismiss();
                personalResponse = response;
//                if (personalResponse.getAuthentication().equals("1") && personalResponse.getAuthentcustid().equals("0")) {
//                    outsideAty.mImgMenu.setImageResource(R.mipmap.titlebar_edit);
//                    outsideAty.mImgMenu.setVisibility(View.VISIBLE);
//                } else {
//                    outsideAty.mImgMenu.setVisibility(View.GONE);
//                }
                fillUI(personalResponse.getUsertruename(), personalResponse.getUseridnumber());

            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                ViewInject.toast("获取个人信息失败,错误代码:" + errorNo + ",错误信息:" + strMsg);
                item_modify_personal_empty_layout.setErrorType(EmptyLayout.NETWORK_ERROR);
            }

            @Override
            public void onFinish() {

            }

        });
    }

    private void fillUI(String realname, String idNumber) {
        this.item_modify_personal_tv_realname.setText(realname == null ? "" : realname);
        this.item_modify_personal_et_realname.setText(realname == null ? "" : realname);
        this.item_modify_personal_et_realname.setHint("真实姓名");

        item_modify_personal_et_realname.setVisibility(View.GONE);
        item_modify_personal_tv_realname.setVisibility(View.VISIBLE);

        this.item_modify_personal_tv_idNumber_show.setText(idNumber == null ? "" : (idNumber.substring(0, 3)+"*** **** ****"+idNumber.substring(idNumber.length()-4)));
//        this.item_modify_personal_et_idNumber.setText(idNumber == null ? "" : (idNumber.substring(0, 3)+"*** **** ****"+idNumber.substring(idNumber.length()-4)));
        this.item_modify_personal_et_idNumber.setHint("身份证号");

        item_modify_personal_et_idNumber.setVisibility(View.GONE);
        item_modify_personal_tv_idNumber_show.setVisibility(View.VISIBLE);

//		this.item_modify_personal_tv_idNum.setText(idNumber == null ? "" : (idNumber.substring(0, 3)+"*** **** ****"+idNumber.substring(idNumber.length()-4)));
        UserUtils.updateUserAccount(UserUtils.TYPE_UPDATE_USER_REALNAME, realname);//更新用户信息
    }


}
