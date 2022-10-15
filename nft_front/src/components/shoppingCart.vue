<template>
    <div>
        <el-table ref="cartTable" :data="cartList" style="width: 90%;margin:0 auto" size="small" @selection-change="dealCartSelect" border>
            <el-table-column type="selection" width="55" align="center"> </el-table-column>
            <el-table-column label="商品" width="380" >
                <template v-slot="{ row }">
                    <div class="goodsShow">
                        <div class="goodsPic">
                            <el-image :src="row.img" :preview-src-list="[row.img]"> </el-image>
                        </div>
                        <div class="goodsDescribe">
                            <div class="name">商品：{{ row.name }}</div>
                            <div class="describe">描述：{{ row.describe }}</div>
                        </div>
                    </div>
                </template>
            </el-table-column>
            <el-table-column label="单价" width="150" align="center">
                <template v-slot="{ row }">
                   <span style="font-size:14px;font-weight:bold">￥ {{row.price}}</span>
                </template>
            </el-table-column>
            <el-table-column label="数量" width="160" align="center">
                <template v-slot="{ row ,$index}">
                    <el-input-number size="mini" v-model="row.totalNum" @change="changeTotalNum($event,$index,row)" :min="1" :max="999" ></el-input-number>
                </template>
            </el-table-column>
            <el-table-column label="小计" width="260" align="center">
                <template v-slot="{ row }" >
                    <span style="font-size:14px;font-weight:bold">
                     ￥ {{row.price * row.totalNum}}
                    </span>
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center">
                <template v-slot="{ row ,$index}">
                    <div>
                        <el-button type="danger" size="mini" @click="removeCart(row,$index)">移除</el-button>
                    </div>
                </template>
            </el-table-column>
        </el-table>

        <div class="staticGoodMes">
            <div class="boxLeft">
                <el-checkbox v-model="cartSelectAll" @change="dealCartSelectAll">全选</el-checkbox>
                <span class="extraButton" @click="removeSelectedCart">删除选中商品</span> 
                <span class="extraButton" @click="deleteAllCart">清空购物车</span>
            </div>
            <div class="boxRight">
                <el-popover
                    v-if="selectCartList.length"
                    placement="bottom"
                    title="已选中商品"
                    width="500"
                    trigger="click"
                    >
                    <div >
                        <el-tooltip v-for="(item,index) in selectCartList" :key="index" class="item" effect="dark" :content="'商品名称：'+item.name" placement="top">
                            <img :src="item.img" alt="" style="width:100px;height:100px">
                        </el-tooltip>
                    </div>
                    <span slot="reference" class="rightExtra" style="{cursor:pointer}" title="点击查看当前已选择商品">
                        已选择商品 <span style="color:#e2231a;">{{selectCartList.length}}</span> 件  
                        <i class="el-icon-arrow-down"></i>
                    </span> 
                </el-popover>
                <span v-else slot="reference" class="rightExtra" >
                        已选择商品 <span style="color:#e2231a;">0</span> 件  
                        <i class="el-icon-arrow-up"></i>
                    </span> 
                <span class="rightExtra">总价 <span style="color:#e2231a;font-size: 17px;">￥{{allSelectedGoodsPrice}}</span></span>
                <el-button>去结算</el-button>
            </div>
        </div>
    </div>
</template>
<script>
import { mapState, mapMutations } from "vuex";
export default {
    name: "shopCart",
    props: {},
    data(){
        return {
            cartSelectAll:false, //购物车是否全选
            selectCartList:[], //购物车选中商品信息
        }
    },
    created() {
        // this.setShopCart([{name:111}])
    },
    mounted() {
        // console.log(this.setShopCart([{name:111}]))
        // this.$store.commit('shopCart/setShopCart',[{name:2}])
        //   console.log(this.$store)
    },
    computed: {
        ...mapState("shopCart", ["cartList"]),
        //购物车选中商品总价
        allSelectedGoodsPrice(){
            let res = this.selectCartList.reduce((prev,target)=> prev+target.totalNum*target.price,0)
            return res
        }
    },
    methods: {
        ...mapMutations("shopCart", ["setShopCart"]),
        //购物车商品数量增加
        changeTotalNum(val,index,data){
            //此处需要掉用增加数量接口（后续补上）
            // console.log(val,index,data)
            this.cartList[index].totalNum = val
            this.setShopCart(this.cartList)
        },
        //购物车选择
        dealCartSelect(val){
            this.selectCartList = val
            console.log(val)
            if(val.length == this.cartList.length){
                this.cartSelectAll = true
            }
        },
        //购物车全选
        dealCartSelectAll(){
            this.$refs.cartTable.toggleAllSelection()
        },
        //购物车商品移除
        removeCart(val,idx){
            this.cartList.splice(idx,1)
            this.setShopCart(this.cartList)
        },
        //页面确认操作
        pageConfirm(word,callback){
            this.$confirm(`${word}`, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    callback()
                }).catch(() => {
                       
                });
        },
        //清除选中商品处理
        dealRemoveSelectedCart(){
            let res = this.selectCartList.map(item=>item.id)
            this.setShopCart(this.cartList.filter(item=>res.indexOf(item.id)==-1))
            
        },
        //清除选中商品
        removeSelectedCart(){
            this.pageConfirm('此操作将清除选中商品, 是否继续?',()=>{this.dealRemoveSelectedCart()})
        },
        //清空购物车
        deleteAllCart(){
            this.pageConfirm('此操作将清空购物车, 是否继续?',()=>{this.setShopCart([])})
        },
    },
    destroyed() { },
};
</script>
<style scoped lang="less" >
.goodsShow {
    display: flex;

    .goodsPic {
        flex: 1;

        img {
            width: 100%;
            height: 100%;
        }
    }

    .goodsDescribe {
        color: #333;
        flex: 3;

        .name {
            font-size: 14px;
        }

        .describe {
            font-size: 14px;
        }
    }
}
.staticGoodMes{
    position: sticky;
    bottom:0px;
    background-color: #fff;
    z-index: 999;
    width:90%;
    margin:0 auto;
    border: 1px solid silver;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .boxLeft{
        margin-left: 15px;
        .extraButton{
            margin-left:15px;
            font-size: 14px;
            &:hover{
                color:skyblue;
                font-weight:bold;
            }
        }
    }
    .boxRight{
        .rightExtra{
            font-size: 14px;
            color:#333;
            margin-right:15px;
            
            &:nth-child(2){
                display:inline-block;
                width:150px;
                text-align: left;
            }
        }
    }

}
</style>
  