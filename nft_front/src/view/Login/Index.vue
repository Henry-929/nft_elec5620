<template>
	<div>

		<div>
			<el-drawer :visible.sync="showDrawer" :with-header="false" :before-close="handleClose">
				<div style="flex-direction: row;display: flex;">
					<img src="../../assets/images/logo/login_wallet.png"
						style="width: 35px;height:35px;margin-left: 10px;margin-top: 10px;">
					<div class="palette_style">My Wallet</div>
				</div>
				<hr />

				<div style="display: flex;flex-direction: column;margin-right: 32px;margin-top: 45px;">
					<img src="../../assets/images/logo/login_wallet.png"
						style="width: 75px;height:75px;margin-left: 174px;">
					<span style="margin-top: 40px;font-size: 33px;">Log in wallet</span>
					<span style="margin-top: 10px;font-size: 10px;color: #409EFF;">About to enter the decentralized network</span>
				</div>

				<div class="el-tabs__item">
					<el-tabs v-model="activeName" style="margin-top: 50px;">

						<el-tab-pane label="Login" name="login">

							<el-input placeholder="请输入账户" v-model="loginInfor.username" clearable>
							</el-input>

							<el-input placeholder="请输入密码" v-model="loginInfor.password" show-password
								style="margin-top: 10px;">
							</el-input>

							<el-button @click="handleLogin" type="primary" style="width: 220px;margin-top: 20px;margin-right: 30px;">Login
							</el-button>
						</el-tab-pane>

						<el-tab-pane label="Register" name="register">
							<el-input placeholder="请输入账户" v-model="registerInfor.register_username" clearable>
							</el-input>

							<el-input placeholder="请输入密码" v-model="registerInfor.register_password1" show-password
								style="margin-top: 8px;">
							</el-input>

							<el-input placeholder="请再次输入密码" v-model="registerInfor.register_password2" show-password
								style="margin-top: 8px;">
							</el-input>
							<el-input placeholder="请输入支付密码" v-model="registerInfor.register_payKey" show-password
								style="margin-top: 8px;">
							</el-input>

							<el-button @click="handleRegister" type="primary" style="width: 260px;margin-top: 20px;">Register
							</el-button>
						</el-tab-pane>
					</el-tabs>
				</div>
			</el-drawer>

		</div>
	</div>
</template>

<script>
import { mapMutations } from 'vuex';
export default {
	props: ['showDrawer'],
	data() {
		return {
			activeName: 'login',
			loginInfor: {
				username: '',
				password: '',
			},
			registerInfor: {
				register_username: '',
				register_password1: '',
				register_password2: '',
				register_payKey: ''

			},
		};
	},
	methods: {
		...mapMutations(['setToken', 'setUser']),

		handleClose(){
			this.$emit('showLogin', false)
		},

		handleLogin() {
			if (this.loginInfor.username === '' || this.loginInfor.password === '') {
				this.$message.error('Username and password cannot be empty !')
			} else {
				this.$axios.post(this.apiUrl+"/user/login", this.loginInfor).then(res => {
					if(res.data.message === "用户名不存在"){
						this.$message.error('The username does not exist')
					}else if(res.data.message === "密码不正确"){
						this.$message.error('Wrong account or password')
					} else {
							//close drawer
							this.$emit('showLogin', false)
							this.$message.success('Successfully logged in')
							this.setToken({
								token: res.data.data.token,
								user: res.data.data.user.userName
							})
							this.setUser({
								user: this.loginInfor.username,
								id: res.data.data.user.userId,
								balance: res.data.data.user.balance
							})
						}
					})
					.catch(function(error) {(error);})
			}

		},
		
		handleRegister() {
			if (!this.registerInfor.register_username || !this.registerInfor.register_password1 
			|| !this.registerInfor.register_password2 || !this.registerInfor.register_payKey) {
				this.$message.error('Username and password cannot be empty !')
			} else if (this.registerInfor.register_password1 != this.registerInfor.register_password2) {
				this.$message.error('The passwords entered twice are inconsistent !')
			} else {
				this.$axios.post(this.apiUrl+"/user/register", {
					username: this.registerInfor.register_username,
					password: this.registerInfor.register_password1,
					payKey: this.registerInfor.register_payKey
				}).then(res => {
						if (res.status == 200) {
							// close drawer
							this.$emit('showLogin', false)
							this.$message.success('Successfully registered')
						} else {
							this.$message.error('registered failed')
						}
					})
					.catch(function(error) {(error);});
			}

		}
	},
}
</script>

<style >
	.el-tabs__item {
		width: 80%;
		padding-right: 45px;
		margin-left: 0px;
	}

	.palette_style {
		height: 10px;
		margin-left: 10px;
		margin-top: 17px;
		font-size: 15px;

	}
</style>