package cn.yyn.last_project.config;

/**
 * @author 杨以诺
 * by 2020-10-19 16:04
 */
public class AliPayConfig {
    // [沙箱环境]应用ID,您的APPID，收款账号既是你的APPID对应支付宝账号
    public static String APP_ID  = "2016102600764848";
    // [沙箱环境]商户私钥，你的PKCS8格式RSA2私钥
    public static String APP_PRIVATE_KEY  = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC4uNNUBHa6YrxmwHArl19LDsut+fbfPNmxQ9BKVt/IAnK5lHNL+M3ycG37MZSEArTMya7+dxPPWBQGZO4Kn4dj2DswMTNir2KSLtsnqBrGcBHKJ/f9Y653GDGN5Xpg8JdhiMMztftITczdcjK7XMBQfnfA+i/6wMJ9tsjyb59CKloHMbvYWOpq0I30kwYSkxaPJiA3wlSra0E2gXRDJbHEtGoV5MGF/ld6vQJCAvo/Dw8pceflkFuflEVERh0sxxe8WdJeT7ZkO4y+1XDAtvZISw3dbhdp+XUW/DYx60Ys8MesS+pIcil8aHO3qhv199VG7G15aXyhfx15116ZmUIbAgMBAAECggEALAD7drxJfEUhFGkrT3cT4FF5+8vcH3njcfLxn4Pqcz8CDWtru+FdTi+yQ3CgLIJoh5BT+5JqSa51tsA8ZJN0MTjFgMl3sD6PgLtgwaWuGy9/TWUQEnC4ku5Uf7T23jjyFu35ckwbvDGsZ+RYaYHC3moCJRp0a4uXyVLNs9MbYStGAXmsH0d8F0EK95y6VCXzfe8ZXB7B3dYKJY3SQiH2G/8vMcuim9/Zp6krfTnndWxH/9fVZ03DU4W8b0VhoRGA1Cawkh3XMlETr3x0fKZhIVwJsywT6fbo7O5aIZ3ft65BSIP99yxK5yx/vzUe3YGzWto91fT7jFuNoqyYuPBugQKBgQDcTkLUy9roh3Nzdj00AEr7fOb5dPPL39zQ9IPFIIc/6+SkNgcBRgCzD5hCzsce/kw77ezwPGh9b8/jwcR7gygUSekTvxv5vtF7aKsW+ylP9ZMiBYPe72KpUqU3TMhcoM0HezLtUxj3Y0nRvAxz+0hHyPkGMvH49dQQIoQrEzZecQKBgQDWpqODRRpzgv4Xh/tnq0zEwYcdqBwHozhAthY306TvKIxsuIfzrlbizZ1AbXBiHkPAu+IFHvhWU9MS6nAJ2IhahjDpxGsCGp7FC6b5I39jKaO8Gz9Oe9rEGHBUOpnOXhAOyhl432d13KerTTJAGzWbD2eNk6+zZgaVeHosBb6HSwKBgE1vxNX/gcbSNaaUHRc2MyUK1jVBAqDa40cfa2HS+Tdit09xHi04ae6GwMJa5Grkf+9gxjP7R88viidIaMPxahkXkbG7Lx4uEHwMYncFeZP9geYngh7OjiNuRVyWxKaqbub+SvxYhSIP6FasfsQVw2ZYIhAnwB3+arfxKfvqGOZBAoGBAM2XcBfEMVCgD6UULp0d+nqd7j9ei/JYyAFbJi60fU5AJTfhCv5AZqH3VH2oFLM1z6kji5liD3QH96wvdb5IcllaHDFbWy75tkZ/IZ+Q+2tML7J02o7enCukSLq0IisB9xljQ0B9ogfGn6W4W8ABD7gD6ig1Y11OBFJXwZS2r8W/AoGBAKOPu4Ujfn1D5Z1Q8WjpQmZCwFthb3CpYjoc4DKCV+zQs6iJFfkhABVHhGP6OlH2wA1WhNa1Ls2M3NXCXAchss3RAem4uMjolNikzBfWkaGvkEmVoBzkMfCDkBu0tJqz1hUQUnFM9Nav5n+pLc8wCmotavPZ9FfXGlitF0numMp0";
    // [沙箱环境]支付宝公钥
    public static String ALIPAY_PUBLIC_KEY  = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAomC+LzKtQN43H4kamC7/z6/836NBoxu8zeznyYz8pzj7CLabFrboIp2FGiZ/IZ/2OFi8gqUYplOLoVQp9JQPQ9aECgqrEIjzs2xDFr+rgJTbNFxAJaEYwwFh8X7VAawi2BnEzN/Uh0c5XBXaf5W5F+LIryCht6ks15KwoX5PpeYd1NexVUFSeXw3wnPWHP3zhCVb/tMX/eekgHrJVAOxLBgC9sENNtETo6DoRtOiauX6qxpnng3PbTvAq9IgGg5wxzA83v3VjvuRevNCy13ukhn9drhpuQyB1oVYYTRTKQh8h2EU109kif4Ra8LZdQBto2hHQB22OJAH0x1MZ8Fq2wIDAQAB";

    // [沙箱环境]服务器异步通知页面路径
//    public static String notify_url = "http://ngrok.sscai.club/alipay/aliPayNotify_url";
    // 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问.如果只是测试使用,那么设置成自己项目启动后可以访问到的一个路径,作为支付宝发送通知的路径(有什么用暂时没发现)
    //结束后跳转的方法页面
    public static String notify_url = "http://localhost:8080/Alipay/notifyUrl";

    // [沙箱环境]页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//    public static String return_url = "《支付成功跳转页面》";
    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问.如果只是测试使用,那么设置成自己项目启动后可以访问到的一个路径.是支付正常完成后,会访问的路径.
    //结束后跳转的方法页面
    public static String return_url = "http://localhost:8080/Alipay/returnUrl";

    // [沙箱环境]
    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
//    https://openapi.alipaydev.com/gateway.do

    // 签名方式，注意这里，如果步骤设置的是RSA则用RSA,如果按照扇面步骤做的话,选择RSA2
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String CHARSET = "utf-8";


}
