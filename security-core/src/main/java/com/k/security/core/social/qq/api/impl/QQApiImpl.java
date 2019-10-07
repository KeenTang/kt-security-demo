package com.k.security.core.social.qq.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.k.security.core.context.SpringContext;
import com.k.security.core.model.QQUserInfo;
import com.k.security.core.social.qq.api.QQApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-05
 * Time: 10:31
 */
@Slf4j
public class QQApiImpl extends AbstractOAuth2ApiBinding implements QQApi {
    /**
     * https://graph.qq.com/oauth2.0/me?access_token=YOUR_ACCESS_TOKEN,
     * 传入accessToken获取用户Openid
     */
    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    /**
     * https://graph.qq.com/user/get_user_info?access_token=YOUR_ACCESS_TOKEN&oauth_consumer_key=YOUR_APP_ID&openid=YOUR_OPENID
     * 传入accessToken、appId、Openid获取用户信息
     * 因为accessToken参数父类AbstractOAuth2ApiBinding会自动加上，所以URL中不需要自己再添加
     *
     * @return
     */
    private static final String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";

    private ObjectMapper objectMapper= SpringContext.context().getBean(ObjectMapper.class);


    private String openId;
    private String appId;

    public QQApiImpl(String accessToken, String appId) {
        //Token策略是将accessToken添加到URL参数,默认是添加到请求头中（TokenStrategy.AUTHORIZATION_HEADER）
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        this.openId = this.getOpenId(accessToken);
    }

    @Override
    public QQUserInfo getQQUserInfo() throws IOException {
        String url = String.format(URL_GET_USER_INFO, appId, openId);
        /**
         *  返回数据格式
         * {
         * "ret":0,
         * "msg":"",
         * "nickname":"Peter",
         * "figureurl":"http://qzapp.qlogo.cn/qzapp/111111/942FEA70050EEAFBD4DCE2C1FC775E56/30",
         * "figureurl_1":"http://qzapp.qlogo.cn/qzapp/111111/942FEA70050EEAFBD4DCE2C1FC775E56/50",
         * "figureurl_2":"http://qzapp.qlogo.cn/qzapp/111111/942FEA70050EEAFBD4DCE2C1FC775E56/100",
         * "figureurl_qq_1":"http://q.qlogo.cn/qqapp/100312990/DE1931D5330620DBD07FB4A5422917B6/40",
         * "figureurl_qq_2":"http://q.qlogo.cn/qqapp/100312990/DE1931D5330620DBD07FB4A5422917B6/100",
         * "gender":"男",
         * "is_yellow_vip":"1",
         * "vip":"1",
         * "yellow_vip_level":"7",
         * "level":"7",
         * "is_yellow_year_vip":"1"
         * }
         * 返回参数说明:
         * ret	返回码
         * msg	如果ret<0，会有相应的错误信息提示，返回数据全部用UTF-8编码。
         * nickname	用户在QQ空间的昵称。
         * figureurl	大小为30×30像素的QQ空间头像URL。
         * figureurl_1	大小为50×50像素的QQ空间头像URL。
         * figureurl_2	大小为100×100像素的QQ空间头像URL。
         * figureurl_qq_1	大小为40×40像素的QQ头像URL。
         * figureurl_qq_2	大小为100×100像素的QQ头像URL。需要注意，不是所有的用户都拥有QQ的100x100的头像，但40x40像素则是一定会有。
         * gender	性别。 如果获取不到则默认返回"男"
         * API文档地址:https://wiki.connect.qq.com/get_user_info
         */
        String userInfoString = getRestTemplate().getForObject(url, String.class);
        log.info("userInfo={}", userInfoString);
        QQUserInfo qqUserInfo = objectMapper.readValue(userInfoString, QQUserInfo.class);
        qqUserInfo.setOpenId(this.openId);
        return qqUserInfo;
    }

    private String getOpenId(String accessToken) {
        String url = String.format(URL_GET_OPENID, accessToken);
        String openIdString = getRestTemplate().getForObject(url, String.class);
        log.info("openId={}", openIdString);
        /**
         * PC网站接入时，获取到用户OpenID，返回包如下：
         * callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
         */
        return StringUtils.substringBetween(openIdString, "\"openid\":\"", "\"}");
    }
}
