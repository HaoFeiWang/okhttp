package okhttp3.ssl;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class SslTrustFactory {

    //创建X509TrustManager
    public static X509TrustManager initTrustManager() {
        return new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }
            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                System.out.println("verify server trusted !");

            }
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                System.out.println("verify client trusted !");

            }
        };
    }

    //第二步：获取SslSocketFactory
    public static SSLSocketFactory initSSLSocketFactory(X509TrustManager trustManager) {
        SSLSocketFactory sslSocketFactory = null;
        try {
            //与服务器保持一致的算法类型版本
            SSLContext sslContext = SSLContext.getInstance("TLS");
            X509TrustManager[] xTrustArray = new X509TrustManager[]{trustManager};
            sslContext.init(null, xTrustArray, new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }
}
