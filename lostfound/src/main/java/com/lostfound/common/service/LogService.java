package com.lostfound.common.service;

import org.springframework.stereotype.Service;

import com.lostfound.common.domain.LogDO;
import com.lostfound.common.domain.PageDO;
import com.lostfound.common.utils.Query;
@Service
public interface LogService {
	void save(LogDO logDO);
	PageDO<LogDO> queryList(Query query);
	int remove(Long id);
	int batchRemove(Long[] ids);
}
