<template>
    <div>
        <el-button type="primary" @click="drawer=true">打开</el-button>
        <el-drawer title="即刻创作" :visible.sync="drawer" direction="rtl" :before-close="handleClose" size="370px">
            <el-form size="small">
                <el-form-item label="作品名称:" label-width="100px">
                    <el-input clearable style="width:250px" v-model="form.workName" placeholder="请输入作品名称"></el-input>
                </el-form-item>
                <el-form-item label="作者名字:" label-width="100px">
                    <el-input clearable style="width:250px" v-model="form.authorName" placeholder="请输入作者名字"></el-input>
                </el-form-item>
                <el-form-item label="所属领域:" label-width="100px">
                    <el-select clearable style="width:250px" v-model="form.region" placeholder="请选择所属领域">
                        <el-option label="区域一" value="shanghai"></el-option>
                        <el-option label="区域二" value="beijing"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="作品简介:" label-width="100px">
                    <el-input clearable style="width:250px" type="textarea" v-model="form.worksDescribe" maxlength="150"
                        placeholder="请输入少于150字">
                    </el-input>
                </el-form-item>
                <el-form-item label="交易密码:" label-width="100px">
                    <el-input clearable style="width:250px" v-model="form.password" placeholder="请输入交易密码"></el-input>
                </el-form-item>
                <div style="display:flex;justify-content:center;align-items: flex-start;">
                    <el-upload style="margin-right:50px" 
                        action="#"
                        class="upload-demo" 
                        :auto-upload="false"
                        :on-change="handleChange" 
                        :file-list="fileList"
                        >
                        <el-button type="primary" size="small">选取文件</el-button>
                    </el-upload>
                    <el-button type="success" @click="confirmWork" size="small">上传作品</el-button>
                </div>
                  
            </el-form>
        </el-drawer>
    </div>
</template>

<script >
export default {
    data() {
        return {
            drawer: false,
            form: { workName: '', authorName: '', region: '', worksDescribe: '', password: '' },
            fileList:[]
        }
    },
    methods: {
        handleClose(done) {
            this.$confirm('确认关闭？')
                .then(_ => {
                    done();
                })
                .catch(_ => { });
        },
        handleChange(file){
            if(this.fileList.length){
                 this.$message.warning("只能上传一个文件")
            }
            this.fileList.push({name:file.name})
            if(this.fileList.length == 2){
                this.fileList.pop()
            }
        },
          confirmWork(){
            if(Object.values(this.form).some(item=>!item) ||this.fileList.length == 0){
                return this.$message.warning('请输入全部信息')
            }
          },
    }
}

</script>
<style scoped lang="less">
    /deep/ .el-upload-list{
        position: absolute;
        .el-upload-list__item{
            max-width: 260px;
        }
    }
</style>