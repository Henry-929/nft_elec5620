package com.nft.entity.vo;

import com.nft.entity.File;
import lombok.Data;

@Data
public class SimpleArt {
    private Long artId;
    private String artName;
    private String artIntroduction;
    private String artAuthor;
    private Long ownerId;
    private File showFile;
}
