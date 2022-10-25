<template>
    <div>
        <Top></Top>
        <div class="selectNFT">
            <img :src="require(`../../../../nft_back/nft/img${selectNFT.file.filePath}`)" alt="selectPhoneItem">
            <p>Art Name: {{selectNFT.art.artName}}</p>
            <p>ArtAurthor: {{selectNFT.art.artAuthor}}</p>
            <p>Price: {{selectNFT.resalePrice}} ETH</p>
            <p>Art introduction: {{selectNFT.art.artIntroduction}}</p>
            <el-button type="danger" class="addToCart" :disabled="selectNFT.art.artAuthor == user ? true : false" plain @click="handleAddCart">Add to Cart</el-button>
        </div>
    </div>
</template>

<script>
import { mapMutations, mapState } from 'vuex';
import Top from '../../components/Top.vue'
export default {
    components: {
        Top
    },
    data() {
        return {
            
        }
    },

    mounted(){
        if(!this.selectNFT.art){
            this.$router.push('/home')
        }
    },

    computed: {
        ...mapState(['selectNFT', 'user', 'shoppingCart'])
    },

    methods: {
        ...mapMutations(['addToCart']),
        handleAddCart(){
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
                this.$set(this.selectNFT, 'artName', this.selectNFT.art.artName)
                this.addToCart(this.selectNFT)
                this.$message.success('add successfully')  
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
    }

    .selectNFT img{
        width: 400px;
        height: 400px;
        float: left;
        margin-right: 30px;
    }

    .selectNFT p{
        font-size: 25px;
        position: relative;
        text-align: left;
        margin-top: 20px;
        
    }
</style>