package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 班次对象 schedule
 *
 * @author ruoyi
 * @date 2021-08-28
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("schedule")
public class Schedule implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * 主键id
     */
    @TableId(value = "schedule_id")
    private String scheduleId;

    /**
     * 名称
     */
    private String name;

    /**
     * 开始时段;格式  HH:mm
     */
    private String startHour;

    /**
     * 结束时段;格式  HH:mm
     */
    private String endHour;

    /**
     * 预约单位;30分钟    60分钟 。。。
     */
    private Long orderUnit;

    /**
     * 每预约单位几个人;默认 2人
     */
    private Long unitSplit;

    /**
     * 公司id
     */
    private String companyId;

    /**
     * 删除标志;0代表存在 2代表删除）
     */
    @TableLogic
    private String delFlag;

    /**
     * 创建者
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新者
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

}
