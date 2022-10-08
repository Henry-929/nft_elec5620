package com.nft.service.impl;

import com.nft.entity.Token;
import com.nft.mapper.TokenMapper;
import com.nft.service.TokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * token表 服务实现类
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Service
public class TokenServiceImpl extends ServiceImpl<TokenMapper, Token> implements TokenService {

}
