<template>
        <el-container style="height: 100%">
            <el-aside :width="sideWidth" style="background-color: rgb(0,94,255);height: 100%;">
                <Aside/>
            </el-aside>
            <el-container>
                <el-header style="border-bottom: 1px solid black;">
                    <Header/>
                </el-header>
                <el-main>
                    <router-view/>
                </el-main>
            </el-container>
        </el-container>
</template>

<script>
    import Aside from "@/components/Aside";
    import Header from "@/components/Header";

    export default {
        name: "Manger",
        components: {Aside,Header},
        data(){
            return{
                sideWidth: 200+'px'
            }
        }
    }
</script>

<style scoped>

</style>