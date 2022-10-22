<template>
    <div>
        <el-drawer :visible.sync="showMinting" :with-header="false" :before-close="handleClose">
			<div style="flex-direction: row;display: flex;margin-top: 5px;">
				<img src="../../assets/images/logo/p.png"
					style="width: 35px;height:35px;margin-left: 10px;margin-top: 10px;">
				<div class="palette_style">Mint</div>
			</div>
			<hr style="margin-top:14px"/>
			<div style="margin-left: 12px;">
				<el-form class="form" ref="form" :model="uploadform" label-width="100px">
					<el-form-item label="Author:">
						<el-input style="width: 250px;" v-model="user" :placeholder="user" disabled></el-input>
					</el-form-item>

					<el-form-item label="art name:">
						<el-input style="width: 250px;" v-model="uploadform.name" placeholder="Please input art name"></el-input>
					</el-form-item>

					<el-form-item label="Description:" class="jianjie" style="margin-top: 30px;">
						<el-input :rows="6" style="width: 250px" type="textarea" placeholder="Write something..." maxlength="150"
							show-word-limit v-model="uploadform.artIntroduction"></el-input>
					</el-form-item>

					<el-upload class="upload-demo" ref="upload" action :http-request="httpRequest"
					:limit="1">
						<el-button slot="trigger" size="small" type="primary">Select file</el-button>

						<el-button style="margin-left: 80px;" size="small" type="success" @click="uploadFile">
							Upload
						</el-button>
					</el-upload>

				</el-form>
			</div>
		</el-drawer>
    </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
    props: ['showMinting'],
    data() {
        return {
            uploadform: {
                name: '',
                artIntroduction: '',
            },
			fileList: []
        }
    },

	computed: {
		...mapState(['user', 'userId'])
	},

	methods: {
		handleClose(){
			this.$emit('showMint', false)
		},

		httpRequest(option) {
			this.fileList.push(option)
		},

		async uploadFile(){
			console.log(this.fileList[0]);
			console.log(this.uploadform.name);
			console.log(this.user);
			console.log(this.uploadform.artIntroduction);
			console.log(this.userId);
			if(!this.uploadform.name || !this.uploadform.artIntroduction || !this.fileList.length){
				this.$message.error('Please complete the information')
			}else{
				let res = await this.$axios.post(this.apiUrl + '/art/uploadArt', {
				file: this.fileList[0],
				artName: this.uploadform.name,
				artAuthor: this.user,
				artIntroduction: this.uploadform.artIntroduction,
				userId: this.userId
			})
			console.log(res)
			}
		},
	}
}
</script>

<style lang="less" scoped>

</style>