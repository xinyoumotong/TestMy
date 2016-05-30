package hotP2B.WageGainTools.android;


public enum SupportBank
{

	ICBC(1,"ICBC", "工商银行", R.mipmap.icon_card_icbc),
	ABC(2,"ABC", "农业银行", R.mipmap.icon_card_abc),
	CMB(3,"CMB", "招商银行", R.mipmap.icon_card_cmb),
	CCB(4,"CCB", "建设银行", R.mipmap.icon_card_ccb),
	BCCB(5,"BCCB", "北京银行",R.mipmap.icon_card_bccb),
	BJRCB(6,"BJRCB", "北京农商",R.mipmap.icon_card_bjrcb),

	BOC(7,"BOC", "中国银行", R.mipmap.icon_card_boc),
	BOCOM(8,"BOCOM", "交通银行", R.mipmap.icon_card_bocom),
	CMBC(9,"CMBC", "民生银行", R.mipmap.icon_card_cmbc),
	BOS(10,"BOS", "上海银行", R.mipmap.icon_card_bos),
	CBHB(11,"CBHB", "渤海银行", R.mipmap.icon_card_cbhb),
	CEB(12,"CEB", "光大银行",R.mipmap.icon_card_ceb),

	CIB(13,"CIB", "兴业银行",R.mipmap.icon_card_cib),
	CITIC(14,"CITIC", "中信银行",R.mipmap.icon_card_citic),
	CZB(15,"CZB", "浙商银行",R.mipmap.icon_card_czb),
	GDB(16,"GDB", "广发银行", R.mipmap.icon_card_gdb),
	HKBEA(17,"HKBEA", "东亚银行",R.mipmap.icon_card_hkbea),
	HXB(18,"HXB", "华夏银行",R.mipmap.icon_card_hxb),

	HZCB(19,"HZCB", "杭州银行",R.mipmap.icon_card_hzcb),
	NJCB(20,"NJCB", "南京银行",R.mipmap.icon_card_njcb),
	PINGAN(21,"PINGAN", "平安银行", R.mipmap.icon_card_pingan),
	PSBC(22,"PSBC", "邮政储蓄", R.mipmap.icon_card_psbc),
	SDB(23,"SDB", "深圳发展",R.mipmap.icon_card_sdb),
	SPDB(24,"SPDB", "浦发银行", R.mipmap.icon_card_spdb),

	SRCB(25,"SRCB", "上海农商",R.mipmap.icon_card_srcb),
	TEST(26,"60000600", "招商银行",R.mipmap.icon_card_cmb);


	private int id;
	private String nick;
	private String name;
	private int logo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLogo() {
		return logo;
	}

	public void setLogo(int logo) {
		this.logo = logo;
	}



	private SupportBank(int id, String nick, String name, int logo)
	{
		this.id=id;
		this.nick=nick;
		this.name=name;
		this.logo=logo;
	}
	
	public static String getNameByNick(String nick)
	{
      for (SupportBank s : values())
      {
         if (s.nick.equals(nick))
         {
            return s.getName();
         }
      }
      return "";
    }
	
	public static int getLogoByNick(String nick)
	{
      for (SupportBank s : values())
      {
         if (s.nick.equals(nick))
         {
            return s.logo;
         }
      }
      return -1;
    }
	
}
