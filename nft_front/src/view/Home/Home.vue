<template>
    <div>
        <!-- top -->
        <Top @searchNft="handleSearchNft" />

        <!-- center -->
        <div style="margin-left: 8%;margin-right: 8%;">
            <el-row>
                <el-col :span="12">
                    <el-carousel class="elcarousel1" indicator-position="none">
                        <el-carousel-item class="image" v-for="item in carouselImages1" :key="item.id">
                            <img :src="item.url" width="100%" height="100%" />
                        </el-carousel-item>
                    </el-carousel>

                </el-col>


                <el-col :span="8" :offset="1">
                    <div class="block">
                        <el-carousel class="elcarousel2" height="230px">
                            <el-carousel-item v-for="item in carouselImages2" :key="item.id">
                                <img :src="item.url" width="100%" height="100%"/>
                            </el-carousel-item>
                        </el-carousel>
                    </div>


                    <el-button class="shoppingCart" type="success" @click="handleShoppingCart">Shopping Cart</el-button>
                </el-col>
            </el-row>
			<p style="font-size: 20px;font-weight: bold;margin-left: 2%;">Market</p>
			<div v-if="marketNFTs.length || searchedMarketNFTs.length">
				<template v-if="searchedMarketNFTs.length">
					<div v-for="item in searchedMarketNFTs" :key="item.goodsId" class="NftGoods" @click="handleGoodsDetail(item)">
						<el-col :span="6" style="margin-left: 3%;">
							<el-card :body-style="{ padding: '15px' }" style="height: 380px;margin-bottom: 20px;">
								<div class="marketNFT">
									<img style="height: 100%;width: 100%;" :src="require(`../../../../nft_back/nft/img${item.file.filePath}`)" alt="">
								</div>
								<h4>{{item.art.artName}}</h4>
								<p style="font-size: 14px;font-weight:200;">intro:{{item.art.artIntroduction}}</p>
							</el-card>
						</el-col>
					</div>
				</template>
				<template v-else>
					<div v-for="item in marketNFTs" :key="item.goodsId" class="NftGoods" @click="handleGoodsDetail(item)">
						<el-col :span="6" style="margin-left: 3%;">
							<el-card :body-style="{ padding: '15px' }" style="height: 380px;margin-bottom: 20px;">
								<div class="marketNFT">
									<img :src="require(`../../../../nft_back/nft/img${item.file.filePath}`)" alt="">
								</div>
								<h4>{{item.art.artName}}</h4>
								<p style="font-size: 14px">{{item.art.artIntroduction}}</p>
							</el-card>
						</el-col>
					</div>
				</template>
			</div>
			<div v-else>
				<p style="font-size: 20px">There is no suitable item at the moment</p>
			</div>
        </div>
    </div>
</template>

<script>
import { mapMutations, mapState } from 'vuex'
import Top from '../../components/Top.vue'
export default {
    components: {
        Top,
    },
    data() {
        return {
            carouselImages1: [{
						id: 1,
						url: require("../../assets/images/carousel1.png")
					},
					{
						id: 2,
						url: require("../../assets/images/carousel2.png")
					},
					{
						id: 3,
						url: require("../../assets/images/carousel3.png")
					}

				],
            carouselImages2: [{
                    id: 1,
                    url: require("../../assets/images/carousel4.png")
                },
                {
                    id: 2,
                    url: require("../../assets/images/carousel5.png")
                }
            ],
		
        }
    },

	mounted() {
		this.getAllNfts()
	},

	computed:{
		...mapState(['marketNFTs', 'searchedMarketNFTs'])
	},	

	methods: {
			...mapMutations(['setSelectedNFT', 'setMarketNFTs', 'setSearchedMarketNFTs']),
			async getAllNfts(){
				let res = await this.$axios.post(this.apiUrl+"/goods/getAllGoods", {
					start: 1,
					limit: 10
				})
				if(res.data.message !== "暂无内容，看看其他的吧"){
					this.setMarketNFTs(res.data.data.data)
				}
			},

			handleSearchNft(value){
				this.setSearchedMarketNFTs(value)
			},
			
			handleGoodsDetail(item){
				this.$router.push(`/detail?id=${item.goodsId}`)
				this.setSelectedNFT(item)
			},

			handleMinting(){
				
			},

			handleShoppingCart(){
				this.$router.push('/shoppingCart')
			}
	}
}
</script>

<style lang="less" scoped>
	.shoppingCart {
		font-size: 20px;
		width: 100%;
		height: 65px;
		border-radius: 5px;
		margin-top: 10px;
	}

	

	#inputSearch {
		height: 45px;
		width: 95%;
		border-radius: 10px;
		margin-left: 2.5%;
		margin-top: 10px;
	}

	#inputSearchButton {
		margin-left: 90%;
		margin-top: 8px;
	}

	.el-carousel__item h3 {
		color: #475669;
		font-size: 18px;
		opacity: 0.75;
		line-height: 300px;
		margin: 0;
	}

	.el-carousel__item:nth-child(2n) {
		background-color: #99a9bf;
	}

	.el-carousel__item:nth-child(2n+1) {
		background-color: #d3dce6;
	}

	.el-button--danger {
		color: #00ff00;
		background-color: #FC813B;
		border-color: #FC813B;
	}

	.elcarousel1 {
		height: 300px;
		width: 100%;
		margin-top: 10px;
		margin-left: 20px;
		/* margin-right: 450px; */
		border-radius: 5px;
	}

	.elcarousel2 {
		margin-top: 10px;
		border-radius: 5px;
	}

	.nftImages{
		width: 200px;
		height: 200px;
		margin-left: 8%;
		cursor: pointer;
	}

	.NftGoods{
		cursor: pointer;
	}
	
	.marketNFT > img{
		width: 268px;
		height: 193px;
	}
</style>