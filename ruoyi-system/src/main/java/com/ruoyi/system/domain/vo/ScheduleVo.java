package com.ruoyi.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.ExcelDictFormat;
import com.ruoyi.common.convert.ExcelDictConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;



/**
 * 班次视图对象 schedule
 *
 * @author ruoyi
 * @date 2021-08-28
 */
@Data
@ApiModel("班次视图对象")
@ExcelIgnoreUnannotated
public class ScheduleVo {

	private static final long serialVersionUID = 1L;

	/**
     *  主键id
     */
	@ApiModelProperty("主键id")
	private String scheduleId;

    /**
     * 名称
     */
	@ExcelProperty(value = "名称")
	@ApiModelProperty("名称")
	private String name;

    /**
     * 开始时段;格式  HH:mm
     */
	@ExcelProperty(value = "开始时段;格式  HH:mm")
	@ApiModelProperty("开始时段;格式  HH:mm")
	private String startHour;

    /**
     * 结束时段;格式  HH:mm
     */
	@ExcelProperty(value = "结束时段;格式  HH:mm")
	@ApiModelProperty("结束时段;格式  HH:mm")
	private String endHour;

    /**
     * 预约单位;30分钟    60分钟 。。。
     */
	@ExcelProperty(value = "预约单位;30分钟    60分钟 。。。")
	@ApiModelProperty("预约单位;30分钟    60分钟 。。。")
	private Long orderUnit;

    /**
     * 每预约单位几个人;默认 2人
     */
	@ExcelProperty(value = "每预约单位几个人;默认 2人")
	@ApiModelProperty("每预约单位几个人;默认 2人")
	private Long unitSplit;

    /**
     * 公司id
     */
	@ExcelProperty(value = "公司id")
	@ApiModelProperty("公司id")
	private String companyId;


}
