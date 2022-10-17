<template>
    <div>
        <el-drawer :visible.sync="showDrawer" :with-header="false" :before-close="handleClose">
            <div style="flex-direction: row;display: flex;">
					<img src="../../assets/images/logo/login_wallet.png"
						style="width: 35px;height:35px;margin-left: 10px;margin-top: 10px;">
					<div class="palette_style">My Wallet</div>
				</div>
			<hr />
            wallet balance: $000
        </el-drawer>
    </div>
</template>

<script>
export default {
    props: ['showDrawer'],
    data() {
        return {
            
        }
    },
    methods: {
        handleClose(){
			this.showDrawer = false
			this.$emit('showProfile', this.showDrawer)
		},
    },
}
</script>