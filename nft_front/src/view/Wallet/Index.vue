<template>
    <div>
        <div v-if="!myNftShow">
            <el-drawer :visible.sync="showDrawer" :with-header="false" :before-close="handleClose">
            <div style="flex-direction: row;display: flex;">
					<img src="../../assets/images/logo/login_wallet.png"
						style="width: 35px;height:35px;margin-left: 10px;margin-top: 10px;">
					<div class="palette_style">
                        My Wallet
                    </div>
				</div>
			<hr />
            <div class="myWallet">
                <div class="walletBalance">
                    <p>Total balance</p> 
                    <p>{{ balance }} ETH</p>
                </div>
                <div class="buttonArea">
                    <el-button 
                    @click="handleOpenBackCardForm" 
                    class="recharge" type="primary" 
                    style="width: 150px;margin-top: 20px;margin-right: 30px;"
                    >
                        Add funds
			        </el-button>
                    <el-button 
                    @click="handleCheckMyNFT" 
                    class="checkNFTButton" type="primary" 
                    style="width: 150px;margin-top: 20px;margin-right: 30px;"
                    >
                        My NFTs
			        </el-button>
                    <el-button 
                    @click="handlePurchaseRecords" 
                    class="checkPurchaseRecord" type="primary" 
                    style="width: 200px;margin-top: 20px;margin-right: 30px;"
                    >
                        View purchase records
			        </el-button>
                    <el-button 
                    @click="handleLoginOut" 
                    class="loginOutButton" type="danger" 
                    style="width: 150px;margin-top: 20px;margin-right: 30px;"
                    >
                        Log out
			        </el-button>
                </div>
            </div>
        </el-drawer>
        </div>
        <div v-else>
            <el-drawer :visible.sync="showDrawer" :with-header="false" :before-close="handleClose">
            <div style="flex-direction: row;display: flex;">
					<img src="../../assets/images/logo/login_wallet.png"
						style="width: 35px;height:35px;margin-left: 10px;margin-top: 10px;">
					<div class="palette_style">
                        My Wallet
                    </div>
				</div>
			<hr />
            <div v-for="item in myNftList" :key="item.artId" class="myNft">
                <img :src="require(`../../../../nft_back/nft/img${item.showFile.filePath}`)" :alt="`${item.artIntroduction}`">
                <p>{{ item.artName }}</p>
                <p>
                    <el-button type="primary" @click="handleUpdateMyNFT(item)" style="width:30px" >
                        <i class="el-icon-edit myNftButton"></i>
                    </el-button>
                    <el-button type="success" @click="handleSetSell(item)" style="width:30px">
                        <i class="el-icon-setting myNftButton"></i>
                    </el-button>
                </p>
            </div>
        </el-drawer>
        </div>

        <el-dialog title="Recharge form" :visible.sync="dialogBankCardForm" style="width: 80%">
            <el-form :model="bankCardForm">
                <el-form-item label="Set bank card:" style="width: 200px" v-if="!bankCard">
                    <el-input v-model="bankCardForm.card" minlength="16" show-word-limit autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="Recharge amount:" style="width: 200px">
                    <el-input v-model="bankCardForm.recharge" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogBankCardForm = false">Cancel</el-button>
                <el-button type="primary" @click="handleAddFunds">Confirm</el-button>
            </div>
        </el-dialog>

        <el-dialog title="Art information form" :visible.sync="dialogformVisible1" style="width: 80%">
            <el-form :model="form1">
                <el-form-item label="Set art name:" style="width: 200px">
                    <el-input v-model="form1.artName" autocomplete="off" :placeholder="selectedNft.artName"></el-input>
                </el-form-item>
                <el-form-item label="Set art introcution:" style="width: 200px">
                    <el-input v-model="form1.artIntroduction" autocomplete="off" :placeholder="selectedNft.artIntroduction"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogformVisible1 = false">Cancel</el-button>
                <el-button type="primary" @click="updateArtInfor">Confirm</el-button>
            </div>
        </el-dialog>

        <el-dialog title="Set sell form" :visible.sync="dialogformVisible2" style="width: 80%">
            <el-form :model="form2">
                <el-form-item label="Set for sell:" style="width: 200px">
                    <el-switch v-model="form2.sell"></el-switch>
                </el-form-item>
                <template v-if="form2.sell">
                    <el-form-item label="Set price:" style="width: 200px">
                        <el-input v-model="form2.price" autocomplete="off"></el-input>
                    </el-form-item>
                </template>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogformVisible2 = false">Cancel</el-button>
                <el-button type="primary" @click="handleSoldOut">Confirm</el-button>
            </div>
        </el-dialog>

        <el-dialog title="Purchase record" :visible.sync="dialogPurchaseRecordForm" style="width: 80%">
            <div v-for="(item, index) in purchaseRecords" :key="item.goodsId">
                <p>Order number: {{ index + 1}}</p>
                <p>GoodsId: {{ item.goodsId }}</p>
                <p>Purchase Time: {{ item.createTime | dateFormat }}</p>
                <p>Cost: {{ item.coin }}</p>
                <hr>
            </div>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="dialogPurchaseRecordForm=false">Confirm</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import { mapMutations, mapState } from 'vuex'
export default {
    props: ['showDrawer'],
    data() {
        return {
            myNftShow: false,
            myNftList: [],
            balance: 0,
            dialogBankCardForm: false,
            dialogformVisible1: false,
            dialogformVisible2: false,
            dialogPurchaseRecordForm: false,
            form1: {
                artName: '',
                artIntroduction: ''
            },
            form2: {
                price: 0,
                sell: false,
            },
            bankCardForm: {
                card: '',
                recharge: 0,
            },
            purchaseRecords: [],
            selectedNft: {}
        }
    },

    computed:{
        ...mapState(['userId', 'bankCard', "marketNFTs"])
    },

    mounted(){
        this.getBalance()
    },

    methods: {
        ...mapMutations(['delToken', 'setBalance', 'setMarketNFTs', 'setBankCard']),

        async getBalance(){
            let res = await this.$axios.post(this.apiUrl + '/user/getBalance', {
                userId: this.userId
            })
            this.balance = res.data.data
            this.setBalance(res.data.data)
        },

        handleClose(){
			this.$emit('showProfile', false)
		},

        handleLoginOut(){
            this.delToken()
            this.$emit('showProfile', false)
            this.$message.success('Successfully logged out !')
        },

        async handleCheckMyNFT(){
            this.myNftShow = true
            let res = await this.$axios.post(this.apiUrl + '/art/getOwnerAllArt', {
                ownerId: this.userId,
                sort: 1
            })
            this.myNftList = res.data.data
        },
    
        handleUpdateMyNFT(item){
            this.$confirm('Do you want to modify the inform1ation of the NFT?', 'message', {
                confirmButtonText: 'confirm',
                cancelButtonText: 'cancel',
                type: 'warning'
            }).then(() => {
                this.dialogformVisible1 = true
                this.selectedNft = item
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: 'Cancel modeified'
                });          
            });
        },

        handleSetSell(item){
            this.$confirm('Do you want to change the sell status for the NFT?', 'message', {
                confirmButtonText: 'confirm',
                cancelButtonText: 'cancel',
                type: 'warning'
            }).then(() => {
                this.dialogformVisible2 = true
                this.selectedNft = item
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: 'Cancel modeified'
                });          
            });
        },

        async updateArtInfor(){
            let res = await this.$axios.post(this.apiUrl + '/art/setArt', {
                artId: this.selectedNft.artId,
                artName: this.form1.artName ? this.form1.artName : this.selectedNft.artName,
                artIntroduction: this.form1.artIntroduction ? this.form1.artIntroduction : this.selectedNft.artIntroduction
            })
            if(res.status == 200){
                this.form1.artName = ""
                this.form1.artIntroduction = ""
                this.handleCheckMyNFT()
                this.$message.success('Update information successfully')
            }
            this.dialogformVisible1 = false
        },

        async setArtForSell(){
            for(let i = 0; i < this.marketNFTs.length; i++){
                if(this.marketNFTs[i].goodsId == this.selectedNft.artId){
                    this.$message.error('goods already exist in market')
                    return
                }
            }
            this.$axios.post(this.apiUrl + '/art/setSell', {
                ownerId: this.userId,
                artId: this.selectedNft.artId,
                price: this.form2.price,
            }).then(async () => {
                let res = await this.$axios.post(this.apiUrl+"/goods/getAllGoods", {
                    start: 1,
                    limit: 10
                })
                
                this.setMarketNFTs(res.data.data.data)
                this.$message.success('Set for sell successfully')
            })
            this.dialogformVisible2 = false
        },

        async setArtForNotSell(){
            this.$axios.post(this.apiUrl + '/art/setNotSell', {
                artId: this.selectedNft.artId,
            }).then(async () => {
                let res = await this.$axios.post(this.apiUrl+"/goods/getAllGoods", {
                    start: 1,
                    limit: 10
                })
                console.log(res);
                this.setMarketNFTs(res.data.data.data)
                this.$message.success('Set for Not sell successfully')
            })
            this.dialogformVisible2 = false
        },

        handleOpenBackCardForm(){
            this.dialogBankCardForm = true
        },

        handlePurchaseRecords(){
            this.$axios.post(this.apiUrl + '/user/getBuyOrder', {
                userId: this.userId
            }).then((res) => {
                console.log(res);
                this.dialogPurchaseRecordForm = true
                if(res.data.message !== "暂无内容，看看其他的吧"){
                    this.purchaseRecords = res.data.data
                }
            })
        },

        async handleAddFunds(){
            if(!this.bankCard && !this.bankCardForm.card){
                this.$message.error('Please bind your bank card')
                return
            }
            if(!this.bankCardForm.recharge){
                this.$message.error('The recharge amount can not be 0')
                return
            }
            this.$axios.post(this.apiUrl + "/user/charge", {
                userId: this.userId,
                ETHBAmount: this.bankCardForm.recharge,
                bankCard: this.bankCard ? this.bankCard : this.bankCardForm.card
            }).then((res)=>{
                if(res.data.message == "银行卡号格式不正确"){
                    this.$message.error('Incorrect format of bank card number')
                }else{
                    this.setBankCard({
                        card: this.bankCardForm.card
                    })
                    this.dialogBankCardForm = false
                    this.getBalance()
                }
            })
        },

        handleSoldOut(){
            if(this.form2.sell && this.form2.price == 0){
                this.$message.error('The price can not be 0')
                return
            }
            if(this.form2.sell){
                this.setArtForSell()
            }else{
                this.setArtForNotSell()
            }
        }
    },
}
</script>

<style lang="less" scoped>
    .myWallet{
        width: 100%;
        height: 300px;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        .walletBalance{
            width: 400px;
            height: 150px;
            border: 1px solid grey;
            border-radius: 5%;
            > :last-child{
                font-size: 25px;
                font-weight: bold;
            }
        }
    }
    .buttonArea{
        position: relative;
    }
    .loginOutButton{
        position: absolute;
        top: 300px;
        right: 28%;
    }
    .myNft{
        display: flex;
        height: 200px;
        flex-wrap: wrap;
        cursor: pointer;
        display: inline-block;
        margin-right: 20px;
        img{
            flex: 1;
            height: 150px;
        }
        span{
            font-size: 16px;
        }
        .myNftButton{
            position: relative;
            right: 5px;
        }
    }
</style>