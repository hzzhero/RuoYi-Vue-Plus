package com.ruoyi.web.controller.system;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.service.ISysUserService;
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
import com.ruoyi.system.domain.vo.CoachVo;
import com.ruoyi.system.domain.bo.CoachBo;
import com.ruoyi.system.service.ICoachService;
import com.ruoyi.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * VIEWController
 *
 * @author ruoyi
 * @date 2021-08-29
 */
@Validated
@Api(value = "VIEW控制器", tags = {"VIEW管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/system/coach")
public class CoachController extends BaseController {

    private final ISysUserService iSysUserService;

    /**
     * 查询VIEW列表
     */
    @ApiOperation("查询教练列表")
    @PreAuthorize("@ss.hasPermi('system:coach:list')")
    @GetMapping("/list")
    public TableDataInfo<SysUser> list(@Validated CoachBo bo) {
        return iCoachService.queryPageList(bo);
    }

    /**
     * 导出VIEW列表
     */
    @ApiOperation("导出VIEW列表")
    @PreAuthorize("@ss.hasPermi('system:coach:export')")
    @Log(title = "VIEW", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated CoachBo bo, HttpServletResponse response) {
        List<CoachVo> list = iCoachService.queryList(bo);
        ExcelUtil.exportExcel(list, "VIEW", CoachVo.class, response);
    }

    /**
     * 获取VIEW详细信息
     */
    @ApiOperation("获取VIEW详细信息")
    @PreAuthorize("@ss.hasPermi('system:coach:query')")
    @GetMapping("/{userId}")
    public AjaxResult<CoachVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("userId") Long userId) {
        return AjaxResult.success(iCoachService.queryById(userId));
    }

    /**
     * 新增VIEW
     */
    @ApiOperation("新增VIEW")
    @PreAuthorize("@ss.hasPermi('system:coach:add')")
    @Log(title = "VIEW", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody CoachBo bo) {
        return toAjax(iCoachService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改VIEW
     */
    @ApiOperation("修改VIEW")
    @PreAuthorize("@ss.hasPermi('system:coach:edit')")
    @Log(title = "VIEW", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody CoachBo bo) {
        return toAjax(iCoachService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除VIEW
     */
    @ApiOperation("删除VIEW")
    @PreAuthorize("@ss.hasPermi('system:coach:remove')")
    @Log(title = "VIEW" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] userIds) {
        return toAjax(iCoachService.deleteWithValidByIds(Arrays.asList(userIds), true) ? 1 : 0);
    }
}
