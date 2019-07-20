package org.elastos.dma.dmademo.config;


import java.math.BigInteger;

/**
 * 功能说明: upTick项目系统配置
 * @author wangbin
 * @since 2015-08-18 15:05:34
 * @version 1.0.0
 */
public class SystemConfig {

    //全局日志级别
    public static final boolean IS_DEBUG = true;
    public static boolean haswallet=false;
    public static String did="";
    public static String address="";
    public static String ethAddress="";
    public static double ethBalance=0;
    public static double ELABalance=0;
    public static String privatekey="";
    public static String elaPrivatekey="";
    public static BigInteger gasPrice= BigInteger.valueOf(20000000000l);
    public static  BigInteger gasLimit= BigInteger.valueOf(6602513);
    public static String linkType="ETH";


    public class Keys{
        public static final String NATIVE_INFO_USER_PRIVATE = "native_info_user_private";
        public static final String NATIVE_INFO_WALLET = "native_info_wallet";




    }




}
