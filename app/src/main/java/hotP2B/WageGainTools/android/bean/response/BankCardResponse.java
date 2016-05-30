package hotP2B.WageGainTools.android.bean.response;

import java.util.List;

import hotP2B.WageGainTools.android.bean.BaseResponse;

public class BankCardResponse extends BaseResponse {
    private static final long serialVersionUID = 1L;

    public static class BankCardItem {
        public String OpenAcctId;
        public String OpenBankId;
        public String IsDefault = "";  //是否是默认卡  Y=>是 N=>否

    }

    private List<BankCardItem> data;
    private String ExpressFlag = "";  //是否有绑定快捷 Y=>是 N=>否

    public String getExpressFlag() {
        return ExpressFlag;
    }

    public void setExpressFlag(String expressFlag) {
        ExpressFlag = expressFlag;
    }

    public List<BankCardItem> getData() {
        return data;
    }

    public void setData(List<BankCardItem> data) {
        this.data = data;
    }

}
