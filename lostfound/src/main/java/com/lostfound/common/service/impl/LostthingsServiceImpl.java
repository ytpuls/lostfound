package com.lostfound.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.lostfound.common.dao.LostthingsDao;
import com.lostfound.common.domain.LostthingsDO;
import com.lostfound.common.service.LostthingsService;



@Service
public class LostthingsServiceImpl implements LostthingsService {
	@Autowired
	private LostthingsDao lostthingsDao;
	
	@Override
	public LostthingsDO get(Long id){
		return lostthingsDao.get(id);
	}
	
	@Override
	public List<LostthingsDO> list(Map<String, Object> map){
		return lostthingsDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return lostthingsDao.count(map);
	}
	
	@Override
	public int save(LostthingsDO lostthings){
		return lostthingsDao.save(lostthings);
	}
	
	@Override
	public int update(LostthingsDO lostthings){
		return lostthingsDao.update(lostthings);
	}
	
	@Override
	public int remove(Long id){
		return lostthingsDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return lostthingsDao.batchRemove(ids);
	}
	
}
