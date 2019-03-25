package com.lostfound.common.service;

import com.lostfound.common.domain.MessageDO;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author yangtao
 * @email 541502337@qq.com
 * @date 2019-03-22 15:58:34
 */
public interface MessageService {
	
	MessageDO get(Long id);
	
	List<MessageDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(MessageDO message);
	
	int update(MessageDO message);
	
	int remove(Long id);
	
	int batchRemove(Long[] ids);
}
