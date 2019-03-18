package com.lostfound.common.service;

import com.lostfound.common.domain.LostthingsDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author yangtao
 * @email 541502337@qq.com
 * @date 2019-03-13 15:39:02
 */
public interface LostthingsService {
	
	LostthingsDO get(Long id);
	
	List<LostthingsDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(LostthingsDO lostthings);
	
	int update(LostthingsDO lostthings);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
