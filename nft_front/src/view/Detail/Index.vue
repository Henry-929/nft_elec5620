<template>
    <div>
        <Top></Top>
        <div class="selectNFT" v-if="selectNFT.art.artName !== 'bigwhite'">
            <img id="selectedImg" :src="require(`../../../../nft_back/nft/img${selectNFT.file.filePath}`)" alt="selectPhoneItem">
            <p>Art Name: {{selectNFT.art.artName}}</p>
            <p>ArtAurthor: {{selectNFT.art.artAuthor}}</p>
            <p>Price: {{selectNFT.resalePrice}} ETH</p>
            <p>Art introduction: {{selectNFT.art.artIntroduction}}</p>
            <el-button type="danger" class="addToCart" plain @click="handleAddCart">Add to Cart</el-button>
        </div>
        <div class="selectNFT" v-else>
            <model-obj :lights="lights" :rotation="rotation" @on-load="onLoad" style="width: 400px; height: 400px"
                src="static/BigWhite.obj" :backgroundColor="bgColor"
                mtl="static/BigWhite.mtl">
			</model-obj>
            <div class="canvasDescription">
                <p>Art Name: {{selectNFT.art.artName}}</p>
                <p>ArtAurthor: {{selectNFT.art.artAuthor}}</p>
                <p>Price: {{selectNFT.resalePrice}} ETH</p>
                <p>Art introduction: {{selectNFT.art.artIntroduction}}</p>
                <el-button type="danger" class="addToCart" plain @click="handleAddCart">Add to Cart</el-button>
            </div>
        </div>
    </div>
</template>

<script>
import { mapMutations, mapState } from 'vuex';
import Top from '../../components/Top.vue'
import { ModelObj } from "vue-3d-model";
export default {
    components: {
        Top,
        ModelObj
    },
    data() {
        return {
            bgColor: "#efefef",
            objWidth: 300,
            objHeight: 300,
            rotation: {
					x: 0,
					y: 0,
					z: 0
				},
				lights: [{
						type: 'HemisphereLight',
						position: {
							x: 0,
							y: 1,
							z: 0
						},
						skyColor: 0xffffff,
						groundColor: 0x808080, 
						intensity: 1,
					},
					{
						type: 'DirectionalLight',
						position: {
							x: 1,
							y: 1,
							z: 1
						},
						color: 0xffffff,
						intensity: .2,
					}
				],
        }
    },

    mounted(){
        if(!this.selectNFT.art){
            this.$router.push('/home')
        }
        if(this.selectNFT.art.artName == "bigwhite"){
            this.getBigWhiteModel()
        }
    },

    computed: {
        ...mapState(['selectNFT', 'user', 'shoppingCart', 'userId'])
    },

    methods: {
        ...mapMutations(['addToCart']),

        onLoad() {
			this.rotate();
        },
        rotate() {
            this.rotation.y += 0.003;
            requestAnimationFrame(this.rotate);
        },

        getBigWhiteModel(){
            console.log(this.selectNFT);
            
        },

        async handleAddCart(){
            for(let i = 0; i < this.shoppingCart.length; i++){
                if(this.shoppingCart[i].art.artName === this.selectNFT.art.artName){
                    this.$message.error('The item already exists in your shopping cart')
                    return
                }
            }
            if(!this.user){
                this.$router.push('/')
                this.$message.error('Please login first')
            }else{
                let res = await this.$axios.post(this.apiUrl + '/shopping-car/setShoppingCar', {
                    userId: this.userId,
                    goodsId: this.selectNFT.goodsId
                })
                if(res.data.message == "无法添加自己出售的商品到购物车"){
                    this.$message.error('You cannot add your own items to the shopping cart')
                    return
                }else{
                    this.$set(this.selectNFT, 'artName', this.selectNFT.art.artName)
                    this.addToCart(this.selectNFT)
                    this.$message.success('add successfully')  
                }
            }
        }
    }
}
</script>

<style lang="less" scoped>
    .selectNFT{
        width: 800px;
        height: 400px;
        margin: 88px auto 0 auto;
        position: relative;
        img{
            width: 400px;
            height: 400px;
            float: left;
            margin-right: 30px;
        }
        p{  
            font-size: 25px;
            position: relative;
            text-align: left;
            margin-top: 20px;
            
        }
        .canvasDescription{
            position: absolute;
            left: 420px;
            top: 0px;
        }
    }
</style>