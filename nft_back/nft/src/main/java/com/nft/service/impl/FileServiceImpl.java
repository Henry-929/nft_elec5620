package com.nft.service.impl;

import com.nft.entity.File;
import com.nft.mapper.FileMapper;
import com.nft.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文件表（保存各类文件，如艺术品的文件，上传的头像的文件，其他文件等） 服务实现类
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

}
