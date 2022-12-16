<template>
    <div style="font-size: 12px;line-height: 60px;display: flex">
        <div style="flex: 1;">
            <span style="cursor: pointer;font-size: 18px"></span>
            <el-breadcrumb separator="/" style="display: inline-block;margin-left: 10px">
                <el-breadcrumb-item :to="'/'">首页</el-breadcrumb-item>
                <el-breadcrumb-item>{{currentPathName}}</el-breadcrumb-item>
            </el-breadcrumb>
        </div>
        <el-dropdown style="width: 150px; cursor: pointer;">
            <div style="display: inline-block">
                <img :src="user.avater" alt="" style="width: 50px; height: 60%;border-radius: 50%;position: absolute;top: 10px;margin-left: 10px">
                <span style="font-size: 18px;position: relative;margin-left: 80px">{{user.username}}</span>
                <i class="el-icon-caret-bottom" style="position: absolute;left: 90%;top: 42%"></i>
            </div>
            <el-dropdown-menu slot="dropdown" style="width: 100px;text-align: center">
                <el-dropdown-item style="font-size: 14px;padding: 5px 0">
                    <router-link to="/people">个人信息</router-link>
                </el-dropdown-item>
                <el-dropdown-item style="font-size: 14px;padding: 5px 0">
                    <span style="text-decoration: none" @click="unlog">退出</span>
                </el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
</template>

<script>
    export default {
        name: "Header",
        data(){
            return{
                user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
                currentPathName: '测试'
            }
        },
        methods: {
            unlog(){
                this.$router.push('/login');
                localStorage.removeItem("user");
                sessionStorage.removeItem("city");
                sessionStorage.removeItem("province");
            }
        }
    }
</script>

<style scoped>

</style>