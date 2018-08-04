package com.zymh.www.zymh.Utils;


public class Constants {
    public static final boolean DEBUG = true;

    public static final String HOST = "http://api.manga163.com";
    public static final String HOST_PIC = "http://img.manga168.com";
    // 用户相关接口
    public static final String URL_REGISTER = HOST + "/user/register";
    public static final String URL_LOGIN = HOST + "/user/login";
    public static final String URL_CHECK_USERNAME = HOST + "/user/check_account";//检测账号是否被注册
    public static final String URL_GET_EMAIL_CODE = HOST + "/user/email_code";//获取邮箱验证码
    public static final String URL_GET_PHONE_CODE = HOST + "/user/sms_code";//获取短信验证码
    public static final String URL_RESET_PWD = HOST + "/user/modify_pwd";//重置密碼/忘記密碼
    public static final String URL_SIGN = HOST + "/activity/daily_coin";//签到
    public static final String URL_GET_USER_INFO = HOST + "/user/info";//获取个人信息
    public static final String URL_RECHARGE = "http://www.manga168.com/happyComic/html/ComicRecharge.html?token=";
    // 漫画相关接口
    public static final String URL_GET_HOT_WORD = HOST + "/book/words";//获取热门搜词（搜索界面）
    public static final String URL_SEARCH = HOST + "/book/search";
    public static final String URL_GET_HOME_PAGE_DATA = HOST + "/book/homepage";//获取首页数据
    public static final String URL_GET_UPDATE_PAGE_DATA = HOST + "/book/get_update";//获取更新页数据
    public static final String URL_GET_COLLECTION_DATA = HOST + "/collection/get";//获取我的收藏数据
    public static final String URL_GET_COMIC_DETAIL = HOST + "/book/get_one";//获取漫画书详情
    public static final String URL_GET_COMIC_GROUP = HOST + "/book/get";
    public static final String URL_GET_COMIC_PAGES = HOST + "/page/get";
    // 其他接口
    public static final String URL_GET_APP_MSG = HOST + "/activity/notification";//系统消息
    // 请求码
    public static final int REQUST_CODE_LOGIN = 1;
    // 结果码
    public static final int RESULT_CODE_LOGIN_SUCCESS = 2;
}
