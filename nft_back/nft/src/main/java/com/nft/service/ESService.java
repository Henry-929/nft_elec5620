package com.nft.service;

import java.io.IOException;
import java.util.Map;

public interface ESService {
    Map<String,Object> searchBlogForQuery(int pageNum, int pageSize, String query) throws IOException;
}
