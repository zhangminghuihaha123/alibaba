<template>
    <div class="wrapper">
        <div style="margin: 200px auto;width: 500px;height: 500px;border-radius: 10px;background-color: #ececec;">
            <div style="margin-top: 200px;background-color: #fff;width: 450px;height: 500px;padding: 20px;border-radius: 10px">
                <p style="text-align: center;font-size: 32px;font-weight: bold">注册</p>
                <el-form v-model="user">
                    <el-form-item prop="username">
                        <el-input size="medium" style="width: 70%" placeholder="请输入账号" class="mt-40" prefix-icon="el-icon-user" v-model="user.username"></el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input size="medium" style="width: 70%"  placeholder="请输入密码" class="mt-40" show-password prefix-icon="el-icon-lock" v-model="user.password"></el-input>
                    </el-form-item>
                    <el-form-item prop="passwords">
                        <el-input size="medium" style="width: 70%"  placeholder="请再次输入密码" class="mt-40" prefix-icon="el-icon-lock" v-model="user.passwords"></el-input>
                    </el-form-item>
                    <el-form-item style="text-align: center;margin-top: 50px">
                        <el-button type="danger" icon="el-icon-caret-right" style="width: 120px;height: 120px;font-size: 24px" circle @click="zc()">注册</el-button>
                    </el-form-item>
                </el-form>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Resport",
        data(){
            return{
                user: {}
            }
        },
        methods: {
            zc(){
                if(this.user.username !=null && this.user.password !=null && this.user.passwords !=null && this.user.username !== '' && this.user.password !== '' && this.user.passwords !== ''){
                    if(this.user.password !== this.user.passwords){
                        this.$message.error("两次密码不一致!");
                    }else {
                        this.request.post('/user/resport',this.user).then(res => {
                            if(res.code === '200'){
                                this.$message.success(res.msg);
                                this.$router.push('/login');
                            }else {
                                this.$message.error(res.msg)
                            }
                        })
                    }
                }else {
                    this.$message.error("下面栏位不能没有未填项！");
                }
            }
        }
    }
</script>

<style>
    .wrapper{
        height: 100vh;
        background-image: linear-gradient(to bottom right, #ff0000, #005eff);
        overflow: hidden;
    }
</style>
