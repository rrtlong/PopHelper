package com.moli.module.net.encryp;

/**
 * 接口请求加密规则
 * 1、该规则描述字段默认参与加密<br>
 * 2、如某个字段不需要参与加密则加上注解@Encrypt(required = false)<br>
 * <p>
 * <p>加密规则<p/>
 * md5(md5(secretKey+accessToken)+md5(version+platform)+md5(deviceId+userId)+timestamp)
 *
 * @author zixiaojun
 * @date 2018/4/21
 * @copyright Moli Teams
 */
public class DigestRule {

    /**
     * 加密口令
     */
    private String secretKey;

    /**
     * 访问授权码
     */
    private String accessToken;

    /**
     * 版本
     */
    private String version;

    /**
     * 设备唯一标识
     */
    private String deviceId;

    /**
     * 系统平台
     */
    private String platform;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 时间戳
     */
    private String timestamp;


    public DigestRule(String secretKey, String accessToken, String version, String deviceId, String platform, String userId, String timestamp) {
        this.secretKey = secretKey;
        this.accessToken = accessToken;
        this.version = version;
        this.deviceId = deviceId;
        this.platform = platform;
        this.userId = userId;
        this.timestamp = timestamp;

    }

    public String getDisgest() {
        DigestRule digestRule = ReflectKit.reflectDigestRule(this);

        String partOne = digestRule.getSecretKey() + digestRule.getAccessToken();
        String partTwo = digestRule.getVersion() + digestRule.getPlatform();
        String partThree = digestRule.getDeviceId() + digestRule.getUserId();
        String mTimestamp = digestRule.getTimestamp();

        StringBuilder sb = new StringBuilder();
        if (DigestKit.isNotBlank(partOne)) {
            sb.append(DigestKit.md5Hex(partOne));
        }
        if (DigestKit.isNotBlank(partTwo)) {
            sb.append(DigestKit.md5Hex(partTwo));
        }
        if (DigestKit.isNotBlank(partThree)) {
            sb.append(DigestKit.md5Hex(partThree));
        }
        if (DigestKit.isNotBlank(mTimestamp)) {
            sb.append(mTimestamp);
        }
        if (DigestKit.isBlank(sb.toString())) {
            throw new IllegalArgumentException("encrypt content is blank");
        }

        return DigestKit.md5Hex(sb.toString());
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
