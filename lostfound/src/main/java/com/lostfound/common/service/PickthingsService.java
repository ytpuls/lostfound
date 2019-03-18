package com.lostfound.common.service;

import com.lostfound.common.domain.PickthingsDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author yangtao
 * @email 541502337@qq.com
 * @date 2019-03-13 15:39:11
 */
public interface PickthingsService {
	
	PickthingsDO get(Long id);
	
	List<PickthingsDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(PickthingsDO pickthings);
	
	int update(PickthingsDO pickthings);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
