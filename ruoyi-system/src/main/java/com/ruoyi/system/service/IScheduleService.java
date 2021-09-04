package com.ruoyi.system.service;

import com.ruoyi.system.domain.Schedule;
import com.ruoyi.system.domain.vo.ScheduleVo;
import com.ruoyi.system.domain.bo.ScheduleBo;
import com.ruoyi.common.core.mybatisplus.core.IServicePlus;
import com.ruoyi.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 班次Service接口
 *
 * @author ruoyi
 * @date 2021-08-28
 */
public interface IScheduleService extends IServicePlus<Schedule, ScheduleVo> {
	/**
	 * 查询单个
	 * @return
	 */
	ScheduleVo queryById(String scheduleId);

	/**
	 * 查询列表
	 */
    TableDataInfo<ScheduleVo> queryPageList(ScheduleBo bo);

	/**
	 * 查询列表
	 */
	List<ScheduleVo> queryList(ScheduleBo bo);

	/**
	 * 根据新增业务对象插入班次
	 * @param bo 班次新增业务对象
	 * @return
	 */
	Boolean insertByBo(ScheduleBo bo);

	/**
	 * 根据编辑业务对象修改班次
	 * @param bo 班次编辑业务对象
	 * @return
	 */
	Boolean updateByBo(ScheduleBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);
}
