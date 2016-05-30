package hotP2B.WageGainTools.android;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum HelpItem implements Serializable {

    One(0, 0, "什么是托管", "托管是指作为第三方，依据法律法规和托管合同规定， 代表资产所有人的利益，从事托管资产保管办理托管 资产名下资金清算、进行托管资产会计核算和估值，监 督管理人投资运作，以确保资产委托人利益."),
    Two(1, 0, "为什么要开通托管账户", "汇付天下资金托管账户，能实现投资用户的资金账户与理财平台自身账户的完全独立，实现资金流与信息流分离，保障用户的资金安全，即使平台方，也无法擅自挪动您的资金。"),
    Three(2, 0, "已经托管，账户上金额没有显示", "在首页下拉刷新页面，即可显示金额，或退出APP重新登录。"),
    Four(3, 1, "什么是汇付天下", "汇付天下有限公司成立于2006年7月，在金融支付领域，首家获得中国证监会批准开展网上基金销售支付服务，汇付天下定位于金融级电子支付专家，聚焦金融支付和产业链支付两大方向。"),
    Five(4, 1, "什么是汇付交易密码", "汇付交易密码用于提现、绑卡、投标、充值时的支付密码。"),
    Six(5, 1, "汇付密码忘记怎么找回", "进入绑卡页面，点击“忘记汇付交易密码”登陆汇付天下平台，输入汇付天下短信发送的初始密码登陆，点击“立即找回”进入到找回交易密码页面，重新获取手机验证码后设置交易密码，设置完成确认，密码找回成功。"),
    Seven(6, 2, "注册时，再次获取验证码未收到短信通知", "60秒之内不能重复获取验证码，需要60秒以后重新获取。"),
    Eight(7, 2, "验证码输入错误或已过期", "请输入正确的验证码或者再次获取新的验证码"),
    Nine(8, 2, "已注册并实名认证，账户资产余额显示为0", "实名认证完，需开通汇付天下。"),
    Ten(9, 3, "登陆密码忘记怎么找回", "进入登录页面，点击“忘记密码”，跳到验证手机页面，输入手机号，获取验证码，点击“下一步”页面弹出新密码，密码成功找回。"),
    Eleven(10, 3, "手机登陆密码和交易密码有什么不同", "登陆密码只用于网页版和手机版的登陆，交易密码可用于绑定银行卡、提现、充值、投标。"),
    TWELVE(11, 4, "实名认证完为什么不能绑卡", "绑卡之前需要先开通汇付天下（托管账户）"),
    THIRTWEEN(12, 4, "为什么绑定别人的银行卡失败", "只能绑定自己的银行卡。"),
    FOURTTEN(11, 5, "账户金额提现到银行卡多久可以到账", "正常情况下，提现资金将在下一个工作日内到账。"),
    FIVETEEN(11, 5, "手机APP金额低于100元不能提现", "目前手机端APP提现金额规定100元起，手机网页端提现金额可10元起"),;

    private int id;
    private int type;
    //    private int typePosition;
    private String question;
    private String answer;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    HelpItem(int id, int type, String question, String answer) {
        this.id = id;
        this.type = type;
        this.question = question;
        this.answer = answer;
    }

//    HelpItem(int id, int type, int typePosition, String question, String answer) {
//        this.id = id;
//        this.type = type;
//        this.typePosition = typePosition;
//        this.question = question;
//        this.answer = answer;
//    }

    public static String getAnswerById(int position) {
        for (HelpItem s : values()) {
            if (s.id == position) {
                return s.getAnswer();
            }
        }
        return "";
    }

    public static List<HelpItem> getListByType(int type) {
        ArrayList<HelpItem> helpItems = new ArrayList<>();
        for (HelpItem s : values()) {
            if (s.type == type) {
                helpItems.add(s);
            }
        }
        return helpItems;
    }

    public static List<HelpItem> getListByDiffrentType(int type) {
        ArrayList<HelpItem> helpItems = new ArrayList<>();
        ArrayList<Integer> types = new ArrayList<>();

        for (HelpItem s : values()) {
            if (!types.contains(s.type)) {
                types.add(s.type);
                helpItems.add(s);
            }
        }
        return helpItems;
    }

    public static String getQuestionById(int position) {
        for (HelpItem s : values()) {
            if (s.id == position) {
                return s.getQuestion();
            }
        }
        return "";
    }
}
