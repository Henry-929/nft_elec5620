package com.nft.config;

import com.nft.entity.User;
import com.nft.service.UserService;
import com.nft.shiro.JwtToken;
import com.nft.util.JwtUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomRealm extends AuthorizingRealm {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;

    /**
     * 限定这个realm只能处理JwtToken（不加的话会报错）
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("------------进入授权------------");
        User user = (User) principalCollection.getPrimaryPrincipal();
        logger.info(user.getUserName()+"----------------------------"+user.getRoles());
        if (user != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRole(user.getRoles());
            info.addStringPermission(user.getPerms());
            return info;
        }
        return null;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("------------进入认证------------");
        String userToken = (String) authenticationToken.getPrincipal();

        User user = userService.selectUserByName(JwtUtil.parseJWT(userToken).getId());
        if (user == null){
            return null;//会抛出用户名不存在异常
        }

        // 交给AuthenticatingRealm使用底层的CredentialsMatcher进行密码匹配
        return new SimpleAuthenticationInfo(user,user.getUserPassword(),getName());
    }
}
