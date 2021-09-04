<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="所属驾校" prop="companyId">
         <el-select v-model="queryParams.companyId" placeholder="请选择驾校">
            <el-option
              v-for="item in companys"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
         </el-select>
      </el-form-item>
      <el-form-item label="班次名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入班次名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:schedule:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:schedule:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:schedule:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          :loading="exportLoading"
          @click="handleExport"
          v-hasPermi="['system:schedule:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="scheduleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="scheduleId" v-if="false"/>
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="开始时段" align="center" prop="startHour" />
      <el-table-column label="结束时段" align="center" prop="endHour" />
      <el-table-column label="单位时长（分钟）" align="center" prop="orderUnit" />
      <el-table-column label="每时段人数" align="center" prop="unitSplit" />
      <el-table-column label="所属驾校" align="center" prop="companyId" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:schedule:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:schedule:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改班次对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="所属驾校" prop="companyId">
          <el-select v-model="form.companyId" placeholder="请选择驾校">
            <el-option
              v-for="item in companys"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="班次名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称" />
        </el-form-item>
        <el-form-item label="开始时段" prop="startHour">
          <!-- <el-input v-model="form.startHour" placeholder="请输入开始时段" /> -->
          <el-time-select
            v-model="form.startHour"
            :picker-options="{
              start: '08:00',
              step: '00:30',
              end: '22:00'
            }"
            placeholder="选择开始时段">
          </el-time-select>
        </el-form-item>
        <el-form-item label="结束时段" prop="endHour">
          <!-- <el-input v-model="form.endHour" placeholder="请输入结束时段" /> -->
          <el-time-select
            v-model="form.endHour"
            :picker-options="{
              start: '08:00',
              step: '00:30',
              end: '22:00'
            }"
            placeholder="请输入结束时段">
          </el-time-select>
        </el-form-item>
        <el-form-item label="单位时长" prop="orderUnit">
          <el-input-number v-model="form.orderUnit" :step="30"></el-input-number>
        </el-form-item>
        <el-form-item label="每时段人数" prop="unitSplit">
          <el-input-number v-model="form.unitSplit" :step="1" step-strictly></el-input-number>
        </el-form-item>
       
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listSchedule, getSchedule, delSchedule, addSchedule, updateSchedule } from "@/api/system/schedule";
import { downLoadExcel } from "@/utils/download";

const initForm = {
        scheduleId: undefined,
        name: undefined,
        startHour: '08:00',
        endHour: '17:00',
        orderUnit: 60,
        unitSplit: 2,
        companyId: undefined,
      }

export default {
  name: "Schedule",
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: false,
      // 总条数
      total: 0,
      // 班次表格数据
      scheduleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        startHour: undefined,
        endHour: undefined,
        orderUnit: undefined,
        unitSplit: undefined,
        companyId: undefined,
      },
      companys: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        companyId: [
            { required: true, message: '请输入公司', trigger: 'blur' },
          ],
        name: [
          { required: true, message: '请输入班次名称', trigger: 'blur' },
        ],
        startHour: [
          { required: true, message: '请输入班次开始时间', trigger: 'blur' },
        ],
        endHour: [
          { required: true, message: '请输入班次结束时间', trigger: 'blur' },
        ],
        orderUnit: [
          { required: true, message: '请输入班次开始时间', trigger: 'blur' },
        ],
        unitSplit: [
          { required: true, message: '请输入班次结束时间', trigger: 'blur' },
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询班次列表 */
    getList() {
      this.loading = true;
      listSchedule(this.queryParams).then(response => {
        this.scheduleList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {...initForm};
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.scheduleId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加班次";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      const scheduleId = row.scheduleId || this.ids
      getSchedule(scheduleId).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改班次";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.scheduleId != null) {
            updateSchedule(this.form).then(response => {
              this.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addSchedule(this.form).then(response => {
              this.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const scheduleIds = row.scheduleId || this.ids;
      this.$confirm('是否确认删除班次编号为"' + scheduleIds + '"的数据项?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          this.loading = true;
          return delSchedule(scheduleIds);
        }).then(() => {
          this.loading = false;
          this.getList();
          this.msgSuccess("删除成功");
      }).finally(() => {
          this.loading = false;
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      downLoadExcel('/system/schedule/export', this.queryParams);
    }
  }
};
</script>
