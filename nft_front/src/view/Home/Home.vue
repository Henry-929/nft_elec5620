<template>
    <div>
        <!-- top -->
        <Top />

        <!-- center -->
        <div style="margin-left: 5%;margin-right: 5%;">
            <el-row>
                <el-col :span="12">
                    <el-carousel class="elcarousel1" indicator-position="none">
                        <el-carousel-item class="image" v-for="item in carouselImages1" :key="item.id">
                            <img :src="item.url" width="100%" height="100%" alt="无图片" />
                        </el-carousel-item>
                    </el-carousel>

                </el-col>


                <el-col :span="8" :offset="1">
                    <div class="block">
                        <el-carousel class="elcarousel2" height="150px">
                            <el-carousel-item v-for="item in carouselImages2" :key="item.id">
                                <img :src="item.url" width="100%" height="100%"/>
                            </el-carousel-item>
                        </el-carousel>
                    </div>

                    <el-button class="mintButton" type="primary" @click="handleMinting">Create</el-button>

                    <el-button class="shoppingCart" type="success" @click="handleShoppingCart">Shopping Cart</el-button>
                </el-col>
            </el-row>
        </div>
    </div>
</template>

<script>
import Top from '../../components/Top.vue'
export default {
    components: {
        Top
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
}
</script>

<style lang="less" scoped>
	.mintButton, .shoppingCart {
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
		height: 150px;
	}
</style>