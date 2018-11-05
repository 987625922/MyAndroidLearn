package com.wyt.hs.zxxtb.network.params;

/**
 * 请求接口参数
 * 该类的字段直接上传到服务器，所以只可以使用String类型
 */
public final class Params {

    //分页加载量
    public static final String DEFAULT_LIMITS = "10";

    //微信APP下单
    public static final String DEFAULT_WX_APP_ORDER = "4";

    //支付宝APP下单
    public static final String DEFAULT_ALI_ORDER = "2";

    //默认下单类型 1-VIP包月 2-课程包
    public static final String DEFAULT_ORDER_TYPE = "1";

    //默认排序（正序）
    public static final String DEFAULT_ORDER_BY = "2";

    //排序 - 默认传2
    public String order_by;
    //分页量 - 默认10
    public String limits;
    public String pagesize;
    //产品id
    public String product_id;
    //uid
    public String uid;
    //当前页
    public String curr;
    //当前页
    public String page;
    //课程id
    public String course_id;
    //模块id
    public String module_id;
    //学段id
    public String xueduan_id;
    //年级id
    public String nianji_id;
    //学科id
    public String xueke_id;
    //专题id
    public String zhuanti_id;
    //专题id
    public String subject_id;
    //学期id
    public String xueqi_id;
    //出版商id
    public String press_id;
    //名称搜索
    public String name;
    //资源id - 添加观看记录
    public String resource_id;
    //资源id - 视频详情
    public String resources_id;
    //资源名称
    public String resources_name;
    //手机号
    public String phone;
    //用户密码
    public String password;
    //短信验证码
    public String verifycode;
    //验证码id
    public String verifycode_id;
    //类型
    public String type;
    //类型id
    public String type_id;
    //渠道id
    public String channel_id;
    //终端id
    public String terminal_id;
    //微信登录code
    public String code;
    //第三方平台ID 1-微信
    public String third_id;
    //旧手机验证码
    public String old_verifycode;
    //旧手机验证码id
    public String old_verifycode_id;
    //新手机
    public String new_phone;
    //月数
    public String months;
    //套餐类型
    public String price_type;
    //订单id
    public String order_ids;
    //真实姓名
    public String realname;
    //性别
    public String gender;
    //身份  #角色 1:学生2:老师,3:家长
    public String identity;
    //头像
    public String icon;
    //内容
    public String content;
    //评分
    public String score;
    //播放时长
    public String playback_time;
    //总时长
    public String total_time;
    //教师id
    public String teacher_id;
    //教师名称
    public String teacher_name;
    //排除的学段id
    public String xd_id;
}
