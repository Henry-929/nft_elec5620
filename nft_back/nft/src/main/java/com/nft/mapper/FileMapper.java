package com.nft.mapper;

import com.nft.entity.File;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 文件表（保存各类文件，如艺术品的文件，上传的头像的文件，其他文件等） Mapper 接口
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Repository
public interface FileMapper extends BaseMapper<File> {

}
