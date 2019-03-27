package com.lostfound.common.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lostfound.common.domain.Constants;
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

import com.lostfound.common.domain.LostthingsDO;
import com.lostfound.common.service.LostthingsService;
import com.lostfound.common.utils.PageUtils;
import com.lostfound.common.utils.Query;
import com.lostfound.common.utils.R;

/**
 * 
 * 
 * @author yangtao
 * @email 541502337@qq.com
 * @date 2019-03-13 15:39:02
 */
 
@Controller
@RequestMapping("/common/lostthings")
public class LostthingsController extends BaseController{

	@Autowired
	private LostthingsService lostthingsService;
	
	@GetMapping()
	@RequiresPermissions("common:lostthings:lostthings")
	String Lostthings(){
	    return "common/lostthings/lostthings";
	}

    @GetMapping("/my")
    @RequiresPermissions("common:lostthings:my")
    String myLostthings(){
        return "common/lostthings/my";
    }

    @ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("common:lostthings:lostthings")
	public PageUtils list(@RequestParam Map<String, Object> params){

		//查询列表数据
        Query query = new Query(params);
		List<LostthingsDO> lostthingsList = lostthingsService.list(query);
		int total = lostthingsService.count(query);
		PageUtils pageUtils = new PageUtils(lostthingsList, total);
		return pageUtils;
	}
    @ResponseBody
    @GetMapping("/mylist")
    @RequiresPermissions("common:lostthings:lostthings")
    public PageUtils mylist(@RequestParam Map<String, Object> params){

        //查询列表数据
        params.put("username",getUsername());
        Query query = new Query(params);
        List<LostthingsDO> lostthingsList = lostthingsService.list(query);
        int total = lostthingsService.count(query);
        PageUtils pageUtils = new PageUtils(lostthingsList, total);
        return pageUtils;
    }

    @GetMapping("/add")
	@RequiresPermissions("common:lostthings:add")
	String add(){
	    return "common/lostthings/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("common:lostthings:edit")
	String edit(@PathVariable("id") Long id,Model model){
		LostthingsDO lostthings = lostthingsService.get(id);
		model.addAttribute("lostthings", lostthings);
	    return "common/lostthings/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("common:lostthings:add")
	public R save( LostthingsDO lostthings){
	    lostthings.setUid(getUserId());
	    lostthings.setPublishtime(new Date());
	    lostthings.setStatus(Constants.ENABLE);
	    lostthings.setUsername(getUsername());
		if(lostthingsService.save(lostthings)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("common:lostthings:edit")
	public R update( LostthingsDO lostthings){
        lostthings.setUid(getUserId());
        lostthings.setPublishtime(new Date());
        lostthings.setStatus(Constants.ENABLE);
        lostthings.setUsername(getUsername());
		lostthingsService.update(lostthings);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("common:lostthings:remove")
	public R remove( Long id){
		if(lostthingsService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("common:lostthings:batchRemove")
	public R remove(@RequestParam("ids[]") Long[] ids){
		lostthingsService.batchRemove(ids);
		return R.ok();
	}
	
}
