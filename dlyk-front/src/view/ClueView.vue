<template>
  <el-button type="primary" class="btn" @click="addClue" v-hasPermission="'clue:add'">录入线索</el-button>
  <el-button type="success" class="btn" @click="importExcel" v-hasPermission="'clue:import'">导入线索(Excel)</el-button>
  <el-button type="danger" class="btn" @click="batchDelClue" v-hasPermission="'clue:delete'">批量删除</el-button>

  <el-table
      :data="clueList"
      style="width: 100%"
      @selection-change="handleSelectionChange">
    <el-table-column type="selection" width="50"/>
    <el-table-column type="index" label="序号" width="55"/>
    <el-table-column property="ownerDO.name" label="负责人" width="120" />
    <el-table-column property="activityDO.name" label="所属活动"/>
    <el-table-column label="姓名">

      <template #default="scope">
        <a href="javascript:" @click="view(scope.row.id)">{{ scope.row.fullName }}</a>
      </template>

    </el-table-column>
    <el-table-column property="appellationDO.typeValue" label="称呼"/>
    <el-table-column property="phone" label="手机" width="120"/>
    <el-table-column property="weixin" label="微信" width="120"/>
    <el-table-column property="n eedLoanDO.typeValue" label="是否贷款"/>
    <el-table-column property="intentionStateDO.typeValue" label="意向状态"/>
    <el-table-column property="intentionProductDO.name" label="意向产品"/>
    <el-table-column label="线索状态">
      <template #default="scope">
        <span style="background: lightgoldenrodyellow" v-if="scope.row.state === -1"> {{ scope.row.stateDO.typeValue }} </span>
        <span v-else> {{ scope.row.stateDO.typeValue }} </span>
      </template>
    </el-table-column>
    <el-table-column property="sourceDO.typeValue" label="线索来源"/>
    <el-table-column property="nextContactTime" label="下次联系时间" width="165"/>
    <el-table-column label="操作" width="230">
      <template #default="scope">
        <el-button type="primary" @click="view(scope.row.id)" v-hasPermission="'clue:view'">详情</el-button>
        <el-button type="success" @click="edit(scope.row.id)" v-hasPermission="'clue:edit'">编辑</el-button>
        <el-button type="danger" @click="del(scope.row.id)" v-hasPermission="'clue:delete'">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
  <p>
    <el-pagination
        background
        layout="prev, pager, next"
        :page-size="pageSize"
        :total="total"
        @prev-click="toPage"
        @next-click="toPage"
        @current-change="toPage"/>
  </p>

  <!--活动备注记录的弹窗-->
  <el-dialog v-model="importExcelDialogVisible" title="导入线索Excel" width="55%" center draggable>

      <el-upload
          ref="uploadRef"
          method="post"
          :http-request="uploadFile"
          :auto-upload="false">

        <template #trigger>
          <el-button type="primary">选择Excel文件</el-button>
        </template>
        仅支持后缀名为.xls或.xlsx的文件
        <template #tip>
          <div class="fileTip">
            重要提示：
            <ul>
              <li>上传仅支持后缀名为.xls或.xlsx的文件;</li>
              <li>给定Excel文件的第一行将视为字段名;</li>
              <li>请确认您的文件大小不超过50MB;</li>
              <li>日期值以文本形式保存，必须符合yyyy-MM-dd格式;</li>
              <li>日期时间以文本形式保存，必须符合yyyy-MM-ddHH:mm:ss的格式;</li>
            </ul>
          </div>
        </template>
      </el-upload>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="importExcelDialogVisible = false">关 闭</el-button>
        <el-button type="primary" @click="submitExcel">导 入</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script>
import {defineComponent} from "vue";
import {doDelete, doGet, doPost} from "../http/httpRequest.js";
import {messageTip, messsageConfirm} from "../util/util.js";

export default defineComponent({
  name: "ClueView",

  // 注入
  inject : ['reload'],

  data() {
    return{
      // 线索列表对象，初始值是空
      clueList:[{
        ownerDO : {},
        activityDO : {},
        appellationDO : {},
        needLoanDO : {},
        intentionStateDO : {},
        intentionProductDO : {},
        stateDO : {},
        sourceDO : {}
      }],
      // 分页时每页显示多少条数据
      pageSize: 0,
      // 分页总共查询出多少条数据
      total: 0,
      // 导入线索Excel的弹窗，true弹，false不弹
      importExcelDialogVisible: false
    }
  },

  mounted(){
    this.getData(1)
  },

  methods:{
    handleSelectionChange(){

    },

    // 查询列表数据
    getData(current) {
      doGet("/api/clues", {
        current: current // 当前查询第几页
      }).then(resp => {
        console.log(resp);
        if (resp.data.code === 200) {
          this.clueList = resp.data.data.list;
          this.pageSize = resp.data.data.pageSize;
          this.total = resp.data.data.total;
        }
      })
    },

    // 分页
    toPage(current){
      this.getData(current);
    },

    // 线索导入Excel
    importExcel(){
      // 弹个窗
      this.importExcelDialogVisible = true;
    },

    // 上传文件的真正请求提交
    uploadFile(param){
      console.log(param);
      let fileObj = param.file; // 相当于input里取得的files
      let formData = new FormData();
      formData.append('file',fileObj) // 文件对象，前面file是参数名，后面fileObj是参数值
      doPost("/api/importExcel",formData).then(resp => {
        if(resp.data.code === 200){
          messageTip("导入成功","success");
          // 清理一下上传的文件
          this.$refs.uploadRef.clearFiles();
          // 页面局部刷新
          this.reload();
        }else{
          messageTip("导入失败","error");
        }
      })
    },

    // 提交上传Excel文件
    submitExcel(){
      this.$refs.uploadRef.submit();
    },

    // 录入线索
    addClue(){
      this.$router.push("/dashboard/clue/add");
    },

    // 编辑
    edit(id){
      this.$router.push("/dashboard/clue/edit/" + id);
    },

    // 详情页面
    view(id){
      this.$router.push("/dashboard/clue/detail/" + id);
    },

    // 删除线索
    del(id) {
      messsageConfirm("是否删除该用户？").then(() => {
        // 用户点击‘确定’按钮就会触发then函数
        doDelete("/api/clue/" + id, {}).then(resp => {
          if (resp.data.code === 200) {
            messageTip("删除成功", "success");
            // 页面刷新
            this.reload();
          } else {
            messageTip("删除失败,原因：" + resp.data.msg, "error");
          }
        })
      }).catch(() => { // 用户点击'取消'按钮就会触发catch函数
        messageTip("取消删除", "warning");
      })
    },

  }

})
</script>

<style scoped>

.el-table{
  margin-top: 15px;
}
.fileTip{
  padding-top: 15px;
}
</style>