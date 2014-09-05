package com.liang.netty.utils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.security.KeyStore;

/**
 * @author BriLiang(liangwen.liang@vipshop.com)
 * @date 2014/8/26.
 * Description:()
 */
public class SercureSslContextFactory {
    private static final String KEY_STORE_TYPE_JKS = "jks";
    private static final String KEY_STORE_TYPE_P12 = "PKCS12";
    private static final String SCHEME_HTTPS = "https";
    private static final int HTTPS_PORT = 8443;
    private static final String HTTPS_URL = "https://127.0.0.1:8443/TestDemo/sslServlet";
    private static final String KEY_STORE_CLIENT_PATH = "D:/key/client.p12";
    private static final String KEY_STORE_TRUST_PATH = "D:/key/client.truststore";
    private static final String KEY_STORE_PASSWORD = "aaaaaa";
    private static final String KEY_STORE_TRUST_PASSWORD = "aaaaaa";

}
