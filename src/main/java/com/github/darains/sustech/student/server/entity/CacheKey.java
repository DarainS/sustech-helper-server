package com.github.darains.sustech.student.server.entity;

import java.io.Serializable;

public interface CacheKey extends Serializable{
    
    /**
     *
     * @return 返回一个String对象,用来作为缓存到Redis中的key值
     */
    String cacheKey();
    
}
