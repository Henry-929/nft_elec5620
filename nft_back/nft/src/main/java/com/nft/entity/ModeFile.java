package com.nft.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 3d模型文件表
 * </p>
 *
 * @author XXXXX
 * @since 2022-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ModeFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件Id
     */
    @TableId(value = "mode_file_id", type = IdType.AUTO)
    private Long modeFileId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件格式（.txt .png）
     */
    private String fileFormat;

    /**
     * 文件保存路径
     */
    private String filePath;

    /**
     * 文件
     */
    private String fileHash;

    /**
     * 艺术品ID
     */
    private Long artId;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;

    /**
     * 创造时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


}
