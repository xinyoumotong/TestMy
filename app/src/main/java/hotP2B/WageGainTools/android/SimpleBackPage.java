
package hotP2B.WageGainTools.android;

import hotP2B.WageGainTools.android.ui.fragment.AboutFragment;
import hotP2B.WageGainTools.android.ui.fragment.AccountSafeFragment;
import hotP2B.WageGainTools.android.ui.fragment.AppIntroduceFragment;
import hotP2B.WageGainTools.android.ui.fragment.BankCardFragment;
import hotP2B.WageGainTools.android.ui.fragment.HelpDetailsAnswerFragment;
import hotP2B.WageGainTools.android.ui.fragment.HelpFragment;
import hotP2B.WageGainTools.android.ui.fragment.LoginFragment;
import hotP2B.WageGainTools.android.ui.fragment.NewsFragment;
import hotP2B.WageGainTools.android.ui.fragment.PersonalFragment;
import hotP2B.WageGainTools.android.ui.fragment.PersonalModifyFragment;
import hotP2B.WageGainTools.android.ui.fragment.ResetPassword2Fragment;
import hotP2B.WageGainTools.android.ui.fragment.ResetPasswordFragment;
import hotP2B.WageGainTools.android.ui.fragment.Verification1Fragment;
import hotP2B.WageGainTools.android.ui.fragment.Verification2Fragment;
import hotP2B.WageGainTools.android.ui.fragment.ChangePasswordFragment;


public enum SimpleBackPage {
	
	
	LOGIN(6, LoginFragment.class),
	VERIFY1(7, Verification1Fragment.class),
	VERIFY2(8, Verification2Fragment.class),
	BANKCARD(9, BankCardFragment.class),
    ABOUT(11, AboutFragment.class),

	
	CHANGEPASSWORD(25, ChangePasswordFragment.class),
	RESETPASSWORD(26, ResetPasswordFragment.class),
	RESETPASSWORD2(27, ResetPassword2Fragment.class),
	ACCOUNTSAFE(28,AccountSafeFragment.class),
	PERSONAL(29,PersonalFragment.class),
	
	FIND_NEWS(30, NewsFragment.class),

	HELP(31,HelpFragment.class),
    HELPANSWER(32, HelpDetailsAnswerFragment.class),
    APPINTRODUCE(33, AppIntroduceFragment.class),
    MODIFYPERSONAL(34, PersonalModifyFragment.class);




    private Class<?> clazz;
    private int value;

    private SimpleBackPage(int value, Class<?> cls) {
        this.clazz = cls;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public static Class<?> getPageByValue(int value) {
        for (SimpleBackPage p : values()) {
            if (p.getValue() == value)
                return p.getClazz();
        }
        return null;
    }

}
