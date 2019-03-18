package com.lostfound.common.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lostfound.common.domain.PickthingsDO;
import com.lostfound.common.service.PickthingsService;
import com.lostfound.common.utils.PageUtils;
import com.lostfound.common.utils.Query;
import com.lostfound.common.utils.R;

/**
 * 
 * 
 * @author yangtao
 * @email 541502337@qq.com
 * @date 2019-03-13 15:39:11
 */
 
@Controller
@RequestMapping("/common/pickthings")
public class PickthingsController extends BaseController{

	@Autowired
	private PickthingsService pickthingsService;
	
	@GetMapping()
	@RequiresPermissions("common:pickthings:pickthings")
	String Pickthings(){
	    return "common/pickthings/pickthings";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("common:pickthings:pickthings")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<PickthingsDO> pickthingsList = pickthingsService.list(query);
		int total = pickthingsService.count(query);
		PageUtils pageUtils = new PageUtils(pickthingsList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("common:pickthings:add")
	String add(){
	    return "common/pickthings/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("common:pickthings:edit")
	String edit(@PathVariable("id") Long id,Model model){
		PickthingsDO pickthings = pickthingsService.get(id);
		model.addAttribute("pickthings", pickthings);
	    return "common/pickthings/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("common:pickthings:add")
	public R save( PickthingsDO pickthings){
		if(pickthingsService.save(pickthings)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("common:pickthings:edit")
	public R update( PickthingsDO pickthings){
		pickthingsService.update(pickthings);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("common:pickthings:remove")
	public R remove( Long id){
		if(pickthingsService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("common:pickthings:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		pickthingsService.batchRemove(ids);
		return R.ok();
	}
	
}
