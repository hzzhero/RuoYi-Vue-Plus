package com.ruoyi.web.controller.system;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.validate.AddGroup;
import com.ruoyi.common.core.validate.EditGroup;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.vo.ScheduleVo;
import com.ruoyi.system.domain.bo.ScheduleBo;
import com.ruoyi.system.service.IScheduleService;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 班次Controller
 *
 * @author ruoyi
 * @date 2021-08-28
 */
@Validated
@Api(value = "班次控制器", tags = {"班次管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/system/schedule")
public class ScheduleController extends BaseController {

    private final IScheduleService iScheduleService;

    /**
     * 查询班次列表
     */
    @ApiOperation("查询班次列表")
    @PreAuthorize("@ss.hasPermi('system:schedule:list')")
    @GetMapping("/list")
    public TableDataInfo<ScheduleVo> list(@Validated ScheduleBo bo) {
        return iScheduleService.queryPageList(bo);
    }

    /**
     * 导出班次列表
     */
    @ApiOperation("导出班次列表")
    @PreAuthorize("@ss.hasPermi('system:schedule:export')")
    @Log(title = "班次", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated ScheduleBo bo, HttpServletResponse response) {
        List<ScheduleVo> list = iScheduleService.queryList(bo);
        ExcelUtil.exportExcel(list, "班次", ScheduleVo.class, response);
    }

    /**
     * 获取班次详细信息
     */
    @ApiOperation("获取班次详细信息")
    @PreAuthorize("@ss.hasPermi('system:schedule:query')")
    @GetMapping("/{scheduleId}")
    public AjaxResult<ScheduleVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("scheduleId") String scheduleId) {
        return AjaxResult.success(iScheduleService.queryById(scheduleId));
    }

    /**
     * 新增班次
     */
    @ApiOperation("新增班次")
    @PreAuthorize("@ss.hasPermi('system:schedule:add')")
    @Log(title = "班次", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody ScheduleBo bo) {
        return toAjax(iScheduleService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改班次
     */
    @ApiOperation("修改班次")
    @PreAuthorize("@ss.hasPermi('system:schedule:edit')")
    @Log(title = "班次", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody ScheduleBo bo) {
        return toAjax(iScheduleService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除班次
     */
    @ApiOperation("删除班次")
    @PreAuthorize("@ss.hasPermi('system:schedule:remove')")
    @Log(title = "班次" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{scheduleIds}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable String[] scheduleIds) {
        return toAjax(iScheduleService.deleteWithValidByIds(Arrays.asList(scheduleIds), true) ? 1 : 0);
    }
}
