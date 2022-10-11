<template>
	<div>

		<div>
			<el-drawer title="login_register" :visible.sync="showLogin" :with-header="false" :before-close="handleClose">
				<div style="flex-direction: row;display: flex;">
					<img src="../../assets/images/logo/login_wallet.png"
						style="width: 35px;height:35px;margin-left: 10px;margin-top: 10px;">
					<div class="palette_style">My Wallet</div>
				</div>
				<hr style="margin-top: 19px;
							border: 0;
							height: 0;
							border-top: 1px solid rgba(0, 0, 0, 0.1);
							border-bottom: 1px solid rgba(255, 255, 255, 0.3);
					" />

				<div style="display: flex;flex-direction: column;margin-right: 30px;margin-top: 40px;">
					<img src="../../assets/images/logo/login_wallet.png"
						style="width: 70px;height:70px;margin-left: 135px;">
					<span style="margin-top: 30px;font-size: 33px;">登录钱包</span>
					<span style="margin-top: 5px;font-size: 10px;color: #409EFF;">您即将进入去中心化网络</span>
				</div>

				<div class="el-tabs__item">
					<el-tabs v-model="activeName" @tab-click="LoginandleClick" style="margin-top: 60px;">

						<el-tab-pane label="账号登录" name="login">

							<el-input placeholder="请输入账户" v-model="loginForm.username" clearable>
							</el-input>

							<el-input placeholder="请输入密码" v-model="loginForm.password" show-password
								style="margin-top: 10px;">
							</el-input>

							<el-button type="text" style="margin-left: 170px;">忘记密码？</el-button>

							<el-button @click="login" type="primary" style="width: 220px;margin-top: 20px;margin-right: 30px;">登录
							</el-button>
						</el-tab-pane>

						<el-tab-pane label="注  册" name="register">
							<el-input placeholder="请输入账户" v-model="reginForm.rgin_username" clearable>
							</el-input>

							<el-input placeholder="请输入密码" v-model="reginForm.regin_password" show-password
								style="margin-top: 10px;">
							</el-input>

							<el-input placeholder="请再次输入密码" v-model="reginForm.regin_password2" show-password
								style="margin-top: 10px;">
							</el-input>
							<el-input placeholder="请输入支付密码" v-model="reginForm.regin_payKey" show-password
								style="margin-top: 10px;">
							</el-input>


							<el-button @click="register" type="primary" style="width: 260px;margin-top: 20px;">注册
							</el-button>
						</el-tab-pane>
					</el-tabs>
				</div>
			</el-drawer>

		</div>
	</div>
</template>

<script>
	import {
		mapMutations
	} from 'vuex';
	export default {
		name: "managerLogin",
        props: ['showLogin'],
		data() {
			return {
				activeName: 'login',
				//账户密码,
				// flag:false,
				loginForm: {
					username: '',
					password: '',

				},

				reginForm: {
					rgin_username: '',
					regin_password: '',
					regin_password2: '',
					regin_payKey: ''

				},
				//抽屉默认关闭
				drawer: false,

				// activeName: 'second',
				// logourl: require("../assets/Images/logo/logo1.png"),
				// activeIndex: '1',
				// activeIndex2: '1'
			};
		},
		methods: {
			...mapMutations(['setToken']),

			handleSelect(key, keyPath) {
				console.log(key, keyPath);
			},

			LoginandleClick() {

			},

            handleClose(){
                this.showLogin = false
                this.$emit('showDrawer', this.showLogin)
            },

			//登陆事件
			login() {
				var that = this
				if (this.loginForm.username === '' || this.loginForm.password === '') {
					this.$message({
						type: 'info',
						message: '账号或密码不能为空'
					});
				} else {
					this.$axios.post(
							this.apiUrl+"/palette/login/login", {
								account: this.loginForm.username,
								password: this.loginForm.password,

							}
						)
						.then(response => {
							if (response.data.code == 200) {
								console.log(response.data);
								//关闭抽屉
								this.drawer = !this.drawer;
								this.$message({
									type: 'success',
									message: '登录成功!',
								
								});
								this.setToken({
									token: response.data.data.token
								}); //store中的为token赋值方法
								//打印token
								//console.log(response.data.data.token);
								//	console.log(this.setToken().token.);

								// console.log(localStoragegetItem('token'));
								//跳转到首页
								location.reload();
								this.$router.push('/');
							} else {
								console.log(response.data);
								this.$message({
									type: 'info',
									message: '账号密码错误'
								});
							}
						})
						.catch(function(error) {});
				}

			},
			//注册
			register() {
				var that = this
				console.log(this.reginForm.regin_payKey);
				if (this.reginForm.rgin_username === '' || this.reginForm.regin_password === '') {
					this.$message({
						type: 'info',
						message: '账号或密码不能为空'
					});
				} else if (this.reginForm.regin_password != this.reginForm.regin_password2) {
					this.$message({
						type: 'info',
						message: '两次密码不一致'
					});
				} else {
					this.$axios.post(
							this.apiUrl+"/palette/login/registered", {
								account: this.reginForm.rgin_username,
								password: this.reginForm.regin_password,
								payKey: this.reginForm.regin_payKey
							}
						)
						.then(response => {
							if (response.data.code == 200) {
								console.log(response.data);
								//关闭抽屉
								this.drawer = !this.drawer;
								this.$message({
									type: 'info',
									message: '注册成功'
								});
							} else {
								console.log(response.data);
								this.$message({
									type: 'info',
									message: '注册失败，账号已存在'
								});
							}
						})
						.catch(function(error) {});
				}

			}
		},

		created() {

		}
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