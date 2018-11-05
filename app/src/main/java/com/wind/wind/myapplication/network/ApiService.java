package com.wyt.hs.zxxtb.network;

import com.wyt.hs.zxxtb.BuildConfig;
import com.wyt.hs.zxxtb.bean.Advertising;
import com.wyt.hs.zxxtb.bean.AliPayOrderInfo;
import com.wyt.hs.zxxtb.bean.CollectionStatus;
import com.wyt.hs.zxxtb.bean.CourseCollection;
import com.wyt.hs.zxxtb.bean.CourseDetail;
import com.wyt.hs.zxxtb.bean.HotCourse;
import com.wyt.hs.zxxtb.bean.MicroCourse;
import com.wyt.hs.zxxtb.bean.OrderInfo;
import com.wyt.hs.zxxtb.bean.Comment;
import com.wyt.hs.zxxtb.bean.RecommendCourse;
import com.wyt.hs.zxxtb.bean.RecordBean;
import com.wyt.hs.zxxtb.bean.ResourceDetail;
import com.wyt.hs.zxxtb.bean.SMSCode;
import com.wyt.hs.zxxtb.bean.ServiceInfo;
import com.wyt.hs.zxxtb.bean.SubjectDetail;
import com.wyt.hs.zxxtb.bean.TeacherDetail;
import com.wyt.hs.zxxtb.bean.TeacherIntro;
import com.wyt.hs.zxxtb.bean.VersionInfo;
import com.wyt.hs.zxxtb.bean.VideoInfo;
import com.wyt.hs.zxxtb.bean.VipSetMeal;
import com.wyt.hs.zxxtb.bean.WXOrderInfo;
import com.wyt.hs.zxxtb.greendao.entity.UserInfo;
import com.wyt.hs.zxxtb.network.base.BaseEntity;
import com.wyt.hs.zxxtb.network.base.BaseList;
import com.wyt.hs.zxxtb.network.params.Params;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    //测试地址
//    String API_HOST = "http://12.12.12.38/";
//    String API_HOST = "http://wxxxkxas.iexue100.com/";
    String API_HOST = "http://www.iexue100.com/";

    /**
     * 请求课程列表
     */
    @POST("api/resource.course/get_course_list")
    Observable<BaseEntity<BaseList<CourseDetail>>> getCourseList(@Body Params params);

    /**
     * 请求资源列表 (视频)
     */
    @POST("api/resource.resources/get_resources_list")
    Observable<BaseEntity<BaseList<VideoInfo>>> getResourceList(@Body Params params);

    /**
     * 请求资源详情
     */
    @POST("api/resource.resources/get_resource_details")
    Observable<BaseEntity<ResourceDetail>> getResourceDetails (@Body Params params);

    /**
     * 精品推荐课程
     */
    @POST("api/resource.course/get_jp_course")
    Observable<BaseEntity<BaseList<RecommendCourse>>> getRecommendCourse(@Body Params params);

    /**
     * 获取热门课程
     */
    @POST("api/resource.course/get_hot_course")
    Observable<BaseEntity<BaseList<HotCourse>>> getHotCourse (@Body Params params);

    /**
     * 获取收藏列表
     */
    @POST("api/user/get_collection_list")
    Observable<BaseEntity<BaseList<CourseCollection>>> getCollectionList(@Body Params params);

    /**
     * 添加、删除收藏
     */
    @POST("api/user/save_course_collection")
    Observable<BaseEntity<CollectionStatus>> switchCollectionState(@Body Params params);

    /**
     * 用户登录
     */
    @POST("api/user/login")
    Observable<BaseEntity<UserInfo>> login(@Body Params params);

    /**
     * 获取手机验证码
     */
    @POST("api/user/get_phone_code")
    Observable<BaseEntity<SMSCode>> getPhoneCode(@Body Params params);

    /**
     * 用户注册
     */
    @POST("api/user/add_user")
    Observable<BaseEntity> registerNewUser(@Body Params params);

    /**
     * 微信登录 - 后台返回个人信息
     */
    @POST("api/weixin_login/app_login")
    Observable<BaseEntity<UserInfo>> wxAppLogin(@Body Params params);

    /**
     * 修改密码
     */
    @POST("api/user/update_password")
    Observable<BaseEntity> updatePassword(@Body Params params);

    /**
     * 修改手机号
     */
    @POST("api/user/change_bind_phone")
    Observable<BaseEntity> changeBindPhone(@Body Params params);

    /**
     * 绑定手机号
     */
    @POST("api/user/bind_phone")
    Observable<BaseEntity> bindPhone(@Body Params params);

    /**
     * 获取个人信息
     */
    @POST("api/user/get_user_data")
    Observable<BaseEntity<UserInfo>> getUserInfo(@Body Params params);

    /**
     * 获取Vip套餐列表
     */
    @POST("api/price.product/get_vip_price_list")
    Observable<BaseEntity<BaseList<VipSetMeal>>> getVipPriceList(@Body Params params);

    /**
     * 微信App下单
     */
    @POST("api/weixin_pay/wechat_app_pay")
    Observable<BaseEntity<WXOrderInfo>> getWXAppOrder(@Body Params params);

    /**
     * 支付宝App下单
     */
    @POST("api/order/alipay_app")
    Observable<AliPayOrderInfo> getAliAppOrder(@Body Params params);

    /**
     * 交易记录
     */
    @POST("api/weixin_pay/get_order_list")
    Observable<BaseEntity<BaseList<OrderInfo>>> getOrderList(@Body Params params);

    /**
     * 删除播放记录
     */
    @POST("api/weixin_pay/del_order")
    Observable<BaseEntity> deleteOrder(@Body Params params);

    /**
     * 获取客服信息
     */
    @POST("api/product/get_service_info")
    Observable<BaseEntity<List<ServiceInfo>>> getServiceInfo(@Body Params params);

    /**
     * 更新个人信息
     */
    @POST("api/user/update_user_info")
    Observable<BaseEntity> updateUserInfo(@Body Params params);

    /**
     * 用户上传头像
     */
    @Multipart
    @POST("api/upload/upload_qiniu")
    Observable<String> upLoadHeadPic(@Part("uid") String uid, @Part MultipartBody.Part file);

    /**
     * 添加观看记录
     */
    @POST("api/user/add_resources_record")
    Observable<BaseEntity> addResourcesRecords(@Body Params params);

    /**
     * 添加观看时长
     */
    @POST("api/user/add_playback_length")
    Observable<BaseEntity> addPlayBackLength(@Body Params params);

    /**
     * 添加点播次数
     */
    @POST("api/user/add_course_record")
    Observable<BaseEntity> addCourseRecord(@Body Params params);

    /**
     * 观看记录列表
     */
    @POST("api/user/get_resources_record_list")
    Observable<BaseEntity<BaseList<RecordBean>>> getRecordList(@Body Params params);

    /**
     * 获取课程详情
     */
    @POST("api/resource.course/get_course_detail")
    Observable<BaseEntity<CourseDetail>> getCourseDetail(@Body Params params);

    /**
     * 获取评论列表
     */
    @POST("api/resource.course/get_course_comment_list")
    Observable<BaseEntity<BaseList<Comment>>> getCourseComment(@Body Params params);

    /**
     * 添加课程评论
     */
    @POST("api/resource.course/add_course_comment")
    Observable<BaseEntity> addCourseComment(@Body Params params);

    /**
     * 获取教师列表
     */
    @POST("api/resource.teacher/get_course_teacher")
    Observable<BaseEntity<BaseList<TeacherIntro>>> getCourseTeacher(@Body Params params);

    /**
     * 获取教师详情
     */
    @POST("api/resource.teacher/get_teacher_detail")
    Observable<BaseEntity<TeacherDetail>> getTeacherDetail(@Body Params params);

    /**
     * 获取专题详情
     */
    @POST("api/resource.subject/get_subject_detail")
    Observable<BaseEntity<SubjectDetail>> getSubjectDetail(@Body Params params);

    /**
     * 获取广告列表
     */
    @POST("api/product/get_loading_list")
    Observable<BaseEntity<BaseList<Advertising>>> getLoadingList(@Body Params params);

    /**
     * 删除播放记录
     */
    @POST("api/user/del_record")
    Observable<BaseEntity> deletePlayRecond(@Body Params params);

    /**
     * 知识点微课列表
     */
    @POST("api/resource.microclass/chapter_list")
    Observable<BaseEntity<BaseList<MicroCourse>>> getMicroCourseList(@Body Params params);

    /**
     * 获取应用信息
     */
    @POST("api/product/get_app_version")
    Observable<BaseEntity<VersionInfo>> getAppVersion(@Body Params params);

    /**
     * 获取教师列表
     */
    @POST("api/resource.teacher/get_teacher_list")
    Observable<BaseEntity<BaseList<TeacherDetail>>> getTeacherList(@Body Params params);

    /**
     * 广告列表
     */
    @POST("api/product/get_advertising_list")
    Observable<BaseEntity<BaseList<Advertising>>> getAdvertisingList(@Body Params params);
}
