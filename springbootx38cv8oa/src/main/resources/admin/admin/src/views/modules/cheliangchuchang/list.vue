<template>
	<div class="main-content" :style='{"color":"#666","padding":"0px 30px 30px","fontSize":"14px"}'>
		<!-- 列表页 -->
		<template v-if="showFlag">
			<el-form class="center-form-pv" :style='{"padding":"0px 0px 0","margin":"0px","overflow":"hidden","flexWrap":"wrap","background":"none","display":"flex","fontSize":"inherit"}' :inline="true" :model="searchForm">
				<el-row :style='{"padding":"10px","margin":"0 0 10px","borderRadius":"0 0 8px 8px","textAlign":"left","background":"#fff","display":"block","width":"100%","fontSize":"inherit","order":"1"}' >
					<div :style='{"margin":"0 10px 0 0","fontSize":"inherit","display":"inline-block"}'>
						<label :style='{"margin":"0 10px 0 0","color":"inherit","display":"inline-block","lineHeight":"40px","fontSize":"inherit","fontWeight":"500","height":"40px"}' class="item-label">车位名称</label>
						<el-input v-model="searchForm.cheweimingcheng" placeholder="车位名称" @keydown.enter.native="search()" clearable></el-input>
					</div>
					<div :style='{"margin":"0 10px 0 0","fontSize":"inherit","display":"inline-block"}' class="select">
						<label :style='{"margin":"0 10px 0 0","color":"inherit","display":"inline-block","lineHeight":"40px","fontSize":"inherit","fontWeight":"500","height":"40px"}' class="item-label">是否支付</label>
						<el-select clearable v-model="searchForm.ispay" placeholder="是否支付">
							<el-option v-for="(item,index) in isPayOptions" v-bind:key="index" :label="item" :value="item"></el-option>
						</el-select>
					</div>
					<el-button class="search" type="success" @click="search()">
						<span class="icon iconfont icon-chakan14" :style='{"margin":"0 2px","fontSize":"inherit","color":"inherit","display":"none","height":"40px"}'></span>
						查询
					</el-button>
				</el-row>

				<el-row class="actions" :style='{"margin":"0px 0 20px","color":"#fff","flexWrap":"wrap","textAlign":"left","flexDirection":"row","display":"flex","width":"100%","fontSize":"inherit","order":"2"}'>
					<el-button class="add" v-if="isAuth('cheliangchuchang','新增')" type="success" @click="addOrUpdateHandler()">
						<span class="icon iconfont icon-tianjia14" :style='{"color":"inherit","margin":"0 2px","fontSize":"inherit"}'></span>
						添加
					</el-button>
					<el-button class="del" v-if="isAuth('cheliangchuchang','删除')" :disabled="dataListSelections.length?false:true" type="danger" @click="deleteHandler()">
						<span class="icon iconfont icon-shanchu6" :style='{"margin":"0 2px","fontSize":"inherit","color":"inherit","height":"40px"}'></span>
						删除
					</el-button>


					<el-button class="btn18" v-if="isAuth('cheliangchuchang','支付')" :disabled="dataListSelections.length?false:true" type="success" @click="payBatch()">
						<span class="icon iconfont icon-xihuan" :style='{"color":"inherit","margin":"0 2px","fontSize":"inherit"}'></span>
						批量支付
					</el-button>

					<el-button class="btn18" v-if="isAuth('cheliangchuchang','停车费统计')" type="success" @click="chartDialog1()">
						<span class="icon iconfont icon-a-fenxiang2" :style='{"color":"inherit","margin":"0 2px","fontSize":"inherit"}'></span>
						停车费统计
					</el-button>
					<el-button class="btn18" v-if="isAuth('cheliangchuchang','入场统计')" type="success" @click="chartDialog2()">
						<span class="icon iconfont icon-a-fenxiang2" :style='{"color":"inherit","margin":"0 2px","fontSize":"inherit"}'></span>
						入场统计
					</el-button>
					<el-button class="btn18" v-if="isAuth('cheliangchuchang','出校统计')" type="success" @click="chartDialog3()">
						<span class="icon iconfont icon-a-fenxiang2" :style='{"color":"inherit","margin":"0 2px","fontSize":"inherit"}'></span>
						出校统计
					</el-button>
				</el-row>
			</el-form>
			<div :style='{"border":"0px solid #eee","width":"100%","padding":"0","margin":"0px 0 0","borderRadius":"0px","background":"rgba(255,255,255,.9)"}'>
				<el-table class="tables"
					:stripe='false'
					:style='{"padding":"0","borderColor":"#edf7ff","color":"inherit","borderRadius":"0px","borderWidth":"2px 2px 0 2px","background":"none","width":"100%","fontSize":"inherit","borderStyle":"solid"}' 
					:border='true'
					v-if="isAuth('cheliangchuchang','查看')"
					:data="dataList"
					v-loading="dataListLoading"
				@selection-change="selectionChangeHandler">
					<el-table-column :resizable='true' type="selection" align="center" width="50"></el-table-column>
					<el-table-column :resizable='true' :sortable='true' label="序号" type="index" width="50" />
					<el-table-column :resizable='true' :sortable='true'  
						prop="cheweibianhao"
						label="车位编号">
						<template slot-scope="scope">
							{{scope.row.cheweibianhao}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'  
						prop="cheweimingcheng"
						label="车位名称">
						<template slot-scope="scope">
							{{scope.row.cheweimingcheng}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'  
						prop="cheweileixing"
						label="校门信息">
						<template slot-scope="scope">
							{{scope.row.cheweileixing}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'  
						prop="ruchangshijian"
						label="入场时间">
						<template slot-scope="scope">
							{{scope.row.ruchangshijian}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'  
						prop="chuchangshijian"
						label="出校时间">
						<template slot-scope="scope">
							{{scope.row.chuchangshijian}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'  
						prop="xiaoshijiage"
						label="小时价格">
						<template slot-scope="scope">
							{{scope.row.xiaoshijiage}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'  
						prop="tingcheshizhang"
						label="停车时长">
						<template slot-scope="scope">
							{{scope.row.tingcheshizhang}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'  
						prop="tingchefeiyong"
						label="停车费用">
						<template slot-scope="scope">
							{{scope.row.tingchefeiyong}}
						</template>
					</el-table-column>
					<!-- 无 -->
					<el-table-column :resizable='true' :sortable='true' prop="tupian" width="200" label="图片">
						<template slot-scope="scope">
							<div v-if="scope.row.tupian">
								<img v-if="scope.row.tupian.substring(0,4)=='http'" :src="scope.row.tupian.split(',')[0]" width="100" height="100" style="object-fit: cover">
								<img v-else :src="$base.url+scope.row.tupian.split(',')[0]" width="100" height="100" style="object-fit: cover">
							</div>
							<div v-else>无图片</div>
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'  
						prop="chepaihao"
						label="车牌号">
						<template slot-scope="scope">
							{{scope.row.chepaihao}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'  
						prop="yanse"
						label="颜色">
						<template slot-scope="scope">
							{{scope.row.yanse}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'  
						prop="weizhi"
						label="位置">
						<template slot-scope="scope">
							{{scope.row.weizhi}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'  
						prop="yonghuzhanghao"
						label="用户账号">
						<template slot-scope="scope">
							{{scope.row.yonghuzhanghao}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true'  
						prop="yonghuxingming"
						label="用户姓名">
						<template slot-scope="scope">
							{{scope.row.yonghuxingming}}
						</template>
					</el-table-column>
					<el-table-column :resizable='true' :sortable='true' prop="ispay" label="是否支付">
						<template slot-scope="scope">
							<span style="margin-right:10px">{{scope.row.ispay=='已支付'?'已支付':'未支付'}}</span>
							<el-button v-if="scope.row.ispay!='已支付' && isAuth('cheliangchuchang','支付') " type="text" size="small" @click="payHandler(scope.row)">支付</el-button>
						</template>
					</el-table-column>
					<el-table-column width="300" label="操作">
						<template slot-scope="scope">
							<el-button class="view" v-if=" isAuth('cheliangchuchang','查看')" type="success" @click="addOrUpdateHandler(scope.row.id,'info')">
								<span class="icon iconfont icon-chakan2" :style='{"margin":"0 2px","fontSize":"inherit","color":"inherit","height":"40px"}'></span>
								查看
							</el-button>
							<el-button class="edit" v-if=" isAuth('cheliangchuchang','修改') " type="success" @click="addOrUpdateHandler(scope.row.id)">
								<span class="icon iconfont icon-xiugai10" :style='{"margin":"0 2px","fontSize":"inherit","color":"inherit","height":"40px"}'></span>
								修改
							</el-button>




							<el-button class="del" v-if="isAuth('cheliangchuchang','删除') " type="primary" @click="deleteHandler(scope.row.id )">
								<span class="icon iconfont icon-guanbi1" :style='{"margin":"0 2px","fontSize":"inherit","color":"inherit","height":"40px"}'></span>
								删除
							</el-button>
						</template>
					</el-table-column>
				</el-table>
			</div>
			<el-pagination
				@size-change="sizeChangeHandle"
				@current-change="currentChangeHandle"
				:current-page="pageIndex"
				background
				:page-sizes="[10, 50, 100, 200]"
				:page-size="pageSize"
				:layout="layouts.join()"
				:total="totalPage"
				prev-text="上一页 "
				next-text="下一页 "
				:hide-on-single-page="false"
				:style='{"padding":"0","margin":"20px 0 0","whiteSpace":"nowrap","color":"inherit","textAlign":"left","width":"100%","fontSize":"inherit","fontWeight":"500"}'
			></el-pagination>
		</template>
		
		<!-- 添加/修改页面  将父组件的search方法传递给子组件-->
		<add-or-update v-if="addOrUpdateFlag" :parent="this" ref="addOrUpdate"></add-or-update>




		<el-dialog
		  :visible.sync="chartVisiable1"
		  width="800">
			<div id="cheweimingchengChart1" style="width:100%;height:600px;"></div>
		  <span slot="footer" class="dialog-footer">
			<el-button @click="chartDialog1">返回</el-button>
		  </span>
		</el-dialog>
		<el-dialog
		  :visible.sync="chartVisiable2"
		  width="800">
			<div id="cheweileixingChart2" style="width:100%;height:600px;"></div>
		  <span slot="footer" class="dialog-footer">
			<el-button @click="chartDialog2">返回</el-button>
		  </span>
		</el-dialog>
		<el-dialog
		  :visible.sync="chartVisiable3"
		  width="800">
			<div id="ruchangshijianChart3" style="width:100%;height:600px;"></div>
		  <span slot="footer" class="dialog-footer">
			<el-button @click="chartDialog3">返回</el-button>
		  </span>
		</el-dialog>
	</div>
</template>

<script>
import * as echarts from 'echarts'
import chinaJson from "@/components/echarts/china.json";
import axios from 'axios'
import AddOrUpdate from "./add-or-update";
	export default {
		data() {
			return {
				searchForm: {
					key: ""
				},
				form:{},
				isPayOptions: [],
				dataList: [],
				pageIndex: 1,
				pageSize: 10,
				totalPage: 0,
				dataListLoading: false,
				dataListSelections: [],
				showFlag: true,
				chartVisiable1: false,
				chartVisiable2: false,
				chartVisiable3: false,
				addOrUpdateFlag:false,
				layouts: ["prev","pager","next","sizes","jumper"],
			};
		},
		created() {
			this.init();
			this.getDataList();
			this.contentStyleChange()
		},
		mounted() {
		},
		filters: {
			htmlfilter: function (val) {
				return val.replace(/<[^>]*>/g).replace(/undefined/g,'');
			}
		},
		computed: {
			tablename(){
				return this.$storage.get('sessionTable')
			},
		},
		components: {
			AddOrUpdate,
		},
		methods: {
			contentStyleChange() {
				this.contentPageStyleChange()
			},
			// 分页
			contentPageStyleChange(){
				let arr = []

				// if(this.contents.pageTotal) arr.push('total')
				// if(this.contents.pageSizes) arr.push('sizes')
				// if(this.contents.pagePrevNext){
				//   arr.push('prev')
				//   if(this.contents.pagePager) arr.push('pager')
				//   arr.push('next')
				// }
				// if(this.contents.pageJumper) arr.push('jumper')
				// this.layouts = arr.join()
				// this.contents.pageEachNum = 10
			},
			payHandler(row){
				this.$storage.set('paytable','cheliangchuchang');
				this.$storage.set('payObject',row);
				this.$router.push('pay');
			},
			// 批量支付
			payBatch(){
				for(let x in this.dataListSelections){
					if(this.dataListSelections[x].ispay=='已支付'){
						this.$message.error('所选订单存在已支付订单')
						return false
					}
				}
				this.$confirm('是否支付所选订单？').then(_ => {
					let arr = JSON.parse(JSON.stringify(this.dataListSelections))
					for(let i in arr){
						arr[i].ispay = '已支付'
						this.$http({
							url: 'cheliangchuchang/update',
							method: "post",
							data: arr[i]
						}).then(res=>{
							
						})
					}
					this.$message({
						message: "支付成功",
						type: "success",
						duration: 1500,
						onClose: () => {
							this.getDataList()
						}
					});
				}).catch(_ => {});
			},


//统计接口
    chartDialog1() {
      this.chartVisiable1 = !this.chartVisiable1;
      this.$nextTick(()=>{
        var cheweimingchengChart1 = echarts.init(document.getElementById("cheweimingchengChart1"),'macarons');
        this.$http({
            url: `cheliangchuchang/valueMul/cheweimingcheng?yColumnNameMul=xiaoshijiage,tingcheshizhang,tingchefeiyong`,
            method: "get",
        }).then(({ data }) => {
            if (data && data.code === 0) {
                let res = data.data;
                let xAxis1 = [];
                let yAxis1 = [];
                let pArray1 = []
                for(let i=0;i<res[0].length;i++){
                    xAxis1.push(res[0][i].cheweimingcheng);
                    yAxis1.push(parseFloat((res[0][i].total)));
                    pArray1.push({
                        value: parseFloat((res[0][i].total)),
                        name: res[0][i].cheweimingcheng
                    })
                }
                let xAxis2 = [];
                let yAxis2 = [];
                let pArray2 = []
                for(let i=0;i<res[1].length;i++){
                    xAxis2.push(res[1][i].cheweimingcheng);
                    yAxis2.push(parseFloat((res[1][i].total)));
                    pArray2.push({
                        value: parseFloat((res[1][i].total)),
                        name: res[1][i].cheweimingcheng
                    })
                }
                let xAxis3 = [];
                let yAxis3 = [];
                let pArray3 = []
                for(let i=0;i<res[2].length;i++){
                    xAxis3.push(res[2][i].cheweimingcheng);
                    yAxis3.push(parseFloat((res[2][i].total)));
                    pArray3.push({
                        value: parseFloat((res[2][i].total)),
                        name: res[2][i].cheweimingcheng
                    })
                }
                var option = {};
                option = {
                    title: {
                        left: 'left'
                    },
                    legend: {
                        data: [
							'小时价格',
							'停车时长',
							'停车费用',
						]
                    },
                    tooltip: {
                      trigger: 'axis',
                    },
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        data: xAxis1
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [
                    {
                        data: yAxis1,
                        type: 'line',
                        name: '小时价格',
                    },
                    {
                        data: yAxis2,
                        type: 'line',
                        name: '停车时长',
                    },
                    {
                        data: yAxis3,
                        type: 'line',
                        name: '停车费用',
                    },
                    ]
                };
                // 使用刚指定的配置项和数据显示图表。
                cheweimingchengChart1.setOption(option);
                  //根据窗口的大小变动图表
                window.onresize = function() {
                    cheweimingchengChart1.resize();
                };
            }
        });
      })
    },

//统计接口
    chartDialog2() {
      this.chartVisiable2 = !this.chartVisiable2;
      this.$nextTick(()=>{

        var cheweileixingChart2 = echarts.init(document.getElementById("cheweileixingChart2"),'macarons');
        this.$http({
            url: "cheliangchuchang/group/cheweileixing",
            method: "get",
        }).then(({ data }) => {
            if (data && data.code === 0) {
                let res = data.data;
                let xAxis = [];
                let yAxis = [];
                let pArray = []
                for(let i=0;i<res.length;i++){
                    xAxis.push(res[i].cheweileixing);
                    yAxis.push(parseFloat((res[i].total)));
                    pArray.push({
                        value: parseFloat((res[i].total)),
                        name: res[i].cheweileixing
                    })
                }
                var option = {};
                option = {
                    title: {
                        text: '入场统计',
                        left: 'center'
                    },
                    tooltip: {
                      trigger: 'item',
                      formatter: '{b} : {c}'
                    },
                    xAxis: {
                        type: 'category',
                        data: xAxis,
                        axisLabel : {
                            rotate:40
                        }
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: yAxis,
                        type: 'bar'
                    }]
                };
                // 使用刚指定的配置项和数据显示图表。
                cheweileixingChart2.setOption(option);
                  //根据窗口的大小变动图表
                window.onresize = function() {
                    cheweileixingChart2.resize();
                };
            }
        });
      })
    },

//统计接口
    chartDialog3() {
      this.chartVisiable3 = !this.chartVisiable3;
      this.$nextTick(()=>{

        var ruchangshijianChart3 = echarts.init(document.getElementById("ruchangshijianChart3"),'macarons');
        this.$http({
            url: "cheliangchuchang/group/ruchangshijian",
            method: "get",
        }).then(({ data }) => {
            if (data && data.code === 0) {
                let res = data.data;
                let xAxis = [];
                let yAxis = [];
                let pArray = []
                for(let i=0;i<res.length;i++){
                    xAxis.push(res[i].ruchangshijian);
                    yAxis.push(parseFloat((res[i].total)));
                    pArray.push({
                        value: parseFloat((res[i].total)),
                        name: res[i].ruchangshijian
                    })
                }
                var option = {};
                option = {
                    title: {
                        text: '出校统计',
                        left: 'center'
                    },
                    tooltip: {
                      trigger: 'item',
                      formatter: '{b} : {c}'
                    },
                    xAxis: {
                        type: 'category',
                        data: xAxis,
                        axisLabel : {
                            rotate:40
                        }
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: yAxis,
                        type: 'bar'
                    }]
                };
                // 使用刚指定的配置项和数据显示图表。
                ruchangshijianChart3.setOption(option);
                  //根据窗口的大小变动图表
                window.onresize = function() {
                    ruchangshijianChart3.resize();
                };
            }
        });
      })
    },


    init () {
		this.isPayOptions = "已支付,未支付".split(',')
    },
    search() {
      this.pageIndex = 1;
      this.getDataList();
    },

    // 获取数据列表
    getDataList() {
      this.dataListLoading = true;
      let params = {
        page: this.pageIndex,
        limit: this.pageSize,
        sort: 'id',
        order: 'desc',
      }
           if(this.searchForm.cheweimingcheng!='' && this.searchForm.cheweimingcheng!=undefined){
            params['cheweimingcheng'] = '%' + this.searchForm.cheweimingcheng + '%'
          }
			if(this.searchForm.ispay!='' && this.searchForm.ispay!=undefined){
				params['ispay'] = this.searchForm.ispay
			}
			let user = JSON.parse(this.$storage.getObj('userForm'))
			console.log(user)
			this.$http({
				url: "cheliangchuchang/page",
				method: "get",
				params: params
			}).then(({ data }) => {
				if (data && data.code === 0) {
					this.dataList = data.data.list;
					this.totalPage = data.data.total;
				} else {
					this.dataList = [];
					this.totalPage = 0;
				}
				this.dataListLoading = false;
			});
    },
    // 每页数
    sizeChangeHandle(val) {
      this.pageSize = val;
      this.pageIndex = 1;
      this.getDataList();
    },
    // 当前页
    currentChangeHandle(val) {
      this.pageIndex = val;
      this.getDataList();
    },
    // 多选
    selectionChangeHandler(val) {
      this.dataListSelections = val;
    },
    // 添加/修改
    addOrUpdateHandler(id,type) {
      this.showFlag = false;
      this.addOrUpdateFlag = true;
      this.crossAddOrUpdateFlag = false;
      if(type!='info'){
        type = 'else';
      }
      this.$nextTick(() => {
        this.$refs.addOrUpdate.init(id,type);
      });
    },
    // 下载
    download(file){
      let arr = file.replace(new RegExp('upload/', "g"), "")
      axios.get(this.$base.url + 'file/download?fileName=' + arr, {
      	headers: {
      		token: this.$storage.get('Token')
      	},
      	responseType: "blob"
      }).then(({
      	data
      }) => {
      	const binaryData = [];
      	binaryData.push(data);
      	const objectUrl = window.URL.createObjectURL(new Blob(binaryData, {
      		type: 'application/pdf;chartset=UTF-8'
      	}))
      	const a = document.createElement('a')
      	a.href = objectUrl
      	a.download = arr
      	// a.click()
      	// 下面这个写法兼容火狐
      	a.dispatchEvent(new MouseEvent('click', {
      		bubbles: true,
      		cancelable: true,
      		view: window
      	}))
      	window.URL.revokeObjectURL(data)
      },err=>{
		  axios.get((location.href.split(this.$base.name).length>1 ? location.href.split(this.$base.name)[0] :'') + this.$base.name + '/file/download?fileName=' + arr, {
		  	headers: {
		  		token: this.$storage.get('Token')
		  	},
		  	responseType: "blob"
		  }).then(({
		  	data
		  }) => {
		  	const binaryData = [];
		  	binaryData.push(data);
		  	const objectUrl = window.URL.createObjectURL(new Blob(binaryData, {
		  		type: 'application/pdf;chartset=UTF-8'
		  	}))
		  	const a = document.createElement('a')
		  	a.href = objectUrl
		  	a.download = arr
		  	// a.click()
		  	// 下面这个写法兼容火狐
		  	a.dispatchEvent(new MouseEvent('click', {
		  		bubbles: true,
		  		cancelable: true,
		  		view: window
		  	}))
		  	window.URL.revokeObjectURL(data)
		  })
	  })
    },
	// 预览
	preClick(file){
		if(!file){
			return false
		}
		window.open((location.href.split(this.$base.name).length>1 ? location.href.split(this.$base.name)[0] + this.$base.name + '/' + file :this.$base.url + file))
	},
	cheliangchuchangstatusChange(e,row){
		if(row.status==0){
			row.passwordwrongnum = 0
		}
		this.$http({
			url:'cheliangchuchang/update',
			method:'post',
			data:row
		}).then(res=>{
			if(row.status==1){
				this.$message.error('该用户已锁定')
			}else{
				this.$message.success('该用户已解除锁定')
			}
		})
	},
    // 删除
    async deleteHandler(id ) {
		var ids = id? [Number(id)]: this.dataListSelections.map(item => {
			return Number(item.id);
		});
		await this.$confirm(`确定进行[${id ? "删除" : "批量删除"}]操作?`, "提示", {
			confirmButtonText: "确定",
			cancelButtonText: "取消",
			type: "warning"
		}).then(async () => {
			await this.$http({
				url: "cheliangchuchang/delete",
				method: "post",
				data: ids
			}).then(async ({ data }) => {
				if (data && data.code === 0) {
					this.$message({
						message: "操作成功",
						type: "success",
						duration: 1500,
						onClose: () => {
							this.search();
						}
					});
            
				} else {
					this.$message.error(data.msg);
				}
			});
		});
    },


  }

};
</script>
<style lang="scss" scoped>
	
	.center-form-pv {
	  .el-date-editor.el-input {
	    width: auto;
	  }
	}
	
	.el-input {
	  width: auto;
	}
	
	// form
	.center-form-pv .el-input /deep/ .el-input__inner {
				border: 0px solid #eee;
				border-radius: 0px;
				padding: 0 12px;
				outline: none;
				color: inherit;
				background: #f0f8ff;
				width: 170px;
				font-size: inherit;
				height: 32px;
			}
	
	.center-form-pv .el-select /deep/ .el-input__inner {
				border: 0px solid #eee;
				border-radius: 0px;
				padding: 0 10px;
				box-shadow: 0 0 0px rgba(64, 158, 255, .5);
				outline: none;
				color: inherit;
				background: #f0f8ff;
				width: 170px;
				font-size: inherit;
				height: 32px;
			}
	
	.center-form-pv .el-date-editor /deep/ .el-input__inner {
				border: 0px solid #eee;
				border-radius: 0px;
				padding: 0 10px 0 30px;
				box-shadow: 0 0 0px rgba(64, 158, 255, .5);
				outline: none;
				color: inherit;
				background: #f0f8ff;
				width: 170px;
				font-size: inherit;
				height: 32px;
			}
	
	.center-form-pv .search {
				border: 0;
				cursor: pointer;
				border-radius: 0px;
				padding: 0 20px;
				outline: none;
				color: #fff;
				background: #00afab;
				width: auto;
				font-size: inherit;
				transition: all 0.3s;
				height: 32px;
			}
	
	.center-form-pv .search:hover {
				transform: scale(0.9);
				opacity: 1;
			}
	
	.center-form-pv .actions .add {
				border: 0px solid rgba(126, 96, 16, .2);
				cursor: pointer;
				border-radius: 6px;
				padding: 0 16px 0 10px;
				margin: 4px 4px 5px 0;
				outline: none;
				color: inherit;
				background: #1e3c4f;
				width: auto;
				font-size: inherit;
				height: 32px;
			}
	
	.center-form-pv .actions .add:hover {
				transform: scale(0.9);
				opacity: 1;
			}
	
	.center-form-pv .actions .del {
				border: 0px solid rgba(126, 96, 16, .2);
				cursor: pointer;
				border-radius: 6px;
				padding: 0 16px 0 10px;
				margin: 4px 4px 5px;
				outline: none;
				color: inherit;
				background: #1e3c4f;
				width: auto;
				font-size: inherit;
				height: 32px;
			}
	
	.center-form-pv .actions .del:hover {
				transform: scale(0.9);
				opacity: 1;
			}
	
	.center-form-pv .actions .statis {
				border: 0px solid rgba(126, 96, 16, .2);
				cursor: pointer;
				border-radius: 6px;
				padding: 0 16px 0 10px;
				margin: 4px 4px 5px;
				outline: none;
				color: inherit;
				background: #1e3c4f;
				width: auto;
				font-size: inherit;
				height: 32px;
			}
	
	.center-form-pv .actions .statis:hover {
				transform: scale(0.9);
				opacity: 1;
			}
	
	.center-form-pv .actions .btn18 {
				border: 0px solid rgba(126, 96, 16, .2);
				cursor: pointer;
				border-radius: 6px;
				padding: 0 16px 0 10px;
				margin: 4px 4px 5px;
				outline: none;
				color: inherit;
				background: #1e3c4f;
				width: auto;
				font-size: inherit;
				height: 32px;
			}
	
	.center-form-pv .actions .btn18:hover {
				transform: scale(0.9);
				opacity: 1;
			}
	
	// table
	.el-table /deep/ .el-table__header-wrapper thead {
				color: inherit;
				background: #fff;
				width: 100%;
			}
	
	.el-table /deep/ .el-table__header-wrapper thead tr {
				background: none;
			}
	
	.el-table /deep/ .el-table__header-wrapper thead tr th {
				padding: 12px 0;
				background: none;
				border-color: #edf7ff;
				border-width: 0 2px 2px 0;
				border-style: solid;
				text-align: left;
			}

	.el-table /deep/ .el-table__header-wrapper thead tr th .cell {
				padding: 0 10px;
				word-wrap: normal;
				word-break: break-all;
				white-space: normal;
				font-weight: 600;
				display: inline-block;
				vertical-align: middle;
				width: 100%;
				line-height: 24px;
				position: relative;
				text-overflow: ellipsis;
			}

	
	.el-table /deep/ .el-table__body-wrapper tbody {
				padding: 0;
				width: 100%;
			}

	.el-table /deep/ .el-table__body-wrapper tbody tr {
				background: none;
			}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td {
				padding: 2px 0;
				color: inherit;
				background: none;
				font-size: inherit;
				border-color: #edf7ff;
				border-width: 0 2px 2px 0px;
				border-style: solid;
				text-align: left;
			}
	
		
	.el-table /deep/ .el-table__body-wrapper tbody tr:hover td {
				padding: 2px 0;
				color: #000;
				background: #edf7ff50;
				border-color: #edf7ff;
				border-width: 0 2px 2px 0px;
				border-style: solid;
				text-align: left;
			}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td {
				padding: 2px 0;
				color: inherit;
				background: none;
				font-size: inherit;
				border-color: #edf7ff;
				border-width: 0 2px 2px 0px;
				border-style: solid;
				text-align: left;
			}

	.el-table /deep/ .el-table__body-wrapper tbody tr td .cell {
				padding: 0 10px;
				overflow: hidden;
				color: inherit;
				word-break: break-all;
				white-space: normal;
				line-height: 24px;
				text-overflow: ellipsis;
			}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td .view {
				border: 0px solid rgba(126, 96, 16, .2);
				cursor: pointer;
				border-radius: 0px;
				padding: 0 12px 0 6px;
				margin: 0 10px 5px 0;
				outline: none;
				color: #2e6160;
				background: #edf7ff;
				width: auto;
				font-size: 14px;
				height: 32px;
			}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td .view:hover {
				transform: scale(1.05);
				opacity: 0.8;
			}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td .add {
			}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td .add:hover {
			}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td .edit {
				border: 0px solid #36ab80;
				cursor: pointer;
				border-radius: 0px;
				padding: 0 12px 0 6px;
				margin: 0 10px 5px 0;
				outline: none;
				color: #2e6160;
				background: #edf7ff;
				width: auto;
				font-size: 14px;
				height: 32px;
			}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td .edit:hover {
				transform: scale(1.05);
				opacity: 0.8;
			}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td .del {
				border: 0px solid #ab3636;
				cursor: pointer;
				border-radius: 0px;
				padding: 0 12px 0 6px;
				margin: 0 10px 5px 0;
				outline: none;
				color: #2e6160;
				background: #edf7ff;
				width: auto;
				font-size: 14px;
				height: 32px;
			}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td .del:hover {
				transform: scale(1.05);
				opacity: 0.8;
			}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td .btn8 {
				border: 0px solid #3696ab;
				cursor: pointer;
				border-radius: 0px;
				padding: 0 12px 0 6px;
				margin: 0 10px 5px 0;
				outline: none;
				color: #2e6160;
				background: #edf7ff;
				width: auto;
				font-size: 14px;
				height: 32px;
			}
	
	.el-table /deep/ .el-table__body-wrapper tbody tr td .btn8:hover {
				transform: scale(1.05);
				opacity: 0.8;
			}
	
	// pagination
	.main-content .el-pagination /deep/ .el-pagination__total {
				margin: 0 10px 0 0;
				color: inherit;
				font-weight: 400;
				display: inline-block;
				vertical-align: top;
				font-size: inherit;
				line-height: 28px;
				height: 28px;
			}
	
	.main-content .el-pagination /deep/ .btn-prev {
				border: none;
				border-radius: 2px;
				padding: 0 5px;
				margin: 0 5px;
				color: inherit;
				background: #fff;
				display: inline-block;
				vertical-align: top;
				font-size: inherit;
				line-height: 24px;
				min-width: 35px;
				height: 24px;
			}
	
	.main-content .el-pagination /deep/ .btn-next {
				border: none;
				border-radius: 2px;
				padding: 0 5px;
				margin: 0 5px;
				color: inherit;
				background: #fff;
				display: inline-block;
				vertical-align: top;
				font-size: inherit;
				line-height: 24px;
				min-width: 35px;
				height: 24px;
			}
	
	.main-content .el-pagination /deep/ .btn-prev:disabled {
				border: none;
				cursor: not-allowed;
				border-radius: 2px;
				padding: 0 5px;
				margin: 0 5px;
				color: #999;
				background: #fff;
				display: inline-block;
				vertical-align: top;
				font-size: inherit;
				line-height: 24px;
				height: 24px;
			}
	
	.main-content .el-pagination /deep/ .btn-next:disabled {
				border: none;
				cursor: not-allowed;
				border-radius: 2px;
				padding: 0 5px;
				margin: 0 5px;
				color: #999;
				background: #fff;
				display: inline-block;
				vertical-align: top;
				font-size: inherit;
				line-height: 24px;
				height: 24px;
			}

	.main-content .el-pagination /deep/ .el-pager {
				padding: 0;
				margin: 0;
				display: inline-block;
				vertical-align: top;
				font-size: inherit;
			}

	.main-content .el-pagination /deep/ .el-pager .number {
				cursor: pointer;
				padding: 0 4px;
				margin: 0 5px;
				color: inherit;
				background: #fff;
				display: inline-block;
				vertical-align: top;
				font-size: inherit;
				line-height: 24px;
				text-align: center;
				height: 24px;
			}
	
	.main-content .el-pagination /deep/ .el-pager .number:hover {
				cursor: pointer;
				padding: 0 4px;
				margin: 0 5px;
				color: #fff;
				background: #00afaa;
				display: inline-block;
				vertical-align: top;
				font-size: inherit;
				line-height: 24px;
				text-align: center;
				height: 24px;
			}
	
	.main-content .el-pagination /deep/ .el-pager .number.active {
				cursor: default;
				padding: 0 4px;
				margin: 0 5px;
				color: #fff;
				background: #00afaa;
				display: inline-block;
				vertical-align: top;
				font-size: inherit;
				line-height: 24px;
				text-align: center;
				height: 24px;
			}
	
	.main-content .el-pagination /deep/ .el-pagination__sizes {
				color: inherit;
				display: inline-block;
				vertical-align: top;
				font-size: inherit;
				line-height: 28px;
				height: 28px;
			}
	
	.main-content .el-pagination /deep/ .el-pagination__sizes .el-input {
				margin: 0 5px;
				color: inherit;
				width: 100px;
				font-size: inherit;
				position: relative;
			}
	
	.main-content .el-pagination /deep/ .el-pagination__sizes .el-input .el-input__inner {
				border: 0px solid #ddd;
				cursor: pointer;
				padding: 0 25px 0 8px;
				color: inherit;
				display: inline-block;
				font-size: inherit;
				line-height: 28px;
				border-radius: 3px;
				outline: 0;
				background: none;
				width: 100%;
				text-align: center;
				height: 28px;
			}
	
	.main-content .el-pagination /deep/ .el-pagination__sizes .el-input span.el-input__suffix {
				top: 0;
				position: absolute;
				right: 0;
				height: 100%;
			}
	
	.main-content .el-pagination /deep/ .el-pagination__sizes .el-input .el-input__suffix .el-select__caret {
				cursor: pointer;
				color: #C0C4CC;
				width: 25px;
				font-size: 14px;
				line-height: 28px;
				text-align: center;
			}
	
	.main-content .el-pagination /deep/ .el-pagination__jump {
				margin: 0 0 0 24px;
				color: inherit;
				display: inline-block;
				vertical-align: top;
				font-size: inherit;
				line-height: 28px;
				height: 28px;
			}
	
	.main-content .el-pagination /deep/ .el-pagination__jump .el-input {
				border-radius: 3px;
				padding: 0 2px;
				margin: 0 2px;
				color: inherit;
				display: inline-block;
				width: 50px;
				font-size: inherit;
				line-height: 18px;
				position: relative;
				text-align: center;
				height: 28px;
			}
	
	.main-content .el-pagination /deep/ .el-pagination__jump .el-input .el-input__inner {
				border: 1px solid #eee;
				cursor: pointer;
				padding: 0 0px;
				color: inherit;
				display: inline-block;
				font-size: inherit;
				line-height: 24px;
				border-radius: 3px;
				outline: 0;
				background: #fff;
				width: auto;
				text-align: center;
				height: 24px;
			}
	
	// list one
	.one .list1-view {
				border: 0px solid rgba(126, 96, 16, .2);
				cursor: pointer;
				border-radius: 0px;
				padding: 0 6px 0 4px;
				margin: 0px 5px 5px 0;
				outline: none;
				color: #2e6160;
				background: #edf7ff;
				width: auto;
				font-size: inherit;
				height: 32px;
			}
	
	.one .list1-view:hover {
				transform: scale(1.1) skew(-10deg, 10deg);
				opacity: 0.8;
			}
	
	.one .list1-edit {
				border: 0px solid rgba(254, 148, 34, .2);
				cursor: pointer;
				border-radius: 0px;
				padding: 0 6px 0 4px;
				margin: 0px 5px 5px 0;
				outline: none;
				color: #2e6160;
				background: #edf7ff;
				width: auto;
				font-size: inherit;
				height: 32px;
			}
	
	.one .list1-edit:hover {
				transform: scale(1.1) skew(-10deg, 10deg);
				opacity: 0.8;
			}
	
	.one .list1-del {
				border: 0px solid rgba(180, 52, 31, .2);
				cursor: pointer;
				border-radius: 0px;
				padding: 0 6px 0 4px;
				margin: 0px 5px 5px 0;
				outline: none;
				color: #2e6160;
				background: #edf7ff;
				width: auto;
				font-size: inherit;
				height: 32px;
			}
	
	.one .list1-del:hover {
				transform: scale(1.1) skew(-10deg, 10deg);
				opacity: 0.8;
			}
	
	.one .list1-btn8 {
				border: 0px solid rgba(31, 160, 180, .2);
				cursor: pointer;
				border-radius: 0px;
				padding: 0 6px 0 4px;
				margin: 0px 5px 5px 0;
				outline: none;
				color: #2e6160;
				background: #edf7ff;
				width: auto;
				font-size: inherit;
				height: 32px;
			}
	
	.one .list1-btn8:hover {
				transform: scale(1.1) skew(-10deg, 10deg);
				opacity: 0.8;
			}
	
	.main-content .el-table .el-switch {
				display: inline-flex;
				vertical-align: middle;
				line-height: 30px;
				position: relative;
				align-items: center;
				height: 30px;
			}
	.main-content .el-table .el-switch /deep/ .el-switch__label--left {
				cursor: pointer;
				margin: 0 10px 0 0;
				color: #333;
				font-weight: 500;
				display: inline-block;
				vertical-align: middle;
				font-size: 16px;
				transition: .2s;
				height: 30px;
			}
	.main-content .el-table .el-switch /deep/ .el-switch__label--right {
				cursor: pointer;
				margin: 0 0 0 10px;
				color: #333;
				font-weight: 500;
				display: inline-block;
				vertical-align: middle;
				font-size: 16px;
				transition: .2s;
				height: 30px;
			}
	.main-content .el-table .el-switch /deep/ .el-switch__core {
				border: 1px solid #8a8a8a;
				cursor: pointer;
				border-radius: 15px;
				margin: 0;
				outline: 0;
				background: #8a8a8a;
				display: inline-block;
				width: 40px;
				box-sizing: border-box;
				transition: border-color .3s,background-color .3s;
				height: 20px;
			}
	.main-content .el-table .el-switch /deep/ .el-switch__core::after {
				border-radius: 100%;
				top: 1px;
				left: 1px;
				background: #FFF;
				width: 16px;
				position: absolute;
				transition: all .3s;
				height: 16px;
			}
	.main-content .el-table .el-switch.is-checked /deep/ .el-switch__core::after {
				margin: 0 0 0 -17px;
				left: 100%;
			}
	
	.main-content .el-table .el-rate /deep/ .el-rate__item {
				cursor: pointer;
				display: inline-block;
				vertical-align: middle;
				font-size: 0;
				position: relative;
			}
	.main-content .el-table .el-rate /deep/ .el-rate__item .el-rate__icon {
				margin: 0 3px;
				color: #666;
				display: inline-block;
				font-size: 18px;
				position: relative;
				transition: .3s;
			}
</style>
