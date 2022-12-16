<template>
    <div class="wrapper">
        <div style="margin: 200px auto;width: 500px;height: 500px;border-radius: 10px;background-color: #ececec;">
            <div style="margin-top: 200px;background-color: #fff;width: 450px;height: 500px;padding: 20px;border-radius: 10px">
                <p style="text-align: center;font-size: 32px;font-weight: bold">登录</p>
                <el-form v-model="user">
                    <el-form-item prop="username">
                        <el-input size="medium" style="width: 70%" placeholder="请输入账号" class="mt-50" prefix-icon="el-icon-user" v-model="user.username"></el-input>
                    </el-form-item>
                    <el-form-item prop="password">
                        <el-input size="medium" style="width: 70%"  placeholder="请输入密码" class="mt-50" show-password prefix-icon="el-icon-lock" v-model="user.password"></el-input>
                    </el-form-item>
                    <el-form-item style="text-align: center;margin-top: 30px">
                        <el-button type="success" icon="el-icon-caret-right" style="width: 120px;height: 120px;font-size: 24px" circle @click="dl()">登录</el-button>
                    </el-form-item>
                </el-form>
                <div style="margin-top: 60px;text-align: center">
                    <router-link to="/resport" >没有账号，前往注册</router-link>
                </div>
            </div>
        </div>
    </div>
</template>

<script type="text/javascript" src="https://api.map.baidu.com/api?v=1.0&type=webgl&ak=ns2haiFU7m2gny12BAFH8pjnWLmvxyUG"></script>

<script>
    export default {
        name: "Login",
        data(){
            return{
                user: {}
            }
        },
        created() {
            //百度地图API
            var geolocation = new BMapGL.Geolocation();
            geolocation.getCurrentPosition(function (r) {
                if(this.getStatus()== BMAP_STATUS_SUCCESS){
                    const province =r.address.province;
                    const city=r.address.city;
                    sessionStorage.setItem("province",province);
                    sessionStorage.setItem("city",city);
                }
            })
        },
        methods: {
            dl(){
                let username=this.user.username;
                let password=this.user.password;
                if(username != null && password != null && username !=='' && password !== ''){
                    this.request.post('/user/login',this.user).then(res =>{
                        if(res.code !== '200'){
                            this.$message.error(res.msg);
                        }else {
                            if((res.data.role !== '管理员') && (res.data.role !== 'vip')){
                                res.data.token=null;
                                window.localStorage.setItem("user",JSON.stringify(res.data))
                                this.$router.push('/resport');
                            }else {
                                window.localStorage.setItem("user",JSON.stringify(res.data))
                                this.$router.push('/');
                            }
                        }
                    })
                }else {
                    this.$message.error("用户名或密码为空");
                }
            }
        }
    }
</script>

<style>
    .wrapper{
        height: 100vh;
        background-image: linear-gradient(to bottom right, #b0fc46, #973ffb);
        overflow: hidden;
    }
</style>