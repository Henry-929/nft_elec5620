<template>
    <div>
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
                    @click="handleLoginOut" 
                    class="loginOutButton" type="primary" 
                    style="width: 150px;margin-top: 20px;margin-right: 30px;"
                    >
                        Log out
			        </el-button>
                </div>
            </div>
        </el-drawer>
    </div>
</template>

<script>
import { mapMutations } from 'vuex'
export default {
    props: ['showDrawer'],
    data() {
        return {
            
        }
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
</style>