package com.zakati.api;

/**
 * Created by Rahil on 6/4/16.
 */
public class ApiConstants {


    //staging
//    static final String BASE_URL = "http://203.123.36.134:8089/api/v2/user/";
    //staging
//    public static final String LIVE_CHAT_URL = "http://203.123.36.134:9095/";

    //live
    static final String BASE_URL = "http://52.221.228.164:8081/api/v2/user/";
    //live
    public static final String LIVE_CHAT_URL = "http://52.221.228.164:9095/";

    static final String USER_SIGNUP = "userSignup";
    static final String VERIFY_OTP = "verifyOTP";

    static final String CHANGE_PHONE_NUMBER = "changePhoneNumber";
    static final String RESEND_OTP = "resendOTP";

    static final String CHANGE_USER_PASSWORD = "changeUserPassword";

    static final String USER_LOGIN = "userLogin";
    static final String FORGOT_PASSWORD = "forgotPassword";
    static final String GET_USER_PROFILE = "getUserProfile";

    static final String EDIT_PROFILE = "editProfile";

    static final String GET_CATEGORY = "getCategory";
    static final String CREATE_NEW_OFFER = "createNewOffer";
    public static final String MAKE_PAYMENT = "makePayment";

    static final String EDIT_OFFER = "editOffer";

    static final String REMOVE_OFFER = "removeOffer";

    static final String GET_BANK_DETAILS = "getBankDetails";
    static final String EDIT_BANK_DETAILS = "editBankDetails";


    static final String GET_OFFER = "getOffer";

    static final String ADD_TO_WISH_LIST = "addToWishlist";
    static final String MY_OFFERS = "myOffers";
    static final String HOME = "getAllOffer";

    static final String INSERT_BANK_DETAILS = "insertBankDetails";

    static final String FOLLOW_USER = "followUser";

    static final String MAKE_REQUEST = "makeRequest";

    static final String GET_USER_NOTIFICATION = "getUserNotification";

    static final String CLEAR_NOTIFICATION = "clearNotification";

    static final String GET_USER_SETTING = "getUserSetting";

    static final String UPDATE_SETTING = "updateSetting";

    static final String READ_NOTIFICATION = "readNotification";
    static final String GET_BIDS = "getBids";
    static final String ACCEPT_OFFERS = "acceptRejectOffer";
    static final String PRODUCT_LISTING = "productListing";

    static final String USER_CHAT_MESSAGES = "userChatMessages";
    static final String CHAT_MESSAGE_HISTORY = "chatMessageHistory";
    static final String REPORT="reportUser";
    static final String REPORT_PRODUCT="reportProduct";
    static final String RATE_USER="rateUser";

    static final String LOGOUT="userLogout";
    static final String CHECK_BALANCE="checkBalanceAmount";

    static final String MAKE_PAYMENT_DONE_BY_USER="makePaymentdonebyuser";

    static final String CLEAR_CHAT_MESSAGE_UNREAD_COUNT="clearChatMessageUnreadCount";

    static final String PAYMENT_METHODS="paymentMethods";

    static final String ADMIN_BANK_DETAIL="adminBankDetail";
    static final String GENERATE_PAYFORT_SDK_TOKEN="generatePayfortSDKToken";

    ///*paymentMethods*/

    //generatePayfortSDKToken


    public static final int PAYMENT_METHOD_COD=1;
    public static final int PAYMENT_METHOD_CREDIT_CARD=2;
    public static final int PAYMENT_METHOD_SADAD=3;

    static final int POOR = 1;
    static final int AVERAGE = 2;
    static final int GOOD = 3;
    static final int BEST = 4;


    /*(1 :Poor, 2: Average,3:Good, 4:Best)*/

    public static final String CHAT_TYPE_TEXT = "1";
    public static final String CHAT_TYPE_IMAGE = "2";
    public static final String CHAT_TYPE_LOCATION = "3";

    public static final int STATUS_BUY = 0;
    public static final int STATUS_SELL = 1;
    public static final int STATUS_FAVOURITE = 2;

    public static final int REQUEST_TYPE_BID = 2;
    public static final int REQUEST_TYPE_BUY = 1;
    public static final String SECRET_KEY = "my_key";


    public static String TERMS_HELP_BASE_URL="http://52.221.228.164:8081/api/user/page";

  /*Terms and Condition  English Url
http://52.221.228.164:8081/api/user/page?id=1&lang=en
Terms and Condition  Arabic Url
http://52.221.228.164:8081/api/user/page?id=1&lang=arabic
[11:43:05 AM] Nitin Shukla: Help English Url
http://52.221.228.164:8081/api/user/page?id=2&lang=en
Help  Arabic Url
http://52.221.228.164:8081/api/user/page?id=2&lang=arabic
*/

}
