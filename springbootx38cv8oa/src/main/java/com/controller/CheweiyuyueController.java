package com.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.annotation.IgnoreAuth;

import com.entity.CheweiyuyueEntity;
import com.entity.view.CheweiyuyueView;

import com.service.CheweiyuyueService;
import com.service.TokenService;
import com.utils.PageUtils;
import com.utils.R;
import com.utils.MPUtil;
import com.utils.MapUtils;
import com.utils.CommonUtil;
import java.io.IOException;

/**
 * 进校预约
 * 后端接口
 * @author 
 * @email 
 * @date 2024-04-07 21:17:03
 */
@RestController
@RequestMapping("/cheweiyuyue")
public class CheweiyuyueController {
    @Autowired
    private CheweiyuyueService cheweiyuyueService;




    



    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,CheweiyuyueEntity cheweiyuyue,
		HttpServletRequest request){
		String tableName = request.getSession().getAttribute("tableName").toString();
		if(tableName.equals("yonghu")) {
			cheweiyuyue.setYonghuzhanghao((String)request.getSession().getAttribute("username"));
		}
        EntityWrapper<CheweiyuyueEntity> ew = new EntityWrapper<CheweiyuyueEntity>();

		PageUtils page = cheweiyuyueService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, cheweiyuyue), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,CheweiyuyueEntity cheweiyuyue, 
		HttpServletRequest request){
        EntityWrapper<CheweiyuyueEntity> ew = new EntityWrapper<CheweiyuyueEntity>();

		PageUtils page = cheweiyuyueService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, cheweiyuyue), params), params));
        return R.ok().put("data", page);
    }



	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( CheweiyuyueEntity cheweiyuyue){
       	EntityWrapper<CheweiyuyueEntity> ew = new EntityWrapper<CheweiyuyueEntity>();
      	ew.allEq(MPUtil.allEQMapPre( cheweiyuyue, "cheweiyuyue")); 
        return R.ok().put("data", cheweiyuyueService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(CheweiyuyueEntity cheweiyuyue){
        EntityWrapper< CheweiyuyueEntity> ew = new EntityWrapper< CheweiyuyueEntity>();
 		ew.allEq(MPUtil.allEQMapPre( cheweiyuyue, "cheweiyuyue")); 
		CheweiyuyueView cheweiyuyueView =  cheweiyuyueService.selectView(ew);
		return R.ok("查询进校预约成功").put("data", cheweiyuyueView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        CheweiyuyueEntity cheweiyuyue = cheweiyuyueService.selectById(id);
        return R.ok().put("data", cheweiyuyue);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        CheweiyuyueEntity cheweiyuyue = cheweiyuyueService.selectById(id);
        return R.ok().put("data", cheweiyuyue);
    }
    



    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CheweiyuyueEntity cheweiyuyue, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(cheweiyuyue);
        cheweiyuyueService.insert(cheweiyuyue);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody CheweiyuyueEntity cheweiyuyue, HttpServletRequest request){
    	//ValidatorUtils.validateEntity(cheweiyuyue);
        cheweiyuyueService.insert(cheweiyuyue);
        return R.ok();
    }





    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody CheweiyuyueEntity cheweiyuyue, HttpServletRequest request){
        //ValidatorUtils.validateEntity(cheweiyuyue);
        cheweiyuyueService.updateById(cheweiyuyue);//全部更新
        return R.ok();
    }

    /**
     * 审核
     */
    @RequestMapping("/shBatch")
    @Transactional
    public R update(@RequestBody Long[] ids, @RequestParam String sfsh, @RequestParam String shhf){
        List<CheweiyuyueEntity> list = new ArrayList<CheweiyuyueEntity>();
        for(Long id : ids) {
            CheweiyuyueEntity cheweiyuyue = cheweiyuyueService.selectById(id);
            cheweiyuyue.setSfsh(sfsh);
            cheweiyuyue.setShhf(shhf);
            list.add(cheweiyuyue);
        }
        cheweiyuyueService.updateBatchById(list);
        return R.ok();
    }


    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        cheweiyuyueService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	










}
