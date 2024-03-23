<template>
  <el-button type="primary" class="btn" @click="addClue">录入线索</el-button>
  <el-button type="success" class="btn" @click="importExcel">录入线索</el-button>
  <el-button type="danger" class="btn" @click="batchDelClue">录入线索</el-button>

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
    <el-table-column property="needLoanDO.typeValue" label="是否贷款"/>
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
        <el-button type="primary" @click="view(scope.row.id)">详情</el-button>
        <el-button type="success" @click="edit(scope.row.id)">编辑</el-button>
        <el-button type="danger" @click="del(scope.row.id)">删除</el-button>
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

</template>

<script>
import {defineComponent} from "vue";
import {doGet} from "../http/httpRequest.js";

export default defineComponent({
  name: "ClueView",

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

  }

})
</script>

<style scoped>

</style>