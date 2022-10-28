package com.nft.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nft.entity.File;
import com.nft.entity.ModeFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

/**
 * <p>
 * 文件表（保存各类文件，如艺术品的文件，上传的头像的文件，其他文件等） 服务类
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
public interface ModeFileService extends IService<ModeFile> {

    ArrayList<ModeFile> addModeFile(MultipartFile[] multipartFile, Long artId) throws IOException;
}
