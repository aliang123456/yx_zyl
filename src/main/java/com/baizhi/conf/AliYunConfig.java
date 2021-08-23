package com.baizhi.conf;

public interface AliYunConfig {
    // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
    String ENDPOINT = "https://oss-cn-beijing.aliyuncs.com";
    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    String ACCESS_KEY_ID = "LTAI5t7MfUEa2vwED5et2xrA";
    String ACCESS_KEY_SECRET = "mGxq3p01FlSV3lCYg2ZPQyNckP03M6";
}
