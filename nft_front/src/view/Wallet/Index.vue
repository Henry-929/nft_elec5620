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
                <div>
                    <el-button 
                    @click="handleLoginOut" 
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
            <div v-for="item in myNftList" :key="item.artId" class="myNft" @click="handleClickMyNFT(item)">
                <img :src="require(`../../../../nft_back/nft/img${item.showFile.filePath}`)" :alt="`${item.artIntroduction}`">
                <p>{{ item.artName }}</p>
            </div>
        </el-drawer>
        </div>

        <el-dialog title="please input your paykey" :visible.sync="dialogFormVisible" style="width: 80%">
            <el-form :model="form">
                <el-form-item label="set Price" style="width: 200px">
                    <el-input v-model="form.price" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="My paykey" style="width: 200px">
                    <el-input v-model="form.payKey" show-password autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">Cancel</el-button>
                <el-button type="primary" @click="handleConfirmSoldOut">Confirm</el-button>
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
            dialogFormVisible: false,
            form: {
                payKey: "",
                price: 0
            },
            soldOutNft: {}
        }
    },

    computed:{
        ...mapState(['userId'])
    },

    mounted(){
        this.getBalance()
    },

    methods: {
        ...mapMutations(['delToken', 'setBalance', 'setMarketNFTs']),

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
    
        handleClickMyNFT(item){
            this.$confirm('Do you want to make this NFT available for sale?', 'message', {
                confirmButtonText: 'confirm',
                cancelButtonText: 'cancel',
                type: 'warning'
            }).then(() => {
                this.$message({
                    type: 'success',
                    message: 'Successfully modified'
                })
                this.dialogFormVisible = true
                this.soldOutNft = item
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: 'Cancel modeified'
                });          
            });
        },

        async handleConfirmSoldOut(){
            this.$axios.post(this.apiUrl + '/art/setSell', {
                ownerId: this.userId,
                artId: this.soldOutNft.artId,
                price: this.form.price,
                payKey: this.form.payKey
            }).then(async () => {
                let res = await this.$axios.post(this.apiUrl+"/goods/getAllGoods", {
					start: 1,
					limit: 10
				})
				this.setMarketNFTs(res.data.data.data)
                this.dialogFormVisible = false
            })
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
    }
</style>