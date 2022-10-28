package com.nft.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nft.entity.File;
import com.nft.entity.ModeFile;
import com.nft.mapper.FileMapper;
import com.nft.mapper.ModeFileMapper;
import com.nft.service.FileService;
import com.nft.service.ModeFileService;
import com.nft.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * <p>
 * 文件表（保存各类文件，如艺术品的文件，上传的头像的文件，其他文件等） 服务实现类
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Service
public class ModeFileServiceImpl extends ServiceImpl<ModeFileMapper, ModeFile> implements ModeFileService {

    @Autowired
    ModeFileMapper modeFileMapper;

    // 基础地址
    @Value("${File.modeBasePath}")
    String modeBasePath;

    @Override
    public ArrayList<ModeFile> addModeFile(MultipartFile[] files, Long artId) throws IOException {
        ArrayList<ModeFile> fileList = new ArrayList<>();

        for (MultipartFile multipartFile : files){
            InputStream inputStream = multipartFile.getInputStream();

            Date now = new Date();
            String addPath = "/" + now.getYear() + now.getMonth() + now.getDate() + "/";
            // 文件名称包括文件类型后缀
            String fileName = multipartFile.getOriginalFilename();

            java.io.File file = new java.io.File(modeBasePath + addPath);
            // 如果路径文件夹不存在，则创造该文件夹
            if (!file.exists()) {
                file.mkdirs();
            }

            file = new java.io.File(modeBasePath + addPath + fileName);
//        System.out.println("这里是————————————————"+file);

            // 同时还需要新建一个file的字段，并设置数据库文件字段的值
            ModeFile dbFile = new ModeFile();

            dbFile.setFilePath(addPath + fileName);
            dbFile.setFileName(multipartFile.getName() + UUID.randomUUID());
            dbFile.setFileFormat(multipartFile.getOriginalFilename());
            dbFile.setArtId(artId);

            // 将文件存储到磁盘
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(bytes)) != -1) {
                bufferedOutputStream.write(bytes, 0, len);
            }
            // MD5Util.md5HashCode 内将资源关闭了
            dbFile.setFileHash(MD5Util.md5HashCode(inputStream));

            modeFileMapper.insert(dbFile);
            fileList.add(dbFile);
            // 关闭资源
            bufferedOutputStream.close();
        }

        return fileList;
    }


}
