<template>
	<div>
		<div class="navbar">
			<el-menu class="el-menu-demo" mode="horizontal">
				<el-row style="flex-direction: row;display: flex;">
					<div style="margin-top: 0px;">
						<el-col style="flex-direction: row;display: flex;">
							<span style="cursor: pointer;text-align: center;margin-top: 20px;font-size: 20px;font-weight: bold" @click="handleIndexPage">MyNFT</span>
						</el-col>
					</div>
					<div>
						<el-col style="width: 360px;margin-left: 40px;margin-top: 6px;">
							<el-input placeholder="Search here" v-model="inputSearch">
								<i
									style="margin-top: 14px;margin-right: 8px;" class="el-icon-search" slot="suffix"
									@click="handleSearch"></i>
							</el-input>
						</el-col>
					</div>
					
					<el-col style="margin-left: 20px;">
						<el-slider
							class="slider"
							v-model="ranegValue"
							range
							:marks="marks"
							:min="0"
							:max="1000"
							@change="handleSlideSearch"
						></el-slider>
					</el-col>

					<el-col>
						<el-tooltip content="Minting">
							<el-menu-item @click="handleMinting()">
								<i class="el-icon-edit-outline" style="font-size: 20px"></i>
							</el-menu-item>
						</el-tooltip>
					</el-col>

					<el-col>
						<el-tooltip content="ShoppingCart">
							<el-menu-item @click="$router.push('/shoppingCart')">
								<i class="el-icon-shopping-cart-full" style="font-size: 20px"></i>
							</el-menu-item>
						</el-tooltip>
					</el-col>

					<el-col>
						<el-tooltip content="Profile">
							<div>
								<el-menu-item  @click="handleLogin()"><i class="el-icon-user-solid"></i></el-menu-item>
							</div>
						</el-tooltip>
					</el-col>
			
					<div v-if="!token && showDrawer">
						<Login :showDrawer="showDrawer" @showLogin="handleLoginDrawer"></Login>
					</div>

					<div v-else-if="token && showDrawer">
						<Wallet :showDrawer="showDrawer" @showProfile="handleProfileDrawer"></Wallet>
					</div>

					<div v-if="token && showMinting">
						<Mint :showMinting="showMinting" @showMint="handleMintDrawer"></Mint>
					</div>

				</el-row>
			</el-menu>
		</div>
	</div>
</template>

<script>
import { mapState } from 'vuex';
import Login from '../view/Login/Index.vue'
import Wallet from '../view/Wallet/Index.vue'
import Mint from '../view/Mint/Index.vue'
export default {
	components:{
		Login,
		Wallet,
		Mint
	},
	data() {
		return {
			changeColor: true,
			inputSearch: '',
			showDrawer: false,
			showMinting: false,
			ranegValue: [0, 1000],
			marks: {
				50:'50',
				200:'200',
				400: '400',
				600: '600',
				800: '800',
				1000: '1000'
			}
		};
	},

	computed: {
		...mapState(['token'])
	},

	methods: {
		handleMinting () {
			if(!this.token){
				this.$message.error('Please login first !')
			}else{
				this.showMinting = true
			}
		},

		handleLogin () {
			this.showDrawer = true
		},

		handleLoginDrawer(value){
			this.showDrawer = value
		},

		handleProfileDrawer(value){
			this.showDrawer = value
		},

		handleMintDrawer(value){
			this.showMinting = value
		},
		
		handleColorChange(){
			if(!this.changeColor){
				document.querySelector("body").setAttribute("style", "background-color:#1E1E1E");
			}else{
				document.querySelector("body").setAttribute("style", "background-color:#ffffff");
			}
		},
	
		async handleSearch() {
			if(this.inputSearch == ''){
				this.$message.error('Please inputs something !')
				window.location.reload()
			}else{
				let res = await this.$axios.get(this.apiUrl + `/goods/search/${this.inputSearch}`)
				if(res.data.message !== '暂无内容，看看其他的吧'){
					this.$emit('searchNft', res.data.data.arts)
					this.inputSearch = ''
				}else{
					this.$emit('searchNft', false)
					this.inputSearch = ''
				}
			}
		},

		handleIndexPage(){
			if(this.$route.fullPath == "/"){
				this.$emit('searchNft', [])
			}else{
				this.$router.push('/')
			}
		},

		async handleSlideSearch(){
			let res = await this.$axios.get(this.apiUrl + `/goods/search/${this.ranegValue[1]}/${this.ranegValue[0]}`)
			this.$emit('searchNft', res.data.data)
		}
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

	.slider{
		position: relative;
		top: 10px;
	}
</style>
