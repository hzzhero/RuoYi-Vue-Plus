package com.ruoyi.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.core.page.PagePlus;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ruoyi.system.domain.bo.ScheduleBo;
import com.ruoyi.system.domain.vo.ScheduleVo;
import com.ruoyi.system.domain.Schedule;
import com.ruoyi.system.mapper.ScheduleMapper;
import com.ruoyi.system.service.IScheduleService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 班次Service业务层处理
 *
 * @author ruoyi
 * @date 2021-08-28
 */
@Service
public class ScheduleServiceImpl extends ServicePlusImpl<ScheduleMapper, Schedule, ScheduleVo> implements IScheduleService {

    @Override
    public ScheduleVo queryById(String scheduleId){
        return getVoById(scheduleId);
    }

    @Override
    public TableDataInfo<ScheduleVo> queryPageList(ScheduleBo bo) {
        PagePlus<Schedule, ScheduleVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<ScheduleVo> queryList(ScheduleBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<Schedule> buildQueryWrapper(ScheduleBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<Schedule> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getName()), Schedule::getName, bo.getName());
        lqw.eq(StringUtils.isNotBlank(bo.getStartHour()), Schedule::getStartHour, bo.getStartHour());
        lqw.eq(StringUtils.isNotBlank(bo.getEndHour()), Schedule::getEndHour, bo.getEndHour());
        lqw.eq(bo.getOrderUnit() != null, Schedule::getOrderUnit, bo.getOrderUnit());
        lqw.eq(bo.getUnitSplit() != null, Schedule::getUnitSplit, bo.getUnitSplit());
        lqw.eq(StringUtils.isNotBlank(bo.getCompanyId()), Schedule::getCompanyId, bo.getCompanyId());
        return lqw;
    }

    @Override
    public Boolean insertByBo(ScheduleBo bo) {
        Schedule add = BeanUtil.toBean(bo, Schedule.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(ScheduleBo bo) {
        Schedule update = BeanUtil.toBean(bo, Schedule.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(Schedule entity){
        //TODO 做一些数据校验,如唯一约束
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return removeByIds(ids);
    }
}
