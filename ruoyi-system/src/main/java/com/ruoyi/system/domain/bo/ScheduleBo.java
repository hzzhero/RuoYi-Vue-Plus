package com.ruoyi.system.domain.bo;

import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 班次业务对象 schedule
 *
 * @author ruoyi
 * @date 2021-08-28
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("班次业务对象")
public class ScheduleBo extends BaseEntity {

    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private String scheduleId;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 开始时段;格式  HH:mm
     */
    @ApiModelProperty(value = "开始时段;格式  HH:mm")
    private String startHour;

    /**
     * 结束时段;格式  HH:mm
     */
    @ApiModelProperty(value = "结束时段;格式  HH:mm")
    private String endHour;

    /**
     * 预约单位;30分钟    60分钟 。。。
     */
    @ApiModelProperty(value = "预约单位;30分钟    60分钟 。。。")
    private Long orderUnit;

    /**
     * 每预约单位几个人;默认 2人
     */
    @ApiModelProperty(value = "每预约单位几个人;默认 2人")
    private Long unitSplit;

    /**
     * 公司id
     */
    @ApiModelProperty(value = "公司id")
    private String companyId;

    /**
     * 删除标志;0代表存在 2代表删除）
     */
    @ApiModelProperty(value = "删除标志;0代表存在 2代表删除）")
    private String delFlag;


    /**
     * 分页大小
     */
    @ApiModelProperty("分页大小")
    private Integer pageSize;

    /**
     * 当前页数
     */
    @ApiModelProperty("当前页数")
    private Integer pageNum;

    /**
     * 排序列
     */
    @ApiModelProperty("排序列")
    private String orderByColumn;

    /**
     * 排序的方向desc或者asc
     */
    @ApiModelProperty(value = "排序的方向", example = "asc,desc")
    private String isAsc;

}
