package hotP2B.WageGainTools.android;

import hotP2B.WageGainTools.android.ui.fragment.Welcome1Fragment;
import hotP2B.WageGainTools.android.ui.fragment.Welcome2Fragment;
import hotP2B.WageGainTools.android.ui.fragment.Welcome3Fragment;
import hotP2B.WageGainTools.android.ui.fragment.Welcome4Fragment;

public enum MainTab 
{

	WALLET(0, R.string.welcome1, R.drawable.selector_bottombar_1,Welcome1Fragment.class),
	LICAI(1, R.string.welcome2, R.drawable.selector_bottombar_2,Welcome2Fragment.class),
	EXPLORE(2, R.string.welcome3, R.drawable.selector_bottombar_3,Welcome3Fragment.class),
	ME(3, R.string.welcome4, R.drawable.selector_bottombar_4,Welcome4Fragment.class);

	private int idx;
	private int resName;
	private int resIcon;
	private Class<?> clz;

	private MainTab(int idx, int resName, int resIcon, Class<?> clz) {
		this.idx = idx;
		this.resName = resName;
		this.resIcon = resIcon;
		this.clz = clz;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getResName() {
		return resName;
	}

	public void setResName(int resName) {
		this.resName = resName;
	}

	public int getResIcon() {
		return resIcon;
	}

	public void setResIcon(int resIcon) {
		this.resIcon = resIcon;
	}

	public Class<?> getClz() {
		return clz;
	}

	public void setClz(Class<?> clz) {
		this.clz = clz;
	}
}
