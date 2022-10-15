package com.nft.shiro;

import com.nft.entity.User;
import com.nft.util.StringUtil;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.stereotype.Component;

@Component
public class MyCredentialsMatcher extends SimpleCredentialsMatcher {

    //使用自定义token
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        JwtToken jwtToken=(JwtToken) token;
        if (jwtToken.getPassword() == null){
            return true;
        }
        //获得用户输入的密码:(可以采用加盐(salt)的方式去检验)
        String inPassword = new String(jwtToken.getPassword());
        //获得数据库中的密码
        User user = (User) info.getPrincipals().getPrimaryPrincipal();
        //加盐加密
        String salt = "aasd123viav9";
        String dbPassword=(String) info.getCredentials();
        //进行密码的比对
        return this.equals(StringUtil.md5(inPassword + salt), dbPassword);
    }

}
