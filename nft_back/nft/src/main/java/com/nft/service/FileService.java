package com.nft.service;

import com.nft.entity.File;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * <p>
 * 文件表（保存各类文件，如艺术品的文件，上传的头像的文件，其他文件等） 服务类
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
public interface FileService extends IService<File> {

    File addFile(MultipartFile multipartFile) throws IOException;
}
