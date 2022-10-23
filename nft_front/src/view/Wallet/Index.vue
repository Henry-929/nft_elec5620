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
                    <p>10 ETH</p>
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
                    
                   
                    <div v-for="item in myNftList" :key="item.artId" class="myNft">
                        <img :src="require(`../../../../nft_back/nft/picture${item.showFile.filePath}`)" alt="">
                        <span>{{ item.artName }}</span>
                    </div>
                    
				</div>
			<hr />
        </el-drawer>
        </div>
    </div>
</template>

<script>
import { mapMutations, mapState } from 'vuex'
export default {
    props: ['showDrawer'],
    data() {
        return {
            myNftShow: false,
            myNftList: []
        }
    },

    computed:{
        ...mapState(['userId'])
    },

    methods: {
        ...mapMutations(['delToken']),
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
            console.log(this.myNftList)
        },
    
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
        img{
            width: 40px;
            height: 40px;
        }
        span{
            font-size: 16px;
        }
    }
</style>