<template>
	<div>
		<div class="navbar">
			<el-menu class="el-menu-demo" mode="horizontal">
				<el-row style="flex-direction: row;display: flex;">
					<div style="margin-top: 0px;">
						<el-col style="flex-direction: row;display: flex;">
							<span
								style="text-align: center;margin-top: 20px;font-size: 20px;font-weight: bold">MyNFT</span>
						</el-col>
					</div>
					<div>
						<el-col style="width: 420px;margin-left: 40px;margin-top: 6px;">
							<el-input placeholder="请在此输入" v-model="inputSearch"><i
									style="margin-top: 12px;margin-right: 10px;" class="el-icon-search" slot="suffix"
									@click="gotosearch"></i>
							</el-input>
						</el-col>
					</div>
					<el-col style="margin-left: 20px;">
						<el-menu-item index="/" background-color="#1c213f" style="font-size: 15px;">Index Page
						</el-menu-item>
					</el-col>

					<el-col>
						<el-tooltip content="Minting">
							<el-menu-item @click="createDrawerLoad()">
								<i class="el-icon-edit-outline" style="font-size: 20px;color: #000000;"></i>
							</el-menu-item>
						</el-tooltip>
					</el-col>

					<el-col>
						<el-tooltip content="Profile">
							<div v-if="token==null">
								<el-menu-item  @click="handleLogin()"><i class="el-icon-user-solid"></i></el-menu-item>
							</div>
							<div v-else>
								<el-menu-item index="/personalCenter/dynamicIssue"><i class="el-icon-user-solid"></i></el-menu-item>
							</div>
						</el-tooltip>
					</el-col>
					
					<el-col>
						<el-tooltip content="ChangeTheme">
							<el-switch class="colorButton" @change="handleColorChange" v-model="changeColor" active-text="White" inactive-text="Dark"></el-switch>
						</el-tooltip>
					</el-col>

					<div v-if="showLogin">
						<Login :showLogin="showLogin" @showDrawer="handleDrawer"></Login>
					</div>

				</el-row>
			</el-menu>
		</div>
	</div>
</template>

<script>
import Login from '../view/Login/Index.vue'
export default {
	components:{
		Login
	},
	data() {

		return {
			changeColor: true,
			inputSearch: '',
			activeIndex: '1',
			activeIndex2: '1',
			showLogin: false,
		};
	},
	methods: {
		createDrawerLoad () {
			this.showCreate = true;
			this.timerCreate = new Date().getTime()
		},

		handleLogin () {
			this.showLogin = true
		},

		handleDrawer(value){
			this.showLogin = value
		},
		
		handleColorChange(){
			if(!this.changeColor){
				document.querySelector("body").setAttribute("style", "background-color:#909399");
			}else{
				document.querySelector("body").setAttribute("style", "background-color:#ffffff");
			}
		},
	
		// 跳转到搜索页面
		gotosearch() {
			this.$router.push({
				path: '/search',
				query: {
					input: this.inputSearch
				}
			});
			//console.log(this.inputSearch);
		},


	},
}
</script>

<style>
	.navbar {
		text-align: center;
		margin-left: 5%;
		margin-right: 5%;

	}

	.navbar el-menu {
		display: inline-block;
	}

	.el-menu-item.is-active {
		background-color: rgb(255, 255, 255) !important;
	}

	.colorButton{
		position: relative;
		top: 20px;
	}
</style>
