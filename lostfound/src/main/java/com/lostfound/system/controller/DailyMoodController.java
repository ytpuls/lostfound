package com.lostfound.system.lostfound.controller;
import com.atom.common.configuration.GlobalConstants;
import com.atom.common.dto.ModelListResult;
import com.atom.common.dto.Page;
import com.little.utils.convert.StringUtils;
import com.lianxin.mngserver.controller.base.BaseController;
import com.lianxin.mngserver.domain.lostfound.DailyMoodMngReq;
import com.lianxin.mngserver.exception.system.BusinessException;
import com.lianxin.mngserver.mapper.lostfound.DailyMoodMapper;
import com.lianxin.mngserver.share.annotation.Log;
import com.lianxin.user.prefer.domain.DailyMood;
import com.lianxin.user.prefer.dto.DailyMoodComplexReq;
import com.little.utils.reflect.Reflect;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 
 *
 * @author yangtao
 * @email 541502337@qq.com
 * @date 2019-10-24 16:02:14
 */
@Controller
@RequestMapping("lostfound/dailyMood")
public class DailyMoodController extends BaseController {

    @Autowired
    private DailyMoodMapper dailyMoodMapper;

    @RequestMapping("")
    @RequiresPermissions("lostfound:dailyMood:list")
    public String main(Model model) {
        return "lostfound/dailyMood/main";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("lostfound:dailyMood:list")
    public Page<DailyMood> list(String correlationID, DailyMoodMngReq dailyMoodMngReq) {
        info(correlationID, "进入列表查询界面,请求参数为: " + dailyMoodReq);
        DailyMoodComplexReq dailyMoodComplexReq= (DailyMoodComplexReq) Reflect.on(dailyMoodMngReq).copy(new DailyMoodComplexReq());
        ModelListResult<DailyMood> model = dailyMoodMapper.findByComplexCondition(correlationID, dailyMoodComplexReq, dailyMoodMngReq.getPage(), dailyMoodMngReq.getPageSize());
        return model.toPage();
    }



    @Log("进入编辑界面")
    @GetMapping("/edit")
    @RequiresPermissions("lostfound:dailyMood:edit")
    public String edit(String correlationID, String moodId, Model model) {
        info(correlationID, "进入编辑界面");
        if(StringUtils.isNotEmpty(id)) {
            DailyMood dailyMood =dailyMoodMapper.get(correlationID, id);
            model.addAttribute("model",dailyMood);
        }
        return "lostfound/dailyMood/edit";
    }

    @Log("调用更新接口")
    @PostMapping("/save")
    @RequiresPermissions("lostfound:dailyMood:edit")
    @ResponseBody
    public void editAction(String correlationID, @RequestBody DailyMood dailyMood) {
        info(correlationID, "调用保存接口，参数为："+dailyMood);
        if(StringUtils.isNotEmpty(DailyMood.getMoodId())) {
            dailyMoodMapper.update(correlationID,dailyMood);
        }else{
            dailyMoodMapper.update(correlationID,dailyMood);
        }
    }

    @PostMapping("/remove")
    @ResponseBody
    @Log("进入删除接口")
    @RequiresPermissions("lostfound:dailyMood:remove")
    public void remove(String correlationID, String moodId) {
        info(correlationID, "进入删除接口,参数为: " + moodId);
        DailyMood dailyMood = dailyMoodMapper.get(correlationID, MoodId);
        dailyMood.setMoodId(moodId);
        dailyMood.setStatus(GlobalConstants.STATUS.DISABLE);
        dailyMoodMapper.update(correlationID, dailyMood);
    }
}
