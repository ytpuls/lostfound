package com.lostfound.common.dao;

import com.lostfound.common.domain.MessageDO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * @author yangtao
 * @email 541502337@qq.com
 * @date 2019-03-22 15:58:34
 */
@Mapper
public interface MessageDao {

	MessageDO get(Long id);
	
	List<MessageDO> list(Map<String, Object> map);
	
	int count(Map<String, Object> map);
	
	int save(MessageDO message);
	
	int update(MessageDO message);
	
	int remove(Long ID);
	
	int batchRemove(Long[] ids);
}
