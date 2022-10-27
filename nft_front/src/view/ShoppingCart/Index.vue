<template>
    <div>
        <Top />

      <div v-if="ispaid">
        <!-- shopping cart -->
        <el-table
            :data="shoppingCart"
            border
            stripe
            style="width: 40%"
            @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" label="selectAll" width="100px"></el-table-column>
          <el-table-column prop="artName" label="Title" width="150px"></el-table-column>
          <el-table-column prop="resalePrice" label="Price" width="150px"></el-table-column>
          <el-table-column  label="Option"  >
            <template slot-scope="scope">
              <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeItem(scope.row)" >Delete</el-button>
            </template>
          </el-table-column>
        </el-table>
  
        <!-- total price -->
        <div class="totalPrice">
          <span>Total Price: {{ totalPrice }} ETH</span>
          <el-button type="danger" @click="handlePayment">Pay</el-button>
        </div>
      </div>
      <div class="countDown" v-else>
        <el-col :sm="18" :lg="18">
          <el-result icon="success" title="Successful payment">
          </el-result>
          <p>You have paid successfully. Return to the main page in {{ seconds }} seconds</p>
        </el-col>
      </div>

      <el-dialog title="please input your paykey" :visible.sync="dialogFormVisible" style="width: 80%">
        <el-form :model="form">
            <el-form-item label="My paykey" style="width: 200px">
                <el-input v-model="form.payKey" show-password autocomplete="off"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">Cancel</el-button>
            <el-button type="primary" @click="handleConfirmPurchase">Confirm</el-button>
        </div>
      </el-dialog>

    </div>
  </template>
  
  <script>
  import Top from '../../components/Top.vue'
  import { mapMutations, mapState } from 'vuex'
  export default {
    components: {
        Top
    },
    data() {
      return {
        checked: true,
        shoppingCart: [],
        multipleSelection: [],
        totalPrice: 0,
        ispaid: true,
        seconds: 5,
        dialogFormVisible: false,
        form: {
          payKey: ''
        }
      }
    },
  
    computed:{
      ...mapState(["balance", "userId"])
    },

    mounted(){
       this.getUserShoppingCart()
    },
  
    methods: {
        ...mapMutations(['deleteFromCart', 'clearCart']),

        async getUserShoppingCart(){
          this.$axios.post(this.apiUrl + '/shopping-car/getShoppingCar', {
            userId: this.userId
          }).then((res) => {
            if(res.data.message == "暂无内容，看看其他的吧"){
              return 
            }else if(res.data.message == "您还未登录"){
              this.$router.push('/home')
            } else{
              this.shoppingCart = res.data.data.arts
              for(let i = 0; i < this.shoppingCart.length; i++){
                this.$set(this.shoppingCart[i], 'artName', this.shoppingCart[i].art.artName)
              }
              this.totalPrice = res.data.data.totalPrice
            }
          })
        },

        handleSelectionChange(val){
          this.totalPrice = 0
          this.multipleSelection = val
          this.multipleSelection.forEach(item => {
          this.totalPrice += item.resalePrice 
          })
        },
    
    
        handleQuantityChange(){
          this.totalPrice = 0
          this.multipleSelection.forEach(item => {
          this.totalPrice += item.resalePrice 
          })
        },
    
        
        removeItem(item){
          this.$axios.post(this.apiUrl + '/shopping-car/deleteShoppingCar', {
            userId: this.userId,
            goodsId: item.goodsId
          }).then(() => {
            this.shoppingCart =  this.shoppingCart.filter(i => {
              return i.goodsId != item.goodsId
            })
            this.getUserShoppingCart()
            this.deleteFromCart(item)
          })
        },
    
        handlePayment(){
          if(this.totalPrice == 0){
            this.$message.error("Please select at least one item!")
          }else{
            if(this.balance >= this.totalPrice){
                this.dialogFormVisible = true
            }else{
                this.$message.error('Insufficient wallet balance')
            }
          }
        },

        async handleConfirmPurchase(){
          let goodsIdList = []
          this.shoppingCart.forEach(item => {
            goodsIdList.push(item.goodsId)
          })
          this.$axios.post(this.apiUrl + '/shopping-car/buyManyGoodsById', {
              userId: this.userId,
              goodsIdList,
              totalPrice: this.totalPrice,
              payKey: this.form.payKey
          }).then(() => {
            this.dialogFormVisible = false
            this.$message.success("Payment succeeded!")
            this.ispaid = false
            this.clearCart()
            new Promise((resolve, reject) => {
              let timer = setInterval(() => {
              if(this.seconds > 0){
                  this.seconds -= 1
              }else{
                  clearInterval(timer)
                  this.$router.replace("/home")
              }
              }, 1000)
            })
            })
        }
    }
  }
  </script>
  
  <style scoped>
  
  .el-button{
    margin: 20px;
  }
  
  .el-table{
    margin: 50px auto
  }
  
  ::v-deep .el-table__header .el-table-column--selection .cell .el-checkbox::after{
    color: #909399;
    content: "Select All";
    font-size: 10px;
    margin-left: 5px;
    font-weight: bold;
  }
  
  .totalPrice{
    margin: 20px auto;
    border: 1px solid #EBEEF5;
    width: 50%;
    height: 50px;
    position: relative;
  }
  
  .totalPrice span{
    position: absolute;
    line-height: 50px;
    color: #909399;
    right: 120px;
  }
  
  .totalPrice .el-button{
    position: absolute;
    width: 80px;
    margin: 0;
    padding: 0;
    height: 50px;
    right: 0px;
  }
  
  .countDown{
    width: 500px;
    height: 10px;
    margin: 0 auto;
    position: relative;
    top: 200px;
  }
  </style>