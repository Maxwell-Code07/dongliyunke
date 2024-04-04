<template>

  <!--概览统计-->
  <el-row>
    <el-col :span="6">
      <el-statistic :value="summaryData.effectiveActivityCount">
        <template #title>
          <div style="display: inline-flex; align-items: center">
            市场活动
          </div>
        </template>
        <template #suffix>/{{summaryData.totalActivityCount}}</template>
      </el-statistic>
    </el-col>

    <el-col :span="6">
      <el-statistic title="线索总数" :value="summaryData.totalClueCount" />
    </el-col>

    <el-col :span="6">
      <el-statistic title="客户总数" :value="summaryData.totalCustomerCount" />
    </el-col>

    <el-col :span="6">
      <el-statistic :value="summaryData.successTranAmount">
        <template #title>
          <div style="display: inline-flex; align-items: center">
            交易总额
          </div>
        </template>
        <template #suffix>/{{summaryData.totalTranAmount}}</template>
      </el-statistic>
    </el-col>

  </el-row>

  <!--销售漏斗图-->
  <div id="saleFunnelChart" style="width: 48%;height:350px; margin-top: 100px; float:left;">图渲染在此处</div>

  <!--线索来源饼图-->
  <div id="sourcePieChart" style="width: 30%;height:350px; margin: 100px; float:left;">图渲染在此处</div>

</template>

<script>

import {defineComponent} from "vue";
import {doGet} from "../http/httpRequest.js";
import * as echarts from 'echarts';

export default defineComponent({
  name: "StatisticView",

  data(){
    return{
      // 概览统计数据对象，初始值是空
      summaryData:{},
    }
  },

  mounted(){
    this.loadSummary();

    // 加载销售漏斗图
    this.loadSaleFunnelChart();

    // 加载线索来源饼图
    this.loadSourcePieChart();
  },

  methods:{
    // 加载概览统计数据
    loadSummary(){
      doGet("/api/summary/data",{}).then(resp => {
        if(resp.data.code === 200){
          this.summaryData = resp.data.data;
        }
      })
    },

    // 加载销售漏斗图
    loadSaleFunnelChart(){

      // 先查数据，再渲染图，不能先渲染图，然后再查数据
      doGet("/api/saleFunnel/data",{}).then(resp => {
        if(resp.data.code === 200){
          let saleFunelData = resp.data.data;
          // 1、拿到页面上渲染图标的dom元素
          var chartDom = document.getElementById('saleFunnelChart');
          // 2、使用echarts组件对dom进行初始化，得到一个空白的图表对象
          var myChart = echarts.init(chartDom);

          // 3、配置可选项（查看文档-->配置项手册）
          var option = {

            // 标题组件
            title: {
              // 主标题文本，支持使用\n换行
              text: '销售漏斗图',
              left: 'center',
              top: 'bottom'
            },

            // 提示框组件
            tooltip: {
              // 提示框的显示方式，触发类型
              trigger: 'item',
              // 提示框的显示方式，提示框浮层内容格式化器，
              // 在漏斗图中：{a}（系列名称），{b}（数据项名称），{c}（数值）, {d}（百分比）
              formatter: '{a} <br/>{b} : {c}'
            },

            // 工具栏
            toolbox: {
              // 各工具配置项
              feature: {
                // 数据视图工具，可以展现当前图表所用的数据，编辑后可以动态更新。
                dataView: {
                  // 是否不可编辑（只读）。
                  readOnly: false
                },
                // 配置项还原
                restore: {},
                // 保存为图片
                saveAsImage: {}
              }
            },

            // 图例组件
            legend: {
              data: ['线索', '客户', '交易', '成交']
            },

            // 系列
            series: [
              {
                // 系列名称
                name: '销售漏斗数据统计',
                // 图表的类型，funnel代表漏斗图
                type: 'funnel',
                // 漏斗图组件离容器左侧的距离
                left: '10%',
                // 漏斗图组件离容器上侧的距离
                top: 60,
                // 漏斗图组件离容器下侧的距离
                bottom: 60,
                width: '80%',
                min: 0,
                max: 100,
                minSize: '0%',
                maxSize: '100%',
                sort: 'descending',
                gap: 2,
                label: {
                  show: true,
                  position: 'inside'
                },
                labelLine: {
                  length: 10,
                  lineStyle: {
                    width: 1,
                    type: 'solid'
                  }
                },
                itemStyle: {
                  borderColor: '#fff',
                  borderWidth: 1
                },
                emphasis: {
                  label: {
                    fontSize: 20
                  }
                },
                // 数据项（系列中的数据内容数组，数组项可以为单个数值，也可以是对象值）
                // data: [
                //   { value: 20, name: '成交' },
                //   { value: 60, name: '交易' },
                //   { value: 80, name: '客户' },
                //   { value: 100, name: '线索' }
                // ]
                data : saleFunelData
              }
            ]
          };

          // 4、如果配置了可选项，就把可选项设置到空白的图表对象中
          option && myChart.setOption(option);
        }
      })
    },

    // 加载线索来源饼图
    loadSourcePieChart(){
      // 先查数据，再渲染图，不能先渲染图，然后再查数据
      doGet("/api/sourcePie/data",{}).then(resp => {
        if(resp.data.code === 200) {
          let sourcePieData = resp.data.data;

          // 1、拿到图表的DOM元素
          var chartDom = document.getElementById('sourcePieChart');

          // 2、使用echarts组件对dom进行初始化，得到一个空白的图表对象
          var myChart = echarts.init(chartDom);

          // 3、配置可选项（查看文档-->配置项手册）
          var option = {
            title: {
              text: '线索来源统计',
              left: 'center',
              top: 'bottom'
            },
            tooltip: {
              trigger: 'item'
            },

            // 图例
            legend: {
              orient: 'horizontal',
              left: 'left'
            },
            // 系列
            series: [
              {
                name: '线索来源统计',
                type: 'pie',
                // 饼图的半径
                radius: '60%',
                // 数据项
                // data: [
                //   { value: 1048, name: 'Search Engine' },
                //   { value: 735, name: 'Direct' },
                //   { value: 580, name: 'Email' },
                //   { value: 484, name: 'Union Ads' },
                //   { value: 300, name: 'Video Ads' }
                // ],
                data: sourcePieData,
                emphasis: {
                  itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
                }
              }
            ]
          };

          // 4、如果配置了可选项，就把可选项设置到空白的图表对象中
          option && myChart.setOption(option);
        }
      });
    }
  }
})
</script>

<style scoped>
.el-row{
  text-align: center;
}
</style>